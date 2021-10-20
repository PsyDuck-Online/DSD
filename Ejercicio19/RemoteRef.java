import java.io.Serializable;

public class RemoteRef implements Serializable {

    private String IP;
    private int puerto;

    public RemoteRef(String IP, int puerto) {
        this.IP = IP;
        this.puerto = puerto;
    }

    public String getIP() {
        return IP;
    }

    public int getPuerto() {
        return puerto;
    }

    @Override
    public String toString() {
        return "RemoteRef{" + "IP=" + IP + ", puerto=" + puerto + '}';
    }
}