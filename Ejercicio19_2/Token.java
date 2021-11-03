import java.io.Serializable;
import java.time.LocalTime;

public class Token implements Serializable {

    private String ip;
    private int puerto_escucha;
    private int puerto_envio;
    private int id_proceso;
    private final LocalTime hora_levantamiento;
    private LocalTime ultima_hora_funcionamiento;

    public Token(String ip, LocalTime hora_levantamiento, int puerto_envio, int puerto_escucha, int id_proceso) {
        this.ip = ip;
        this.hora_levantamiento = hora_levantamiento;
        this.ultima_hora_funcionamiento = hora_levantamiento;
        this.puerto_envio = puerto_envio;
        this.id_proceso = id_proceso;
        this.puerto_escucha = puerto_escucha;
    }

    public String getIp() {
        return ip;
    }

    public int getPuerto_escucha() {
        return puerto_escucha;
    }

    public int getPuerto_envio() {
        return puerto_envio;
    }

    public int getId_proceso() {
        return id_proceso;
    }

    public LocalTime getHora_levantamiento() {
        return hora_levantamiento;
    }

    public LocalTime getUltima_hora_funcionamiento() {
        return ultima_hora_funcionamiento;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setId_proceso(int id_proceso) {
        this.id_proceso = id_proceso;
    }

    public void setPuerto_escucha(int puerto_escucha) {
        this.puerto_escucha = puerto_escucha;
    }

    public void setPuerto_envio(int puerto_envio) {
        this.puerto_envio = puerto_envio;
    }

    public void setUltima_hora_funcionamiento(LocalTime ultima_hora_funcionamiento) {
        this.ultima_hora_funcionamiento = ultima_hora_funcionamiento;
    }

    @Override
    public String toString() {
        return "Token{" + "ip=" + ip + ", puerto_escucha=" + puerto_escucha + ", puerto_envio=" + puerto_envio
                + ", id_proceso=" + id_proceso + ", hora_levantamiento=" + hora_levantamiento
                + ", ultima_hora_funcionamiento=" + ultima_hora_funcionamiento + '}';
    }

}