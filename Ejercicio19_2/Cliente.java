import java.util.ArrayList;
import java.util.Random;

public class Cliente {
    private static final int puerto = 6789;

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Uso: java Cliente <ip_dest>");
            System.exit(0);
        }

        System.out.println("Cliente iniciado");

        RemoteRef rr = new RemoteRef(args[0], puerto);
        Solicitud solicitud = new Solicitud();
        ArrayList<String> args_enviar = new ArrayList<>();
        Random random = new Random();
        int operationId = 1;

        int bd_cliente = 0;

        while (true) {
            int deposito = random.nextInt((10 - 1) + 1) + 1;
            int bd_servidor;

            // System.out.println(String.format("{deposito: %d, bd_cliente: %d}", deposito,
            // bd_cliente));

            System.out.println(String.format("\n\nNueva solicitud realizada\ndeposito: %d, monto: %d -> monto: %d",
                    deposito, bd_cliente, (bd_cliente + deposito)));

            args_enviar.add(String.valueOf(deposito));
            bd_cliente += deposito;

            ArrayList<String> args_recv = solicitud.doOperation(rr, operationId, args_enviar);
            operationId += 1;

            if ("./CONN_LOST".equals(args_recv.get(0))) {
                System.out.println("Conexion perdida.");
                break;
            } else if ("./PACKET_LOST".equals(args_recv.get(0))) {
                System.out.println(String.format("HUBO PERDIDA DE PAQUETE!!\nmonto: %d -> %d\n", bd_cliente,
                        (bd_cliente - deposito)));
                bd_cliente -= deposito;
                bd_servidor = Integer.parseInt(args_recv.get(1));
            } else {
                bd_servidor = Integer.parseInt(args_recv.get(0));
            }

            if (bd_cliente != bd_servidor) {
                System.out.println("ERROR EN LA CUENTA!!");
                System.out.println(String.format("(bd_cliente: %d / bd_servidor: %d)", bd_cliente, bd_servidor));
                break;
            }

            args_enviar.clear();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
        }
    }
}
