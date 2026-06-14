import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdatePassForm extends JFrame {
    private JTextField personIdField, passIdField, sourceField, destinationField;
    private JComboBox<String> monthComboBox;
    private JFormattedTextField fromDateField, toDateField;
    private JButton updateButton;

    public UpdatePassForm() {
        setTitle("Update Pass Details");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 10, 10));
        
        add(new JLabel("Person ID:"));
        personIdField = new JTextField();
        add(personIdField);

        add(new JLabel("Pass ID:"));
        passIdField = new JTextField();
        add(passIdField);

        add(new JLabel("Source:"));
        sourceField = new JTextField();
        add(sourceField);

        add(new JLabel("Destination:"));
        destinationField = new JTextField();
        add(destinationField);

        add(new JLabel("From Date:"));
        fromDateField = new JFormattedTextField();
        add(fromDateField);

        add(new JLabel("To Date:"));
        toDateField = new JFormattedTextField();
        add(toDateField);

        add(new JLabel("Month:"));
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        monthComboBox = new JComboBox<>(months);
        add(monthComboBox);

        updateButton = new JButton("Update Pass");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePassDetails();
            }
        });
        add(updateButton);
        
        setVisible(true);
    }

    private void updatePassDetails() {
        String personId = personIdField.getText();
        String passId = passIdField.getText();
        String source = sourceField.getText();
        String destination = destinationField.getText();
        String fromDate = fromDateField.getText();
        String toDate = toDateField.getText();
        String month = (String) monthComboBox.getSelectedItem();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bus_pass_system", "root", "root")) {
            String query = "UPDATE person_pass SET source = ?, destination = ?, from_date = ?, to_date = ?, month = ? WHERE person_id = ? AND pass_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, source);
            stmt.setString(2, destination);
            stmt.setString(3, fromDate);
            stmt.setString(4, toDate);
            stmt.setString(5, month);
            stmt.setString(6, personId);
            stmt.setString(7, passId);
            
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Pass details updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "No record found with given Person ID and Pass ID.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UpdatePassForm::new);
    }
}
