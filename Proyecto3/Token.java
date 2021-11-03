import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;

public class Token implements Serializable {
    private ArrayList<String> ip_list;
    private int id_proceso;
    private final LocalTime hora_levantamiento;
    private LocalTime ultima_hora_funcionamiento;

    public Token(LocalTime hora_levantamiento, int id_proceso) {
        ip_list = new ArrayList<>();
        this.hora_levantamiento = hora_levantamiento;
        this.ultima_hora_funcionamiento = hora_levantamiento;
        this.id_proceso = id_proceso;
    }

    public void addIp(String ip) {
        boolean seEncuentra = ip_list.contains(ip);
        if (!seEncuentra) {
            ip_list.add(ip);
        } else {
            System.out.println("Ya se encuentra una ip con la misma direccion");
        }
    }

    public void deleteIp(String ip) {
        int indice = ip_list.indexOf(ip);
        if (indice != -1) {
            ip_list.remove(indice);
        } else {
            System.out.println(String.format("El elemento '%s' no esta en la lista", ip));
        }
    }

    public int searchIp(String ip) {
        return ip_list.indexOf(ip);
    }

    public String getIp(int pos) {
        return ip_list.get(pos);
    }

    public ArrayList<String> getIp_list() {
        return ip_list;
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

    public void setId_proceso(int id_proceso) {
        this.id_proceso = id_proceso;
    }

    public void setUltima_hora_funcionamiento(LocalTime ultima_hora_funcionamiento) {
        this.ultima_hora_funcionamiento = ultima_hora_funcionamiento;
    }
}