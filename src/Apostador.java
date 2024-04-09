package src;

import java.util.ArrayList;
import java.util.List;

public class Apostador {

    private String nome;
    private long cpf;
    private List<Aposta> apostas;

    public Apostador (String nome, long cpf){
        this.apostas = new ArrayList<>();

        if(nome != null && !nome.isEmpty()){
            this.nome = nome;
        }
        else{
            throw new IllegalArgumentException("nome inválido");
        }
        if(String.valueOf(cpf).length() == 11){
            this.cpf = cpf;
        }
        else{
            throw new IllegalArgumentException("CPF inválido");
        }
        
    }


    public void setNome(String nome){
        if(nome != null && !nome.isEmpty()){
            this.nome = nome;
        }
    }
    
    public void setCPF(long cpf){
        if(String.valueOf(cpf).length() == 11){
            this.cpf = cpf;
        }
    }

    public String getNome(){
        return nome;
    }

    public long getCPF(){
        return cpf;
    }

    public void adicionarAposta(Aposta aposta){
        apostas.add(aposta);
    }

    public List<Aposta> getApostas(){
        return apostas;
    }


    @Override
    public String toString() {
        return "Apostador [nome=" + nome + ", cpf=" + cpf + "]";
    }
}
