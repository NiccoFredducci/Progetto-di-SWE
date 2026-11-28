package it.niccolo.catalogo.framework.domain;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/** A user profile whose catalog preferences are personal, while catalog entries are shared. */
public final class CatalogProfile {
    private final UUID id;
    private final String name;
    private final Path photoPath;
    private final Set<UUID> preferredMediaIds;
    private final Set<UUID> preferredAuthorIds;

    public CatalogProfile(UUID id, String name, Path photoPath,
                          Set<UUID> preferredMediaIds, Set<UUID> preferredAuthorIds) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = DomainValidation.requiredText(name, "name");
        this.photoPath = photoPath;
        this.preferredMediaIds = immutableIds(preferredMediaIds, "preferredMediaIds");
        this.preferredAuthorIds = immutableIds(preferredAuthorIds, "preferredAuthorIds");
    }

    public static CatalogProfile create(String name) {
        return new CatalogProfile(UUID.randomUUID(), name, null, Set.of(), Set.of());
    }

    public UUID id() { return id; }
    public String name() { return name; }
    public Path photoPath() { return photoPath; }
    public Set<UUID> preferredMediaIds() { return preferredMediaIds; }
    public Set<UUID> preferredAuthorIds() { return preferredAuthorIds; }

    public CatalogProfile withPreferredMedia(UUID mediaId) {
        return copyWith(add(preferredMediaIds, mediaId), preferredAuthorIds);
    }

    public CatalogProfile withoutPreferredMedia(UUID mediaId) {
        return copyWith(remove(preferredMediaIds, mediaId), preferredAuthorIds);
    }

    public CatalogProfile withPreferredAuthor(UUID authorId) {
        return copyWith(preferredMediaIds, add(preferredAuthorIds, authorId));
    }

    public CatalogProfile withoutPreferredAuthor(UUID authorId) {
        return copyWith(preferredMediaIds, remove(preferredAuthorIds, authorId));
    }

    private CatalogProfile copyWith(Set<UUID> mediaIds, Set<UUID> authorIds) {
        return new CatalogProfile(id, name, photoPath, mediaIds, authorIds);
    }

    private static Set<UUID> add(Set<UUID> source, UUID id) {
        Set<UUID> result = new HashSet<>(source);
        result.add(Objects.requireNonNull(id, "associated id must not be null"));
        return result;
    }

    private static Set<UUID> remove(Set<UUID> source, UUID id) {
        Set<UUID> result = new HashSet<>(source);
        result.remove(Objects.requireNonNull(id, "associated id must not be null"));
        return result;
    }

    private static Set<UUID> immutableIds(Set<UUID> ids, String fieldName) {
        Objects.requireNonNull(ids, fieldName + " must not be null");
        Set<UUID> copy = new HashSet<>(ids);
        if (copy.contains(null)) {
            throw new IllegalArgumentException(fieldName + " must not contain null");
        }
        return Collections.unmodifiableSet(copy);
    }
}
