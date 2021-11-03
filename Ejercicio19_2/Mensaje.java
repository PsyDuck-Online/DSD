import java.io.Serializable;
import java.util.ArrayList;

public class Mensaje implements Serializable {
    private int messageType;
    private int requestId;
    private RemoteRef rr;
    private int operationId;
    ArrayList<String> arguments;

    public Mensaje(int messageType, int requestId, RemoteRef rr, int operationId, ArrayList<String> arguments) {
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

    public ArrayList<String> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        for (String i : arguments) {
            System.out.println(i);
        }
        return "";
    }
}
