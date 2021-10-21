import java.util.ArrayList;
import java.util.Random;

public class Cliente {

    private static final int PORT = 6789;

    public static void main(String[] args) {
        System.out.println("Cliente iniciado.");
        RemoteRef rr = new RemoteRef(args[0], PORT);
        Solicitud solicitud = new Solicitud();
        ArrayList<String> args_enviar = new ArrayList<>();
        Random random = new Random();
        int operationId = 0;

        int bd_cliente = 0;

        while (true) {
            int deposito = random.nextInt((10 - 1) + 1) + 1;

            System.out.println("deposito: " + deposito + " cuenta: " + bd_cliente);

            args_enviar.add(String.valueOf(deposito));

            ArrayList<String> args_recv = solicitud.doOperation(rr, operationId, args_enviar);
            operationId += 1;

            if ("./CONN_LOSED".equals(args_recv.get(0))) {
                System.out.println("Cerrando cliente, falla con el servidor");
                break;
            }

            int bd_servidor = Integer.parseInt(args_recv.get(0));

            if (bd_cliente != bd_servidor) {
                System.out.println("Error en la cuenta!");
                System.out
                        .println(String.format("Cantidad real: %d || Cantidad devuelta: %d", bd_cliente, bd_servidor));
                System.exit(0);
            } else {
                bd_cliente += deposito;
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
