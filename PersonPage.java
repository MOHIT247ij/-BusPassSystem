import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PersonPage extends JFrame {
    private JTextField idField, nameField, typeField, addressField, contactField, cityField;
    private JButton submitButton;

    public PersonPage() {
        setTitle("Insert Data into Person Table");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(25, 25, 112)); // Dark Blue Background
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Insert Person Data");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, gbc);

        idField = createTextField(panel, "Person ID:");
        nameField = createTextField(panel, "Name:");
        typeField = createTextField(panel, "Type:");
        addressField = createTextField(panel, "Address:");
        contactField = createTextField(panel, "Contact (10 digits):");
        cityField = createTextField(panel, "City:");

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Poppins", Font.BOLD, 14));
        submitButton.setBackground(new Color(173, 216, 230)); // Light Blue
        submitButton.setForeground(Color.BLACK);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertPersonData();
            }
        });

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JTextField createTextField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        JTextField textField = new JTextField(15);
        panel.add(label);
        panel.add(textField);
        return textField;
    }

    private void insertPersonData() {
        String personID = idField.getText();
        String name = nameField.getText();
        String type = typeField.getText();
        String address = addressField.getText();
        String contact = contactField.getText();
        String city = cityField.getText();

        // Validate Contact Number (10 digits)
        if (!contact.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Contact number must be exactly 10 digits!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bus_pass_system", "root", "root");
            String query = "INSERT INTO person (p_id, name, type, address, contact, city) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(personID));
            stmt.setString(2, name);
            stmt.setString(3, type);
            stmt.setString(4, address);
            stmt.setString(5, contact);
            stmt.setString(6, city);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Data Inserted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
     SwingUtilities.invokeLater(PersonPage::new);
    }
}
