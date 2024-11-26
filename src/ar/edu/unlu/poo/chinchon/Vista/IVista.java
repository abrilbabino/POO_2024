package ar.edu.unlu.poo.chinchon.Vista;

import ar.edu.unlu.poo.chinchon.Modelo.CartaMostrable;
import ar.edu.unlu.poo.chinchon.Modelo.JugadorMostrable;

import java.util.ArrayList;

public interface IVista {
    public void mostrarMensaje(String mensaje);
    public void mostrarCarta(CartaMostrable cartaMostrable);
    public void mostrarMazo(ArrayList<CartaMostrable> mazo);
    public void mostrarMenuPrincipal();
    public void mostrarMenuJugador();
    public void opcionesCartasTirar();
    public void opcionesCartasTirarOCortar();
    public void verificarTurno();
    public void mostrarMano(ArrayList<CartaMostrable> mano);
    public void verificarPerdedores();
    public void mostrarPuntos(ArrayList<JugadorMostrable> jugador);
    public boolean isTurno();
    public void limpiarPantalla();
}
