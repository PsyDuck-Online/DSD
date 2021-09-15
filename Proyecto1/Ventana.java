import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class Ventana extends JFrame {
    JPanel panel;
    ArrayList<PoligonoReg> poligonos;
    Random r;

    public Ventana(int n) {
        poligonos = new ArrayList<PoligonoReg>();
        r = new Random();

        panel = new JPanel();
        add(panel);
        panel.setPreferredSize(new Dimension(860, 600));

        setTitle("Ventana");
        setSize(new Dimension(860, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Llenado del arreglo poligonos
        for (int i = 0; i < n; i++) {
            poligonos.add(new PoligonoReg(r.nextInt(20) + 3));
        }

        System.out.println("Antes de ortenar:\n");
        for (PoligonoReg pR : poligonos) {
            System.out.println(pR.getArea());
        }
        Collections.sort(poligonos);

        System.out.println("Despues de ordenar");
        for (PoligonoReg pR : poligonos) {
            System.out.println(pR.getArea());
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int size;
        int positionX;
        int positionY;
        Polygon p = new Polygon();

        g.setColor(Color.RED);

        for (PoligonoReg pR : poligonos) {
            size = r.nextInt(200) + 50;
            positionX = r.nextInt(500) + 100;
            positionY = r.nextInt(500) + 100;
            for (Coordenada c : pR.vertices) {
                p.addPoint((int) ((c.abcisa() * size) + positionX), (int) ((c.ordenada() * size) + positionY));
            }

            g.drawPolygon(p);
            p.reset();
        }
    }

    public static void main(String[] args) {
        new Ventana(Integer.parseInt(args[0]));
    }
}