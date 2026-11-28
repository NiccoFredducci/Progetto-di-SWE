package it.niccolo.catalogo.framework.persistence.memory;

import it.niccolo.catalogo.framework.application.repository.CatalogSectionRepository;
import it.niccolo.catalogo.framework.domain.CatalogSection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class InMemoryCatalogSectionRepository implements CatalogSectionRepository {
    private final Map<UUID, CatalogSection> sections = new LinkedHashMap<>();

    @Override public Optional<CatalogSection> findById(UUID id) { return Optional.ofNullable(sections.get(id)); }
    @Override public List<CatalogSection> findAll() { return new ArrayList<>(sections.values()); }
    @Override public boolean existsByName(String name) {
        return sections.values().stream().anyMatch(section -> CatalogSection.namesEqual(section.name(), name));
    }
    @Override public void save(CatalogSection section) { sections.put(section.id(), section); }
}
