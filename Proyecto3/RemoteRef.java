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

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public String toString() {
        return String.format("RemoteRef{ip -> %s, puerto -> %d}", ip, puerto);
    }
}
