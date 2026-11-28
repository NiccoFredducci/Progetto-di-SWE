package it.niccolo.catalogo.framework.persistence.memory;

import it.niccolo.catalogo.framework.application.repository.CatalogAuthorRepository;
import it.niccolo.catalogo.framework.domain.CatalogAuthor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class InMemoryCatalogAuthorRepository implements CatalogAuthorRepository {
    private final Map<UUID, CatalogAuthor> authors = new LinkedHashMap<>();

    @Override public Optional<CatalogAuthor> findById(UUID id) { return Optional.ofNullable(authors.get(id)); }
    @Override public List<CatalogAuthor> findAll() { return new ArrayList<>(authors.values()); }
    @Override public void save(CatalogAuthor author) { authors.put(author.id(), author); }
}
