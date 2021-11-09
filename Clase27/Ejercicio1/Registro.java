import java.io.Serializable;

public class Registro implements Serializable {
    public String celular;
    public String curp;
    public String partido;

    public Registro(String celular, String curp, String partido) {
        this.celular = celular;
        this.curp = curp;
        this.partido = partido;
    }

    @Override
    public String toString() {
        return "Telefono: " + celular + " CURP: " + curp + " Partido: " + partido;
    }
}