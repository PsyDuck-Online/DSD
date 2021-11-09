import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Error: java Cliente <direccion> <n> <archivo>");
            System.exit(0);
        }

        // Variables
        String ip = args[0];
        int n = Integer.parseInt(args[1]);
        String fileName = args[2];
        int puerto = 6789;
        RemoteRef rr = new RemoteRef(ip, puerto);
        Solicitud solicitud = new Solicitud();
        ArrayList<Object> argsEnviar = new ArrayList<>();
        File file = null;
        Scanner sc = null;

        // Inicio del cliente
        try {
            file = new File(fileName);
            sc = new Scanner(file);
            int i = 0;
            while (sc.hasNextLine() && i < n) {
                String registroString = sc.nextLine();

                // Convertimos en arreglo de Strings
                String aux[] = registroString.split(" ");
                if (aux.length == 6) {
                    // Asignamos las partes del arreglo y creamos el nuevoRegistro
                    String telefono = aux[1];
                    String curp = aux[3];
                    String partido = aux[5];
                    Registro nuevoRegistro = new Registro(telefono, curp, partido);

                    System.out.println("\nRegistro: " + i);
                    System.out.println(nuevoRegistro);

                    // Generamos el arreglo de objetos que vamos a enviar
                    argsEnviar.add(nuevoRegistro);

                    // Realizamos la solicitud
                    ArrayList<Object> argsRecibidos = solicitud.doOperation(rr, i, argsEnviar);
                    System.out.println("Respeusta del servidor: " + argsRecibidos.get(0));

                    // Enviamos la peticion hasta que rercibamos un ./RECIBIDO
                    while (!(argsRecibidos.get(0).equals("./RECIBIDO"))) {
                        argsRecibidos = solicitud.doOperation(rr, 0, argsEnviar);
                        System.out.println("Respeusta del servidor: " + argsRecibidos.get(0));
                    }

                }
                argsEnviar.clear();
                i++;
                // Thread.sleep(2000);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + e.getMessage());
        }

    }

}
