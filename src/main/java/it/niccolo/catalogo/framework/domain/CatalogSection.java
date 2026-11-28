package it.niccolo.catalogo.framework.domain;

import java.util.Objects;
import java.util.UUID;

/** A generic grouping for catalog media; an empty section is valid. */
public final class CatalogSection {
    private final UUID id;
    private final String name;

    public CatalogSection(UUID id, String name) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = DomainValidation.requiredText(name, "name");
    }

    public static CatalogSection create(String name) {
        return new CatalogSection(UUID.randomUUID(), name);
    }

    public UUID id() { return id; }
    public String name() { return name; }
}
