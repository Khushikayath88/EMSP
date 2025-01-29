package employee.management.system;

import java.awt.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

public class AddEmployee extends JFrame implements ActionListener {

    Random ran = new Random();
    int number = ran.nextInt(999999);

    JTextField tfname, tffname, tfaddress, tfphone, tfaadhar, tfemail, tfsalary, tfdesignation;
    JDateChooser dcdob;
    JComboBox<String> cbeducation;
    JLabel lblempId;
    JButton add, back;

    AddEmployee() {
        setLayout(new BorderLayout());

        // Header
        JLabel heading = new JLabel("Add Employee Details", JLabel.CENTER);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 30));
        heading.setOpaque(true);
        heading.setBackground(Color.BLACK); // Black background
        heading.setForeground(Color.WHITE); // White text
        heading.setPreferredSize(new Dimension(getWidth(), 80));
        add(heading, BorderLayout.NORTH);

        // Main Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components
        addFormRow(formPanel, gbc, "Name", tfname = new JTextField());
        addFormRow(formPanel, gbc, "Father's Name", tffname = new JTextField());
        addFormRow(formPanel, gbc, "Date of Birth", dcdob = new JDateChooser(), "yyyy-MM-dd");
        addFormRow(formPanel, gbc, "Salary", tfsalary = new JTextField());
        addFormRow(formPanel, gbc, "Address", tfaddress = new JTextField());
        addFormRow(formPanel, gbc, "Phone", tfphone = new JTextField());
        addFormRow(formPanel, gbc, "Email", tfemail = new JTextField());
        addFormRow(formPanel, gbc, "Highest Education", cbeducation = new JComboBox<>(new String[]{"BBA", "BCA", "BA", "BSC", "B.COM", "BTech", "MBA", "MCA", "MA", "MTech", "MSC", "PHD"}));
        addFormRow(formPanel, gbc, "Designation", tfdesignation = new JTextField());
        addFormRow(formPanel, gbc, "Aadhar Number", tfaadhar = new JTextField());
        addFormRow(formPanel, gbc, "Employee ID", lblempId = new JLabel("" + number));

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        add = createButton("Add Details", Color.BLACK, Color.WHITE); // Black button
        back = createButton("Back", Color.BLACK, Color.WHITE); // Black button
        buttonPanel.add(add);
        buttonPanel.add(back);
        add(buttonPanel, BorderLayout.SOUTH);

        // Frame Settings
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full-screen mode
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Add Action Listeners
        add.addActionListener(this);
        back.addActionListener(this);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, String label, JComponent component) {
        addFormRow(panel, gbc, label, component, null);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, String label, JComponent component, String dateFormat) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel lbl = new JLabel(label + ":");
        lbl.setFont(new Font("serif", Font.PLAIN, 20));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        component.setPreferredSize(new Dimension(250, 30));
        panel.add(component, gbc);

        if (component instanceof JDateChooser && dateFormat != null) {
            ((JDateChooser) component).setDateFormatString(dateFormat);
        }
    }

    private JButton createButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(new Font("serif", Font.BOLD, 18));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            String name = tfname.getText().trim();
            String fname = tffname.getText().trim();
            String dob = ((JTextField) dcdob.getDateEditor().getUiComponent()).getText().trim();
            String salary = tfsalary.getText().trim();
            String address = tfaddress.getText().trim();
            String phone = tfphone.getText().trim();
            String email = tfemail.getText().trim();
            String education = (String) cbeducation.getSelectedItem();
            String designation = tfdesignation.getText().trim();
            String aadhar = tfaadhar.getText().trim();
            String empId = lblempId.getText().trim();

            try {
                // Validations
                if (name.isEmpty() || fname.isEmpty() || dob.isEmpty() || salary.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty() || designation.isEmpty() || aadhar.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    JOptionPane.showMessageDialog(null, "Invalid Email Format", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (phone.length() != 10 || !phone.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Phone number must be 10 digits", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (aadhar.length() != 12 || !aadhar.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Aadhar number must be 12 digits", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                LocalDate dobDate = LocalDate.parse(dob);
                LocalDate today = LocalDate.now();
                Period age = Period.between(dobDate, today);
                if (age.getYears() < 18) {
                    JOptionPane.showMessageDialog(null, "Employee must be at least 18 years old", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Conn conn = new Conn();
                String query = "insert into employee values('" + name + "', '" + fname + "', '" + dob + "', '" + salary + "', '" + address + "', '" + phone + "', '" + email + "', '" + education + "', '" + designation + "', '" + aadhar + "', '" + empId + "')";
                conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Details added successfully");
                setVisible(false);
                new Home();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new AddEmployee();
    }
}  
