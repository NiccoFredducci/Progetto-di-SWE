package it.niccolo.catalogo.framework.application;

public final class CatalogEntityNotFoundException extends RuntimeException {
    public CatalogEntityNotFoundException(String message) {
        super(message);
    }
}
