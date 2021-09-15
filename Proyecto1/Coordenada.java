public class Coordenada {
    private double x, y;

    public Coordenada(double x, double y) {
        this.x = x;
        this.y = y;
    } // - constructor

    public double distanciaPunto(Coordenada c) {
        double distancia;

        distancia = Math.sqrt(Math.pow(x - c.x, 2) + Math.pow(y - c.y, 2));

        return distancia;
    }

    public double abcisa() {
        return x;
    } // abcisa

    public double ordenada() {
        return y;
    } // ordenada

    public void setAbcisa(double x) {
        this.x = x;
    }

    public void setOrdenada(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    } // - toString

} // - Coordenada
