import java.sql.*;

public class StudentDAO {

    private Connection conn;

    private static final String URL  =
        "jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public StudentDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Driver load error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Always get a fresh/open connection
    private Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
        return conn;
    }

    // Check if a student exists by roll number
    public boolean studentExistsByRoll(String rollNo) {
        try {
            Connection c = getConnection();
            String sql = "SELECT COUNT(*) FROM students WHERE roll_no = ?";
            try (PreparedStatement stmt = c.prepareStatement(sql)) {
                stmt.setString(1, rollNo);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking duplicate roll_no: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Insert new student and return generated id
    // >0: id, 0: no row, -1: duplicate roll, -2: other error
    public int addStudentReturnId(Student s) {
        try {
            Connection c = getConnection();
            String sql = "INSERT INTO students (" +
                    "roll_no, name, email, phone, branch, year, section" +
                    ") VALUES (?,?,?,?,?,?,?)";

            try (PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                int i = 1;
                stmt.setString(i++, s.rollNo);
                stmt.setString(i++, s.name);
                stmt.setString(i++, s.email);
                stmt.setString(i++, s.phone);
                stmt.setString(i++, s.branch);
                stmt.setInt(i++, s.year);
                stmt.setString(i++, s.section);

                int affected = stmt.executeUpdate();
                if (affected == 0) return 0;

                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
                return 0;
            }
        } catch (SQLIntegrityConstraintViolationException dup) {
            System.out.println("Duplicate roll_no: " + dup.getMessage());
            return -1;
        } catch (Exception e) {
            System.out.println("Insert Error: " + e.getMessage());
            e.printStackTrace();
            return -2;
        }
    }

    // Update existing student by id
    // 1: ok, 0: not found, -2: error
    public int updateStudent(Student s) {
        try {
            Connection c = getConnection();
            String sql = "UPDATE students SET " +
                    "roll_no=?, name=?, email=?, phone=?, branch=?, year=?, section=? " +
                    "WHERE id=?";

            try (PreparedStatement stmt = c.prepareStatement(sql)) {
                int i = 1;
                stmt.setString(i++, s.rollNo);
                stmt.setString(i++, s.name);
                stmt.setString(i++, s.email);
                stmt.setString(i++, s.phone);
                stmt.setString(i++, s.branch);
                stmt.setInt(i++, s.year);
                stmt.setString(i++, s.section);
                stmt.setInt(i++, s.id);

                int affected = stmt.executeUpdate();
                if (affected == 0) return 0;
                return 1;
            }
        } catch (Exception e) {
            System.out.println("Update Error: " + e.getMessage());
            e.printStackTrace();
            return -2;
        }
    }

    // Return all students as JSON array string
    public String getAllStudentsJson() {
        try {
            Connection c = getConnection();
            String sql = "SELECT * FROM students ORDER BY id ASC";

            try (PreparedStatement stmt = c.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                StringBuilder sb = new StringBuilder();
                sb.append("[");

                boolean first = true;
                while (rs.next()) {
                    if (!first) sb.append(",");
                    first = false;

                    sb.append("{")
                            .append("\"id\":").append(rs.getInt("id")).append(",")
                            .append("\"roll_no\":\"").append(rs.getString("roll_no")).append("\",")
                            .append("\"name\":\"").append(rs.getString("name")).append("\",")
                            .append("\"email\":\"").append(rs.getString("email")).append("\",")
                            .append("\"phone\":\"").append(rs.getString("phone")).append("\",")
                            .append("\"branch\":\"").append(rs.getString("branch")).append("\",")
                            .append("\"year\":").append(rs.getInt("year")).append(",")
                            .append("\"section\":\"").append(rs.getString("section")).append("\"")
                            .append("}");
                }

                sb.append("]");
                return sb.toString();
            }
        } catch (Exception e) {
            System.out.println("List Error: " + e.getMessage());
            e.printStackTrace();
            return "[]";
        }
    }

    public void close() {
        try { if (conn != null) conn.close(); } catch (Exception ignored) {}
    }
}
