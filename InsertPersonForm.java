import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class InsertPersonForm extends JFrame {
    private JTextField nameField, typeField, addressField, contactField, cityField;

    public InsertPersonForm() {
        setTitle("Add Person");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        nameField = new JTextField(15);
        typeField = new JTextField(15);
        addressField = new JTextField(15);
        contactField = new JTextField(15);
        cityField = new JTextField(15);

        panel.add(createField("Name:", nameField));
        panel.add(createField("Type:", typeField));
        panel.add(createField("Address:", addressField));
        panel.add(createField("Contact:", contactField));
        panel.add(createField("City:", cityField));

        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this::insertPersonData);
        buttonPanel.add(submitButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createField(String labelText, JTextField textField) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));
        rowPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel label = new JLabel(labelText);
        textField.setMaximumSize(new Dimension(300, 30));

        rowPanel.add(label);
        rowPanel.add(textField);
        return rowPanel;
    }

    private void insertPersonData(ActionEvent e) {
        String name = nameField.getText().trim();
        String type = typeField.getText().trim();
        String address = addressField.getText().trim();
        String contact = contactField.getText().trim();
        String city = cityField.getText().trim();

        if (name.isEmpty() || type.isEmpty() || address.isEmpty() || contact.isEmpty() || city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!contact.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Contact must be a 10-digit number!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bus_pass_system", "root", "root")) {
            String query = "INSERT INTO person (name, type, address, contact, city) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setString(3, address);
            stmt.setString(4, contact);
            stmt.setString(5, city);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Person added successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to insert person!", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InsertPersonForm::new);
    }
}
