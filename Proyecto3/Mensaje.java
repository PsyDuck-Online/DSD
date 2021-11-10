/* 
* Clase: Desarrollo de Sistemas Distribuidos.
* Proyecto: 3.
* Alumno: Baltazar Real David.
* Grupo: 4CM11.
*/

import java.io.Serializable;
import java.util.ArrayList;

public class Mensaje implements Serializable {
    private int messageType;
    private int requestId;
    private RemoteRef rr;
    private int operationId;
    private ArrayList<Object> arguments;

    public Mensaje(int messageType, int requestId, int operationId, RemoteRef rr, ArrayList<Object> arguments) {
        this.messageType = messageType;
        this.requestId = requestId;
        this.operationId = operationId;
        this.rr = rr;
        this.arguments = arguments;
    }

    // -------------------
    // Getters
    // -------------------

    public int getMessageType() {
        return messageType;
    }

    public int getRequestId() {
        return requestId;
    }

    public RemoteRef getRr() {
        return rr;
    }

    public int getOperationId() {
        return operationId;
    }

    public ArrayList<Object> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        System.out.println(arguments);
        return "";
    }
}
