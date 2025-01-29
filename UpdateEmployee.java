package employee.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateEmployee extends JFrame implements ActionListener {

    JTextField tfeducation, tfaddress, tfphone, tfemail, tfsalary, tfdesignation;
    JLabel lblempId, lblname, lbldob, lblaadhar, lblfname;  // Added lblfname for Father's Name
    JButton update, back;
    String empId;

    public UpdateEmployee(String empId) {
        this.empId = empId;

        // Set full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK);  // Set background to black
        headerPanel.setPreferredSize(new Dimension(getWidth(), 80));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel heading = new JLabel("Update Employee Details");
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 36));
        heading.setForeground(Color.WHITE);  // Set text color to white
        headerPanel.add(heading);
        add(headerPanel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add Components (excluding Father's Name TextField, only showing it as a label)
        addLabelAndField(mainPanel, gbc, "Name", 0, lblname = new JLabel());
        addLabelAndField(mainPanel, gbc, "Father's Name", 1, lblfname = new JLabel()); // Label for Father's Name
        addLabelAndField(mainPanel, gbc, "Date of Birth", 2, lbldob = new JLabel());
        addLabelAndField(mainPanel, gbc, "Salary", 3, tfsalary = createTextField());
        addLabelAndField(mainPanel, gbc, "Address", 4, tfaddress = createTextField());
        addLabelAndField(mainPanel, gbc, "Phone", 5, tfphone = createTextField());
        addLabelAndField(mainPanel, gbc, "Email", 6, tfemail = createTextField());
        addLabelAndField(mainPanel, gbc, "Highest Education", 7, tfeducation = createTextField());
        addLabelAndField(mainPanel, gbc, "Designation", 8, tfdesignation = createTextField());
        addLabelAndField(mainPanel, gbc, "Aadhar Number", 9, lblaadhar = new JLabel());
        addLabelAndField(mainPanel, gbc, "Employee ID", 10, lblempId = new JLabel());

        add(mainPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        update = createButton("Update Details", Color.BLACK, Color.WHITE);  // Set background to black
        back = createButton("Back", Color.BLACK, Color.WHITE);  // Set background to black
        buttonPanel.add(update);
        buttonPanel.add(back);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load Employee Data
        loadEmployeeDetails();

        // Action Listeners
        update.addActionListener(this);
        back.addActionListener(this);

        setVisible(true);
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String labelText, int row, Component field) {
        JLabel label = new JLabel(labelText + ":");
        label.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);

        field.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        if (field instanceof JTextField) {
            ((JTextField) field).setPreferredSize(new Dimension(200, 40)); // Smaller field size
        }
        gbc.gridx = 5;
        gbc.gridy = row;
        panel.add(field, gbc);
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("SAN_SERIF", Font.PLAIN, 30));
        textField.setPreferredSize(new Dimension(100, 40)); // Reduced width
        textField.setMinimumSize(new Dimension(300, 20)); // Ensure minimum size
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Border for visibility
        textField.setMargin(new Insets(5, 10, 5, 10)); // Padding inside text field
        return textField;
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        button.setBackground(bg);  // Set button background to black
        button.setForeground(fg);  // Set text color to white
        button.setPreferredSize(new Dimension(200, 50));
        button.setFocusPainted(false);
        return button;
    }

    private void loadEmployeeDetails() {
        try {
            Conn c = new Conn();
            String query = "select * from employee where empId = '" + empId + "'";
            ResultSet rs = c.s.executeQuery(query);

            if (rs.next()) {
                lblname.setText(rs.getString("name"));
                lblfname.setText(rs.getString("fname"));  // Displaying Father's Name as label
                lbldob.setText(rs.getString("dob"));
                tfaddress.setText(rs.getString("address"));
                tfsalary.setText(rs.getString("salary"));
                tfphone.setText(rs.getString("phone"));
                tfemail.setText(rs.getString("email"));
                tfeducation.setText(rs.getString("education"));
                lblaadhar.setText(rs.getString("aadhar"));
                lblempId.setText(rs.getString("empId"));
                tfdesignation.setText(rs.getString("designation"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            String salary = tfsalary.getText();
            String address = tfaddress.getText();
            String phone = tfphone.getText();
            String email = tfemail.getText();
            String education = tfeducation.getText();
            String designation = tfdesignation.getText();

            try {
                Conn conn = new Conn();
                String query = "update employee set salary = '" + salary + "', address = '" + address + 
                               "', phone = '" + phone + "', email = '" + email + "', education = '" + education + 
                               "', designation = '" + designation + "' where empId = '" + empId + "'";
                conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Details updated successfully");
                setVisible(false);
                new Home();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new UpdateEmployee("");
    }
}
