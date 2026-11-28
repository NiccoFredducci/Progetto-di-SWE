package it.niccolo.catalogo.framework.application.repository;

import it.niccolo.catalogo.framework.domain.CatalogMedia;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CatalogMediaRepository {
    Optional<CatalogMedia> findById(UUID id);
    List<CatalogMedia> findAll();
    List<CatalogMedia> findByAuthorId(UUID authorId);
    List<CatalogMedia> findBySectionId(UUID sectionId);
    void save(CatalogMedia media);
}
