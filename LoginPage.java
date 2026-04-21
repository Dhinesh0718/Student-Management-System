
import java.awt.*;
import javax.swing.*;

public class LoginPage extends JFrame {

    JTextField userField;
    JPasswordField passField;

    public LoginPage() {

        setTitle("Login");
        setSize(300, 180);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Username:"));
        userField = new JTextField();
        panel.add(userField);

        panel.add(new JLabel("Password:"));
        passField = new JPasswordField();
        panel.add(passField);

        JButton loginBtn = new JButton("Login");
        panel.add(new JLabel()); // empty space
        panel.add(loginBtn);

        add(panel, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> checkLogin());

        setLocationRelativeTo(null);
        setResizable(false); // 🔥 prevents stretching
        setVisible(true);
    }

    void checkLogin() {
        String user = userField.getText();
        String pass = new String(passField.getPassword());

        if (user.equals("admin") && pass.equals("1234")) {
            new StudentGUI();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials");
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
