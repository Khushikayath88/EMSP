package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LeaveHistory extends JFrame {

    JTextArea leaveHistoryArea;
    JScrollPane scrollPane;
    JButton backButton;

    LeaveHistory(int empId) {
        setTitle("Leave History");
        setLayout(new BorderLayout());

        // Create a Text Area to display leave records
        leaveHistoryArea = new JTextArea(20, 40);
        leaveHistoryArea.setEditable(false);

        // Add a scroll pane to the text area
        scrollPane = new JScrollPane(leaveHistoryArea);

        // Add back button
        backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        // Fetch and display leave history
        fetchLeaveHistory(empId);

        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void fetchLeaveHistory(int empId) {
        try {
            // Create connection to the database
            Conn conn = new Conn();

            // SQL query to fetch leave history for a specific employee
            String query = "SELECT leave_start_date, leave_end_date, leave_days FROM Leave_Details WHERE emp_id = ? ORDER BY leave_start_date DESC";
            PreparedStatement pst = conn.c.prepareStatement(query);
            pst.setInt(1, empId);

            // Execute the query
            ResultSet rs = pst.executeQuery();

            // Display the result in the text area
            StringBuilder history = new StringBuilder();
            history.append("Leave History for Employee ID: " + empId + "\n\n");
            while (rs.next()) {
                String leaveStartDate = rs.getString("leave_start_date");
                String leaveEndDate = rs.getString("leave_end_date");
                int leaveDays = rs.getInt("leave_days");

                history.append("Leave Start Date: " + leaveStartDate + " | Leave End Date: " + leaveEndDate + " | Days: " + leaveDays + "\n");
            }

            // Set the text in the text area
            leaveHistoryArea.setText(history.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Pass employee ID as an example (replace with actual emp ID)
        new LeaveHistory(101);
    }
}
