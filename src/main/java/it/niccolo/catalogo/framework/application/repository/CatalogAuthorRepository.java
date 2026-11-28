package it.niccolo.catalogo.framework.application.repository;

import it.niccolo.catalogo.framework.domain.CatalogAuthor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CatalogAuthorRepository {
    Optional<CatalogAuthor> findById(UUID id);
    List<CatalogAuthor> findAll();
    void save(CatalogAuthor author);
}
