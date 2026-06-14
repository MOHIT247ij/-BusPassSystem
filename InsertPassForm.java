import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class InsertPassForm extends JFrame {
    private JComboBox<String> typeComboBox; // Dropdown for pass type
    private JTextField validityField, priceField;

    public InsertPassForm() {
        setTitle("Add Pass Information");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Pass Type:"));
        typeComboBox = new JComboBox<>(new String[]{"Daily", "Monthly", "Quarterly", "Yearly"});
        add(typeComboBox);

        add(new JLabel("Validity (in days):"));
        validityField = new JTextField();
        add(validityField);

        add(new JLabel("Price:"));
        priceField = new JTextField();
        add(priceField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this::insertPassData);
        add(submitButton);

        setVisible(true);
    }

    private void insertPassData(ActionEvent e) {
        String type = (String) typeComboBox.getSelectedItem(); // Get selected type
        String validityText = validityField.getText().trim();
        String priceText = priceField.getText().trim();

        if (type == null || validityText.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int validity;
        double price;
        try {
            validity = Integer.parseInt(validityText);
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Validity must be an integer and Price must be a number!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bus_pass_system", "root", "root")) {
            String query = "INSERT INTO pass (type, validity, price) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, type);
            stmt.setInt(2, validity);
            stmt.setDouble(3, price);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Pass added successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to insert pass!", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InsertPassForm::new);
    }
}
