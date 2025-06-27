import java.rmi.RemoteException;                  // --> Required for remote method implementation
import java.util.List;                            // --> Used for handling collections
import java.util.Map;                             // --> Used for representing key-value data

/* Brief expl: This class implements the remote methods declared in RemoteService. It delegates all
database tasks (store/fetch students and courses) to the DBHelper class. */

 // "DELEGATES" OR DELEGATION means one object passes a task to another object.

public class RemoteServiceImpl implements RemoteService {  // --> Implements the RemoteService interface

    public RemoteServiceImpl() throws RemoteException {
        super();  // --> Calls the superclass constructor (Object)
    }
    // "DELEGATES" OR DELEGATION means one object passes a task to another object.
    @Override
    public void storeStudent(List<Map<String, String>> students) {
        DBHelper.insertStudents(students);  // --> Delegates student insertion to DBHelper
    }
    // "DELEGATES" OR DELEGATION means one object passes a task to another object.
    @Override
    public void storeCourse(List<Map<String, String>> courses) {
        DBHelper.insertCourses(courses);  // --> Delegates course insertion to DBHelper
    }
// "DELEGATES" OR DELEGATION means one object passes a task to another object.
    @Override
    public List<Map<String, String>> getStudent() {
        return DBHelper.fetchStudents();  // --> Retrieves student data from DBHelper
    }
    // "DELEGATES" OR DELEGATION means one object passes a task to another object.
    @Override
    public List<Map<String, String>> getCourse() {
        return DBHelper.fetchCourses();  // --> Retrieves course data from DBHelper
    }
}
