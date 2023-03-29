package src.Lamport;

public class Evento_Mssg implements Comparable<Evento_Mssg>{
    public static final String RESPONSE_ACCEPT = "ACCEPT";
    public static final String RESPONSE_REJECT = "REJECT";

    public static final int TYPE_LOCAL = 0;
    public static final int TYPE_REMOTE = 1;

    private int tipo;
    private int id_envio;
    private int id_evento;
    private int tiempo;

    public int oper;
    public int var;
    public float val;
    public float result;

    public int resp_In;
    public int resp_Out;
    public String content;
    public boolean isProcessed;


    public Evento_Mssg(int tipo, int id_envio, int id_evento, int tiempo, int oper, int var, float val){
        this.tipo = tipo;
        this.id_envio = id_envio;
        this.id_evento = id_evento;
        this.oper = oper;
        this.var = var;
        this.val = val;
        this.tiempo = tiempo;
    }

    public int[] getPrioridad(){
        int[] prioridad = new int[2];

        prioridad[0] = id_envio;
        prioridad[1] = id_evento;

        return prioridad;
    }

    public void setTiempo(int tiempo){
        this.tiempo = tiempo;
    }
    public int getTiempo(){
        return this.tiempo;
    }
    public int getTipo(){
        return this.tipo;
    }

    @Override
    public int compareTo(Evento_Mssg evt) {

        int[] otro_Prioridad = evt.getPrioridad();

        if(otro_Prioridad[0] == this.id_envio && otro_Prioridad[1] == this.id_evento){
            return 0;
        } else if (otro_Prioridad[0] > this.id_envio || otro_Prioridad[1] > this.id_evento) {
            return 1;
        } else {
            return -1;
        }
    }
}
