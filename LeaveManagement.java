package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LeaveManagement extends JFrame implements ActionListener {

    JTextField empIdField, leaveDaysField, leaveStartDateField, leaveEndDateField;
    JButton submitButton, backButton;

    LeaveManagement() {
        setLayout(new GridLayout(6, 2, 10, 10)); // Updated grid layout for 6 rows

        // Labels and Text Fields
        JLabel empIdLabel = new JLabel("Employee ID:");
        empIdField = new JTextField();

        JLabel leaveDaysLabel = new JLabel("Leave Days:");
        leaveDaysField = new JTextField();

        JLabel leaveStartDateLabel = new JLabel("Leave Start Date (YYYY-MM-DD):");
        leaveStartDateField = new JTextField();

        JLabel leaveEndDateLabel = new JLabel("Leave End Date (YYYY-MM-DD):");
        leaveEndDateField = new JTextField();

        submitButton = new JButton("Add Leave");
        submitButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.addActionListener(this);

        // Add components to frame
        add(empIdLabel);
        add(empIdField);
        add(leaveDaysLabel);
        add(leaveDaysField);
        add(leaveStartDateLabel);
        add(leaveStartDateField);
        add(leaveEndDateLabel);
        add(leaveEndDateField);
        add(submitButton);
        add(backButton);

        // Frame settings
        setTitle("Leave Management");
        setSize(400, 350); // Updated size for new fields
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submitButton) {
            try {
                int empId = Integer.parseInt(empIdField.getText().trim());
                int leaveDays = Integer.parseInt(leaveDaysField.getText().trim());
                String leaveStartDate = leaveStartDateField.getText().trim();
                String leaveEndDate = leaveEndDateField.getText().trim();

                // Validate input fields
                if (empId <= 0 || leaveDays <= 0 || leaveStartDate.isEmpty() || leaveEndDate.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter valid values for all fields.");
                    return;
                }

                // Check if the start date is before the end date
                if (leaveStartDate.compareTo(leaveEndDate) > 0) {
                    JOptionPane.showMessageDialog(this, "Start date cannot be after end date.");
                    return;
                }

                // Database connection
                Conn conn = new Conn();
                String query = "INSERT INTO Leave_Details (emp_id, leave_days, leave_start_date, leave_end_date) VALUES (?, ?, ?, ?)";

                // Use the getConnection method to create PreparedStatement
                PreparedStatement pst = conn.getConnection().prepareStatement(query);
                pst.setInt(1, empId);
                pst.setInt(2, leaveDays);
                pst.setString(3, leaveStartDate);
                pst.setString(4, leaveEndDate);

                // Execute query
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Leave added successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add leave. Please try again.");
                }

                dispose();  // Close the window after successful operation
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error with database operation: " + e.getMessage());
                e.printStackTrace();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for employee ID and leave days.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (ae.getSource() == backButton) {
            dispose();  // Close the window when 'Back' button is clicked
        }
    }

    public static void main(String[] args) {
        new LeaveManagement();
    }
}
