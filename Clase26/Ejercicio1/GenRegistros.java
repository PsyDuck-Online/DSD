import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Registro {
    public String celular;
    public String CURP;
    public String partido;

    public Registro(String celular, String CURP, String partido) {
        this.celular = celular;
        this.CURP = CURP;
        this.partido = partido;
    }
}

class GenRegistros {
    // Función que genera un CURP aleatorio
    static String getCURP() {
        String Letra = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Numero = "0123456789";
        String Sexo = "HM";
        String Entidad[] = { "AS", "BC", "BS", "CC", "CS", "CH", "CL", "CM", "DF", "DG", "GT", "GR", "HG", "JC", "MC",
                "MN", "MS", "NT", "NL", "OC", "PL", "QT", "QR", "SP", "SL", "SR", "TC", "TL", "TS", "VZ", "YN", "ZS" };
        int indice;

        StringBuilder sb = new StringBuilder(18);

        for (int i = 1; i < 5; i++) {
            indice = (int) (Letra.length() * Math.random());
            sb.append(Letra.charAt(indice));
        }

        for (int i = 5; i < 11; i++) {
            indice = (int) (Numero.length() * Math.random());
            sb.append(Numero.charAt(indice));
        }
        indice = (int) (Sexo.length() * Math.random());
        sb.append(Sexo.charAt(indice));

        sb.append(Entidad[(int) (Math.random() * 32)]);
        for (int i = 14; i < 17; i++) {
            indice = (int) (Letra.length() * Math.random());
            sb.append(Letra.charAt(indice));
        }
        for (int i = 17; i < 19; i++) {
            indice = (int) (Numero.length() * Math.random());
            sb.append(Numero.charAt(indice));
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("java GenRegistros <num_regustros> <documento>");
            System.exit(0);
        }

        int n, inicial;
        String telefono;
        String partido[] = { "PRI", "PAN", "PRD", "P_T", "VDE", "MVC", "MOR", "PES", "PNL" };

        // Numero de registros n
        n = Integer.parseInt(args[0]);
        ArrayList<Registro> lista = new ArrayList<>(n);

        // Genera un numero telefonicos inicial de 9 digitos y despues se obtendran su
        // secuenciales para evitar repetición
        inicial = 500000000 + (int) (Math.random() * 100000000);

        // Crea todos los registros con numero de telefono consecutivo y los almacena en
        // el ArrayList
        for (int j = 0; j < n; j++) {
            telefono = Integer.toString(inicial);
            inicial++;
            Registro regtmp = new Registro(telefono, getCURP(), partido[(int) (Math.random() * 9)]);
            lista.add(regtmp);
        }

        // Aleatoriza el ArrayList de registros e imprime el resultado
        Collections.shuffle(lista);
        Iterator itr = lista.iterator();

        // Manejo del archivo
        File file = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            file = new File(args[1]);
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);

            // llenado del archivo
            while (itr.hasNext()) {
                Registro regtmp = null;
                regtmp = (Registro) itr.next();
                bw.write("Telefono: " + regtmp.celular + " CURP: " + regtmp.CURP + " Partido:" + regtmp.partido + "\n");

            }

            bw.close();
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    System.out.println("IO: " + e.getMessage());
                }
            }
        }

    }
}