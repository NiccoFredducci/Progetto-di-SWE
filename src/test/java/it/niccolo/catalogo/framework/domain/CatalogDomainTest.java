package it.niccolo.catalogo.framework.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CatalogDomainTest {

    @Test
    @DisplayName("Il profilo richiede un nome e ammette foto e preferenze assenti")
    void profiloRichiedeNomeMaAmmetteFotoEPreferenzeAssenti() {
        CatalogProfile profile = CatalogProfile.create("Niccolò");

        assertEquals("Niccolò", profile.name());
        assertNull(profile.photoPath());
        assertTrue(profile.preferredMediaIds().isEmpty());
        assertTrue(profile.preferredAuthorIds().isEmpty());
    }

    @Test
    @DisplayName("Il profilo rifiuta un nome assente o composto solo da spazi")
    void profiloRifiutaNomeAssenteOVuoto() {
        assertThrows(NullPointerException.class, () -> CatalogProfile.create(null));
        assertThrows(IllegalArgumentException.class, () -> CatalogProfile.create("  "));
    }

    @Test
    @DisplayName("Le preferenze sono personali, many-to-many e non modificabili dall'esterno")
    void preferenzeSonoPersonaliEImmutabiliEConsentonoMoltiAMolti() {
        UUID sharedMedia = UUID.randomUUID();
        UUID sharedAuthor = UUID.randomUUID();
        CatalogProfile first = CatalogProfile.create("A").withPreferredMedia(sharedMedia)
                .withPreferredAuthor(sharedAuthor);
        CatalogProfile second = CatalogProfile.create("B").withPreferredMedia(sharedMedia)
                .withPreferredAuthor(sharedAuthor);

        assertTrue(first.preferredMediaIds().contains(sharedMedia));
        assertTrue(second.preferredMediaIds().contains(sharedMedia));
        assertTrue(first.preferredAuthorIds().contains(sharedAuthor));
        assertThrows(UnsupportedOperationException.class,
                () -> first.preferredMediaIds().add(UUID.randomUUID()));
        assertFalse(first.withoutPreferredMedia(sharedMedia).preferredMediaIds().contains(sharedMedia));
        assertTrue(first.preferredMediaIds().contains(sharedMedia));
    }

    @Test
    @DisplayName("Il media richiede file, titolo e un autore e può non avere sezioni")
    void mediaRichiedeFileTitoloAutoreEAmmetteZeroSezioni() {
        UUID authorId = UUID.randomUUID();
        CatalogMedia media = CatalogMedia.create(Path.of("somewhere", "item.bin"), "Elemento", authorId);

        assertEquals(Path.of("somewhere", "item.bin"), media.filePath());
        assertEquals("Elemento", media.title());
        assertEquals(authorId, media.authorId());
        assertNull(media.coverPath());
        assertTrue(media.sectionIds().isEmpty());
    }

    @Test
    @DisplayName("Il media rifiuta i dati obbligatori mancanti o vuoti")
    void mediaRifiutaDatiObbligatoriMancanti() {
        UUID authorId = UUID.randomUUID();
        assertThrows(NullPointerException.class, () -> CatalogMedia.create(null, "Titolo", authorId));
        assertThrows(IllegalArgumentException.class,
                () -> CatalogMedia.create(Path.of("item.bin"), " ", authorId));
        assertThrows(NullPointerException.class,
                () -> CatalogMedia.create(Path.of("item.bin"), "Titolo", null));
    }

    @Test
    @DisplayName("Il media accetta più sezioni e protegge l'insieme ricevuto")
    void mediaAmmettePiuSezioniECopiaAssociazioni() {
        UUID sectionOne = UUID.randomUUID();
        UUID sectionTwo = UUID.randomUUID();
        Set<UUID> input = new HashSet<>(Set.of(sectionOne, sectionTwo));
        CatalogMedia media = new CatalogMedia(UUID.randomUUID(), Path.of("item.bin"), "Titolo",
                null, null, UUID.randomUUID(), input);
        input.clear();

        assertEquals(Set.of(sectionOne, sectionTwo), media.sectionIds());
        assertThrows(UnsupportedOperationException.class,
                () -> media.sectionIds().clear());
    }

    @Test
    @DisplayName("La sezione richiede un nome e può esistere senza media")
    void sezioneRichiedeNomeEAmmetteZeroMedia() {
        CatalogSection section = CatalogSection.create("Sezione vuota");

        assertEquals("Sezione vuota", section.name());
        assertThrows(NullPointerException.class, () -> CatalogSection.create(null));
        assertThrows(IllegalArgumentException.class, () -> CatalogSection.create(""));
    }

    @Test
    @DisplayName("L'autore richiede un nome e ammette foto e descrizione assenti")
    void autoreRichiedeNomeEAmmetteFotoEDescrizioneAssenti() {
        CatalogAuthor author = CatalogAuthor.create("Autore generico");

        assertEquals("Autore generico", author.name());
        assertNull(author.photoPath());
        assertNull(author.description());
        assertThrows(IllegalArgumentException.class, () -> CatalogAuthor.create("\t"));
    }
}
