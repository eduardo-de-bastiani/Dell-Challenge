package src;

import java.util.List;

public class Aposta {
    private Apostador apostador;
    private int idAposta;
    private List<Integer> numAposta; 

    

    public Aposta(Apostador apostador, int idAposta, List<Integer> numAposta){
        if (apostador != null && numAposta != null && !numAposta.isEmpty()) {
            this.apostador = apostador;
            this.idAposta = idAposta;
            this.numAposta = numAposta;
        }
        else{
            throw new IllegalArgumentException("Apostador ou número da aposta inválido");
        }
    }

    public List<Integer> getNumAposta(){
        return numAposta;
    }

    public Apostador getApostador(){
        return apostador;
    }

    public int getIdAposta(){
        return idAposta;
    }

    @Override
    public String toString() {
        return "Aposta [apostador=" + apostador + ", idAposta=" + idAposta + ", numAposta=" + numAposta + "]";
    }

    
}
