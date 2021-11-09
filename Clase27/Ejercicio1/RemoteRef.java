import java.io.Serializable;

public class RemoteRef implements Serializable {
    private String ip;
    private int puerto;

    public RemoteRef(String ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }

    public String getIp() {
        return ip;
    }

    public int getPuerto() {
        return puerto;
    }

    @Override
    public String toString() {
        return String.format("ip: %s, puerto: %d", ip, puerto);
    }
}
