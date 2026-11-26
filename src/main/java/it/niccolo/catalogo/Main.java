package it.niccolo.catalogo;

import it.niccolo.catalogo.application.UserService;
import it.niccolo.catalogo.persistence.PersistenceException;
import it.niccolo.catalogo.persistence.SqliteUserRepository;
import it.niccolo.catalogo.ui.SignInWindow;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Path dataDirectory = Paths.get("data");
            Files.createDirectories(dataDirectory);
            UserService users = new UserService(
                    new SqliteUserRepository(dataDirectory.resolve("catalogo.db")));
            SwingUtilities.invokeLater(() -> new SignInWindow(users).setVisible(true));
        } catch (PersistenceException | java.io.IOException | javax.swing.UnsupportedLookAndFeelException
                | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            exception.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null,
                    "Non è stato possibile avviare l'applicazione.\n" + exception.getMessage(),
                    "Errore di avvio", JOptionPane.ERROR_MESSAGE);
        }
    }
}
