package it.niccolo.catalogo.persistence;

import it.niccolo.catalogo.domain.User;
import java.util.List;

public interface UserRepository {
    List<User> findAll() throws PersistenceException;

    User create(String name) throws PersistenceException;
}
