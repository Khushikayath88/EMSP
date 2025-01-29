package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JTextField tfusername;
    JPasswordField pfpassword;
    JCheckBox showPassword;
    JButton login, addUser;

    Login() {
        // Set the layout to null for manual positioning
        setLayout(null);

        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Set full-screen window
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.WHITE);

        // Username label
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(screenWidth / 4, screenHeight / 5, 150, 30);
        lblusername.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblusername);

        // Username text field
        tfusername = new JTextField();
        tfusername.setBounds((screenWidth / 4) + 150, screenHeight / 5, 250, 30);
        add(tfusername);

        // Password label
        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(screenWidth / 4, screenHeight / 5 + 50, 150, 30);
        lblpassword.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblpassword);

        // Password field
        pfpassword = new JPasswordField();
        pfpassword.setBounds((screenWidth / 4) + 150, screenHeight / 5 + 50, 250, 30);
        add(pfpassword);

        // Show password checkbox
        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds((screenWidth / 4) + 150, screenHeight / 5 + 90, 200, 30);
        showPassword.setBackground(Color.WHITE);
        showPassword.addActionListener(this);
        add(showPassword);

        // Login button
        login = new JButton("LOGIN");
        login.setBounds(screenWidth / 4, screenHeight / 5 + 150, 150, 40);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        // Add New User button
        addUser = new JButton("Add New User");
        addUser.setBounds((screenWidth / 4) + 200, screenHeight / 5 + 150, 200, 40);
        addUser.setBackground(Color.BLACK);
        addUser.setForeground(Color.WHITE);
        addUser.addActionListener(this);
        add(addUser);

        // Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/second.jpg"));
        Image i2 = i1.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds((3 * screenWidth) / 4 - 250, screenHeight / 7, 250, 270);
        add(image);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == showPassword) {
            // Toggle password visibility
            if (showPassword.isSelected()) {
                pfpassword.setEchoChar((char) 0);
            } else {
                pfpassword.setEchoChar('*');
            }
        } else if (ae.getSource() == login) {
            try {
                String username = tfusername.getText();
                String password = new String(pfpassword.getPassword());

                // Validations
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter both username and password");
                    return;
                }

                Conn c = new Conn();
                String query = "select * from login where username = '" + username + "' and password = '" + password + "'";

                ResultSet rs = c.s.executeQuery(query);
                if (rs.next()) {
                    setVisible(false);
                    new Home();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == addUser) {
            // Redirect to add new user functionality
            setVisible(false);
            new AddUser(); // AddUser is a separate class you can define to handle user registration
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
