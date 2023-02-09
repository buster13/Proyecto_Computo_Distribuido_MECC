package src.Server;

import org.json.simple.parser.ParseException;

import java.rmi.*;

public interface RemoteInterface extends Remote{

    //Variables id number
    static final int a_ID = 0;
    static final int b_ID = 1;

    //Operations id number
    static final int set_Oper = 0;
    static final int add_Oper = 1;
    static final int mult_Oper = 2;
    static final int read_Oper = 3;



    // Metodos remotos
    double read(int var) throws RemoteException;
    boolean update(int var, int oper, double val) throws RemoteException;
    String operation(String JSON_Oper) throws Exception;
}
