package Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class State_Machine extends UnicastRemoteObject implements RemoteInterface  {

    //Operation variables
    private double a;
    private double b;

    //Constructor
    public State_Machine() throws RemoteException {
        super();
        a = 0;
        b = 0;
    }

    // Métodos para a
    private double get_A() throws RemoteException{
        return a;
    }

    private boolean set_A(double val) throws RemoteException{
        a = val;
        return true;
    }

    private boolean add_A(double val) throws RemoteException{
        a = a + val;
        return true;
    }

    private boolean mult_A(double val) throws RemoteException{
        a = a * val;
        return true;
    }

    // Métodos para b
    private double get_B() throws RemoteException{
        return b;
    }

    private boolean set_B(double val) throws RemoteException{
        b = val;
        return true;
    }

    private boolean add_B(double val) throws RemoteException{
        b = b + val;
        return true;
    }

    private boolean mult_B(double val) throws RemoteException{
        b = b * val;
        return true;
    }

    public double read(int var) throws RemoteException{
        if(var == a_ID)
            return a;
        else
            return b;
    }

    public boolean update(int var, int oper, double val) throws RemoteException{
        switch (oper){
            case set_Oper:
                if(var == a_ID)
                    return set_A(val);
                else
                    return set_B(val);
            case add_Oper:
                if(var == a_ID)
                    return add_A(val);
                else
                    return add_B(val);
            case mult_Oper:
                if(var == a_ID)
                    return mult_A(val);
                else
                    return mult_B(val);
        }
        return false;
    }

}
