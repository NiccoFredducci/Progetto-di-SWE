package it.niccolo.catalogo.framework.application;

public final class DuplicateCatalogSectionNameException extends RuntimeException {
    public DuplicateCatalogSectionNameException(String name) {
        super("A catalog section already exists with an equivalent name: " + name);
    }
}
