package ar.edu.unlu.poo.chinchon.Vista;

import ar.edu.unlu.poo.chinchon.Controlador.Controlador;
import ar.edu.unlu.poo.chinchon.Modelo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

public class VistaConsolaG extends JFrame implements  IVista {
    private Controlador controlador;
    private EstadoVista estadoVista;
    private JButton enterButton;
    private JTextField ingresaTexto;
    private JTextArea saleTexto;
    private JPanel panelPrincipal;
    private String nombreJugador;

    public VistaConsolaG(Controlador c) {
        this.controlador = c;
        setTitle("ChinChon Consola");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        saleTexto.setFont(new Font("Courier New", Font.PLAIN, 14));
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
    public void procesarEntrada(String entrada) {
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
        if(!ingresaTexto.getText().equals("")) {
            nombreJugador = ingresaTexto.getText();
            controlador.agregarJugador(nombreJugador);
            mostrarMenuPrincipal();
        }
        else{
            println("INGRESE UN NOMBRE VALIDO PARA EL JUGADOR: ");
        }
    }

    private void mostrarJugadores() {
        if(controlador.obtenerJugadores().size()==0){
            println("NO HAY JUGADORES");
        }
        else {
            for (JugadorMostrable j : controlador.obtenerJugadores()) {
                println(j.getNombre());
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
        println("MENU PRINCIPAL");
        println("1-Agregar Jugador");
        println("2- Mostrar jugadores");
        println(("3-Iniciar Partida"));
        println("4-Salir");
        print("Seleccione una opcion: ");
    }

    private void procesarEntradaMenu(String entrada) {
        switch (entrada) {
            case "1":
                print("\nNOMBRE JUGADOR: ");
                estadoVista = EstadoVista.AGREGAR_JUGADOR;
                break;
            case "2":
                println("\nLISTA JUGADORES");
                mostrarJugadores();
                break;
            case "3":
                iniciarPartida();
                break;
            case "4":
                System.exit(0);
                break;
            default:
                print("Opcion no valida, elija una opcion valida: ");
                mostrarMenuPrincipal();
        }
    }

    @Override
    public void mostrarMenuJugador() {
        estadoVista = EstadoVista.MENU_JUGADOR;
        println("MENU JUGADOR");
        println("1-Agarrar carta del mazo");
        println("2-Agarrar carta tope de la pila descarte");
        println("3-Mover cartas Ncarta-Ncarta");
        print("Seleccione una opcion: ");
    }


    private void procesarEntradaJugador(String entrada) {
        switch (entrada) {
            case "1":
            case "2":
                controlador.agarrarCarta(Integer.parseInt(entrada));
                opcionesCartasTirarOCortar();
                break;
            case "3":
                println("INGRESE LAS CARTAS A MOVER (POS_CARTA1-POS_CARTA2): ");
                estadoVista=EstadoVista.CAMBIAR_CARTAS;
                break;
            default:
                print("OPCION NO VALIDA, ELIJA OTRA");
                mostrarMenuJugador();
        }
    }

    private void procesarCartasAMover(String entrada){
        controlador.moverCartas(Integer.parseInt(entrada.substring(0,1)),Integer.parseInt(entrada.substring(2,3)));
        mostrarMenuJugador();
    }

    private void opcionesCartasTirarOCortar() {
        estadoVista=EstadoVista.TIRAR_O_CORTAR;
        println("1- TIRAR CARTA");
        println("2- CORTAR");
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
                println("Opcion no valida");
                opcionesCartasTirarOCortar();
        }
    }

    @Override
    public void opcionesCartasTirar() {
        estadoVista=EstadoVista.TIRAR_CARTA;
        println("OPCIONES DE CARTAS A TIRAR");
        println("1-Primer Carta");
        println("2-Segunda Carta");
        println("3-Tercer Carta");
        println("4-Cuarta Carta");
        println("5-Quinta Carta");
        println("6-Sexta Carta");
        println("7-Septima Carta");
        println("8-Ultima Carta Levantada");
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
                print("OPCION NO VALIDA");
                opcionesCartasTirar();

        }
    }

    public void opcionesCartasCortar(){
        estadoVista= EstadoVista.CORTAR;
        println("OPCIONES DE CARTAS PARA CORTAR");
        println("1-Primer Carta");
        println("2-Segunda Carta");
        println("3-Tercer Carta");
        println("4-Cuarta Carta");
        println("5-Quinta Carta");
        println("6-Sexta Carta");
        println("7-Septima Carta");
        println("8-Ultima Carta Levantada");
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
                println("INGRESE UNA OPCION VALIDA");
                opcionesCartasCortar();
        }

    }

    public void mostrarPuntos(ArrayList<JugadorMostrable> jugadores){
        println("RONDA Nº "+ (controlador.obtenerCantidadDeRondas()+1));
        for (JugadorMostrable j:jugadores) {
           println("Jugador "+j.getNombre()+": "+ j.getPuntos());
           println("");
        }
    }


    public void verificarPerdedores() {
        ArrayList<String> perdedores=controlador.obtenerPerdedores();
        for(String nombre: perdedores){
            if(nombre.equals(nombreJugador)){
                println("PERDISTE :(");
                finalizar();
            }
        }
    }


    public void esperarTurno(){
        println("\nTURNO DEL JUGADOR: "+ controlador.jugadorActual());
        println("");
        println("ESPERANDO");
        Component[] components = panelPrincipal.getComponents();
        for (Component component : components) {
            component.setEnabled(false);
        }
    }
    public void esTurno(){
        println("\nES TU TURNO "+ controlador.jugadorActual());
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
            mostrarMano(controlador.obtenerCartasMano());
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
        if(mensaje.equals("JUEGO TERMINADO")) {
            println(mensaje);
            println("GRACIAS POR JUGAR");
            finalizar();
        }
        else{
            println(mensaje);
        }
    }

    public void mostrarMano(ArrayList<CartaMostrable> mano){
        println("\nMANO DEL JUGADOR: "+ controlador.jugadorActual());
        for(CartaMostrable c: mano){
            mostrarCarta(c);
        }
    }

    @Override
    public void mostrarCarta(CartaMostrable carta) {

        String palo = carta.getPalo().toString();
        int numero = carta.getNumero();
        int anchoCarta = 11;
        String bordeSuperior = "┌─────────┐";
        String bordeLado = "│";
        String bordeInferior = "└─────────┘";

        int espacio = (anchoCarta - 2 - palo.length()) / 2;
        int resto = (anchoCarta - 2 - palo.length()) % 2;
        String suitLine = String.format("│%" + espacio + "s%s%" + (espacio + resto) + "s│", "", palo, "");

        String numeroIzquierda = String.format("%-2s", numero);
        String numeroDerecha = String.format("%2s", numero);

        String[] card = {
                bordeSuperior,
                bordeLado + numeroIzquierda + "       │",
                bordeLado + "         │",
                suitLine,
                bordeLado + "         │",
                bordeLado + "       " + numeroDerecha + "│",
                bordeInferior
        };

        for (String line : card) {
            println(line);
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
            println("Mazo vacio");
        }
    }

    @Override
    public void limpiarPantalla() {
        saleTexto.setText("");
    }


}

