package it.niccolo.catalogo.framework.persistence.memory;

import it.niccolo.catalogo.framework.application.repository.CatalogProfileRepository;
import it.niccolo.catalogo.framework.domain.CatalogProfile;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class InMemoryCatalogProfileRepository implements CatalogProfileRepository {
    private final Map<UUID, CatalogProfile> profiles = new LinkedHashMap<>();

    @Override public Optional<CatalogProfile> findById(UUID id) { return Optional.ofNullable(profiles.get(id)); }
    @Override public List<CatalogProfile> findAll() { return new ArrayList<>(profiles.values()); }
    @Override public void save(CatalogProfile profile) { profiles.put(profile.id(), profile); }
    @Override public void deleteById(UUID id) { profiles.remove(id); }
}
