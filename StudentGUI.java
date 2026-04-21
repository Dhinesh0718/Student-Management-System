
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentGUI extends JFrame {

    JTextField idField, nameField, ageField;
    JTextArea outputArea;
    JLabel totalLabel;

    Connection con;

    public StudentGUI() {

        // DB connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/studentdb",
                    "root",
                    "sql123"
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e);
        }

        setTitle("Student Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color bg = new Color(18, 18, 18);
        Color sidebarBg = new Color(28, 28, 28);

        getContentPane().setBackground(bg);

        // 🔹 Sidebar
        JPanel sidebar = new JPanel(new GridLayout(5, 1, 10, 10));
        sidebar.setBackground(sidebarBg);
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        sidebar.setPreferredSize(new Dimension(200, 0));

        JButton addBtn = createBtn("Add", new Color(0, 180, 0));
        JButton viewBtn = createBtn("View", new Color(0, 120, 220));
        JButton searchBtn = createBtn("Search", new Color(255, 140, 0));
        JButton deleteBtn = createBtn("Delete", new Color(200, 50, 50));
        JButton updateBtn = createBtn("Update", new Color(255, 200, 0));

        sidebar.add(addBtn);
        sidebar.add(viewBtn);
        sidebar.add(searchBtn);
        sidebar.add(deleteBtn);
        sidebar.add(updateBtn);

        add(sidebar, BorderLayout.WEST);

        // 🔹 Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(bg);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 🔹 Dashboard
        JPanel topPanel = new JPanel(new GridLayout(1, 1));
        topPanel.setBackground(bg);

        totalLabel = createCard("Total Students: 0", new Color(0, 150, 255));
        topPanel.add(totalLabel);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 🔹 Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        inputPanel.setBackground(new Color(35, 35, 35));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        idField = new JTextField();
        nameField = new JTextField();
        ageField = new JTextField();

        inputPanel.add(label("ID:"));
        inputPanel.add(idField);
        inputPanel.add(label("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(label("Age:"));
        inputPanel.add(ageField);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // 🔹 Output panel (FIXED SIZE)
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(Color.GREEN);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setPreferredSize(new Dimension(0, 250));

        mainPanel.add(scroll, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        // 🔹 Actions
        addBtn.addActionListener(e -> {
            if (validateInput()) {
                addStudent();
                updateCount();
            }
        });

        viewBtn.addActionListener(e -> viewStudents());

        searchBtn.addActionListener(e -> {
            if (!idField.getText().isEmpty()) {
                searchStudent();
            } else {
                showMsg("Enter ID");
            }
        });

        deleteBtn.addActionListener(e -> {
            if (!idField.getText().isEmpty()) {
                deleteStudent();
                updateCount();
            } else {
                showMsg("Enter ID");
            }
        });

        updateBtn.addActionListener(e -> {
            if (validateInput()) {
                updateStudent();
                updateCount();
            }
        });

        updateCount();
        setVisible(true);
    }

    // 🔥 Clean Button (FIXED)
    JButton createBtn(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(color.brighter());
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }
        });

        return btn;
    }

    JLabel createCard(String text, Color color) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setOpaque(true);
        l.setBackground(color);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Arial", Font.BOLD, 20));
        l.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        return l;
    }

    JLabel label(String t) {
        JLabel l = new JLabel(t);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Arial", Font.BOLD, 14));
        return l;
    }

    void showMsg(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    // 🔹 Validation
    boolean validateInput() {
        try {
            Integer.parseInt(idField.getText());
            Integer.parseInt(ageField.getText());
        } catch (Exception e) {
            showMsg("ID & Age must be numbers!");
            return false;
        }

        if (nameField.getText().isEmpty()) {
            showMsg("Name required!");
            return false;
        }

        return true;
    }

    // 🔹 Dashboard update
    void updateCount() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM students");
            if (rs.next()) {
                totalLabel.setText("Total Students: " + rs.getInt(1));
            }
        } catch (Exception e) {
            totalLabel.setText("Error loading count");
        }
    }

    // 🔹 DB methods
    void addStudent() {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO students VALUES (?, ?, ?)");
            ps.setInt(1, Integer.parseInt(idField.getText()));
            ps.setString(2, nameField.getText());
            ps.setInt(3, Integer.parseInt(ageField.getText()));
            ps.executeUpdate();
            outputArea.setText("Added Successfully");
        } catch (Exception e) {
            outputArea.setText(e.toString());
        }
    }

    void viewStudents() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            outputArea.setText("ID   NAME   AGE\n------------------\n");

            while (rs.next()) {
                outputArea.append(
                        rs.getInt(1) + "   "
                        + rs.getString(2) + "   "
                        + rs.getInt(3) + "\n"
                );
            }

        } catch (Exception e) {
            outputArea.setText(e.toString());
        }
    }

    void searchStudent() {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM students WHERE id=?");
            ps.setInt(1, Integer.parseInt(idField.getText()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                outputArea.setText("Found:\n" + rs.getString(2) + " | Age: " + rs.getInt(3));
            } else {
                outputArea.setText("Not Found");
            }

        } catch (Exception e) {
            outputArea.setText(e.toString());
        }
    }

    void deleteStudent() {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM students WHERE id=?");
            ps.setInt(1, Integer.parseInt(idField.getText()));
            int rows = ps.executeUpdate();

            outputArea.setText(rows > 0 ? "Deleted" : "Not Found");

        } catch (Exception e) {
            outputArea.setText(e.toString());
        }
    }

    void updateStudent() {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE students SET name=?, age=? WHERE id=?");
            ps.setString(1, nameField.getText());
            ps.setInt(2, Integer.parseInt(ageField.getText()));
            ps.setInt(3, Integer.parseInt(idField.getText()));

            int rows = ps.executeUpdate();
            outputArea.setText(rows > 0 ? "Updated" : "Not Found");

        } catch (Exception e) {
            outputArea.setText(e.toString());
        }
    }

    public static void main(String[] args) {
        new StudentGUI();
    }
}
