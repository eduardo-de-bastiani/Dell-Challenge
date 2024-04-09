package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

public class Sorteio{
    private final List<Integer> numSorteados;

    public Sorteio(){
        this.numSorteados = new ArrayList<>();
    }

    
    public List<Integer> sortear(){
        Random rdm = new Random();
        numSorteados.clear();

        for(int i = 0; i < 5; i++){
            int numero = rdm.nextInt(50) + 1;
            numSorteados.add(numero);
        }
        return numSorteados;
    }

    public List<Integer> verificaVencedores(List<Aposta> apostas){
        List<Integer> vencedores = new ArrayList<>();
        for (Aposta aposta : apostas) {
            List<Integer> numerosApostados = aposta.getNumAposta();
            int numerosCorretos = 0;
            for(int numeroSorteado : numSorteados)
            if (numerosApostados.contains(numeroSorteado)) {
                numerosCorretos++;
                
            }
            if (numerosCorretos == 5) {
                vencedores.add(aposta.getIdAposta());
            }
        }
        return vencedores;
    }

    public int quantidadeApostasVencedoras(List<Integer> vencedores){
        return vencedores.size();
    }

    public List<Integer> ordenarApostasVencedoras(List<Integer> vencedores){
        Collections.sort(vencedores);
        return vencedores;
    }

}
    
