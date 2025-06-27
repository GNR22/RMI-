import java.rmi.Remote;                    // --> Base interface for all remote objects
import java.rmi.RemoteException;           // --> Exception thrown during remote method calls
import java.util.List;                     // --> Used to handle collections of students/courses
import java.util.Map;                      // --> Represents key-value pairs for each student or course

/* Brief expl: This class defines a remote interface for RMI. It allows a client to send and 
retrieve student and course data (as lists of maps) to/from a server remotely. */ 

public interface RemoteService extends Remote {  // --> Declares a remote interface for RMI communication

    // Method to store a list of students remotely
    void storeStudent(List<Map<String, String>> students) throws RemoteException;
    // --> Accepts a list of student records to store; each student is a map of field names to values

    // Method to store a list of courses remotely
    void storeCourse(List<Map<String, String>> courses) throws RemoteException;
    // --> Accepts a list of course records to store; each course is a map of field names to values

    // Method to fetch the list of students remotely
    List<Map<String, String>> getStudent() throws RemoteException;
    // --> Returns a list of student maps from the server

    // Method to fetch the list of courses remotely
    List<Map<String, String>> getCourse() throws RemoteException;
    // --> Returns a list of course maps from the server
}
