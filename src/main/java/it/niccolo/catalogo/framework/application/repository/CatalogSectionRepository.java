package it.niccolo.catalogo.framework.application.repository;

import it.niccolo.catalogo.framework.domain.CatalogSection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CatalogSectionRepository {
    Optional<CatalogSection> findById(UUID id);
    List<CatalogSection> findAll();
    boolean existsByName(String name);
    void save(CatalogSection section);
}
