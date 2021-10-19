import java.util.ArrayList;
import java.util.Random;

public class Cliente {

    public static void main(String[] args) {
        String ip = "localhost";
        RemoteRef rr = new RemoteRef(ip, 6789);
        Solicitud solicitud = new Solicitud();
        ArrayList<String> args_envio = new ArrayList<>();
        Random random = new Random();

        try {
            while (true) {
                double ciclos = 1 + (1000000 - 1) * random.nextDouble();
                double incremento = random.nextDouble();

                args_envio.add(String.valueOf(ciclos));
                args_envio.add(String.valueOf(incremento));

                ArrayList<String> args_recv = solicitud.doOperation(rr, 0, args_envio);

                for (String i : args_recv) {
                    if ("./EXIT".equals(i)) {
                        System.out.println("Error con el servidor.");
                        System.exit(0);
                    }
                }

                String total = solicitud.doOperation(rr, 0, args_envio).get(0);

                System.out.println("Ciclos: " + ciclos + " || Incremneto: " + incremento + " || Total: " + total);
                args_envio.clear();

                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
    }
}