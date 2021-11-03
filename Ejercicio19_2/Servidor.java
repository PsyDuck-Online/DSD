import java.util.ArrayList;

public class Servidor {
    private static final int puerto = 6789;

    public static void main(String[] args) {
        Respuesta respuesta = new Respuesta(puerto);
        ArrayList<String> args_enviar = new ArrayList<>();
        int nbd = 0;

        System.out.println("Servidor iniciado.\n");

        while (true) {
            ArrayList<String> args_recv = respuesta.getRequest();

            System.out.println("\n\nNUEVA PETICION!");

            if (args_recv.get(0) == "./PACKET_LOST") {
                System.out.println(String.format("HUBO PERDIDA DE PAQUETES!!\nnbd: %d", nbd));
                args_enviar.add("./PACKET_LOST");
            } else {
                int deposito = Integer.parseInt(args_recv.get(0));

                System.out.println(String.format("nbd: %d, deposito: %d -> nbd: %d", nbd, deposito, (nbd + deposito)));
                nbd = nbd + deposito;
            }

            args_enviar.add(String.valueOf(nbd));

            respuesta.sendReply(args_enviar);
            args_enviar.clear();
        }
    }
}