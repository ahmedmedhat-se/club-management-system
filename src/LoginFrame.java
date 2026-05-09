import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Login screen - validates credentials, passes User to MainFrame
 */
public class LoginFrame extends JFrame {

    private static final String[][] USERS = {
        {"Medhat", "medhat123", "ADMIN", "U001"},
        {"lojain", "lojain123", "CLUB_LEADER", "U002"},
        {"sereen", "sereen123", "MEMBER", "U003"},
        {"admin", "admin", "ADMIN", "U005"},
    };

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Club Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel title = new JLabel("CLUB MANAGEMENT", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(47, 52, 65));
        panel.add(title, gbc);

        gbc.gridy = 1;
        JLabel sub = new JLabel("Sign in to continue", SwingConstants.CENTER);
        sub.setFont(new Font("Arial", Font.PLAIN, 12));
        sub.setForeground(Color.GRAY);
        panel.add(sub, gbc);

        // Username
        gbc.gridwidth = 1; gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Password
        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Login button
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBackground(new Color(52, 152, 219));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.addActionListener(e -> handleLogin());
        panel.add(loginBtn, gbc);

        passwordField.addActionListener(e -> handleLogin());

        // Hint
        gbc.gridy = 5;
        JLabel hint = new JLabel("Hint: admin / admin", SwingConstants.CENTER);
        hint.setFont(new Font("Arial", Font.ITALIC, 11));
        hint.setForeground(Color.LIGHT_GRAY);
        panel.add(hint, gbc);

        add(panel);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        for (String[] user : USERS) {
            if (user[0].equalsIgnoreCase(username) && user[1].equals(password)) {
                this.dispose();
                User loggedIn = new User(user[3], getNameFromUsername(user[0]), user[0] + "@club.com", user[1], user[2]);
                MainFrame mainFrame = new MainFrame(loggedIn);
                mainFrame.setVisible(true);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        passwordField.setText("");
        passwordField.requestFocus();
    }

    private String getNameFromUsername(String username) {
        switch (username.toLowerCase()) {
            case "medhat": return "Ahmed Medhat";
            case "lojain": return "Lojain Mohammed";
            case "sereen": return "Sereen Diab";
            case "admin": return "Admin User";
            default: return username;
        }
    }
}