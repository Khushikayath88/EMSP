package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddUser extends JFrame implements ActionListener {
    
    JTextField tfusername;
    JPasswordField pfpassword, pfconfirmPassword;
    JButton addUser, back;
    
    AddUser() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(40, 20, 100, 30);
        add(lblusername);
        
        tfusername = new JTextField();
        tfusername.setBounds(150, 20, 150, 30);
        add(tfusername);
        
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(40, 70, 100, 30);
        add(lblpassword);
        
        pfpassword = new JPasswordField();
        pfpassword.setBounds(150, 70, 150, 30);
        add(pfpassword);
        
        JLabel lblconfirmPassword = new JLabel("Confirm Password");
        lblconfirmPassword.setBounds(40, 120, 150, 30);
        add(lblconfirmPassword);
        
        pfconfirmPassword = new JPasswordField();
        pfconfirmPassword.setBounds(150, 120, 150, 30);
        add(pfconfirmPassword);
        
        addUser = new JButton("Add User");
        addUser.setBounds(150, 180, 100, 30);
        addUser.setBackground(Color.BLACK);
        addUser.setForeground(Color.WHITE);
        addUser.addActionListener(this);
        add(addUser);
        
        back = new JButton("Back");
        back.setBounds(260, 180, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);
        
        setSize(450, 300);
        setLocation(500, 250);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addUser) {
            try {
                String username = tfusername.getText();
                String password = new String(pfpassword.getPassword());
                String confirmPassword = new String(pfconfirmPassword.getPassword());
                
                // Validations
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required");
                    return;
                }
                
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match");
                    return;
                }
                
                Conn c = new Conn();
                String query = "insert into login (username, password) values ('" + username + "', '" + password + "')";
                
                int result = c.s.executeUpdate(query);
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "User added successfully");
                    setVisible(false);
                    new Login();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add user");
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                JOptionPane.showMessageDialog(null, "Username already exists. Please choose another username.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }
    
    public static void main(String[] args) {
        new AddUser();
    }
}
