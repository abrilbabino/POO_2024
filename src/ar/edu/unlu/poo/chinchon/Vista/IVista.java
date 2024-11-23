package ar.edu.unlu.poo.chinchon.Vista;

import ar.edu.unlu.poo.chinchon.Modelo.CartaMostrable;

import java.util.ArrayList;

public interface IVista {
    public void mostrarMensaje(String mensaje);
    public void mostrarCarta(CartaMostrable cartaMostrable);
    public void mostrarMazo(ArrayList<CartaMostrable> mazo);
    public void mostrarMenuPrincipal();
    public void mostrarMenuJugador();
    public void opcionesCartasTirar();
    public void limpiarPantalla();
    public void verificarTurno();
    public void esTurno();
    public void esperarTurno();
    public void mostrarMano(ArrayList<CartaMostrable> mano);
    public void verificarPerdedores();
    public boolean isTurno();
}
