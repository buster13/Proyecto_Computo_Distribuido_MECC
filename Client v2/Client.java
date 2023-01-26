package Client;

import Server.RemoteInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        RemoteInterface service = (RemoteInterface) Naming.lookup("rmi://localhost:5099/Compute");
       // System.out.println("Set A: " + service.update(0,0,2));
        //System.out.println("A: " + service.read(0));
        //System.out.println("Set B: " + service.update(1,0,3));
        // System.out.println("B: " + service.read(1));

        //   System.out.println("Add A: " + service.update(0,1,2));
        //   System.out.println("A: " + service.read(0));
        //    System.out.println("Add B: " + service.update(1,1,3));
        //    System.out.println("B: " + service.read(1));

        //    System.out.println("Mult A: " + service.update(0,2,2));
        //     System.out.println("A: " + service.read(0));
        //    System.out.println("Mult B: " + service.update(1,2,3));
        //     System.out.println("B: " + service.read(1));

        Scanner scan = new Scanner(System.in);



            System.out.println("¿Qué desea hacer: 0-read, 1-update");

            int var = scan.nextInt();

            if (var == 0) {
                System.out.println("A: " + service.read(0));
                System.out.println("B: " + service.read(1));

            } else {


                System.out.println("¿Qué valor desea actualizar: 0-a, 1-b");
                int ab = scan.nextInt();
                System.out.println("¿Qué operación desea realizar: 0-Asignar, 1-Agregar, 2-Multiplicar");
                int oper = scan.nextInt();
                System.out.println("Escriba un valor");
                int val = scan.nextInt();

                System.out.println(service.update(ab, oper, val));

            }


    }
}
