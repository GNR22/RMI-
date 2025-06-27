import java.sql.*;  // --> Required for JDBC operations
import java.util.*; // --> Needed for List, Map, HashMap, etc.

/*Brief expl:
This class (DBHelper) handles all database operations for students and courses using JDBC with PostgreSQL. 
It can:
-Connect to the DB
-Insert lists of students/courses (ignoring duplicates)
-Fetch all students or courses
-Uses List<Map<String, String>> to hold multiple records as key-value pairs. */
public class DBHelper {

    // Database connection info
    private static final String URL = "jdbc:postgresql://localhost:5432/ryandb";  // --> PostgreSQL database URL
    private static final String USER = "postgres";  // --> Database username
    private static final String PASSWORD = "guenryan22";  // --> Database password

    // Method to get a connection to the PostgreSQL database
    private static Connection getConnection() throws SQLException {
        // --> Loads the PostgreSQL JDBC driver
        try {
            Class.forName("org.postgresql.Driver");  // --> Loads the PostgreSQL driver
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();  // -->

        return DriverManager.getConnection(URL, USER, PASSWORD);  // --> Establishes and returns DB connection
    }

    // ====================== STUDENTS ======================

    // Insert students into the students table
    public static void insertStudents(List<Map<String, String>> students) {
        String sql = "INSERT INTO students (id, name, age, address, contact_number) " +
             "VALUES (?, ?, ?, ?, ?) ON CONFLICT (id) DO NOTHING";
        // The above SQL statement will insert a new student if the id does not exist
        // Use ON CONFLICT to avoid duplicate entries based on the id (EXCLUSIVELY FOR POSTGRESQL)
        // The DO NOTHING clause means that if a conflict occurs, the insert will be skipped (EXCLUSIVELY FOR POSTGRESQL)

        // --> Prepares the SQL statement for execution
        try (Connection conn = getConnection();  // --> Auto-closes connection
             PreparedStatement ps = conn.prepareStatement(sql)) {  // --> Prepares statement to insert data

            // --> The for loop iterates through each student map in the list of students.
            for (Map<String, String> student : students) {
                ps.setString(1, student.get("id"));  // --> Sets student ID
                ps.setString(2, student.get("name"));  // --> Sets name
                ps.setInt(3, Integer.parseInt(student.get("age")));  // --> Sets age (converted from String to int)
                ps.setString(4, student.get("address"));  // --> Sets address
                ps.setString(5, student.get("contact_number"));  // --> Sets contact number
                ps.addBatch();  // --> Adds to batch for execution
            }

            ps.executeBatch();  // --> Executes all inserts as a batch
            System.out.println("Student data inserted successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting student data: " + e.getMessage());
            e.printStackTrace();  // --> Prints detailed SQL error
        }
    }

    // Fetch all students from the students table
    public static List<Map<String, String>> fetchStudents() {
        // --> List to hold student records AKA list of maps
        List<Map<String, String>> studentsList = new ArrayList<>();  // --> Will store all fetched student records
        String sql = "SELECT * FROM students";  // --> Query to fetch all records

        // --> Executes the SQL query and retrieves results
         // --> Uses try-with-resources to ensure connection and statement are closed automatically, also prevents resource leaks
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();  // --> Creates SQL statement
             ResultSet rs = stmt.executeQuery(sql)) {  // --> Executes query and gets results

            /* The while loop reads each row of student data from the database result and stores
             it into a list of maps, where each map represents one student. */
            while (rs.next()) {
                /* Creates a map for each student. The map will hold the student's ID, name, age, address, and contact number.*/
                Map<String, String> student = new HashMap<>();// --> Creates a new map for each student
                /* The getString method retrieves the value of the specified column in the current row of the result set. */
                /* The getInt method retrieves the value of the specified column as an int. */
                student.put("id", rs.getString("id"));  // --> Reads ID from result
                student.put("name", rs.getString("name"));
                student.put("age", String.valueOf(rs.getInt("age")));  
                student.put("address", rs.getString("address"));
                student.put("contact_number", rs.getString("contact_number"));
                studentsList.add(student);  // Add the student map to the list
            }

            if (studentsList.isEmpty()) {
                System.out.println("No student data found.");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching student data: " + e.getMessage());
            e.printStackTrace();
        }
        return studentsList; // Return the list of students
    }

    // ====================== COURSES ======================

    // Insert courses into the courses table
    public static void insertCourses(List<Map<String, String>> courses) {
        String sql = "INSERT INTO courses (course_id, student_id, course_title, course_description) " +
             "VALUES (?, ?, ?, ?) ON CONFLICT (course_id) DO NOTHING";
        // The above SQL statement will insert a new course if the course_id does not exist
        // Use ON CONFLICT to avoid duplicate entries based on the course_id
        // The DO NOTHING clause means that if a conflict occurs, the insert will be skipped

        // --> Prepares the SQL statement for execution
        try (Connection conn = getConnection();  // --> Auto-managed connection
             PreparedStatement ps = conn.prepareStatement(sql)) {  // --> Prepares course insert

            /* The for loop iterates through each course map in the list of courses. */
            for (Map<String, String> course : courses) { //
                ps.setString(1, course.get("course_id"));  // --> Sets course ID
                ps.setString(2, course.get("student_id"));  // --> Links to student
                ps.setString(3, course.get("course_title"));  // --> Sets title
                ps.setString(4, course.get("course_description"));  // --> Sets description
                ps.addBatch();  // --> Batch insert
            }

            ps.executeBatch();  // --> Executes all course insertions
            System.out.println("Course data inserted successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting course data: " + e.getMessage());
            e.printStackTrace();  // --> Detailed error
        }
    }

    // Fetch all courses from the courses table
    public static List<Map<String, String>> fetchCourses() {
        List<Map<String, String>> courses = new ArrayList<>();  // --> To hold course records
        String sql = "SELECT * FROM courses";  // --> Query to fetch all courses

        // --> Executes the SQL query and retrieves results
        // --> Uses try-with-resources to ensure connection and statement are closed automatically, also prevents resource leaks
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, String> course = new HashMap<>();
                course.put("course_id", rs.getString("course_id"));  // --> Read course ID
                course.put("student_id", rs.getString("student_id"));  // --> Read student link
                course.put("course_title", rs.getString("course_title"));
                course.put("course_description", rs.getString("course_description"));
                courses.add(course);  // --> Add course map to list
            }

            if (courses.isEmpty()) {
                System.out.println("No course data found.");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching course data: " + e.getMessage());
            e.printStackTrace();  // --> Debugging SQL issues
        }
        return courses;
    }
}

}