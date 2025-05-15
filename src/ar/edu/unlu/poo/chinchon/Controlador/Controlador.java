package ar.edu.unlu.poo.chinchon.Controlador;

import  ar.edu.unlu.poo.chinchon.Modelo.*;
import ar.edu.unlu.poo.chinchon.Vista.*;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Controlador implements IControladorRemoto {
    private IModelo modelo;
    private IVista vista;

    public void setVista(IVista vista){
        this.vista=vista;
    }

    //VERIFICA SI YA EXISTE UN JUGADOR CON ESE NOMBRE PARA VALIDAR QUE NO HAYA DOS CON EL MISMO NOMBRE
    public boolean existeJugador(String nombre){
        boolean existe=false;
        try {
            ArrayList<Jugador> jugadores =modelo.getJugadores();
            for(Jugador j: jugadores){
                if(j.getNombre().equals(nombre)){
                    existe=true;
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return existe;
    }

    //CREA UN JUGADOR CON EL NOMBRE RECIBIDO Y LO AGREGA A LA LISTA DE JUGADORES
    public void agregarJugador(String nombre){
        try {
            Jugador j = new Jugador(nombre);
            modelo.agregarJugador(j);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //OBTIENE DE PARTE DEL MODELO LA LISTA DE JUGADORES Y RETORNA UNA LISTA DE JUGADORES MOSTRABLES
    public ArrayList<JugadorMostrable> obtenerJugadores(){
        ArrayList<JugadorMostrable> jugadores=new ArrayList<>();
        try{
            jugadores=new ArrayList<JugadorMostrable>(modelo.getJugadores());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return jugadores;
    }

    //VERIFICA QUE LA CANTIDAD DE JUGADORES SEA COMO MINIMO 2
    public int faltanJugadores(){
        int valor=-1;
        int cantidadJ = obtenerJugadores().size();
        if (cantidadJ == 0) {
            valor=2;
        } else if (cantidadJ == 1) {
            valor=1;
        }
        return valor;
    }

    //INICIA EL JUEGO
    public boolean iniciarJuego(){
        boolean inicia=false;
        try {
            inicia=modelo.iniciar();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return inicia;
    }

    //OBTIENE DE PARTE DEL MODELO EL MAZO Y RETORNA UNA PILA DE CARTAS MOSTRABLES
    public  ArrayList<CartaMostrable> obtenerMazo(){
        ArrayList<CartaMostrable> mazo=new ArrayList<>();
        try {
            mazo=new ArrayList<CartaMostrable>(modelo.getMazo().getPilaDeCartas());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return mazo;
    }

    //OBTIENE DE PARTE DEL MODELO LA MANO DEL JUGADOR CUYO NOMBRE COINCIDE CON EL RECIBIDO POR PARAMETRO
    //DEVUELVE UN CONJUNTO DE CARTAS MOSTRABLES
    public ArrayList<CartaMostrable> obtenerCartasJugador(String nombre){
        ArrayList<CartaMostrable> mano=new ArrayList<>();
        try {
            mano=new ArrayList<CartaMostrable>(modelo.obtenerJugador(nombre).getMano().getCartas());
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return mano;
    }

    //OBTIENE DE PARTE DEL MODELO LA CARTA DEL TOPE DE LA PILA DE DESCARTE Y DEVUELVE UNA CARTA MOSTRABLE
    public CartaMostrable obtenerCartaTope(){
        CartaMostrable cartaTope=null;
        try {
            if(modelo.getPilaDescarte().getTope()!=null) {
                cartaTope=modelo.getPilaDescarte().getTope();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return cartaTope;
    }

    //OBTIENE DE PARTE DEL MODELO LA ULTIMA CARTA LEVANTADA POR EL JUGADOR CUYO NOMBRE COINCIDE CON EL
    //RECIBIDO POR PARAMETRO, DEVUELVE UNA CARTA MOSTRABLE
    public CartaMostrable obtenerCartaExtra(String nombre){
        CartaMostrable cartaExtra=null;
        try {
            if(modelo.getJugadorActual().getMano().getCartaExtraTurno()!=null) {
                cartaExtra=modelo.obtenerJugador(nombre).getMano().getCartaExtraTurno();
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return cartaExtra;
    }

    //OBTIENE DE PARTE DEL MODELO EL JUGADOR ACTUAL (QUE TIENE EL TURNO) Y DEVUELVE SU NOMBRE
    public String jugadorActual(){
        String nombre=null;
        try {
            Jugador jugadorAct = modelo.getJugadorActual();
            nombre=jugadorAct.getNombre();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return nombre;
    }

    //INDICA QUE SE DEBE CAMBIAR EL TURNO YA QUE EL JUGADOR QUE TENIA EL TURNO YA JUGO
    public void cambiarTurno(){
        try {
            modelo.cambiarTurno();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //INDICA QUE EL JUGADOR ACTUAL QUIERE AGARRAR UNA CARTA DEPENDIENDO DE LA OPCION RECIBIDA DEL MAZO
    //O DEL TOPE DE LA PILA DE DESCARTE
    public void agarrarCarta(int opcion){
        try {
            if(opcion==1){
                modelo.agarrarCartaDelMazo();
            }
            else{
                modelo.agarrarCartaDeLaPiLaDescarte();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //INDICA QUE EL JUGADOR ACTUAL QUIERE TIRAR UNA CARTA
    public void tirarCarta(int posicionCarta){
        try {
            if(posicionCarta<8){
                modelo.tirarCartaMano(posicionCarta-1);
            }
            else{
                modelo.tirarCartaExtra();
            }
            cambiarTurno();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //INDICA QUE EL JUGADOR ACTUAL (QUE TIENE EL TURNO) DESEA MOVER SUS CARTAS PARA ACOMODARLAS EN LA MANO
    public void moverCartas(int pos1,int pos2){
        try {
            modelo.moverCartas(pos1, pos2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //INDICA EL COMIENZO DE UNA NUEVA RONDA
    public void reiniciaRonda(){
        try {
            modelo.otraRonda();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //OBTIENE DE PARTE DEL MODELO LA LISTA DE PERDEDORES Y DEVUELVE UNA LISTA CON LOS NOMBRES
    // DE LOS PERDEDORES
    public ArrayList<String> obtenerPerdedores(){
        ArrayList<String> perdedores=new ArrayList<>();
        for(JugadorMostrable j: obtenerJugadores()){
            if(j.getEstado().equals(EstadoJugador.PERDEDOR)){
                System.out.println("Agregando a perdedores a "+ j.getNombre());
                perdedores.add(j.getNombre());
            }
        }
        return perdedores;
    }

    //INDICA QUE EL JUGADOR ACTUAL (QUE TIENE EL TURNO) QUIERE CORTAR, EN CASO DE QUE NO PUEDA LE PIDE
    // A LA VISTA QUE LE INDIQUE AL JUGADOR QUE NO PUEDE CORTAR
    public void cortar(int opcion){
        try {
            if (!modelo.cortar(opcion)) {
                vista.mostrarMensaje("TODAVIA NO PODES CORTAR");
                vista.opcionesCartasTirarOCortar();
            }

        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    //OBTIENE DEL MODELO LA CANTIDAD DE RONDAS JUGADAS PARA SABER QUE NUMERO DE RONDA SE ESTA JUGANDO
    public int obtenerCantidadDeRondas(){
        int cantRondas=-1;
        try{
            cantRondas=modelo.getCantidadDeRondas();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return cantRondas;
    }

    //OBTIENE DEL MODELO EL JUGADOR QUE GANO LA PARTIDA Y DEVUELVE SU NOMBRE
    public String obtenerGanador(){
        String nombreGanador=null;
        try {
            nombreGanador=modelo.getGanador().getNombre();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return nombreGanador;
    }

    //OBTIENE DEL MODELO EL RANKING DE LOS JUGADORES, DEVUELVE UN RANKING MOSTRABLE
    public RankingMostrable obtenerRanking(){
        RankingMostrable ranking=null;
        try{
            ranking=modelo.getRanking();
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return ranking;
    }

    //INDICA A LA VISTA QUE VERIFIQUE EL TURNO PARA ACTUALIZAR LA PANTALLA DE LOS JUGADORES
    private void actualizarVistaTurno() {
        if(jugadorActual()!=null){
            vista.verificarTurno();
        }
    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
        this.modelo = (IModelo) modeloRemoto;
    }

    //MANEJA LOS EVENTOS
    @Override
    public void actualizar(IObservableRemoto modelo, Object evento) throws RemoteException {
        switch(evento) {
            case Eventos.FALTAN_JUGADORES:
                if (faltanJugadores() != -1) {
                    vista.mostrarMensaje("FALTAN " + faltanJugadores() + " JUGADORES PARA INICIAR LA PARTIDA");
                }
                break;
            case Eventos.JUEGO_INICIADO:
                vista.limpiarPantalla();
                vista.mostrarPuntos(obtenerJugadores());
                vista.mostrarMazo(obtenerMazo());
                vista.mostrarCartaTope(obtenerCartaTope());
                vista.mostrarMano();
                actualizarVistaTurno();
                break;
            case Eventos.CAMBIA_TURNO:
                actualizarVistaTurno();
                break;
            case Eventos.CAMBIA_CARTA_TOPE:
                vista.mostrarCartaTope(obtenerCartaTope());
                vista.mostrarMano();
                break;
            case Eventos.CAMBIA_ORDEN_CARTAS:
                if(vista.isTurno()) {
                    vista.mostrarCartaTope(obtenerCartaTope());
                    vista.mostrarMano();
                }
                break;
            case Eventos.CAMBIA_MAZO:
                if (!obtenerMazo().isEmpty()) {
                    vista.mostrarMazo(obtenerMazo());
                }
                break;

            case Eventos.CAMBIA_MANO:
                if(vista.isTurno()) {
                    if (jugadorActual() != null) {
                        vista.mostrarMano();
                    }
                }
                break;

            case Eventos.FIN_DEL_JUEGO:
                if(obtenerGanador()!=null) {
                    vista.mostrarPuntos(obtenerJugadores());
                    vista.verificarPerdedores();
                    vista.mostrarMensaje("JUEGO TERMINADO");
                }
                break;

            case Eventos.NUEVA_RONDA:
                vista.verificarPerdedores();
                reiniciaRonda();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + evento);
        }
    }

}
