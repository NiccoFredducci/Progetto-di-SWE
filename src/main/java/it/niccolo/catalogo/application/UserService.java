package it.niccolo.catalogo.application;

import it.niccolo.catalogo.domain.User;
import it.niccolo.catalogo.persistence.PersistenceException;
import it.niccolo.catalogo.persistence.UserRepository;
import java.util.List;

public final class UserService {
    private final UserRepository users;

    public UserService(UserRepository users) {
        this.users = users;
    }

    public List<User> getUsers() throws PersistenceException {
        return users.findAll();
    }

    public User registerUser(String requestedName) throws PersistenceException {
        if (requestedName == null || requestedName.trim().isEmpty()) {
            throw new IllegalArgumentException("Inserisci un nome per il profilo.");
        }
        return users.create(requestedName.trim());
    }
}
