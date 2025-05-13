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

    public void agregarJugador(String nombre){
        try {
            Jugador j = new Jugador(nombre);
            modelo.agregarJugador(j);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<JugadorMostrable> obtenerJugadores(){
        ArrayList<JugadorMostrable> jugadores=new ArrayList<>();
        try{
            jugadores=new ArrayList<JugadorMostrable>(modelo.getJugadores());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return jugadores;
    }

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

    public boolean iniciarJuego(){
        boolean inicia=false;
        try {
            inicia=modelo.iniciar();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return inicia;
    }

    public  ArrayList<CartaMostrable> obtenerMazo(){
        ArrayList<CartaMostrable> mazo=new ArrayList<>();
        try {
            mazo=new ArrayList<CartaMostrable>(modelo.getMazo().getPilaDeCartas());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return mazo;
    }


    public ArrayList<CartaMostrable> obtenerCartasJugador(String nombre){
        ArrayList<CartaMostrable> mano=new ArrayList<>();
        try {
            mano=new ArrayList<CartaMostrable>(modelo.obtenerJugador(nombre).getMano().getCartas());
        }catch (RemoteException e){
            e.printStackTrace();
        }
        return mano;
    }

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

    public void cambiarTurno(){
        try {
            modelo.cambiarTurno();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

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

    public void moverCartas(int pos1,int pos2){
        try {
            modelo.moverCartas(pos1, pos2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void reiniciaRonda(){
        try {
            modelo.otraRonda();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
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

    public Jugador obtenerJugadorActual(){
        Jugador jugadorActual=null;
        try{
            jugadorActual=modelo.getJugadorActual();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return jugadorActual;
    }

    public int obtenerCantidadDeRondas(){
        int cantRondas=-1;
        try{
            cantRondas=modelo.getCantidadDeRondas();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return cantRondas;
    }

    public String getGanador(){
        String nombreGanador=null;
        try {
            nombreGanador=modelo.getGanador().getNombre();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return nombreGanador;
    }

    private void actualizarVistaTurno() {
        if(jugadorActual()!=null){
            vista.verificarTurno();
        }
    }

    @Override
    public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) throws RemoteException {
        this.modelo = (IModelo) modeloRemoto;
    }

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
                    if (jugadorActual() != null && obtenerJugadorActual() != null) {
                        vista.mostrarMano();
                    }
                }
                break;

            case Eventos.FIN_DEL_JUEGO:
                if(getGanador()!=null) {
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
