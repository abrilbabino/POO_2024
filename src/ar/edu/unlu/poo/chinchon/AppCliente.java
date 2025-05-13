package ar.edu.unlu.poo.chinchon;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ar.edu.unlu.poo.chinchon.Controlador.Controlador;
import ar.edu.unlu.poo.chinchon.Vista.*;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;

public class AppCliente {

    public static void main(String[] args) {
        ArrayList<String> ips = Util.getIpDisponibles();
        String ip = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la que escuchar� peticiones el cliente", "IP del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        String port = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que escuchar� peticiones el cliente", "Puerto del cliente",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                9999
        );
        String ipServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la IP en la corre el servidor", "IP del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ips.toArray(),
                null
        );
        String portServidor = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el puerto en el que corre el servidor", "Puerto del servidor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                8888
        );
        Controlador controlador = new Controlador();
        Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
        JFrame elegirVista = new JFrame("ELEGI UNA VISTA");
        JPanel contenidoMensaje = new JPanel();
        contenidoMensaje.setLayout(new BorderLayout());
        JLabel mensaje=new JLabel("ELEGI UNA VISTA");
        mensaje.setFont(new Font("Courier New",Font.BOLD,20));
        mensaje.setBorder(new EmptyBorder(0,80,0,0));
        contenidoMensaje.add(mensaje, BorderLayout.CENTER);
        JPanel contenidoBotones = new JPanel();
        contenidoBotones.setLayout(new FlowLayout());
        JButton vistaGButton = new JButton("VISTA GRAFICA");
        JButton vistaCButton = new JButton("VISTA CONSOLA");
        vistaGButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elegirVista.setVisible(false);
                VistaGrafica vistaG = new VistaGrafica(controlador);
                controlador.setVista(vistaG);
                try {
                    c.iniciar(controlador);
                } catch (RemoteException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                } catch (RMIMVCException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
            }
        });
        vistaCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elegirVista.setVisible(false);
                VistaConsola vista = new VistaConsola(controlador);
                controlador.setVista(vista);
                vista.setVisible(true);
                try {
                    c.iniciar(controlador);
                } catch (RemoteException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                } catch (RMIMVCException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }
            }
        });
        contenidoBotones.add(vistaGButton);
        contenidoBotones.add(vistaCButton);
        contenidoMensaje.add(contenidoBotones, BorderLayout.SOUTH);
        elegirVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        elegirVista.setSize(350, 350);
        elegirVista.setLocationRelativeTo(null);
        elegirVista.setContentPane(contenidoMensaje);
        elegirVista.setVisible(true);
    }
}