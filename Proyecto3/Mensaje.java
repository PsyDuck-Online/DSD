import java.io.Serializable;

public class Mensaje implements Serializable{
    private int messageType;
    private int requestId;
    private int operationId;
    private RemoteRef rr;
    private Token token;

    public Mensaje(int messageType, int requestId, int operationId, RemoteRef rr, Token token) {
        this.messageType = messageType;
        this.requestId = requestId;
        this.operationId = operationId;
        this.rr = rr;
        this.token = token;
    }

    public int getMessageType() {
        return messageType;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getOperationId() {
        return operationId;
    }

    public RemoteRef getRr() {
        return rr;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String toString() {
        return token.toString();
    }
}
