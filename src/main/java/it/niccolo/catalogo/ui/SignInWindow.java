package it.niccolo.catalogo.ui;

import it.niccolo.catalogo.application.UserService;
import it.niccolo.catalogo.domain.User;
import it.niccolo.catalogo.persistence.PersistenceException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/** The only application view in this first step: profile selection and creation. */
public final class SignInWindow extends JFrame {
    private static final Color BACKGROUND = new Color(18, 20, 27);
    private static final Color SURFACE = new Color(31, 35, 45);
    private static final Color ACCENT = new Color(115, 92, 255);
    private static final Color TEXT = new Color(244, 245, 249);
    private static final Color MUTED = new Color(163, 168, 182);

    private final UserService userService;
    private final JPanel profiles = new JPanel(new FlowLayout(FlowLayout.CENTER, 22, 12));
    private final JLabel status = new JLabel(" ", SwingConstants.CENTER);
    private final List<ProfileButton> profileButtons = new ArrayList<ProfileButton>();
    private User selectedUser;

    public SignInWindow(UserService userService) {
        super("Catalogo · Profili");
        this.userService = userService;
        buildWindow();
        reloadUsers();
    }

    private void buildWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(760, 500));
        setSize(900, 560);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);
        root.setBorder(BorderFactory.createEmptyBorder(48, 42, 34, 42));

        JLabel eyebrow = new JLabel("CATALOGO LOCALE", SwingConstants.CENTER);
        eyebrow.setForeground(ACCENT);
        eyebrow.setFont(new Font("SansSerif", Font.BOLD, 12));

        JLabel title = new JLabel("Chi sta guardando?", SwingConstants.CENTER);
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));

        JLabel subtitle = new JLabel("Scegli un profilo o creane uno nuovo", SwingConstants.CENTER);
        subtitle.setForeground(MUTED);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.add(eyebrow);
        heading.add(Box.createVerticalStrut(12));
        heading.add(title);
        heading.add(Box.createVerticalStrut(8));
        heading.add(subtitle);
        root.add(heading, BorderLayout.NORTH);

        profiles.setOpaque(false);
        JScrollPane scroll = new JScrollPane(profiles,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getHorizontalScrollBar().setUnitIncrement(24);

        JPanel rowWrapper = new JPanel(new BorderLayout());
        rowWrapper.setOpaque(false);
        rowWrapper.add(scroll, BorderLayout.CENTER);
        root.add(rowWrapper, BorderLayout.CENTER);

        status.setForeground(MUTED);
        status.setFont(new Font("SansSerif", Font.PLAIN, 13));
        status.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));
        root.add(status, BorderLayout.SOUTH);
        setContentPane(root);
    }

    private void reloadUsers() {
        try {
            List<User> users = userService.getUsers();
            profiles.removeAll();
            profileButtons.clear();
            for (User user : users) {
                ProfileButton button = new ProfileButton(user);
                button.addActionListener(event -> selectUser(user));
                profileButtons.add(button);
                profiles.add(button);
            }
            JButton addButton = createProfileButton();
            profiles.add(addButton);
            updateSelectionStyle();
            profiles.revalidate();
            profiles.repaint();
        } catch (PersistenceException exception) {
            showPersistenceError(exception);
        }
    }

    private JButton createProfileButton() {
        JButton add = new JButton();
        add.setPreferredSize(new Dimension(118, 154));
        add.setFocusPainted(false);
        add.setBorderPainted(false);
        add.setContentAreaFilled(false);
        add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        CircleIcon plus = new CircleIcon("+", ACCENT, TEXT);
        JLabel icon = new JLabel(plus);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel label = new JLabel("Aggiungi", SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(MUTED);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        content.add(Box.createVerticalGlue());
        content.add(icon);
        content.add(Box.createVerticalStrut(12));
        content.add(label);
        content.add(Box.createVerticalGlue());
        add.setLayout(new BorderLayout());
        add.add(content, BorderLayout.CENTER);
        add.addActionListener(event -> showCreateUserDialog());
        return add;
    }

    private void selectUser(User user) {
        selectedUser = user;
        status.setText("Profilo selezionato: " + user.getName());
        status.setForeground(TEXT);
        updateSelectionStyle();
    }

    private void updateSelectionStyle() {
        for (ProfileButton button : profileButtons) {
            button.setProfileSelected(selectedUser != null && selectedUser.getId() == button.user.getId());
        }
    }

    private void showCreateUserDialog() {
        JDialog dialog = new JDialog(this, "Nuovo profilo", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setBorder(BorderFactory.createEmptyBorder(22, 24, 20, 24));
        JLabel prompt = new JLabel("Come vuoi chiamare il profilo?");
        JTextField nameField = new JTextField(22);
        JPanel form = new JPanel(new BorderLayout(0, 8));
        form.add(prompt, BorderLayout.NORTH);
        form.add(nameField, BorderLayout.CENTER);

        JButton cancel = new JButton("Annulla");
        JButton create = new JButton("Crea profilo");
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.add(cancel);
        actions.add(create);
        panel.add(form, BorderLayout.CENTER);
        panel.add(actions, BorderLayout.SOUTH);

        cancel.addActionListener(event -> dialog.dispose());
        create.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    User created = userService.registerUser(nameField.getText());
                    dialog.dispose();
                    reloadUsers();
                    selectUser(created);
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(dialog, exception.getMessage(),
                            "Nome non valido", JOptionPane.INFORMATION_MESSAGE);
                    nameField.requestFocusInWindow();
                } catch (PersistenceException exception) {
                    JOptionPane.showMessageDialog(dialog, exception.getMessage(),
                            "Errore database", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        dialog.getRootPane().setDefaultButton(create);
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        nameField.requestFocusInWindow();
        dialog.setVisible(true);
    }

    private void showPersistenceError(Exception exception) {
        JOptionPane.showMessageDialog(this,
                exception.getMessage() + "\nControlla i dettagli nella console.",
                "Errore database", JOptionPane.ERROR_MESSAGE);
        exception.printStackTrace(System.err);
    }

    private static final class ProfileButton extends JButton {
        private final User user;
        private boolean selected;

        private ProfileButton(User user) {
            this.user = user;
            setPreferredSize(new Dimension(118, 154));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JPanel content = new JPanel();
            content.setOpaque(false);
            content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
            JLabel avatar = new JLabel(new CircleIcon(initials(user.getName()),
                    colorFor(user.getName()), TEXT));
            avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel name = new JLabel(user.getName(), SwingConstants.CENTER);
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            name.setForeground(TEXT);
            name.setFont(new Font("SansSerif", Font.PLAIN, 13));
            name.setMaximumSize(new Dimension(112, 24));
            content.add(Box.createVerticalGlue());
            content.add(avatar);
            content.add(Box.createVerticalStrut(12));
            content.add(name);
            content.add(Box.createVerticalGlue());
            setLayout(new BorderLayout());
            add(content, BorderLayout.CENTER);
        }

        private void setProfileSelected(boolean selected) {
            this.selected = selected;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (selected) {
                g.setColor(new Color(115, 92, 255, 40));
                g.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 20, 20);
                g.setColor(ACCENT);
                g.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 20, 20);
            } else if (getModel().isRollover()) {
                g.setColor(SURFACE);
                g.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 20, 20);
            }
            g.dispose();
            super.paintComponent(graphics);
        }
    }

    private static final class CircleIcon implements javax.swing.Icon {
        private final String text;
        private final Color fill;
        private final Color foreground;

        private CircleIcon(String text, Color fill, Color foreground) {
            this.text = text;
            this.fill = fill;
            this.foreground = foreground;
        }

        @Override
        public int getIconWidth() {
            return 72;
        }

        @Override
        public int getIconHeight() {
            return 72;
        }

        @Override
        public void paintIcon(Component component, Graphics graphics, int x, int y) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(fill);
            g.fillOval(x + 2, y + 2, 68, 68);
            g.setColor(foreground);
            g.setFont(new Font("SansSerif", Font.BOLD, text.length() > 1 ? 21 : 34));
            int textWidth = g.getFontMetrics().stringWidth(text);
            int textHeight = g.getFontMetrics().getAscent();
            g.drawString(text, x + (72 - textWidth) / 2, y + (72 + textHeight) / 2 - 4);
            g.dispose();
        }
    }

    private static String initials(String name) {
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) {
            return parts[0].substring(0, 1).toUpperCase();
        }
        return (parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1)).toUpperCase();
    }

    private static Color colorFor(String name) {
        Color[] colors = {
            new Color(79, 112, 196), new Color(57, 145, 132), new Color(186, 105, 91),
            new Color(142, 94, 173), new Color(194, 139, 67), new Color(67, 132, 173)
        };
        return colors[Math.floorMod(name.hashCode(), colors.length)];
    }
}
