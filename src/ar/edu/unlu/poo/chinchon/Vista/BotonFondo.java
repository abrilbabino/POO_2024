package ar.edu.unlu.poo.chinchon.Vista;
import javax.swing.*;
import java.awt.*;
public class BotonFondo extends JButton{
    private Image imagen;

    public BotonFondo(String rutaImagen) {
        setLayout(null);
        this.imagen = new ImageIcon(rutaImagen).getImage();
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
}
