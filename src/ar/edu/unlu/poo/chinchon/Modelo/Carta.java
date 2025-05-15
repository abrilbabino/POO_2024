package ar.edu.unlu.poo.chinchon.Modelo;

public class Carta implements CartaMostrable{
    private Palo palo;
    private int numero;

    public Carta(Palo palo,int numero){
        this.palo=palo;
        this.numero=numero;
    }

    @Override
    public int getNumero() {
        return numero;
    }

    @Override
    public Palo getPalo() {
        return palo;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setPalo(Palo palo) {
        this.palo = palo;
    }
}

