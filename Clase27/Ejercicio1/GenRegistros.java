import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class GenRegistros {
    // Generador de CURP aleatorio
    static String getCurp() {
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

        if(args.hashCode() < 2) {
            System.out.println("Error: java GenRegistros <num> <archivo>");
            System.exit(0);
        }

        int n;
        int inicial;
        String telefono;
        String partido[] = { "PRI", "PAN", "PRD", "p_T", "VDE", "MVC", "MOR", "PES", "PNL" };

        n = Integer.parseInt(args[0]);
        ArrayList<Registro> lista = new ArrayList<>(n);

        inicial = 500000000 + (int) (Math.random() * 100000000);

        for (int j = 0; j < n; j++) {
            telefono = Integer.toString(inicial);
            inicial++;
            Registro regtmp = new Registro(telefono, getCurp(), partido[(int) (Math.random() * 9)]);
            lista.add(regtmp);
        }

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
                bw.write(regtmp + "\n");

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
