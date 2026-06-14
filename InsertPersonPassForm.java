import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertPersonPassForm extends JFrame {
    private JTextField personIdField, passIdField, sourceField, destinationField, fromDateField, toDateField, monthField;

    public InsertPersonPassForm() {
        setTitle("Add Person-Pass");
        setSize(400, 400);  // Adjusted size for better layout
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Use BoxLayout to align components vertically
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Create components and add them to the form
        add(createFieldPanel("Person ID:", personIdField = new JTextField()));
        add(createFieldPanel("Pass ID:", passIdField = new JTextField()));
        add(createFieldPanel("Source:", sourceField = new JTextField()));
        add(createFieldPanel("Destination:", destinationField = new JTextField()));
        add(createFieldPanel("From Date (YYYY-MM-DD):", fromDateField = new JTextField()));
        add(createFieldPanel("To Date (YYYY-MM-DD):", toDateField = new JTextField()));
        add(createFieldPanel("Month:", monthField = new JTextField()));

        // Add the submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this::insertPersonPassData);
        add(submitButton);

        // Add some space at the bottom for a cleaner look
        add(Box.createVerticalStrut(20));

        setVisible(true);
    }

    private JPanel createFieldPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        
        // Set the preferred size of the text fields to make them smaller
        textField.setPreferredSize(new Dimension(150, 25));  // Adjust the width and height as needed
        
        panel.add(textField);
        return panel;
    }

    private void insertPersonPassData(ActionEvent e) {
        String personId = personIdField.getText().trim();
        String passId = passIdField.getText().trim();
        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();
        String fromDate = fromDateField.getText().trim();
        String toDate = toDateField.getText().trim();
        String month = monthField.getText().trim();

        if (personId.isEmpty() || passId.isEmpty() || source.isEmpty() || destination.isEmpty() || fromDate.isEmpty() || toDate.isEmpty() || month.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bus_pass_system", "root", "root")) {
            String query = "INSERT INTO person_pass (person_id, pass_id, source, destination, from_date, to_date, month) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(personId));
            stmt.setInt(2, Integer.parseInt(passId));
            stmt.setString(3, source);
            stmt.setString(4, destination);
            stmt.setDate(5, java.sql.Date.valueOf(fromDate));
            stmt.setDate(6, java.sql.Date.valueOf(toDate));
            stmt.setString(7, month);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Person-Pass added successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to insert person-pass!", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format! Use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InsertPersonPassForm::new);
    }
}
