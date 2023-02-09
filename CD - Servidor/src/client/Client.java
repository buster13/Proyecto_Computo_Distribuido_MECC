package src.client;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import src.server.RemoteInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException, ParseException {

        System.out.println("inicio");

        RemoteInterface service = (RemoteInterface) Naming.lookup("rmi://148.205.36.209:5099/Compute");
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

        Scanner scan = new Scanner(System.in);

        /*System.out.println("Que operacion quieres hacer? 3-Leer; 0-Asignar; 1-Sumar; 2-Multiplicar");
        int oper = scan.nextInt();
        System.out.println("Sobre que variable? 0-a; 1-b");
        int var = scan.nextInt();
        System.out.println("Que valor? 0-a; 1-b");
        double valor = scan.nextInt();

        System.out.println("El resultado es" + service.update(var,oper,valor) + " " + service.read(var));
        */


        JSONObject json = new JSONObject();

        json.put("operation", "1");
        json.put("variable", "1");
        json.put("value", "2");

        try {
            System.out.println(service.operation(json.toJSONString()));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}


