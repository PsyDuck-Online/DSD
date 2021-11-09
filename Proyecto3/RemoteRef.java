public class RemoteRef {
    private String ip;
    private int puerto;

    public RemoteRef(String ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }

    // -------------------
    // Getters
    // -------------------

    public String getIp() {
        return ip;
    }

    public int getPuerto() {
        return puerto;
    }

    // -------------------
    // Setters
    // -------------------

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

}
