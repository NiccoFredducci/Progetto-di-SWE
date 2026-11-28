package it.niccolo.catalogo.framework.persistence.memory;

import it.niccolo.catalogo.framework.application.repository.CatalogMediaRepository;
import it.niccolo.catalogo.framework.domain.CatalogMedia;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public final class InMemoryCatalogMediaRepository implements CatalogMediaRepository {
    private final Map<UUID, CatalogMedia> media = new LinkedHashMap<>();

    @Override public Optional<CatalogMedia> findById(UUID id) { return Optional.ofNullable(media.get(id)); }
    @Override public List<CatalogMedia> findAll() { return new ArrayList<>(media.values()); }
    @Override public List<CatalogMedia> findByAuthorId(UUID authorId) {
        return media.values().stream().filter(item -> item.authorId().equals(authorId)).collect(Collectors.toList());
    }
    @Override public List<CatalogMedia> findBySectionId(UUID sectionId) {
        return media.values().stream().filter(item -> item.sectionIds().contains(sectionId)).collect(Collectors.toList());
    }
    @Override public void save(CatalogMedia item) { media.put(item.id(), item); }
}
