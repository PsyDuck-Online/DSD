import java.util.ArrayList;

public class Servidor {

    public static void main(String[] args) {

        System.out.println("Servidor iniciado.");
        Respuesta respuesta = new Respuesta(6789);
        ArrayList<String> args_enviar = new ArrayList<>();
        int nbd = 0;

        while (true) {
            ArrayList<String> args_recv = respuesta.getRequest();

            System.out.println("\nNueva peticion!");

            if (!("./PACKET_LOSED".equals(args_recv.get(0)))) {
                int deposito = Integer.parseInt(args_recv.get(0));
                nbd += deposito;
            } else {
                System.out.println("Hubo perdida de paquete y no se realizo la operación.");
            }

            args_enviar.add(String.valueOf(nbd));
            respuesta.sendReply(args_enviar);
            args_enviar.clear();

        }
    }
}
