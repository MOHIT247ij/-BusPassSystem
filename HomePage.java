import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    public HomePage() {
        setTitle("Home Page");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton addPersonButton = new JButton("Add Person");
        JButton addPassButton = new JButton("Add Pass");
        JButton personPassButton = new JButton("Add Person-Pass");
        JButton reportButton = new JButton("Reports");
        JButton updateButton = new JButton("Update Data");

        // Action Listeners
        addPersonButton.addActionListener(e -> new InsertPersonForm());
        addPassButton.addActionListener(e -> new InsertPassForm());
        personPassButton.addActionListener(e -> new InsertPersonPassForm());
        reportButton.addActionListener(e -> {
            System.out.println("Reports button clicked! Opening GenerateReportForm...");
            new GenerateReportForm();
        });
        updateButton.addActionListener(e -> {
            System.out.println("Update button clicked! Opening UpdatePassForm...");
            new UpdatePassForm();
        });

        add(addPersonButton);
        add(addPassButton);
        add(personPassButton);
        add(reportButton);
        add(updateButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomePage::new);
    }
}
