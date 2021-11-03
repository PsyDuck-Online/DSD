import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class Servidor {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Uso: java Servidor <archivo>");
            System.exit(0);
        }

        File file = new File(args[0]);
        FileWriter fwriter = null;

        Respuesta respuesta = new Respuesta(6789);
        ArrayList<String> args_enviar = new ArrayList<>();

        System.out.println("Servidor iniciado.");

        try {

            while (true) {
                fwriter = new FileWriter(file, true);
                ArrayList<String> args_recv = respuesta.getRequest();
                System.out.println("\n\nNueva Peticion");

                fwriter.write(args_recv.get(0) + "\n");

                args_enviar.add("Registro guardado correctamente");

                fwriter.close();

                respuesta.sendReply(args_enviar);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
