package src;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GerenciaAposta {
    private List<Aposta> apostas = new ArrayList<>();

    public void registrarAposta(Aposta aposta){
        apostas.add(aposta);
    }

    public void apostar(Apostador apostador, int idAposta, List<Aposta> apostas, List<Integer> numerosApostados) {
        validarAposta(numerosApostados); 

        if (apostas == null) {
            apostas = new ArrayList<>();
        }

        apostas.add(new Aposta(apostador, idAposta, numerosApostados));
    }


    public void validarAposta(List<Integer> numerosApostados) {
        if (numerosApostados.size() != 5) {
            throw new IllegalArgumentException("Você deve fornecer exatamente 5 números para a aposta.");
        }

        for (int num : numerosApostados) {
            if (num < 1 || num > 50) {
                throw new IllegalArgumentException("Número inválido. Os números devem estar entre 1 e 50.");
            }
        }

        // Verifica se há números repetidos
        Set<Integer> numerosSet = new HashSet<>(numerosApostados);
        if (numerosSet.size() != 5) {
            throw new IllegalArgumentException("Não são permitidos números repetidos na aposta.");
        }
    }


    public void surpresinha(Apostador apostador, int idAPosta){
        List<Integer> numAposta = new ArrayList<>();
        Random rdm = new Random();
        while (numAposta.size() < 5) {
            int random = rdm.nextInt(50) + 1;

            if(!numAposta.contains(random)){
                numAposta.add(random);
            }
        }

        Aposta surpresinha = new Aposta(apostador, getNextId(), numAposta);
        registrarAposta(surpresinha);
    }

    public String getAposta(int idAPosta){
        for (Aposta aposta : apostas) {
            if (aposta.getIdAposta() == idAPosta) {
                return aposta.toString();
            }
        }
        return null;
    }


    public String getApostas(){
        StringBuilder apostasString = new StringBuilder();
        for (Aposta aposta : apostas) {
            apostasString.append(aposta.toString()).append("\n");
        }
        return apostasString.toString();
    }

    public List<Aposta> getApostasDoApostador(Apostador apostador){
        List<Aposta> apostasDoApostador = new ArrayList<>();
        for (Aposta aposta : apostas) {
            if (aposta.getApostador().equals(apostador)) {
                apostasDoApostador.add(aposta);
            }
        }
        return apostasDoApostador;
    }


    public int getNextId(){
        if(apostas.isEmpty()){
            return 1000;
        }
        else{
            return apostas.get(apostas.size() - 1).getIdAposta() + 1;
        }
    }


    public List<Integer> quantNumApostados(){
        Map<Integer, Integer> mapa = new HashMap<>();

        for (Aposta aposta : apostas) {
            List<Integer> numerosApostados = aposta.getNumAposta();

            for(int numero : numerosApostados){
                mapa.put(numero, mapa.getOrDefault(numero, 0) + 1);
            }
        }

        List<Map.Entry<Integer, Integer>> listaOrdenada = new ArrayList<>(mapa.entrySet());
        listaOrdenada.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        List<Integer> numerosOrdenados = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : listaOrdenada){
            numerosOrdenados.add(entry.getKey());
        }

        return numerosOrdenados;
    } 
}
