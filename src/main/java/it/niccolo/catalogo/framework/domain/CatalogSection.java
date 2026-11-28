package it.niccolo.catalogo.framework.domain;

import java.text.Normalizer;
import java.util.Locale;
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

    /** Canonical key: NFC, case-insensitive, and whitespace-trimmed/collapsed. */
    public static String normalizedName(String name) {
        String normalized = Normalizer.normalize(
                Objects.requireNonNull(name, "name must not be null"), Normalizer.Form.NFC);
        StringBuilder result = new StringBuilder();
        boolean pendingSpace = false;
        for (int offset = 0; offset < normalized.length();) {
            int codePoint = normalized.codePointAt(offset);
            offset += Character.charCount(codePoint);
            if (Character.isWhitespace(codePoint) || Character.isSpaceChar(codePoint)) {
                pendingSpace = result.length() > 0;
            } else {
                if (pendingSpace) {
                    result.append(' ');
                    pendingSpace = false;
                }
                result.appendCodePoint(codePoint);
            }
        }
        return Normalizer.normalize(result.toString().toLowerCase(Locale.ROOT), Normalizer.Form.NFC);
    }

    public static boolean namesEqual(String first, String second) {
        return normalizedName(first).equals(normalizedName(second));
    }

    public UUID id() { return id; }
    public String name() { return name; }
}
