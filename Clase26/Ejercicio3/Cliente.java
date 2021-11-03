import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java Cliente <direccion> <archivo>");
            System.exit(0);
        }

        System.out.println("Cliente Iniciado\n");

        RemoteRef rr = new RemoteRef(args[0], 6789);
        Solicitud soliciutd = new Solicitud();
        ArrayList<String> args_enviar = new ArrayList<>();

        try {
            String registro = leerRegistro(args[1]);

            System.out.println(registro + "\n");

            args_enviar.add(registro);

            ArrayList<String> args_recv = soliciutd.doOperation(rr, 0, args_enviar);

            if ("./CONN_LOST".equals(args_recv.get(0))) {
                System.out.println("No fue posible conectar con el servidor.\n");
            } else {
                System.out.println(args_recv.get(0) + "\n");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + e.getMessage());
            System.exit(0);
        }

    }

    public static String leerRegistro(String nombre_archivo) throws FileNotFoundException {
        String registro = "";
        File file = new File(nombre_archivo);
        Scanner scanner = new Scanner(file);

        if (scanner.hasNextLine()) {
            String linea = scanner.nextLine();
            registro = registro + linea;
        }

        scanner.close();
        return registro;
    }

}
