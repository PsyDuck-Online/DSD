import java.util.ArrayList;

public class Servidor {

    public static void main(String[] args) {
        System.out.println("Servidor iniciado.");
        Respuesta respuesta = new Respuesta(6789);
        ArrayList<String> args_env = new ArrayList<>();
        while (true) {
            ArrayList<String> args_recv = respuesta.getRequest();

            System.out.println("Nueva peticion!!!!");

            double total = calcularTotal(Double.parseDouble(args_recv.get(0)), Double.parseDouble(args_recv.get(1)));

            args_env.add(String.valueOf(total));

            respuesta.sendReply(args_env);
            args_env.clear();
        }
    }

    private static double calcularTotal(double ciclos, double incremento) {
        double total = 0;

        double i = 0;
        for (int j = 0; j < ciclos; j++) {
            total += Math.sqrt(Math.pow(i, 3) + Math.pow(i, 4) + Math.pow(i, 5) + Math.pow(i, 6) + Math.pow(i, 7));
            i += incremento;
        }

        return total;
    }
}
