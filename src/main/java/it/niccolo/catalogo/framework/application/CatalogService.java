package it.niccolo.catalogo.framework.application;

import it.niccolo.catalogo.framework.application.repository.CatalogAuthorRepository;
import it.niccolo.catalogo.framework.application.repository.CatalogMediaRepository;
import it.niccolo.catalogo.framework.application.repository.CatalogProfileRepository;
import it.niccolo.catalogo.framework.application.repository.CatalogSectionRepository;
import it.niccolo.catalogo.framework.domain.CatalogAuthor;
import it.niccolo.catalogo.framework.domain.CatalogMedia;
import it.niccolo.catalogo.framework.domain.CatalogProfile;
import it.niccolo.catalogo.framework.domain.CatalogSection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/** Application use cases for shared catalog entries and per-profile preferences. */
public final class CatalogService {
    private final CatalogProfileRepository profiles;
    private final CatalogMediaRepository media;
    private final CatalogSectionRepository sections;
    private final CatalogAuthorRepository authors;

    public CatalogService(CatalogProfileRepository profiles,
                          CatalogMediaRepository media,
                          CatalogSectionRepository sections,
                          CatalogAuthorRepository authors) {
        this.profiles = Objects.requireNonNull(profiles, "profiles must not be null");
        this.media = Objects.requireNonNull(media, "media must not be null");
        this.sections = Objects.requireNonNull(sections, "sections must not be null");
        this.authors = Objects.requireNonNull(authors, "authors must not be null");
    }

    public CatalogProfile createProfile(String name) {
        CatalogProfile profile = CatalogProfile.create(name);
        profiles.save(profile);
        return profile;
    }

    public void deleteProfile(UUID profileId) {
        requireProfile(profileId);
        profiles.deleteById(profileId);
    }

    public CatalogAuthor addAuthor(CatalogAuthor author) {
        Objects.requireNonNull(author, "author must not be null");
        authors.save(author);
        return author;
    }

    public CatalogSection createSection(String name) {
        CatalogSection section = CatalogSection.create(name);
        if (sections.existsByName(section.name())) {
            throw new DuplicateCatalogSectionNameException(section.name());
        }
        sections.save(section);
        return section;
    }

    /** Adds a media entry only when all referenced shared entries already exist. */
    public CatalogMedia addMedia(CatalogMedia catalogMedia) {
        Objects.requireNonNull(catalogMedia, "catalogMedia must not be null");
        requireAuthor(catalogMedia.authorId());
        catalogMedia.sectionIds().forEach(this::requireSection);
        media.save(catalogMedia);
        return catalogMedia;
    }

    public CatalogMedia addMediaToSection(UUID mediaId, UUID sectionId) {
        CatalogMedia current = requireMedia(mediaId);
        requireSection(sectionId);
        CatalogMedia updated = current.withSection(sectionId);
        media.save(updated);
        return updated;
    }

    public CatalogMedia removeMediaFromSection(UUID mediaId, UUID sectionId) {
        CatalogMedia current = requireMedia(mediaId);
        requireSection(sectionId);
        CatalogMedia updated = current.withoutSection(sectionId);
        media.save(updated);
        return updated;
    }

    public CatalogProfile preferMedia(UUID profileId, UUID mediaId) {
        CatalogProfile current = requireProfile(profileId);
        requireMedia(mediaId);
        CatalogProfile updated = current.withPreferredMedia(mediaId);
        profiles.save(updated);
        return updated;
    }

    public CatalogProfile unpreferMedia(UUID profileId, UUID mediaId) {
        CatalogProfile current = requireProfile(profileId);
        requireMedia(mediaId);
        CatalogProfile updated = current.withoutPreferredMedia(mediaId);
        profiles.save(updated);
        return updated;
    }

    public CatalogProfile preferAuthor(UUID profileId, UUID authorId) {
        CatalogProfile current = requireProfile(profileId);
        requireAuthor(authorId);
        CatalogProfile updated = current.withPreferredAuthor(authorId);
        profiles.save(updated);
        return updated;
    }

    public CatalogProfile unpreferAuthor(UUID profileId, UUID authorId) {
        CatalogProfile current = requireProfile(profileId);
        requireAuthor(authorId);
        CatalogProfile updated = current.withoutPreferredAuthor(authorId);
        profiles.save(updated);
        return updated;
    }

    public List<CatalogProfile> profiles() { return List.copyOf(profiles.findAll()); }
    public List<CatalogMedia> media() { return List.copyOf(media.findAll()); }
    public List<CatalogSection> sections() { return List.copyOf(sections.findAll()); }
    public List<CatalogAuthor> authors() { return List.copyOf(authors.findAll()); }
    public List<CatalogMedia> mediaByAuthor(UUID authorId) {
        requireAuthor(authorId);
        return List.copyOf(media.findByAuthorId(authorId));
    }
    public List<CatalogMedia> mediaBySection(UUID sectionId) {
        requireSection(sectionId);
        return List.copyOf(media.findBySectionId(sectionId));
    }

    private CatalogProfile requireProfile(UUID id) {
        return profiles.findById(Objects.requireNonNull(id, "profileId must not be null"))
                .orElseThrow(() -> notFound("profile", id));
    }

    private CatalogMedia requireMedia(UUID id) {
        return media.findById(Objects.requireNonNull(id, "mediaId must not be null"))
                .orElseThrow(() -> notFound("media", id));
    }

    private CatalogSection requireSection(UUID id) {
        return sections.findById(Objects.requireNonNull(id, "sectionId must not be null"))
                .orElseThrow(() -> notFound("section", id));
    }

    private CatalogAuthor requireAuthor(UUID id) {
        return authors.findById(Objects.requireNonNull(id, "authorId must not be null"))
                .orElseThrow(() -> notFound("author", id));
    }

    private CatalogEntityNotFoundException notFound(String entity, UUID id) {
        return new CatalogEntityNotFoundException("No " + entity + " found with id " + id);
    }
}
