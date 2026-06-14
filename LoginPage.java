import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        setTitle("Login Page");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Background Panel
        JPanel panel = new JPanel(); 
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(25, 25, 25)); // Dark background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title Label
        JLabel titleLabel = new JLabel("LOGIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, gbc);

        // Username Label & Field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        panel.add(userLabel, gbc);

        usernameField = new JTextField(15);
        styleTextField(usernameField);
        panel.add(usernameField, gbc);

        // Password Label & Field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        panel.add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        styleTextField(passwordField);
        panel.add(passwordField, gbc);

        // Login Button
        JButton loginButton = createStyledButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });

        panel.add(loginButton, gbc);

        // Forgot Password Link
        JLabel forgotPassword = new JLabel("<html><u>Forgot Password?</u></html>");
        forgotPassword.setForeground(new Color(0, 191, 255));
        forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(forgotPassword, gbc);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setBackground(new Color(50, 50, 50));
        field.setForeground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 191, 255), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 191, 255));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        return button;
    }

    private void authenticateUser() {
    String username = usernameField.getText();
    String password = new String(passwordField.getPassword());

    try {
        // Connect to database
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bus_pass_system", "root", "root");

        // Prepare query
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);

        // Execute query
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new HomePage(); // Redirect to HomePage
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Close connection
        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace(); // Print error details in the console for debugging
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginPage::new);
    }
}
