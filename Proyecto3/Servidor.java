import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalTime;

public class Servidor {
    public static void main(String[] args) {
        // Validamos que tengan los parametrso correctos
        if (args.length != 2) {
            System.out.println("Error: java Servidor <ip_envio> <token: 1 o 0>");
            System.exit(0);
        }
        // Iniciamos el programa del Servidor
        int puerto = 6789;
        String ip_envio = args[0];
        String ip_local = "";
        Respuesta respuesta = new Respuesta(puerto);
        Solicitud solicitud = new Solicitud();
        Token token = null;
        RemoteRef rr = new RemoteRef(ip_envio, puerto);

        try {
            ip_local = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (args[1] == "1") {
            token = new Token(LocalTime.now(), 0);
            token.addIp(ip_local);
        }

        while (true) {
            if (token == null) {
                token = respuesta.getRequest();
                respuesta.sendReply(token);

                token.addIp(ip_local);
            } else {
                boolean status = solicitud.doOperation(rr, 0, token);

                if (status) {
                    token = null;
                } else {
                    if (token.getIp_list().size() <= 1) {
                        System.out.println("Error");
                        System.exit(0);
                    }

                    token.deleteIp(ip_envio);

                    int i = token.searchIp(ip_local);

                    if (i + 1 > token.getIp_list().size()) {
                        rr.setIp(token.getIp(0));
                    } else {
                        rr.setIp(token.getIp(i + 1));
                    }
                }
            }
        }

    }

}
