package it.niccolo.catalogo.framework.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.niccolo.catalogo.framework.domain.CatalogAuthor;
import it.niccolo.catalogo.framework.domain.CatalogMedia;
import it.niccolo.catalogo.framework.domain.CatalogProfile;
import it.niccolo.catalogo.framework.domain.CatalogSection;
import it.niccolo.catalogo.framework.persistence.memory.InMemoryCatalogAuthorRepository;
import it.niccolo.catalogo.framework.persistence.memory.InMemoryCatalogMediaRepository;
import it.niccolo.catalogo.framework.persistence.memory.InMemoryCatalogProfileRepository;
import it.niccolo.catalogo.framework.persistence.memory.InMemoryCatalogSectionRepository;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CatalogServiceTest {

    @Test
    @DisplayName("Il catalogo è condiviso mentre le preferenze restano per profilo")
    void catalogoCondivisoPreferenzeIsolatePerProfilo() {
        TestContext context = new TestContext();
        CatalogProfile first = context.service.createProfile("Profilo A");
        CatalogProfile second = context.service.createProfile("Profilo B");
        CatalogAuthor author = context.service.addAuthor(CatalogAuthor.create("Autore"));
        CatalogMedia item = context.service.addMedia(
                CatalogMedia.create(Path.of("media.bin"), "Elemento", author.id()));

        context.service.preferMedia(first.id(), item.id());
        context.service.preferAuthor(first.id(), author.id());
        context.service.preferMedia(second.id(), item.id());

        assertEquals(List.of(item), context.service.media());
        assertEquals(List.of(author), context.service.authors());
        assertTrue(context.service.profiles().get(0).preferredMediaIds().contains(item.id()));
        assertFalse(context.service.profiles().get(1).preferredAuthorIds().contains(author.id()));
        assertEquals(List.of(item), context.service.mediaByAuthor(author.id()));

        context.service.unpreferMedia(first.id(), item.id());
        context.service.unpreferAuthor(first.id(), author.id());
        assertTrue(context.service.profiles().get(0).preferredMediaIds().isEmpty());
        assertTrue(context.service.profiles().get(0).preferredAuthorIds().isEmpty());
        assertTrue(context.service.profiles().get(1).preferredMediaIds().contains(item.id()));
    }

    @Test
    @DisplayName("Eliminare un profilo conserva il catalogo e le preferenze degli altri")
    void eliminazioneProfiloNonEliminaContenutiCondivisi() {
        TestContext context = new TestContext();
        CatalogProfile removed = context.service.createProfile("Da eliminare");
        CatalogProfile retained = context.service.createProfile("Da conservare");
        CatalogAuthor author = context.service.addAuthor(CatalogAuthor.create("Autore"));
        CatalogSection section = context.addSection("Sezione");
        CatalogMedia item = context.service.addMedia(new CatalogMedia(UUID.randomUUID(),
                Path.of("media.bin"), "Elemento", null, null, author.id(), Set.of(section.id())));
        context.service.preferMedia(removed.id(), item.id());
        CatalogProfile updatedRetained = context.service.preferAuthor(retained.id(), author.id());

        context.service.deleteProfile(removed.id());

        assertTrue(context.service.profiles().stream().noneMatch(p -> p.id().equals(removed.id())));
        assertEquals(Set.of(author.id()), context.service.profiles().getFirst().preferredAuthorIds());
        assertEquals(List.of(item), context.service.media());
        assertEquals(List.of(author), context.service.authors());
        assertEquals(List.of(section), context.service.sections());
        assertEquals(updatedRetained.id(), context.service.profiles().getFirst().id());
    }

    @Test
    @DisplayName("Le associazioni sezione-media sono modificabili e interrogabili in entrambi i versi")
    void associazioniMediaSezioniRestanoCoerenti() {
        TestContext context = new TestContext();
        CatalogAuthor author = context.service.addAuthor(CatalogAuthor.create("Autore"));
        CatalogSection first = context.addSection("Sezione A");
        CatalogSection second = context.addSection("Sezione B");
        CatalogMedia item = context.service.addMedia(
                CatalogMedia.create(Path.of("media.bin"), "Elemento", author.id()));

        context.service.addMediaToSection(item.id(), first.id());
        CatalogMedia associated = context.service.addMediaToSection(item.id(), second.id());

        assertEquals(Set.of(first.id(), second.id()), associated.sectionIds());
        assertEquals(List.of(associated), context.service.mediaBySection(first.id()));
        assertEquals(List.of(associated), context.service.mediaBySection(second.id()));

        CatalogMedia withoutFirst = context.service.removeMediaFromSection(item.id(), first.id());
        assertEquals(Set.of(second.id()), withoutFirst.sectionIds());
        assertTrue(context.service.mediaBySection(first.id()).isEmpty());
        assertEquals(List.of(first, second), context.service.sections());
    }

    @Test
    @DisplayName("Il servizio rifiuta un media con autore o sezione inesistenti senza salvarlo")
    void mediaConRiferimentiMancantiNonVieneSalvato() {
        TestContext context = new TestContext();
        CatalogAuthor author = context.service.addAuthor(CatalogAuthor.create("Autore"));
        UUID missingSection = UUID.randomUUID();
        CatalogMedia invalidAuthor = CatalogMedia.create(Path.of("one.bin"), "Senza autore registrato",
                UUID.randomUUID());
        CatalogMedia invalidSection = new CatalogMedia(UUID.randomUUID(), Path.of("two.bin"),
                "Senza sezione registrata", null, null, author.id(), Set.of(missingSection));

        assertThrows(CatalogEntityNotFoundException.class, () -> context.service.addMedia(invalidAuthor));
        assertThrows(CatalogEntityNotFoundException.class, () -> context.service.addMedia(invalidSection));
        assertTrue(context.service.media().isEmpty());
    }

    @Test
    @DisplayName("Le preferenze rifiutano riferimenti a media e autori inesistenti")
    void preferenzeConRiferimentiMancantiSonoRifiutate() {
        TestContext context = new TestContext();
        CatalogProfile profile = context.service.createProfile("Profilo");

        assertThrows(CatalogEntityNotFoundException.class,
                () -> context.service.preferMedia(profile.id(), UUID.randomUUID()));
        assertThrows(CatalogEntityNotFoundException.class,
                () -> context.service.preferAuthor(profile.id(), UUID.randomUUID()));
        assertTrue(context.service.profiles().getFirst().preferredMediaIds().isEmpty());
        assertTrue(context.service.profiles().getFirst().preferredAuthorIds().isEmpty());
    }

    @Test
    @DisplayName("Eliminare un profilo inesistente segnala l'assenza dell'entità")
    void eliminazioneProfiloInesistenteVieneRifiutata() {
        TestContext context = new TestContext();

        assertThrows(CatalogEntityNotFoundException.class,
                () -> context.service.deleteProfile(UUID.randomUUID()));
    }

    @Test
    @DisplayName("I nomi di sezione ignorano maiuscole, spazi ridondanti e forme Unicode equivalenti")
    void nomiSezioneNormalizzatiNonPossonoRipetersi() {
        TestContext context = new TestContext();
        context.service.createSection(" Dramma");

        assertThrows(DuplicateCatalogSectionNameException.class,
                () -> context.service.createSection("dramma   "));
        assertThrows(DuplicateCatalogSectionNameException.class,
                () -> context.service.createSection("DRAMMA"));
        assertEquals(1, context.service.sections().size());

        context.service.createSection("Fantascienza    spaziale");
        assertThrows(DuplicateCatalogSectionNameException.class,
                () -> context.service.createSection("fantascienza spaziale"));

        context.service.createSection("Caf\u00e9");
        assertThrows(DuplicateCatalogSectionNameException.class,
                () -> context.service.createSection("Cafe\u0301"));
        assertEquals(3, context.service.sections().size());
    }

    private static final class TestContext {
        private final InMemoryCatalogSectionRepository sectionRepository = new InMemoryCatalogSectionRepository();
        private final CatalogService service = new CatalogService(
                new InMemoryCatalogProfileRepository(),
                new InMemoryCatalogMediaRepository(),
                sectionRepository,
                new InMemoryCatalogAuthorRepository());

        private CatalogSection addSection(String name) {
            return service.createSection(name);
        }
    }
}
