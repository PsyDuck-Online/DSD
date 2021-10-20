import java.util.ArrayList;
import java.util.Random;

public class Cliente {

    private static final String IP = "localhost";
    private static final int PORT = 6789;

    public static void main(String[] args) {
        System.out.println("Cliente iniciado.");
        RemoteRef rr = new RemoteRef(IP, PORT);
        Solicitud solicitud = new Solicitud();
        ArrayList<String> args_enviar = new ArrayList<>();
        Random random = new Random();

        int bd_cliente = 0;

        while (true) {
            int deposito = random.nextInt((10 - 1) + 1) + 1;
            bd_cliente += deposito;

            System.out.println("deposito: " + deposito + " cuenta: " + bd_cliente);

            args_enviar.add(String.valueOf(deposito));

            ArrayList<String> args_recv = solicitud.doOperation(rr, 0, args_enviar);

            if ("./CONN_LOSED".equals(args_recv.get(0))) {
                continue;
            }

            int bd_servidor = Integer.parseInt(args_recv.get(0));

            if (bd_cliente != bd_servidor) {
                System.out.println("Error en la cuenta!");
                System.out
                        .println(String.format("Cantidad real: %d || Cantidad devuelta: %d", bd_cliente, bd_servidor));
                System.exit(0);
            }

            args_enviar.clear();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                System.out.println("Interrupted: " + ex.getMessage());
            }
        }
    }
}
