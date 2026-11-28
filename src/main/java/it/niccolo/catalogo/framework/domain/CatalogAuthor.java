package it.niccolo.catalogo.framework.domain;

import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

/** A person associated as the single author/contributor of catalog media. */
public final class CatalogAuthor {
    private final UUID id;
    private final String name;
    private final Path photoPath;
    private final String description;

    public CatalogAuthor(UUID id, String name, Path photoPath, String description) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = DomainValidation.requiredText(name, "name");
        this.photoPath = photoPath;
        this.description = description;
    }

    public static CatalogAuthor create(String name) {
        return new CatalogAuthor(UUID.randomUUID(), name, null, null);
    }

    public UUID id() { return id; }
    public String name() { return name; }
    public Path photoPath() { return photoPath; }
    public String description() { return description; }
}
