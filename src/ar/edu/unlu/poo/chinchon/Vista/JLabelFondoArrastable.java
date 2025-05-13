package ar.edu.unlu.poo.chinchon.Vista;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class JLabelFondoArrastable extends JLabel{
    private Image imagen;
    private Point puntoInicial;
    private Point posicionOriginal;
    public int i;

    public JLabelFondoArrastable(String rutaImagen) {
        this.imagen= new ImageIcon(rutaImagen).getImage();
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                puntoInicial = e.getPoint();
                posicionOriginal = getLocation();
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point puntoEnPanel = getLocation();
                int x = puntoEnPanel.x + e.getX() - puntoInicial.x;
                int y = puntoEnPanel.y + e.getY() - puntoInicial.y;
                setLocation(x, y);
                getParent().repaint();
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
    public Point getPosicionOriginal() {
        return posicionOriginal;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
