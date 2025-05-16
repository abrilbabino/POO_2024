package ar.edu.unlu.poo.chinchon.Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import ar.edu.unlu.poo.chinchon.Modelo.Eventos;
import ar.edu.unlu.poo.chinchon.Modelo.RankingMostrable;
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
    private JButton mostrarRankingButton;
    private JPanel panelPartida;
    private JLabel carta;
    private JButton mazoButton;
    private JButton topeButton;
    private JButton cortarButton;
    private JPanel panelAyuda;
    private JButton ayudaButton;
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
        mostrarRankingButton=new BotonFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\mostrarRanking.png");
        mostrarRankingButton.setBounds(20, 450, 100, 100);
        mostrarRankingButton.setBorderPainted(false);
        mostrarRankingButton.setContentAreaFilled(false);
        mostrarRankingButton.setFocusPainted(false);
        mostrarRankingButton.setOpaque(false);
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
                agregarJugadorFrame.setLocation(menuPrincipal.getLocation());
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
                setVolverButton(listaJugadoresFrame,580,520);
                panelListaJugadores.add(volverButton);
                panelListaJugadores.add(pantalla);
                mostrarListaJugadores();
                listaJugadoresFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                listaJugadoresFrame.setSize(800, 600);
                listaJugadoresFrame.setLocation(menuPrincipal.getLocation());
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
                    warningNoJugadoresSuf.setLocationRelativeTo(menuPrincipal);
                    warningNoJugadoresSuf.pack();
                    warningNoJugadoresSuf.setVisible(true);
                }
            }
        });

        mostrarRankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRanking();
            }
        });
        ayudaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuPrincipal.setVisible(false);
                panelAyuda=new PanelFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\Como_jugar.png");
                ayudaFrame=new JFrame("AYUDA");
                setVolverButton(ayudaFrame,580,520);
                panelAyuda.add(volverButton);
                ayudaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ayudaFrame.setSize(800,600);
                ayudaFrame.setLocation(menuPrincipal.getLocation());
                ayudaFrame.setContentPane(panelAyuda);
                ayudaFrame.setVisible(true);
            }
        });
        panelPrincipal.add(agregarJugadorButton);
        panelPrincipal.add(listaDeJugadoresButton);
        panelPrincipal.add(iniciarPartidaButton);
        panelPrincipal.add(mostrarRankingButton);
        panelPrincipal.add(salirButton);
        panelPrincipal.add(ayudaButton);
        menuPrincipal = new JFrame("MENU PRINCIPAL");
        menuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuPrincipal.setSize(800, 600);
        menuPrincipal.setLocationRelativeTo(null);
        menuPrincipal.setContentPane(panelPrincipal);
        mostrarMenuPrincipal();
    }

    //SIRVE EN DOS CASOS, CUANDO NO PUEDE CORTAR INFORMA POR PANTALLA, Y CUANDO EL JUEGO TERMINA MUESTRA
    // EL CARTEL DEL GANADOR
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
            if(nombreJugador.equals(controlador.obtenerGanador())){
                mostrarCartelGanador();
            }
        }
    }

    //MUESTRA POR PANTALLA LA CARTA DEL TOPE DE LA PILA DESCARTE
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
            topeButton.setBounds(250, 160, 115, 202);
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
            topeButton.setBounds(250, 160, 115, 202);
            topeButton.setName("TOPE");
        }
        if(!tiro && levanto){
            topeButton.setEnabled(false);
        }
        panelPartida.add(topeButton);
        panelPartida.revalidate();
        panelPartida.repaint();
    }

    //MUESTRA POR PANTALLA EL MAZO DE CARTAS
    @Override
    public void mostrarMazo(ArrayList<CartaMostrable> mazo) {
        Component[] componentes = panelPartida.getComponents();
        for (Component comp : componentes) {
            if ("MAZO".equals(comp.getName())) {
                panelPartida.remove(comp);
            }
        }
        mazoButton=new BotonFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\REVERSO.png");
        mazoButton.setBounds(433, 158, 117, 202);
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
        if(mazo.size()>=1){
            if(!tiro||!isTurno()){
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

    //ABRE UNA PANTALLA CON EL MENU PRINCIPAL
    @Override
    public void mostrarMenuPrincipal(){
        menuPrincipal.setVisible(true);
    }

    //NO SE USA EN LA VISTA GRAFICA YA QUE EL JUGADOR INTERACTUA CON LOS COMPONENTES DE LA PANTALLA
    @Override
    public void mostrarMenuJugador() {
        //NO HACE NADA
    }

    //NO SE USA EN LA VISTA GRAFICA YA QUE EL JUGADOR INTERACTUA CON LOS COMPONENTES DE LA PANTALLA
    @Override
    public void opcionesCartasTirar() {
        //NO HACE NADA
    }

    //NO SE USA EN LA VISTA GRAFICA YA QUE EL JUGADOR INTERACTUA CON LOS COMPONENTES DE LA PANTALLA
    @Override
    public void opcionesCartasTirarOCortar() {
        //NO HACE NADA
    }

    //VERIFICA SI ES EL TURNO DEL JUGADOR, SI ES EL TURNO HABILITA LOS COMPONENTES, CASO CONTRARIO
    // SE DESHABILITAN LOS COMPONENTES DE LA PANTALLA PARA QUE EL JUGADOR ESPERE SU TURNO
    @Override
    public void verificarTurno() {
        Component[] components = panelPartida.getComponents();
        if(!isTurno()){
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

    //VERIFICA SI EL JUGADOR PERDIO Y EN ESE CASO SE LE MUESTRA UN CARTEL DE PERDEDOR
    @Override
    public void verificarPerdedores() {
        corta=false;
        ArrayList<String> perdedores=controlador.obtenerPerdedores();
        for(String nombre: perdedores){
            if(nombre.equals(nombreJugador)){
              mostrarCartelPerdedor();
            }
        }
    }

    //MUESTRA POR PANTALLA LOS PUNTOS DE TODOS LOS JUGADORES
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

    //EVALUA SI ES EL TURNO DEL JUGADOR Y DEVUELVE EL RESULTADO
    @Override
    public boolean isTurno() {
        return controlador.jugadorActual().equals(nombreJugador);
    }

    //AL PRINCIPIO DE CADA RONDA MUESTRA LA PANTALLA DE JUEGO CON LOS COMPONENTES CORRESPONDIENTES
    @Override
    public void limpiarPantalla() {
        if(iniciarPartidaFrame==null) {
            menuPrincipal.setVisible(false);
            panelPartida = new PanelFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\fondoPartida.png");
            cortarButton.setBounds(690, 350, 90, 40);
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
            iniciarPartidaFrame.setSize(800, 600);
            iniciarPartidaFrame.setLocation(menuPrincipal.getLocation());
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

    //MUESTRA POR PANTALLA LA MANO DEL JUGADOR
    @Override
    public void mostrarMano() {
        if(!controlador.obtenerCartasJugador(nombreJugador).isEmpty()) {
            mostrarCartas(controlador.obtenerCartasJugador(nombreJugador));
        }
    }

    //MUESTRA POR PANTALLA LA ULTIMA CARTA LEVANTADA POR EL JUGADOR
    @Override
    public void mostrarCartaExtra() {
        if(controlador.obtenerCartaExtra(nombreJugador)!=null){
            CartaMostrable cartaExtra=controlador.obtenerCartaExtra(nombreJugador);
            carta=new JLabelFondoArrastable("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\"+cartaExtra.getPalo()+cartaExtra.getNumero()+".png");
            setRealeseCarta();
            carta.setBounds((98*7)+3,390,97,165);
            ((JLabelFondoArrastable)carta).setI(8);
            carta.setName("CARTA");
            panelPartida.add(carta);
            panelPartida.revalidate();
            panelPartida.repaint();
        }
    }

    //CREA UN PANEL PRINCIPAL CON FONDO
    private void createUIComponents() {
        panelPrincipal=new PanelFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\Menu_principal.png");
    }

    //SETEA LA PANTALLA EN LA QUE VA A APARECER EL BOTON VOLVER Y SETEA SU POSICION EN LA PANTALLA
    private void setVolverButton(JFrame frame,int x,int y){
        volverButton.setBounds(x,y,200,30);
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                mostrarMenuPrincipal();
            }
        });
    }

    //MUESTRA POR PANTALLA EL CONJUNTO DE CARTAS QUE RECIBE
    private void mostrarCartas(ArrayList<CartaMostrable> cartasMostrable) {
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
            carta.setBounds((98*i)+3,390,97,165);
            carta.setName("CARTA");
            ((JLabelFondoArrastable)carta).setI(i+1);
            panelPartida.add(carta);
            i++;
        }
        mostrarCartaExtra();
        panelPartida.revalidate();
        panelPartida.repaint();
    }

    //DEFINE LAS ACCIONES QUE SE REALIZAN CUANDO UN JUGADOR SUELTA UNA CARTA
    private void setRealeseCarta(){
        carta.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Component c = e.getComponent();
                panelPartida.setComponentZOrder(c, 0);
                panelPartida.repaint();
            }
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
                            if(isTurno()) {
                                controlador.moverCartas(cartaSoltada.getI(), ((JLabelFondoArrastable) comp).getI());
                            }
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

    //VALIDA LA ENTRADA DE NOMBRE JUGADOR Y SI ES CORRECTA SE AGREGA AL JUEGO, CASO CONTRARIO MUESTRA
    // EL ERROR
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

    //MUESTRA POR PANTALLA LA LISTA DE JUGADORES EN JUEGO
    private void mostrarListaJugadores(){
        pantalla.setText("   LISTA DE JUGADORES\n\n");
        for(JugadorMostrable j:controlador.obtenerJugadores()) {
            pantalla.append(j.getNombre()+"\n");
        }
    }

    //MUESTRA POR PANTALLA EL RANKING DE LOS MEJORES 5 PUNTAJES
    private void mostrarRanking(){
        menuPrincipal.setVisible(false);
        JPanel panelRanking=new PanelFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\Ranking.png");
        JTextArea entradas=new JTextArea();
        entradas.setOpaque(false);
        entradas.setEditable(false);
        entradas.setBounds(145,293,300,500);
        entradas.setFont(new Font("Monospaced", Font.PLAIN, 22));
        entradas.setEditable(false);
        entradas.setFocusable(false);
        JFrame rankingFrame = new JFrame("TOP 5");
        setVolverButton(rankingFrame,380,720);
        panelRanking.add(volverButton);
        panelRanking.add(entradas);
        RankingMostrable rankingMostrable=controlador.obtenerRanking();
        for(JugadorMostrable j: rankingMostrable.getRanking()){
            entradas.append(j.getNombre()+ ": "+ j.getPuntos()+ " PUNTOS\n\n");
        }
        rankingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rankingFrame.setSize(600, 800);
        rankingFrame.setLocation(menuPrincipal.getLocation());
        rankingFrame.setContentPane(panelRanking);
        rankingFrame.setVisible(true);
    }

    //MUESTRA POR PANTALLA EL CARTEL DEL PERDEDOR
    private void mostrarCartelPerdedor(){
        JPanel panelPerdedor;
        JFrame perdedorFrame;
        iniciarPartidaFrame.setVisible(false);
        panelPerdedor=new PanelFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\Game_Over.png");
        perdedorFrame= new JFrame("PERDISTE :(");
        perdedorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        perdedorFrame.setSize(800, 600);
        perdedorFrame.setLocation(iniciarPartidaFrame.getLocation());
        perdedorFrame.setContentPane(panelPerdedor);
        perdedorFrame.setVisible(true);
        mostrarPuntos(controlador.obtenerJugadores());
    }

    //MUESTRA POR PANTALLA EL CARTEL DEL GANADOR
    private void mostrarCartelGanador(){
        JPanel panelGanador;
        JFrame ganadorFrame;
        iniciarPartidaFrame.setVisible(false);
        panelGanador=new PanelFondo("C:\\Users\\Usuario\\IdeaProjects\\ProyectoFinal\\src\\ar\\edu\\unlu\\poo\\chinchon\\Imagenes\\GANADOR.png");
        ganadorFrame= new JFrame("GANASTE :)");
        ganadorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ganadorFrame.setSize(800, 600);
        ganadorFrame.setLocation(iniciarPartidaFrame.getLocation());
        ganadorFrame.setContentPane(panelGanador);
        ganadorFrame.setVisible(true);
        mostrarPuntos(controlador.obtenerJugadores());
    }
}
