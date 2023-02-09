package src.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import src.AES;

public class State_Machine extends UnicastRemoteObject implements RemoteInterface  {

    //Operation variables
    private double a;
    private double b;

    private AES aes;

    //Constructor
    public State_Machine() throws Exception {
        super();
        a = 0;
        b = 0;
        aes = new AES();
        try {
            aes.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
            return get_A();
        else
            return get_B();
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

    public String operation(String JSON_Oper) throws Exception {

        JSONParser parser = new JSONParser();

        String request = aes.decrypt(JSON_Oper);

        JSONObject json = (JSONObject)parser.parse(request);
        JSONObject json_out = new JSONObject();

        int operation = Integer.parseInt((String)json.get("operation"));
        int variable = Integer.parseInt((String)json.get("variable"));
        double value = Double.parseDouble((String)json.get("value"));
        double read_result;
        boolean update_result;

        json_out.put("pad","");
        if(operation == read_Oper)
            json_out.put("result",read(variable)+"");
        else{
            json_out.put("result",Boolean.toString(update(variable, operation, value)));
        }

        String output = aes.encrypt(json_out.toString());

        return output;
    }

}
