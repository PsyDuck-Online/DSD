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

            System.out.println("Nueva peticion recibida!");
            System.out.println("nbd: " + nbd);

            if (!("./PACKET_LOST".equals(args_recv.get(0)))) {
                int deposito = Integer.parseInt(args_recv.get(0));
                nbd += deposito;
            } else {
                args_enviar.add("./PACKET_LOST");
            }

            args_enviar.add(String.valueOf(nbd));

            respuesta.sendReply(args_enviar);
            args_enviar.clear();
        }
    }
}