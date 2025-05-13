package ar.edu.unlu.poo.chinchon.Vista;
import javax.swing.*;
import java.awt.*;
public class PanelFondo extends JPanel{
    private Image imagen;

    public PanelFondo(String rutaImagen) {
        setLayout(null);
        this.imagen = new ImageIcon(rutaImagen).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
