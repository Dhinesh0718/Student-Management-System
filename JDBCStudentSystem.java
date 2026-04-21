
import java.sql.*;
import java.util.Scanner;

public class JDBCStudentSystem {

    static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "root";     // change if needed
    static final String PASS = "sql123";     // change your password

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            // Load driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create connection
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            System.out.println("✅ Connected to MySQL!");

            while (true) {
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Search Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Update Student");
                System.out.println("6. Exit");
                System.out.print("Enter choice: ");

                int ch = sc.nextInt();

                switch (ch) {
                    case 1:
                        addStudent(con);
                        break;
                    case 2:
                        viewStudents(con);
                        break;
                    case 3:
                        searchStudent(con);
                        break;
                    case 4:
                        deleteStudent(con);
                        break;
                    case 5:
                        updateStudent(con);
                        break;
                    case 6:
                        con.close();
                        System.out.println("Exit...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e);
        }
    }

    static void addStudent(Connection con) {
        try {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = sc.nextInt();

            String sql = "INSERT INTO students VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);

            ps.executeUpdate();
            System.out.println("✅ Student Inserted!");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e);
        }
    }

    static void viewStudents(Connection con) {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            System.out.println("\n--- Student List ---");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | "
                        + rs.getString("name") + " | "
                        + rs.getInt("age")
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e);
        }
    }

    static void searchStudent(Connection con) {
        try {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();

            String sql = "SELECT * FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Found: " + rs.getString("name"));
            } else {
                System.out.println("❌ Student not found");
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e);
        }
    }

    static void deleteStudent(Connection con) {
        try {
            System.out.print("Enter ID: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Deleted!");
            } else {
                System.out.println("❌ Student not found");
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e);
        }
    }

    static void updateStudent(Connection con) {
        try {
            System.out.print("Enter ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter new Name: ");
            String name = sc.nextLine();

            System.out.print("Enter new Age: ");
            int age = sc.nextInt();

            String sql = "UPDATE students SET name=?, age=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Updated!");
            } else {
                System.out.println("❌ Student not found");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
