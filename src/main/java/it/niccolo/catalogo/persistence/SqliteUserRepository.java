package it.niccolo.catalogo.persistence;

import it.niccolo.catalogo.domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** JDBC adapter for the local SQLite database. */
public final class SqliteUserRepository implements UserRepository {
    private final String jdbcUrl;

    public SqliteUserRepository(Path databaseFile) throws PersistenceException {
        this.jdbcUrl = "jdbc:sqlite:" + databaseFile.toAbsolutePath();
        initializeSchema();
    }

    @Override
    public List<User> findAll() throws PersistenceException {
        String sql = "SELECT id, name FROM users ORDER BY id";
        List<User> users = new ArrayList<User>();
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                users.add(new User(result.getLong("id"), result.getString("name")));
            }
            return users;
        } catch (SQLException exception) {
            throw new PersistenceException("Impossibile leggere gli utenti dal database.", exception);
        }
    }

    @Override
    public User create(String name) throws PersistenceException {
        String sql = "INSERT INTO users(name) VALUES (?)";
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.executeUpdate();
            try (Statement idStatement = connection.createStatement();
                 ResultSet result = idStatement.executeQuery("SELECT last_insert_rowid()")) {
                if (result.next()) {
                    return new User(result.getLong(1), name);
                }
            }
            throw new SQLException("SQLite non ha restituito l'identificativo del nuovo utente.");
        } catch (SQLException exception) {
            throw new PersistenceException("Impossibile salvare il nuovo utente.", exception);
        }
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl);
    }

    private void initializeSchema() throws PersistenceException {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL CHECK(length(trim(name)) > 0)"
                + ")";
        try (Connection connection = openConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException exception) {
            throw new PersistenceException("Impossibile inizializzare il database locale.", exception);
        }
    }
}
