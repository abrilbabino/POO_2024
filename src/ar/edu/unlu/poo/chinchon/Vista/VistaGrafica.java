package ar.edu.unlu.poo.chinchon.Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import ar.edu.unlu.poo.chinchon.Modelo.Eventos;
import ar.edu.unlu.poo.chinchon.Vista.JLabelFondoArrastable;
import ar.edu.unlu.poo.chinchon.Controlador.*;
import ar.edu.unlu.poo.chinchon.Modelo.CartaMostrable;
import ar.edu.unlu.poo.chinchon.Modelo.JugadorMostrable;

public class VistaGrafica implements IVista{
    private Controlador controlador;
    private JFrame menuPrincipal;
    private JFrame agregarJugadorFrame;
    private JFrame listaJugadoresFrame;
    private JFrame iniciarPartidaFrame;
    private JFrame ayudaFrame;
    private JPanel panelPrincipal;
    private JPanel panelAgregarJugador;
    private JPanel panelListaJugadores;
    private JButton listaDeJugadoresButton;
    private JButton salirButton;
    private JButton agregarJugadorButton;
    private JButton iniciarPartidaButton;
    private JPanel panelPartida;
    private JLabel carta;
    private JButton mazoButton;
    private JButton topeButton;
    private JButton cortarButton;
    private JPanel panelAyuda;
    private JButton ayudaButton;
    private JPanel panelPerdedor;
    private JPanel panelGanador;
    private JFrame perdedorFrame;
    private JFrame ganadorFrame;
    private JTextField ingresaNombre;
    private JTextArea pantalla;
    private JButton enterButton;
    private JButton volverButton;
    private JDialog tableroPuntos;
    private boolean tiro=false;
    private boolean corta=false;
    private boolean levanto=false;
    private String nombreJugador;

