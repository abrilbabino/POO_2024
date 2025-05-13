package ar.edu.unlu.poo.chinchon.Vista;

import ar.edu.unlu.poo.chinchon.Controlador.Controlador;
import ar.edu.unlu.poo.chinchon.Modelo.CartaMostrable;
import ar.edu.unlu.poo.chinchon.Modelo.JugadorMostrable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VistaConsola extends JFrame implements  IVista {
    private Controlador controlador;
    private EstadoVista estadoVista;
    private JButton enterButton;
    private JTextField ingresaTexto;
    private JTextArea saleTexto;
    private JPanel panelPrincipal;
    private String nombreJugador;

    public VistaConsola(Controlador c) {
        this.controlador = c;
        setTitle("ChinChon Consola");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        saleTexto.setFont(new Font("Courier New", Font.PLAIN, 14));
        saleTexto.setEditable(false);
        ingresaTexto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterButton.doClick(); // Simula el clic en el botón
            }
        });
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                println(ingresaTexto.getText());
                procesarEntrada(ingresaTexto.getText());
                ingresaTexto.setText("");
            }
        });
        mostrarMenuPrincipal();
    }
    private void procesarEntrada(String entrada) {
        switch (estadoVista) {
            case MENU:
                limpiarPantalla();
                procesarEntradaMenu(entrada);
                break;
            case AGREGAR_JUGADOR:
                agregarJugador();
                break;
            case MENU_JUGADOR:
                procesarEntradaJugador(entrada);
                break;
            case TIRAR_O_CORTAR:
                procesarEntradaTirarOCortar(entrada);
                break;
            case TIRAR_CARTA:
                tirarCarta(entrada);
                break;
            case CAMBIAR_CARTAS:
                procesarCartasAMover(entrada);
                break;
            case CORTAR:
                procesarEntradaCortar(entrada);
                break;
        }
    }

    private void print(String texto) {
        saleTexto.append(texto);
    }

    private void println(String texto) {
        print(texto + "\n");
    }

    private void agregarJugador() {
        if (!ingresaTexto.getText().equals("")) {
            nombreJugador = ingresaTexto.getText();
            if (!controlador.existeJugador(nombreJugador)) {
                controlador.agregarJugador(nombreJugador);
                mostrarMenuPrincipal();
            } else {
                println("-------------------------------------------------------------------------------------");
                println("ESE JUGADOR YA ESTA EN EL JUEGO, INGRESE OTRO: ");
            }
        } else {
            println("-------------------------------------------------------------------------------------");
            println("INGRESE UN NOMBRE VALIDO PARA EL JUGADOR: ");
        }
    }

    private void mostrarJugadores() {
        if(controlador.obtenerJugadores().size()==0){
            println("-------------------------------------------------------------------------------------");
            println("NO HAY JUGADORES");
            println("-------------------------------------------------------------------------------------");
        }
        else {
            println("-------------------------------------------------------------------------------------");
            for (JugadorMostrable j : controlador.obtenerJugadores()) {
                println(j.getNombre());
                println("-------------------------------------------------------------------------------------");
            }
        }
        println("");
        mostrarMenuPrincipal();
    }

    private void iniciarPartida() {
        boolean inicia = controlador.iniciarJuego();
        if (!inicia) {
            mostrarMenuPrincipal();
        }
    }

    @Override
    public void mostrarMenuPrincipal() {
        estadoVista = EstadoVista.MENU;
        println("----------------------------------------------------------------------------------------");
        println("                                    MENU PRINCIPAL");
        println("----------------------------------------------------------------------------------------");
        println("                                        OPCIONES");
        println("----------------------------------------------------------------------------------------");
        println(" 1- AGREGAR JUGADOR");
        println("----------------------------------------------------------------------------------------");
        println(" 2- MOSTRAR JUGADORES");
        println("----------------------------------------------------------------------------------------");
        println(" 3- INICIAR PARTIDA");
        println("----------------------------------------------------------------------------------------");
        println(" 4- SALIR");
        println("----------------------------------------------------------------------------------------");
        print(" SELECCIONE UNA OPCION: ");

    }

    private void procesarEntradaMenu(String entrada) {
        switch (entrada) {
            case "1":
                if(nombreJugador==null) {
                    println("----------------------------------------------------------------------------------------");
                    print(" NOMBRE JUGADOR: ");
                    estadoVista = EstadoVista.AGREGAR_JUGADOR;
                }
                else{
                    println("----------------------------------------------------------------------------------------");
                    println(" YA AGREGASTE UN JUGADOR!!");
                    println("----------------------------------------------------------------------------------------");
                    mostrarMenuPrincipal();
                }
                break;
            case "2":
                println("----------------------------------------------------------------------------------------");
                println(" LISTA JUGADORES");
                mostrarJugadores();
                break;
            case "3":
                iniciarPartida();
                break;
            case "4":
                System.exit(0);
                break;
            default:
                println("----------------------------------------------------------------------------------------");
                println(" OPCION INVALIDA, ELIJA UNA OPCION VALIDA: ");
                mostrarMenuPrincipal();
        }
    }

    @Override
    public void mostrarMenuJugador() {
        estadoVista = EstadoVista.MENU_JUGADOR;
        println("----------------------------------------------------------------------------------------");
        println("                                    MENU JUGADOR");
        println("----------------------------------------------------------------------------------------");
        println(" 1-AGARRAR CARTA DEL MAZO");
        println("----------------------------------------------------------------------------------------");
        println(" 2-AGARRAR CARTA DEL TOPE DE LA PILA DE DESCARTE");
        println("----------------------------------------------------------------------------------------");
        println(" 3-MOVER CARTAS");
        println("----------------------------------------------------------------------------------------");
        print(" SELECCIONE UNA OPCION: ");
    }


    private void procesarEntradaJugador(String entrada) {
        switch (entrada) {
            case "1":
            case "2":
                controlador.agarrarCarta(Integer.parseInt(entrada));
                opcionesCartasTirarOCortar();
                break;
            case "3":
                println("----------------------------------------------------------------------------------------");
                println(" INGRESE LAS CARTAS A MOVER POSCARTA1-POSCARTA2: ");
                estadoVista=EstadoVista.CAMBIAR_CARTAS;
                break;
            default:
                println("----------------------------------------------------------------------------------------");
                println("OPCION NO VALIDA, ELIJA OTRA:");
                mostrarMenuJugador();
        }
    }

    private boolean esNumero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void procesarCartasAMover(String entrada){
        if(entrada.length()==3) {
            if(entrada.substring(1,2).equals("-")) {
                String n1=entrada.substring(0,1);
                String n2=entrada.substring(2,3);
                if(esNumero(n1)&& esNumero(n2)) {
                    int numero1 = Integer.parseInt(n1);
                    int numero2 = Integer.parseInt(n2);
                    if (numero1 >= 1 && numero1 <= 7 && numero2 >= 1 && numero2 <= 7) {
                        controlador.moverCartas(Integer.parseInt(entrada.substring(0, 1)), Integer.parseInt(entrada.substring(2, 3)));
                        mostrarMenuJugador();
                    } else {
                        println("----------------------------------------------------------------------------------------");
                        println("POSCARTA FUERA DEL RANGO, DEBE SER UN NUMERO ENTRE [1-7]");
                        println("----------------------------------------------------------------------------------------");
                        println("DEBE INGRESAR POS_CARTA_MOVER1-POS_CARTA_MOVER2: ");
                    }
                }
                else{
                    println("----------------------------------------------------------------------------------------");
                    println("FORMATO INCORRECTO POS_CARTA DEBE SER UN NUMERO");
                    println("----------------------------------------------------------------------------------------");
                    println("DEBE INGRESAR POS_CARTA_MOVER1-POS_CARTA_MOVER2: ");
                }
            }
            else{
                println("----------------------------------------------------------------------------------------");
                println("FORMATO INCORRECTO, DEBE INGRESAR POS_CARTA_MOVER1-POS_CARTA_MOVER2: ");
            }
        }
        else{
            println("----------------------------------------------------------------------------------------");
            println("FORMATO INCORRECTO, DEBE INGRESAR POS_CARTA_MOVER1-POS_CARTA_MOVER2: ");
        }
    }

    public void opcionesCartasTirarOCortar() {
        estadoVista=EstadoVista.TIRAR_O_CORTAR;
        println("----------------------------------------------------------------------------------------");
        println("1- TIRAR CARTA");
        println("----------------------------------------------------------------------------------------");
        println("2- CORTAR");
        println("----------------------------------------------------------------------------------------");
        print("SELECCIONE UNA OPCION: ");
    }

    private void procesarEntradaTirarOCortar(String entrada) {
        switch(entrada){
            case "1":
                opcionesCartasTirar();
                break;
            case "2":
                opcionesCartasCortar();
                break;
            default:
                println("----------------------------------------------------------------------------------------");
                println("OPCION INVALIDA");
                opcionesCartasTirarOCortar();
        }
    }

    @Override
    public void opcionesCartasTirar() {
        estadoVista=EstadoVista.TIRAR_CARTA;
        println("----------------------------------------------------------------------------------------");
        println("                                 OPCIONES DE CARTAS PARA TIRAR");
        println("----------------------------------------------------------------------------------------");
        println("1-Primer Carta");
        println("----------------------------------------------------------------------------------------");
        println("2-Segunda Carta");
        println("----------------------------------------------------------------------------------------");
        println("3-Tercer Carta");
        println("----------------------------------------------------------------------------------------");
        println("4-Cuarta Carta");
        println("----------------------------------------------------------------------------------------");
        println("5-Quinta Carta");
        println("----------------------------------------------------------------------------------------");
        println("6-Sexta Carta");
        println("----------------------------------------------------------------------------------------");
        println("7-Septima Carta");
        println("----------------------------------------------------------------------------------------");
        println("8-Ultima Carta Levantada");
        println("----------------------------------------------------------------------------------------");
        print("Seleccione la carta a tirar: ");
    }

    private void tirarCarta(String entrada) {
        switch (entrada) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
                controlador.tirarCarta(Integer.parseInt(entrada));
                break;
            default:
                println("----------------------------------------------------------------------------------------");
                println("OPCION INVALIDA");
                opcionesCartasTirar();

        }
    }

    public void opcionesCartasCortar(){
        estadoVista= EstadoVista.CORTAR;
        println("----------------------------------------------------------------------------------------");
        println("                              OPCIONES DE CARTAS PARA CORTAR");
        println("----------------------------------------------------------------------------------------");
        println("1-Primer Carta");
        println("----------------------------------------------------------------------------------------");
        println("2-Segunda Carta");
        println("----------------------------------------------------------------------------------------");
        println("3-Tercer Carta");
        println("----------------------------------------------------------------------------------------");
        println("4-Cuarta Carta");
        println("----------------------------------------------------------------------------------------");
        println("5-Quinta Carta");
        println("----------------------------------------------------------------------------------------");
        println("6-Sexta Carta");
        println("----------------------------------------------------------------------------------------");
        println("7-Septima Carta");
        println("----------------------------------------------------------------------------------------");
        println("8-Ultima Carta Levantada");
        println("----------------------------------------------------------------------------------------");
        print("Seleccione la carta para cortar: ");
    }

    private void procesarEntradaCortar(String entrada) {
        switch (entrada){
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
                controlador.cortar(Integer.parseInt(entrada));
                break;
            default:
                println("----------------------------------------------------------------------------------------");
                println("OPCION INVALIDA");
                opcionesCartasCortar();
        }
    }

    public void mostrarPuntos(ArrayList<JugadorMostrable> jugadores){
        println("----------------------------------------------------------------------------------------");
        println("RONDA Nº "+ (controlador.obtenerCantidadDeRondas()+1));
        println("----------------------------------------------------------------------------------------");
        for (JugadorMostrable j:jugadores) {
            println("----------------------------------------------------------------------------------------");
            println("Jugador "+j.getNombre()+": "+ j.getPuntos());
            println("----------------------------------------------------------------------------------------");
        }
    }

    public void verificarPerdedores() {
        ArrayList<String> perdedores=controlador.obtenerPerdedores();
        for(String nombre: perdedores){
            if(nombre.equals(nombreJugador)){
                limpiarPantalla();
                mostrarPuntos(controlador.obtenerJugadores());
                println("----------------------------------------------------------------------------------------");
                println("                                      PERDISTE :( ");
                println("----------------------------------------------------------------------------------------");
                finalizar();
            }
        }
    }

    private void esperarTurno(){
        mostrarMensaje("TURNO DEL JUGADOR: "+ controlador.jugadorActual());
        mostrarMensaje("ESPERANDO...");
        Component[] components = panelPrincipal.getComponents();
        for (Component component : components) {
            component.setEnabled(false);
        }
    }

    private void esTurno(){
        println("----------------------------------------------------------------------------------------");
        println(" ES TU TURNO "+ controlador.jugadorActual());
        println("----------------------------------------------------------------------------------------");
        Component[] components = panelPrincipal.getComponents();
        for (Component component : components) {
            component.setEnabled(true);
        }
    }

    public boolean isTurno(){
        boolean es=false;
        if(controlador.jugadorActual().equals(nombreJugador)){
            es=true;
        }
        return es;
    }

    public void verificarTurno(){
        if(!controlador.jugadorActual().equals(nombreJugador)){
            esperarTurno();
        }
        else{
            esTurno();
            mostrarMenuJugador();
        }
    }

    private void finalizar() {
        Component[] components = panelPrincipal.getComponents();
        for (Component component : components) {
            component.setEnabled(false);
        }
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        int anchoTotal = 88;
        int espaciosDisponibles = mensaje.length();
        int espaciosIzquierda = (anchoTotal-espaciosDisponibles)/2;

        if(mensaje.equals("JUEGO TERMINADO")) {
            if(nombreJugador.equals(controlador.getGanador())){
                limpiarPantalla();
                println("----------------------------------------------------------------------------------------");
                println("                                       GANASTE!! :)");
                println("----------------------------------------------------------------------------------------");
            }
            println("----------------------------------------------------------------------------------------");
            println(" ".repeat(espaciosIzquierda) +mensaje);
            println("----------------------------------------------------------------------------------------");
            println("                                   GRACIAS POR JUGAR");
            println("----------------------------------------------------------------------------------------");
            finalizar();
        }
        else{
            println("----------------------------------------------------------------------------------------");
            println(" ".repeat(espaciosIzquierda) +mensaje);
            println("----------------------------------------------------------------------------------------");
        }
    }

    @Override
    public void mostrarCartaTope(CartaMostrable cartaMostrable) {
        mostrarMensaje("CARTA TOPE PILA DESCARTE");
        ArrayList<CartaMostrable> cartas= new ArrayList<>();
        if(controlador.obtenerCartaTope()!=null) {
            cartas.add(cartaMostrable);
            mostrarCartas(cartas);
        }
        else{
            mostrarMensaje("CARTA TOPE (VACIO)");
        }
    }

    public void mostrarCartas(ArrayList<CartaMostrable> cartas) {
        ArrayList<String[]> cartasRenderizadas = new ArrayList<>();
        int anchoCarta = 11;
        if(cartas.size()>0) {
            for (CartaMostrable carta : cartas) {
                String palo = carta.getPalo().toString();
                int numero = carta.getNumero();
                String bordeSuperior = "┌─────────┐";
                String bordeLado = "│";
                String bordeInferior = "└─────────┘";

                int espacio = (anchoCarta - 2 - palo.length()) / 2;
                int resto = (anchoCarta - 2 - palo.length()) % 2;
                String suitLine = String.format("│%" + espacio + "s%s%" + (espacio + resto) + "s│", "", palo, "");

                String numeroIzquierda = String.format("%-2s", numero);
                String numeroDerecha = String.format("%2s", numero);

                String[] cartaRenderizada = {
                        bordeSuperior,
                        bordeLado + numeroIzquierda + "       │",
                        bordeLado + "         │",
                        suitLine,
                        bordeLado + "         │",
                        bordeLado + "       " + numeroDerecha + "│",
                        bordeInferior
                };

                cartasRenderizadas.add(cartaRenderizada);
            }

            for (int i = 0; i < 7; i++) { // 7 líneas por carta
                for (String[] carta : cartasRenderizadas) {
                    print(carta[i] + " ");
                }
                println("");
            }
        }
    }



    @Override
    public void mostrarMazo(ArrayList<CartaMostrable> mazo) {
        println("MAZO");
        String[] card = {
                "┌─────┐",
                "│     │",
                "│     │",
                "└─────┘"
        };
        if(mazo.size()>1) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < card.length - 1; j++) {
                    for (int k = 0; k < i; k++) {
                        print(" ");
                    }
                    println(card[j]);
                }
            }

            for (int i = 0; i < 2; i++) {
                print(" ");
            }
            println(card[card.length - 1]);
        }
        else if(mazo.size()==1){
            for(String linea:card){
                println(linea);
            }
        }
        else{
            println("----------------------------------------------------------------------------------------");
            println("                                         MAZO VACIO");
            println("----------------------------------------------------------------------------------------");
        }
    }

    @Override
    public void limpiarPantalla() {
        saleTexto.setText("");
    }

    @Override
    public void mostrarMano() {
        mostrarMensaje("MANO");
        if(!controlador.obtenerCartasJugador(nombreJugador).isEmpty()) {
            mostrarCartas(controlador.obtenerCartasJugador(nombreJugador));
        }
        mostrarCartaExtra();
    }

    @Override
    public void mostrarCartaExtra() {
        if(controlador.obtenerCartaExtra(nombreJugador)!=null) {
            mostrarMensaje("CARTA LEVANTADA");
            ArrayList<CartaMostrable> cartas=new ArrayList<>();
            cartas.add(controlador.obtenerCartaExtra(nombreJugador));
            mostrarCartas(cartas);
        }
    }

}

