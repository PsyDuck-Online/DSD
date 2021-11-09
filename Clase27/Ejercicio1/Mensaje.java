import java.io.Serializable;
import java.util.ArrayList;

public class Mensaje implements Serializable {
    private int messageType;
    private int requestId;
    private RemoteRef rr;
    private int operationId;
    private ArrayList<Object> arguments;

    public Mensaje(int messageType, int requestId, RemoteRef rr, int operationId, ArrayList<Object> arguments) {
        this.messageType = messageType;
        this.requestId = requestId;
        this.rr = rr;
        this.operationId = operationId;
        this.arguments = arguments;
    }

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