    public VistaGrafica(Controlador controlador){
        this.controlador=controlador;
        createUIComponents();
        agregarJugadorButton.setBounds(280, 200, 200, 30);
        listaDeJugadoresButton.setBounds(280, 250, 200, 30);
        iniciarPartidaButton.setBounds(280, 305, 200, 30);
        salirButton.setBounds(280, 360, 200, 30);
        ayudaButton.setBounds(730,520,50,20);

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        agregarJugadorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPrincipal.setVisible(false);
                panelAgregarJugador=new PanelFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\Ingreso_Jugador.png");
                enterButton.setBounds(700,520,80,40);
                ingresaNombre.setBounds(540,165,150,30);
                ingresaNombre.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        enterButton.doClick();
                    }
                });
                enterButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        agregarJugador(ingresaNombre.getText());
                        ingresaNombre.setText("");
                    }
                });
                panelAgregarJugador.add(enterButton);
                panelAgregarJugador.add(ingresaNombre);
                agregarJugadorFrame = new JFrame("AGREGAR JUGADOR");
                agregarJugadorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                agregarJugadorFrame.setSize(800, 600);
                agregarJugadorFrame.setLocationRelativeTo(null);
                agregarJugadorFrame.setContentPane(panelAgregarJugador);
                agregarJugadorFrame.setVisible(true);
            }
        });

        listaDeJugadoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPrincipal.setVisible(false);
                panelListaJugadores=new PanelFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\Lista_Jugadores.png");
                pantalla=new JTextArea();
                pantalla.setOpaque(false);
                pantalla.setEditable(false);
                pantalla.setBounds(250,80,500,500);
                pantalla.setFont(new Font("Courier New",Font.BOLD,20));
                listaJugadoresFrame = new JFrame("LISTA JUGADORES");
                setVolverButton(listaJugadoresFrame);
                panelListaJugadores.add(volverButton);
                panelListaJugadores.add(pantalla);
                mostrarListaJugadores();
                listaJugadoresFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                listaJugadoresFrame.setSize(800, 600);
                listaJugadoresFrame.setLocationRelativeTo(null);
                listaJugadoresFrame.setContentPane(panelListaJugadores);
                listaJugadoresFrame.setVisible(true);

            }
        });
        iniciarPartidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean inicia = controlador.iniciarJuego();
                if (!inicia) {
                    JDialog warningNoJugadoresSuf = new JDialog(menuPrincipal, "WARNING", false);
                    warningNoJugadoresSuf.setLayout(new BorderLayout());
                    JLabel mensaje = new JLabel("NO HAY JUGADORES SUFICIENTES PARA INICIAR LA PARTIDA");
                    mensaje.setBorder(new EmptyBorder(20, 20, 20, 20));
                    warningNoJugadoresSuf.add(mensaje, BorderLayout.CENTER);
                    JButton cerrar = new JButton("OK");
                    cerrar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            warningNoJugadoresSuf.setVisible(false);
                        }
                    });
                    warningNoJugadoresSuf.add(cerrar, BorderLayout.SOUTH);
                    warningNoJugadoresSuf.setLocationRelativeTo(agregarJugadorFrame);
                    warningNoJugadoresSuf.pack();
                    warningNoJugadoresSuf.setVisible(true);
                }
            }
        });
        ayudaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPrincipal.setVisible(false);
                panelAyuda=new PanelFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\Como_jugar.png");
                ayudaFrame=new JFrame("AYUDA");
                setVolverButton(ayudaFrame);
                panelAyuda.add(volverButton);
                ayudaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ayudaFrame.setSize(800,600);
                ayudaFrame.setLocationRelativeTo(null);
                ayudaFrame.setContentPane(panelAyuda);
                ayudaFrame.setVisible(true);
            }
        });
        panelPrincipal.add(agregarJugadorButton);
        panelPrincipal.add(listaDeJugadoresButton);
        panelPrincipal.add(iniciarPartidaButton);
        panelPrincipal.add(salirButton);
        panelPrincipal.add(ayudaButton);
        menuPrincipal = new JFrame("MENU PRINCIPAL");
        menuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuPrincipal.setSize(800, 600);
        menuPrincipal.setLocationRelativeTo(null);
        menuPrincipal.setContentPane(panelPrincipal);
        mostrarMenuPrincipal();
    }
    private void setVolverButton(JFrame frame){
        volverButton.setBounds(580,520,200,30);
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                mostrarMenuPrincipal();
            }
        });
    }
    private void createUIComponents() {
        panelPrincipal=new PanelFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\Menu_principal.png");
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        if(mensaje.equals("TODAVIA NO PODES CORTAR")){
            System.out.println("TODAVIA NO PODES CORTAR");
            levanto=true;
            corta=false;
            JDialog warningNoCorta = new JDialog(iniciarPartidaFrame, "WARNING", false);
            warningNoCorta.setLayout(new BorderLayout());
            JLabel mens = new JLabel("TODAVIA NO PODES CORTAR");
            mens.setBorder(new EmptyBorder(20, 20, 20, 20));
            warningNoCorta.add(mens,BorderLayout.CENTER);
            JButton cerrar = new JButton("OK");
            cerrar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    warningNoCorta.setVisible(false);
                }
            });
            warningNoCorta.add(cerrar, BorderLayout.SOUTH);
            warningNoCorta.setLocationRelativeTo(iniciarPartidaFrame);
            warningNoCorta.pack();
            warningNoCorta.setVisible(true);
        }
        else if(mensaje.equals("JUEGO TERMINADO")){
            if(nombreJugador.equals(controlador.getGanador())){
                mostrarCartelGanador();
                mostrarPuntos(controlador.obtenerJugadores());
            }
        }
    }

    @Override
    public void mostrarCartaTope(CartaMostrable cartaMostrable) {
        Component[] componentes = panelPartida.getComponents();
        for (Component comp : componentes) {
            if ("TOPE".equals(comp.getName())) {
                panelPartida.remove(comp);
            }
        }
        if(controlador.obtenerCartaTope()!=null) {
            topeButton=new BotonFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\"+cartaMostrable.getPalo()+cartaMostrable.getNumero()+".png");
            topeButton.setBounds(410, 218, 188, 270);
            topeButton.setName("TOPE");
            topeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mazoButton.setEnabled(false);
                    controlador.agarrarCarta(2);
                    levanto=true;
                    cortarButton.setEnabled(true);
                }
            });
        }
        else{
            topeButton=new BotonFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\topeVacio.png");
            topeButton.setBounds(410, 218, 188, 270);
            topeButton.setName("TOPE");
        }
        if(!tiro){
            topeButton.setEnabled(false);
        }
        panelPartida.add(topeButton);
        panelPartida.revalidate();
        panelPartida.repaint();
    }

    public void mostrarCartas(ArrayList<CartaMostrable> cartasMostrable) {
        Component[] componentes = panelPartida.getComponents();
        for (Component comp : componentes) {
            if ("CARTA".equals(comp.getName())) {
                panelPartida.remove(comp);
            }
        }
        panelPartida.revalidate();
        panelPartida.repaint();
        int i=0;
        for(CartaMostrable c: cartasMostrable){
            carta=new JLabelFondoArrastable("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\"+c.getPalo()+c.getNumero()+".png");
            carta.setEnabled(true);
            setRealeseCarta();
            carta.setBounds((160*i)+5,520,158,220);
            carta.setName("CARTA");
            ((JLabelFondoArrastable)carta).setI(i+1);
            panelPartida.add(carta);
            i++;
        }
        mostrarCartaExtra();
        panelPartida.revalidate();
        panelPartida.repaint();
    }

    private void setRealeseCarta(){
        carta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                JLabelFondoArrastable cartaSoltada = (JLabelFondoArrastable) e.getComponent();
                Rectangle areaCartaSoltada = cartaSoltada.getBounds();

                for (Component comp : panelPartida.getComponents()) {
                    Rectangle areaCartaComparada = comp.getBounds();
                    Point centroCarta = new Point(areaCartaSoltada.x + areaCartaSoltada.width / 2, areaCartaSoltada.y + areaCartaSoltada.height / 2);
                    if ("TOPE".equals(comp.getName())) {
                        if (areaCartaComparada.contains(centroCarta)) {
                            if(!levanto){
                                cartaSoltada.setLocation(cartaSoltada.getPosicionOriginal());
                            }
                            else {
                                if (!corta) {
                                    controlador.tirarCarta(((JLabelFondoArrastable) cartaSoltada).getI());
                                    tiro = true;
                                } else {
                                    controlador.cortar(((JLabelFondoArrastable) cartaSoltada).getI());
                                }
                                break;
                            }
                        }
                    }
                    else if ("CARTA".equals(comp.getName()) && cartaSoltada != comp) {
                        if (areaCartaComparada.contains(centroCarta)) {
                            Point locacionCartaSoltada=cartaSoltada.getLocation();
                            cartaSoltada.setLocation(areaCartaComparada.x, areaCartaComparada.y);
                            comp.setLocation(locacionCartaSoltada.x,locacionCartaSoltada.y);
                            controlador.moverCartas(cartaSoltada.getI(), ((JLabelFondoArrastable) comp).getI());
                            break;
                        }
                    }
                    else{
                        cartaSoltada.setLocation(cartaSoltada.getPosicionOriginal());
                    }
                }
            }
        });
    }

    @Override
    public void mostrarMazo(ArrayList<CartaMostrable> mazo) {
        Component[] componentes = panelPartida.getComponents();
        for (Component comp : componentes) {
            if ("MAZO".equals(comp.getName())) {
                panelPartida.remove(comp);
            }
        }
        mazoButton=new BotonFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\REVERSO.png");
        mazoButton.setBounds(710, 215, 190, 270);
        mazoButton.setName("MAZO");
        mazoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazoButton.setEnabled(false);
                topeButton.setEnabled(false);
                controlador.agarrarCarta(1);
                levanto=true;
                cortarButton.setEnabled(true);
            }
        });
        if(mazo.size()>1){
            if(!tiro){
                mazoButton.setEnabled(false);
            }
            panelPartida.add(mazoButton);
            panelPartida.revalidate();
            panelPartida.repaint();
        }
        else{
           panelPartida.remove(mazoButton);
           panelPartida.revalidate();
           panelPartida.repaint();
        }
    }

    public void mostrarMenuPrincipal(){
        menuPrincipal.setVisible(true);
    }

    @Override
    public void mostrarMenuJugador() {
        //NO SIRVE PARA VISTA GRAFICA
    }

    @Override
    public void opcionesCartasTirar() {
        //NO SIRVE PARA VISTA GRAFICA
    }

    @Override
    public void opcionesCartasTirarOCortar() {
        //NO SIRVE PARA VISTA GRAFICA
    }

    @Override
    public void verificarTurno() {
        Component[] components = panelPartida.getComponents();
        if(!controlador.jugadorActual().equals(nombreJugador)){
            for (Component component : components) {
                if(!component.getName().equals("CARTA")) {
                    component.setEnabled(false);
                }
            }
        }
        else{
            for (Component component : components) {
                if(component.getName().equals("CORTAR")){
                    component.setEnabled(false);
                }
                else{
                    component.setEnabled(true);
                }
            }
            tiro=false;
        }
        levanto=false;
    }

    @Override
    public void verificarPerdedores() {
        corta=false;
        ArrayList<String> perdedores=controlador.obtenerPerdedores();
        for(String nombre: perdedores){
            if(nombre.equals(nombreJugador)){
              mostrarCartelPerdedor();
              mostrarPuntos(controlador.obtenerJugadores());
            }
        }
    }

    private void mostrarCartelPerdedor(){
        iniciarPartidaFrame.setVisible(false);
        panelPerdedor=new PanelFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\Game_Over.png");
        perdedorFrame= new JFrame("PERDISTE :(");
        perdedorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        perdedorFrame.setSize(800, 600);
        perdedorFrame.setLocationRelativeTo(null);
        perdedorFrame.setContentPane(panelPerdedor);
        perdedorFrame.setVisible(true);
    }

    private void mostrarCartelGanador(){
        iniciarPartidaFrame.setVisible(false);
        panelGanador=new PanelFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\GANADOR.png");
        ganadorFrame= new JFrame("GANASTE :)");
        ganadorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ganadorFrame.setSize(800, 600);
        ganadorFrame.setLocationRelativeTo(null);
        ganadorFrame.setContentPane(panelGanador);
        ganadorFrame.setVisible(true);
    }

    @Override
    public void mostrarPuntos(ArrayList<JugadorMostrable> jugador) {
        if(tableroPuntos==null){
            tableroPuntos = new JDialog(iniciarPartidaFrame, "PUNTOS", false);
            tableroPuntos.setLayout(new BorderLayout());
            JButton cerrar = new JButton("OK");
            cerrar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tableroPuntos.setVisible(false);
                }
            });
            tableroPuntos.add(cerrar, BorderLayout.SOUTH);
            tableroPuntos.setLocationRelativeTo(iniciarPartidaFrame);
        }
        else{
            Component[] componentes=tableroPuntos.getContentPane().getComponents();
            for(Component component: componentes){
                if("MENSAJES".equals(component.getName())){
                    tableroPuntos.getContentPane().remove(component);
                }
            }
        }
        JPanel panelMensajes = new JPanel();
        panelMensajes.setName("MENSAJES");
        panelMensajes.setLayout(new BoxLayout(panelMensajes, BoxLayout.Y_AXIS));
        JLabel rondas= new JLabel("RONDA Nº "+ (controlador.obtenerCantidadDeRondas()+1));
        panelMensajes.add(rondas);
        for(JugadorMostrable j: jugador){
            JLabel mensaje = new JLabel("Jugador "+j.getNombre()+": "+ j.getPuntos());
            mensaje.setBorder(new EmptyBorder(20, 20, 20, 20));
            panelMensajes.add(mensaje,BorderLayout.CENTER);
        }
        tableroPuntos.add(panelMensajes);
        tableroPuntos.revalidate();
        tableroPuntos.repaint();
        tableroPuntos.pack();
        tableroPuntos.setVisible(true);
    }

    @Override
    public boolean isTurno() {
        boolean es=false;
        if(controlador.jugadorActual().equals(nombreJugador)){
            es=true;
        }
        return es;
    }

    @Override
    public void limpiarPantalla() {
        if(iniciarPartidaFrame==null) {
            menuPrincipal.setVisible(false);
            panelPartida = new PanelFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\fondoPartida.png");
            cortarButton.setBounds(1150, 470, 100, 40);
            cortarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    corta = true;
                }
            });
            cortarButton.setName("CORTAR");
            panelPartida.add(cortarButton);
            iniciarPartidaFrame = new JFrame("PARTIDA CHINCHON "+nombreJugador);
            iniciarPartidaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            iniciarPartidaFrame.setSize(1300, 800);
            iniciarPartidaFrame.setLocationRelativeTo(null);
            iniciarPartidaFrame.setContentPane(panelPartida);
            iniciarPartidaFrame.setVisible(true);
        }
        else{
            Component[] components= iniciarPartidaFrame.getContentPane().getComponents();
            for(Component component: components){
                iniciarPartidaFrame.getContentPane().remove(component);
            }
            panelPartida.add(cortarButton);
            panelPartida.revalidate();
            panelPartida.repaint();
        }
    }
    @Override
    public void mostrarMano() {
        if(!controlador.obtenerCartasJugador(nombreJugador).isEmpty()) {
            mostrarCartas(controlador.obtenerCartasJugador(nombreJugador));
        }
    }

    @Override
    public void mostrarCartaExtra() {
        if(controlador.obtenerCartaExtra(nombreJugador)!=null){
            CartaMostrable cartaExtra=controlador.obtenerCartaExtra(nombreJugador);
            carta=new JLabelFondoArrastable("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\"+cartaExtra.getPalo()+cartaExtra.getNumero()+".png");
            setRealeseCarta();
            carta.setBounds((160*7)+5,520,158,220);
            ((JLabelFondoArrastable)carta).setI(8);
            carta.setName("CARTA");
            panelPartida.add(carta);
            panelPartida.revalidate();
            panelPartida.repaint();
        }
    }

    private void agregarJugador(String string) {
        JLabel mensaje;
        if(nombreJugador==null) {
            if (!string.equals("")) {
                nombreJugador = string;
                if (!controlador.existeJugador(nombreJugador)) {
                    controlador.agregarJugador(nombreJugador);
                    agregarJugadorFrame.setVisible(false);
                    mostrarMenuPrincipal();
                } else {
                    nombreJugador=null;
                    JDialog warning = new JDialog(agregarJugadorFrame, "WARNING", false);
                    warning.setLayout(new FlowLayout());
                    mensaje = new JLabel("ESE JUGADOR YA EXISTE INGRESE OTRO");
                    mensaje.setBorder(new EmptyBorder(20, 20, 20, 20));
                    warning.add(mensaje);
                    JButton cerrar = new JButton("OK");
                    cerrar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            warning.setVisible(false);
                        }
                    });
                    warning.add(cerrar);
                    warning.pack();
                    warning.setLocationRelativeTo(agregarJugadorFrame);
                    warning.setVisible(true);
                }
            } else {
                JDialog warning2 = new JDialog(agregarJugadorFrame, "WARNING", false);
                warning2.setLayout(new BorderLayout());
                mensaje = new JLabel("INGRESE UN NOMBRE VALIDO");
                mensaje.setBorder(new EmptyBorder(20, 20, 20, 20));
                warning2.add(mensaje, BorderLayout.CENTER);
                JButton cerrar = new JButton("OK");
                cerrar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        warning2.setVisible(false);
                    }
                });
                warning2.add(cerrar, BorderLayout.SOUTH);
                warning2.setLocationRelativeTo(agregarJugadorFrame);
                warning2.pack();
                warning2.setVisible(true);
            }
        }
        else{
            JDialog warning3 = new JDialog(agregarJugadorFrame, "WARNING", false);
            warning3.setLayout(new BorderLayout());
            mensaje = new JLabel("YA INGRESASTE UN JUGADOR");
            mensaje.setBorder(new EmptyBorder(20, 20, 20, 20));
            warning3.add(mensaje, BorderLayout.CENTER);
            JButton cerrar = new JButton("OK");
            cerrar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    warning3.setVisible(false);
                    agregarJugadorFrame.setVisible(false);
                    mostrarMenuPrincipal();
                }
            });
            warning3.add(cerrar, BorderLayout.SOUTH);
            warning3.setLocationRelativeTo(agregarJugadorFrame);
            warning3.pack();
            warning3.setVisible(true);
        }
    }

    private void mostrarListaJugadores(){
        pantalla.setText("   LISTA DE JUGADORES\n\n");
        for(JugadorMostrable j:controlador.obtenerJugadores()) {
            pantalla.append(j.getNombre()+"\n");
        }
    }

}
