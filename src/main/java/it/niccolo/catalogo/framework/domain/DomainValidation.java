package it.niccolo.catalogo.framework.domain;

import java.util.Objects;

final class DomainValidation {
    private DomainValidation() {
    }

    static String requiredText(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }
}
