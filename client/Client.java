import java.rmi.registry.LocateRegistry;    // --> Used to locate the RMI registry
import java.rmi.registry.Registry;          // --> Interface to interact with the RMI registry
import java.rmi.RemoteException;            // --> Exception for remote communication issues
import java.util.List;                      // --> Used to hold student/course records
import java.util.Map;                       // --> Used to store key-value pairs for each record

/* Brief expl:
This Client class connects to the RMI server, looks up the remote "RemoteService", reads 
student and course data from XML files, sends that data to the server, then retrieves and 
prints the stored student and course data from the server. */
public class Client {
    public static void main(String[] args) {
        try {
            // Connect to the RMI Registry on localhost at port 1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Lookup the remote service registered with the name "RemoteService"
            RemoteService service = (RemoteService) registry.lookup("RemoteService");

            // Load student data from students.xml using XMLReader
            List<Map<String, String>> students = XMLReader.readXML("students.xml");

            service.storeStudent(students);  // --> Send student data to the server

            // Load course data from courses.xml using XMLReader
            List<Map<String, String>> courses = XMLReader.readXML("courses.xml");
            
            service.storeCourse(courses);    // --> Send course data to the server

            // retrieve and display student data from the server
            List<Map<String, String>> fetchedStudents = service.getStudent();
            System.out.println("Students from server:");
            for (Map<String, String> student : fetchedStudents) {
                System.out.printf("ID: %-10s Name: %-20s Age: %-3s Address: %-25s Contact: %-15s\n",
                                  student.get("id"),
                                  student.get("name"),
                                  student.get("age"),
                                  student.get("address"),
                                  student.get("contact_number"));
            }

            // retrieve and display course data from the server
            List<Map<String, String>> fetchedCourses = service.getCourse();
            System.out.println("\nCourses from server:");
            for (Map<String, String> course : fetchedCourses) {
                System.out.printf("Course ID: %-10s Student ID: %-10s Title: %-25s Description: %-40s\n",
                                  course.get("course_id"),
                                  course.get("student_id"),
                                  course.get("course_title"),
                                  course.get("course_description"));
            }
            
            // --> Print a message indicating successful data retrieval
            System.out.println("\nData retrieved successfully from the server.");
        } catch (RemoteException e) {
            System.err.println("RMI Exception: " + e.getMessage());  // --> Handle remote exceptions
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());     // --> Handle general exceptions
            e.printStackTrace();
        }
    }
}
