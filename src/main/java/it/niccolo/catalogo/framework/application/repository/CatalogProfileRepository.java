package it.niccolo.catalogo.framework.application.repository;

import it.niccolo.catalogo.framework.domain.CatalogProfile;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CatalogProfileRepository {
    Optional<CatalogProfile> findById(UUID id);
    List<CatalogProfile> findAll();
    void save(CatalogProfile profile);
    void deleteById(UUID id);
}
