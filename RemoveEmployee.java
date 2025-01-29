package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class RemoveEmployee extends JFrame implements ActionListener {

    Choice cEmpId;
    JButton delete, back;
    JLabel lblname, lblphone, lblemail;

    RemoveEmployee() {
        setLayout(new BorderLayout());

        // Header (Black Background with White Text)
        JLabel header = new JLabel("Remove Employee", JLabel.CENTER);
        header.setFont(new Font("SAN_SERIF", Font.BOLD, 36));
        header.setOpaque(true);
        header.setBackground(Color.BLACK); // Black background
        header.setForeground(Color.WHITE); // White text
        header.setPreferredSize(new Dimension(getWidth(), 80));
        add(header, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Employee ID
        JLabel labelempId = new JLabel("Employee ID:");
        labelempId.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(labelempId, gbc);

        cEmpId = new Choice();
        cEmpId.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(cEmpId, gbc);

        // Fetch Employee IDs
        try {
            Conn c = new Conn();
            String query = "select empId from employee";
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                cEmpId.add(rs.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Name
        JLabel labelname = new JLabel("Name:");
        labelname.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(labelname, gbc);

        lblname = new JLabel();
        lblname.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(lblname, gbc);

        // Phone
        JLabel labelphone = new JLabel("Phone:");
        labelphone.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(labelphone, gbc);

        lblphone = new JLabel();
        lblphone.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(lblphone, gbc);

        // Email
        JLabel labelemail = new JLabel("Email:");
        labelemail.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(labelemail, gbc);

        lblemail = new JLabel();
        lblemail.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(lblemail, gbc);

        // Fetch Details for the First Employee ID
        fetchDetails();

        // Add Item Listener for Employee ID Choice
        cEmpId.addItemListener(e -> fetchDetails());
        add(mainPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        // Delete Button (Black Background with White Text)
        delete = createButton("Delete", Color.BLACK, Color.WHITE);
        // Back Button (Black Background with White Text)
        back = createButton("Back", Color.BLACK, Color.WHITE);

        buttonPanel.add(delete);
        buttonPanel.add(back);

        add(buttonPanel, BorderLayout.SOUTH);

        // Background Image
        JLabel image = new JLabel(new ImageIcon(new ImageIcon(ClassLoader.getSystemResource("icons/delete.png"))
                .getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH)));
        image.setHorizontalAlignment(JLabel.CENTER);
        add(image, BorderLayout.EAST);

        // Frame Settings
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full-Screen Mode
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Action Listeners
        delete.addActionListener(this);
        back.addActionListener(this);
    }

    private void fetchDetails() {
        try {
            Conn c = new Conn();
            String query = "select * from employee where empId = '" + cEmpId.getSelectedItem() + "'";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                lblname.setText(rs.getString("name"));
                lblphone.setText(rs.getString("phone"));
                lblemail.setText(rs.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JButton createButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == delete) {
            try {
                Conn c = new Conn();
                String query = "delete from employee where empId = '" + cEmpId.getSelectedItem() + "'";
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Employee Information Deleted Successfully");
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
        new RemoveEmployee();
    }
}
