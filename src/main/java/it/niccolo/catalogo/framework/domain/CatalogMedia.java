package it.niccolo.catalogo.framework.domain;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/** A generic catalog item backed by a file, with one required author and optional sections. */
public final class CatalogMedia {
    private final UUID id;
    private final Path filePath;
    private final String title;
    private final String description;
    private final Path coverPath;
    private final UUID authorId;
    private final Set<UUID> sectionIds;

    public CatalogMedia(UUID id, Path filePath, String title, String description,
                        Path coverPath, UUID authorId, Set<UUID> sectionIds) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.filePath = Objects.requireNonNull(filePath, "filePath must not be null");
        this.title = DomainValidation.requiredText(title, "title");
        this.description = description;
        this.coverPath = coverPath;
        this.authorId = Objects.requireNonNull(authorId, "authorId must not be null");
        this.sectionIds = immutableIds(sectionIds);
    }

    public static CatalogMedia create(Path filePath, String title, UUID authorId) {
        return new CatalogMedia(UUID.randomUUID(), filePath, title, null, null, authorId, Set.of());
    }

    public UUID id() { return id; }
    public Path filePath() { return filePath; }
    public String title() { return title; }
    public String description() { return description; }
    public Path coverPath() { return coverPath; }
    public UUID authorId() { return authorId; }
    public Set<UUID> sectionIds() { return sectionIds; }

    private static Set<UUID> immutableIds(Set<UUID> ids) {
        Objects.requireNonNull(ids, "sectionIds must not be null");
        Set<UUID> copy = new HashSet<>(ids);
        if (copy.contains(null)) {
            throw new IllegalArgumentException("sectionIds must not contain null");
        }
        return Collections.unmodifiableSet(copy);
    }
}
