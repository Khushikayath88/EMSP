package employee.management.system;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Splash extends JFrame implements ActionListener {

    Splash() {
        // Set the layout to null for manual positioning
        setLayout(null);

        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Set full-screen window
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.WHITE);

        // Heading label with a smaller font to show full name
        JLabel heading = new JLabel("EMPLOYEE MANAGEMENT SYSTEM");
        heading.setBounds((screenWidth - 200) / 2, screenHeight / 5 - 100, 900, 60);
        heading.setFont(new Font("serif", Font.PLAIN, 40)); // Reduced font size
        heading.setForeground(Color.BLACK);
        add(heading);

        // Image resized to fit the screen while maintaining aspect ratio
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/front.jpg"));
        Image i2 = i1.getImage();
        
        // Calculate new width and height maintaining the aspect ratio
        double aspectRatio = (double) i2.getWidth(null) / (double) i2.getHeight(null);
        int newWidth = screenWidth;
        int newHeight = (int) (screenWidth / aspectRatio);

        // If the height exceeds the screen height, adjust the width to maintain the aspect ratio
        if (newHeight > screenHeight) {
            newHeight = screenHeight;
            newWidth = (int) (screenHeight * aspectRatio);
        }

        // Scale the image
        Image i3 = i2.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon i4 = new ImageIcon(i3);
        JLabel image = new JLabel(i4);
        image.setBounds(0, 0, newWidth, newHeight);
        add(image);

        // Button placed below the image
        JButton clickhere = new JButton("CLICK HERE TO CONTINUE");
        clickhere.setBounds((screenWidth - 300) / 2, screenHeight - 200, 300, 70);
        clickhere.setBackground(Color.BLACK);
        clickhere.setForeground(Color.WHITE);
        clickhere.addActionListener(this);
        add(clickhere);

        setSize(screenWidth, screenHeight);  // Set the size to full screen
        setLocation(0, 0);  // Top-left corner
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Login();
    }

    public static void main(String args[]) {
        new Splash();
    }
}
