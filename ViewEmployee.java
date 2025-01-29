package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class ViewEmployee extends JFrame implements ActionListener {

    JTable table;
    Choice cemployeeId;
    JButton search, print, update, back;

    ViewEmployee() {
        // Set the background color
        getContentPane().setBackground(Color.WHITE);

        // Set layout to BorderLayout for better component alignment
        setLayout(new BorderLayout());

        // Panel for the search field, dropdown, and buttons aligned to the left
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));  // Align components vertically
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);  // Align the panel to the left

        // Create a new panel to hold the label and dropdown and align them left
        JPanel searchFieldPanel = new JPanel();
        searchFieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Align to the left with some space between

        // Add search label and dropdown inside the searchFieldPanel
        JLabel searchlbl = new JLabel("Search by Employee Id");
        searchFieldPanel.add(searchlbl);

        cemployeeId = new Choice();
        searchFieldPanel.add(cemployeeId);

        // Add the searchFieldPanel to the searchPanel
        searchPanel.add(searchFieldPanel);

        // Populate employee IDs in the dropdown
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from employee");
            while (rs.next()) {
                cemployeeId.add(rs.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Panel for buttons, aligned to the left
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));  // Set layout for buttons in a row with space between them

        search = new JButton("Search");
        search.addActionListener(this);
        buttonPanel.add(search);

        print = new JButton("Print");
        print.addActionListener(this);
        buttonPanel.add(print);

        update = new JButton("Update");
        update.addActionListener(this);
        buttonPanel.add(update);

        back = new JButton("Back");
        back.addActionListener(this);
        buttonPanel.add(back);

        // Add button panel to the search panel
        searchPanel.add(buttonPanel);

        // Add the search panel to the top of the frame (just above the table)
        add(searchPanel, BorderLayout.NORTH);

        // Table setup
        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);

        // Add JScrollPane (table) to the center of the frame
        add(jsp, BorderLayout.CENTER);

        // Fetch employee data and populate the table
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from employee");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set the frame to full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Full screen
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "select * from employee where empId = '" + cemployeeId.getSelectedItem() + "'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == update) {
            setVisible(false);
            new UpdateEmployee(cemployeeId.getSelectedItem());
        } else {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new ViewEmployee();
    }
}
