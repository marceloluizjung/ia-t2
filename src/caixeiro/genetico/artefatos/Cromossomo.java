package caixeiro.genetico.artefatos;

import java.nio.channels.IllegalSelectorException;
import java.util.ArrayList;
import java.util.Arrays;
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
            if (genoma == null) {
                retorno.append("xxxx - ");
            } else {
                retorno.append(genoma.getNomeCidade());
                retorno.append(" - ");
            }
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
    // Impossivel realizar a tarefa em grafos não cicliclos
    public ArrayList<Genoma> gerarCadeiaAleatoria() {


        ArrayList<Genoma> tempGenomas = new ArrayList<>();
        ArrayList<Genoma> auxGenomas = this.getGenomas();
        Cromossomo tempCromossomo = new Cromossomo();

        int index = 0;
        Genoma elemento = null;

        do {
            tempGenomas.clear();
            while (tempGenomas.size() != auxGenomas.size()) {
                index = seed.nextInt(auxGenomas.size());
                elemento = auxGenomas.get(index);
                if (!tempGenomas.contains(elemento)) {
                    tempGenomas.add(elemento);
                }
            }
            tempCromossomo.setGenomas(tempGenomas);
        } while (tempCromossomo.getAvaliacao() == -1);

        return tempGenomas;
    }

    public void addLigacao(Integer origem, Integer destino, Integer peso) {
        this.genomas.get(origem).addAdjacencia(this.genomas.get(destino), peso);
    }
}
