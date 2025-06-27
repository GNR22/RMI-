// This class starts an RMI server, creates or gets the RMI registry on port 1099,
// exports the RemoteService implementation, and binds it to the registry under the name "RemoteService".
// Clients can then look up "RemoteService" in the registry to invoke remote methods.
// The server prints status messages to indicate its progress.

/* Brief expl: This class starts the RMI server. It creates an instance of the remote service, exports 
it for remote access, registers it under "RemoteService" in the RMI registry on port 1099, and prints status messages. */

import java.rmi.registry.LocateRegistry;        // --> Used to locate or create the RMI registry
import java.rmi.registry.Registry;              // --> Interface to interact with the RMI registry
import java.rmi.RemoteException;                // --> Handles remote method call errors
import java.rmi.server.UnicastRemoteObject;     // --> Used to export remote objects

public class Server {
    public static void main(String[] args) {
        try {
            // Create the implementation
            RemoteServiceImpl service = new RemoteServiceImpl();  // --> Create an instance of the service class

            /*  Export it as a stub, it creates an instance of RemoteServiceImpl, exports it as a remote stub, creates 
            or gets the RMI registry on port 1099, and binds the stub under the name "RemoteService". It also prints status messages.*/
            RemoteService stub = (RemoteService) UnicastRemoteObject.exportObject(service, 0);  
            // --> Makes the service remotely accessible by generating a stub

            // Create or get the RMI registry
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(1099);  // --> Creates a new registry on port 1099
                System.out.println("RMI registry created on port 1099.");
            } catch (Exception e) {
                System.out.println("Using existing registry.");
                registry = LocateRegistry.getRegistry(1099);  // --> Gets the already running registry on port 1099
            }

            // Bind the service
            registry.rebind("RemoteService", stub);  // --> Binds the exported object under the name "RemoteService"
            System.out.println("Server is ready and RemoteService is bound.");  // --> Confirmation message

        } catch (RemoteException e) {
            e.printStackTrace();  // --> Logs remote exceptions
        }
    }
}

