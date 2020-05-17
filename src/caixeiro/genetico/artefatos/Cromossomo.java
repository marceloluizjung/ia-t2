package caixeiro.genetico.artefatos;

import java.nio.channels.IllegalSelectorException;
import java.util.ArrayList;
import java.util.Random;

public class Cromossomo {
    private ArrayList<Genoma> genomas;
    private Random seed;

    public Cromossomo() {
        this.genomas = new ArrayList<Genoma>();
        this.seed = new Random();
    }

    public void addGenoma(Genoma genoma) {
        this.genomas.add(genoma);
    }

    public void setGenomas(ArrayList<Genoma> genomas) {
        this.genomas = genomas;
    }
    public ArrayList<Genoma> getGenomas() {
        return this.genomas;
    }

    @Override
    public String toString() {
        StringBuilder retorno = new StringBuilder();
        for (Genoma genoma : this.genomas) {
            retorno.append(genoma.getNomeCidade());
            retorno.append(" - ");
        }
        // Hotfix para retirar o ultimo traço
        return retorno.toString().substring(0, retorno.toString().length()-2);
    }

    public int getAvaliacao() {
        int avaliacao = 0;
        Genoma anterior = this.genomas.get(0);
        Genoma atual;
        for (int i = 1; i < this.genomas.size(); i++) {
            atual = this.genomas.get(i);
            if (anterior.temAdjacencia(atual)) {
                avaliacao += anterior.getPesoAdjacencia(atual);
            } else {
                return -1; // se não encontrar a adjacencia
            }
            anterior = atual;
        }
        return  avaliacao;
    }

    // Embaralha de forma aleatoria os genomas mantendo a integridade das adjacencias
    public ArrayList<Genoma> gerarCadeiaAleatoria() {
        ArrayList<Genoma> tempGenomas = new ArrayList<>();
        int quantidade = this.genomas.size();

        int item = seed.nextInt(quantidade);
        Genoma elemento = this.genomas.get(item);
        Genoma elementoAnterior = elemento;
        tempGenomas.add(elemento);

        while (tempGenomas.size() < quantidade) {
            item = seed.nextInt(quantidade);
            elemento = this.genomas.get(item);

            // Verifica se tem adjacencia e se ainda não está na lista
            if (elementoAnterior.temAdjacencia(elemento) && tempGenomas.indexOf(elemento) == -1) {
                tempGenomas.add(elemento);
                elementoAnterior = elemento;
            }
        }

        return tempGenomas;
    }
}
