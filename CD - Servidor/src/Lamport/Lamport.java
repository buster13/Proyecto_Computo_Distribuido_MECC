package src.Lamport;

import java.util.PriorityQueue;

public class Lamport {

    private static int reloj;
    private int id_nodo;
    public static PriorityQueue<Evento_Mssg> messages_queue = new PriorityQueue<>();

    public Lamport(int id_nodo) {
        this.id_nodo = id_nodo;
        reloj = 0;
    }

    public static void agrega_Evento(Evento_Mssg evt){
        switch(evt.getTipo()){
            case 0:

                evt.setTiempo( reloj + 1);
                messages_queue.add(evt);

                break;
            case 1:

                reloj = Math.max(reloj, evt.getTiempo()) + 1;
                messages_queue.add(evt);

                break;
        }

    }
}
