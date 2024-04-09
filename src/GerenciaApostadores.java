package src;

import java.util.ArrayList;
import java.util.List;

public class GerenciaApostadores {
    
    private List<Apostador> apostadores = new ArrayList<>();


    public void cadastraApostador(Apostador apostador){
        apostadores.add(apostador);
    }

    public List<Apostador> getApostadores(){
        return apostadores;
    }

    public boolean apostadorExiste(List<Apostador> apostadores, long cpf){
        for (Apostador apostador : apostadores) {
            if (apostador.getCPF() == cpf) {
                return true;
            }
        }
        return false;
    }

    public Apostador getApostador(long cpf){
        for (Apostador apostador: apostadores) {
            if (apostador.getCPF() == cpf) {
                return apostador;
            }
        }
        return null;
    }
}
