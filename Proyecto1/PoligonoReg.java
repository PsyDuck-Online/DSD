public class PoligonoReg extends PoligonoIrreg implements Comparable<PoligonoReg> {
    public int n_vertices;
    public double anguloCentral;

    public PoligonoReg(int n_vertices) {
        super();
        this.n_vertices = n_vertices;
        anguloCentral = ((double) 360 / n_vertices);

        double x, y, teta;

        for (int i = 0; i < n_vertices; ++i) {
            teta = (2 * Math.PI / n_vertices) * i;

            x = Math.cos(teta);
            y = Math.sin(teta);

            anadeVertice(new Coordenada(x, y));
        }

    }

    public double getArea() {

        return (this.getPerimetro() * this.getApotema()) / 2;
    }

    private double getPerimetro() {
        double perimetro;
        double l = vertices.get(0).distanciaPunto(vertices.get(1));

        perimetro = n_vertices * l;

        return perimetro;
    }

    private double getApotema() {
        double l = vertices.get(0).distanciaPunto(vertices.get(1));
        return l / (2 * Math.tan(anguloCentral / 2));
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int compareTo(PoligonoReg p) {
        if(p.getArea() > getArea()) {
            return -1;
        } else if(p.getArea() == getArea()) {
            return 0;
        } else {
            return 1;
        }
    }
}
