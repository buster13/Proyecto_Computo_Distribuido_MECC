package Client;

import Server.RemoteInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        RemoteInterface service = (RemoteInterface) Naming.lookup("rmi://localhost:5099/Compute");
        System.out.println("Set A: " + service.update(0,0,2));
        System.out.println("A: " + service.read(0));
        System.out.println("Set B: " + service.update(1,0,3));
        System.out.println("B: " + service.read(1));

        System.out.println("Add A: " + service.update(0,1,2));
        System.out.println("A: " + service.read(0));
        System.out.println("Add B: " + service.update(1,1,3));
        System.out.println("B: " + service.read(1));

        System.out.println("Mult A: " + service.update(0,2,2));
        System.out.println("A: " + service.read(0));
        System.out.println("Mult B: " + service.update(1,2,3));
        System.out.println("B: " + service.read(1));
    }
}


