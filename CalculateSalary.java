package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CalculateSalary extends JFrame implements ActionListener {

    JTextField tfEmpId, tfLeaveDays, tfNewSalary;
    JButton calculateSalary, updateSalary;
    JLabel lblEmpId, lblLeaveDays, lblOldSalary, lblNewSalary;

    CalculateSalary() {
        setTitle("Calculate Salary");
        setLayout(new GridLayout(5, 2, 10, 10));  // Use GridLayout for consistent spacing

        // Labels
        lblEmpId = new JLabel("Enter Employee ID:");
        lblLeaveDays = new JLabel("Enter Leave Days:");
        lblOldSalary = new JLabel("Old Salary: ");
        lblNewSalary = new JLabel("Updated Salary: ");
        
        // Text Fields
        tfEmpId = new JTextField(15);
        tfLeaveDays = new JTextField(15);
        tfNewSalary = new JTextField(15);
        tfNewSalary.setEditable(false); // This will be updated after calculation
        
        // Buttons
        calculateSalary = new JButton("Calculate Salary");
        updateSalary = new JButton("Update Salary");

        // Add Action Listeners
        calculateSalary.addActionListener(this);
        updateSalary.addActionListener(this);

        // Adding Components to Frame
        add(lblEmpId);
        add(tfEmpId);
        add(lblLeaveDays);
        add(tfLeaveDays);
        add(calculateSalary);
        add(lblOldSalary);
        add(lblNewSalary);
        add(tfNewSalary);
        add(updateSalary);

        // Frame Settings
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == calculateSalary) {
            String empId = tfEmpId.getText().trim();
            String leaveDaysStr = tfLeaveDays.getText().trim();
            
            try {
                // Validate Input
                if (empId.isEmpty() || leaveDaysStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter all fields.");
                    return;
                }
                
                int leaveDays = Integer.parseInt(leaveDaysStr);
                Conn conn = new Conn();
                
                // Fetch employee details from database using empId
                String query = "SELECT salary FROM employee WHERE empId = '" + empId + "'";
                ResultSet rs = conn.s.executeQuery(query);
                
                if (rs.next()) {
                    double oldSalary = rs.getDouble("salary");
                    
                    // Assuming daily salary is the total salary divided by 30 days in a month
                    double dailySalary = oldSalary / 30;
                    double totalDeduction = dailySalary * leaveDays;
                    double updatedSalary = oldSalary - totalDeduction;
                    
                    lblOldSalary.setText("Old Salary: " + oldSalary);
                    tfNewSalary.setText(String.valueOf(updatedSalary));
                } else {
                    JOptionPane.showMessageDialog(null, "Employee ID not found.");
                }
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (ae.getSource() == updateSalary) {
            String empId = tfEmpId.getText().trim();
            String newSalaryStr = tfNewSalary.getText().trim();

            if (empId.isEmpty() || newSalaryStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please calculate the salary first.");
                return;
            }

            try {
                double updatedSalary = Double.parseDouble(newSalaryStr);
                Conn conn = new Conn();
                
                // Update the new salary in the database
                String updateQuery = "UPDATE employee SET salary = '" + updatedSalary + "' WHERE empId = '" + empId + "'";
                conn.s.executeUpdate(updateQuery);

                JOptionPane.showMessageDialog(null, "Salary updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new CalculateSalary();
    }
}
