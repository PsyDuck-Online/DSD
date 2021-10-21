public class Servidor {
    private static final String ip = "localhost";

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Uso: java Servidor <ip_env> (<crea_token>)");
            System.exit(0);
        }

        String ip_env = args[0];

        if (args.length == 2 && args[1].equals("1")) {
            System.out.println("Creacion del token");
        }

    }
}