package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {

    JMenuBar menuBar;
    JMenu employeeMenu, logoutMenu, hrMenu;
    JMenuItem addEmployee, viewEmployees, updateEmployee, removeEmployee, logout, leaveManagement, calculateSalary, viewLeaveHistory;

    Home() {

        // Set layout and title
        setTitle("Employee Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set window to full screen
        setLocation(0, 0); // Position the window at the top-left corner
        setLayout(new BorderLayout());

        // Menu Bar setup
        menuBar = new JMenuBar();

        // Employee menu
        employeeMenu = new JMenu("Employee");
        employeeMenu.setFont(new Font("Arial", Font.PLAIN, 15));

        addEmployee = new JMenuItem("Add Employee");
        addEmployee.addActionListener(this);
        employeeMenu.add(addEmployee);

        viewEmployees = new JMenuItem("View Employees");
        viewEmployees.addActionListener(this);
        employeeMenu.add(viewEmployees);

        updateEmployee = new JMenuItem("Update Employee");
        updateEmployee.addActionListener(this);
        employeeMenu.add(updateEmployee);

        removeEmployee = new JMenuItem("Remove Employee");
        removeEmployee.addActionListener(this);
        employeeMenu.add(removeEmployee);

        menuBar.add(employeeMenu);

        // HR Menu for Leave Management and Salary Calculation
        hrMenu = new JMenu("HR");
        hrMenu.setFont(new Font("Arial", Font.PLAIN, 15));

        leaveManagement = new JMenuItem("Leave Management");
        leaveManagement.addActionListener(this);
        hrMenu.add(leaveManagement);

        calculateSalary = new JMenuItem("Calculate Salary");
        calculateSalary.addActionListener(this);
        hrMenu.add(calculateSalary);

        // Add the View Leave History option
        viewLeaveHistory = new JMenuItem("View Leave History");
        viewLeaveHistory.addActionListener(this);
        hrMenu.add(viewLeaveHistory);

        menuBar.add(hrMenu);

        // Logout menu
        logoutMenu = new JMenu("Logout");
        logoutMenu.setFont(new Font("Arial", Font.PLAIN, 15));

        logout = new JMenuItem("Logout");
        logout.addActionListener(this);
        logoutMenu.add(logout);

        menuBar.add(logoutMenu);

        setJMenuBar(menuBar);

        // Main Panel for Heading
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new GridBagLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Frame settings
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addEmployee) {
            setVisible(false);
            new AddEmployee();
        } else if (ae.getSource() == viewEmployees) {
            setVisible(false);
            new ViewEmployee();
        } else if (ae.getSource() == updateEmployee) {
            setVisible(false);
            new ViewEmployee(); // Assuming you reuse ViewEmployee for updates
        } else if (ae.getSource() == removeEmployee) {
            setVisible(false);
            new RemoveEmployee();
        } else if (ae.getSource() == leaveManagement) {
            new LeaveManagement(); // Opens Leave Management form
        } else if (ae.getSource() == calculateSalary) {
            new CalculateSalary(); // Opens Salary Calculation form
        } else if (ae.getSource() == viewLeaveHistory) {
            String empIdStr = JOptionPane.showInputDialog(this, "Enter Employee ID to view leave history:");
            if (empIdStr != null && !empIdStr.trim().isEmpty()) {
                try {
                    int empId = Integer.parseInt(empIdStr);
                    new LeaveHistory(empId); // Open the leave history screen for the entered empId
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid Employee ID.");
                }
            }
        } else if (ae.getSource() == logout) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                setVisible(false);
                new Login();
            }
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}
