import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GenerateReportForm extends JFrame {
    private JComboBox<String> monthComboBox;
    private JTable reportTable;
    private DefaultTableModel tableModel;

    public GenerateReportForm() {
        setTitle("Generate Reports");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Month Selection
        JPanel monthPanel = new JPanel();
        monthPanel.add(new JLabel("Choose a Month:"));
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        monthComboBox = new JComboBox<>(months);
        monthPanel.add(monthComboBox);
        panel.add(monthPanel, BorderLayout.NORTH);

        // Generate Report Button
        JButton generateButton = new JButton("Generate Report");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(generateButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Table for Report Display (Smaller Size)
        String[] columnNames = {"Person ID", "Pass ID", "Source", "Destination", "From", "To"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reportTable = new JTable(tableModel);
        reportTable.setPreferredScrollableViewportSize(new Dimension(450, 100)); // Minimize table size
        JScrollPane scrollPane = new JScrollPane(reportTable);
        panel.add(scrollPane, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void generateReport() {
        String selectedMonth = (String) monthComboBox.getSelectedItem();
        tableModel.setRowCount(0); // Clear previous data

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bus_pass_system", "root", "root")) {
            String query = "SELECT person_id, pass_id, source, destination, from_date, to_date FROM person_pass WHERE MONTHNAME(from_date) = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, selectedMonth);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("person_id"),
                    rs.getInt("pass_id"),
                    rs.getString("source"),
                    rs.getString("destination"),
                    rs.getString("from_date"),
                    rs.getString("to_date")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new GenerateReportForm();
    }
}