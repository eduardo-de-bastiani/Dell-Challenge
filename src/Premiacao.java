package src;

import java.util.Random;

public class Premiacao {
    private int premio;

    public Premiacao(){
        this.premio = gerarPremio();
    }

    public int getPremio(){
        return premio;
    }


    public int gerarPremio(){
        Random rdm = new Random();
        return rdm.nextInt(10000) + 1000;
        
    }

    public double dividirPremio(double premioTotal, int numVencedores){
        double premioDividido = premioTotal / numVencedores;
        return premioDividido;
    }
}

