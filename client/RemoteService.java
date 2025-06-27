import java.rmi.Remote;                    // --> Superinterface for all remote interfaces
import java.rmi.RemoteException;           // --> Exception thrown if a remote communication error occurs
import java.util.List;                     // --> Used to represent collections of student or course data
import java.util.Map;                      // --> Used to store key-value pairs for each record

/* Brief expl:
This interface (RemoteService) declares remote methods for clients to store and retrieve lists of students 
and courses via RMI. It extends Remote and all methods can throw RemoteException. */

public interface RemoteService extends Remote {  // --> Defines remote methods that can be called from a client

    // --> The methods below allow clients to send data to the server
    void storeStudent(List<Map<String, String>> students) throws RemoteException;
    // --> Allows clients to send a list of student records to be stored on the server

    void storeCourse(List<Map<String, String>> courses) throws RemoteException;
    // --> Allows clients to send a list of course records to be stored on the server

    // --> The methods below allow clients to retrieve stored data from the server
    List<Map<String, String>> getStudent() throws RemoteException;
    // --> Allows clients to retrieve all student records from the server

    List<Map<String, String>> getCourse() throws RemoteException;
    // --> Allows clients to retrieve all course records from the server
}
