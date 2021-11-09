import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class Servidor {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: java Servidor <archivo>");
            System.exit(0);
        }
        // Variables del servidor
        File file = null;
        FileWriter fwriter = null;
        int puerto = 6789;
        String fileName = args[0];
        Respuesta respuesta = new Respuesta(puerto);
        ArrayList<Object> argsEnviar = new ArrayList<>();

        // Inicio del servidor
        System.out.println("Servidor Iniciado\n");

        try {
            file = new File(fileName);
            while (true) {
                // Preparando el archivo para escribir en el
                fwriter = new FileWriter(file, true);

                // Obteniendo la peticion
                ArrayList<Object> argsRecv = respuesta.getRequest();
                System.out.println("//// Nueva peticion ////\n");
                argsEnviar.clear();

                if (argsRecv.get(0).equals("./REQUEST")) {
                    Registro nuevoRegistro = (Registro) argsRecv.get(1);
                    System.out.println(nuevoRegistro);
                    fwriter.write(nuevoRegistro + "\n");
                    System.out.println("Nuevo registro guardado\n");
                    argsEnviar.add("./RECIBIDO");
                } else {
                    System.out.println("Hubo perdida de paquetes\n");
                    argsEnviar.add("./ERROR");
                }

                fwriter.close();
                respuesta.sendReply(argsEnviar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
