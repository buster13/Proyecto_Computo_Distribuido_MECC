package Server;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Server implements Remote{

    public static void main(String[] args) throws RemoteException{

        try {
            String name = "Compute";
            State_Machine engine = new State_Machine();
            //State_Machine stub =
            //        (State_Machine) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.createRegistry(5099);
            registry.rebind(name, engine);
            System.out.println("ComputeEngine bound");
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
    }

}
