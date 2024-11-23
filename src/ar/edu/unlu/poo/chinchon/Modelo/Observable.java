package ar.edu.unlu.poo.chinchon.Modelo;

public interface Observable {
    public void agregarObservador(Observador observador);

    public void eliminarObservador(Observador observador);

    public void notificar(Eventos evento);

}
