package caixeiro.genetico.artefatos;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Genoma {

    private int codigoCidade;
    private String nomeCidade;

                         //cidade, distancia
    private ArrayList<Pair<Genoma, Integer>> cidadesAdjacentes;

    public String getNomeCidade() {
        return this.nomeCidade;
    }

    public int getCodigoCidade() {
        return codigoCidade;
    }

    public int getPesoAdjacencia(Genoma adjacencia) {
        for (Pair<Genoma, Integer> cidade : this.cidadesAdjacentes) {
            if (cidade.getKey().equals(adjacencia)) {
                return cidade.getValue();
            }
        }
        return 0;
    }

    public Genoma(int codigoCidade, String nomeCidade) {
        this.codigoCidade = codigoCidade;
        this.nomeCidade = nomeCidade;
        this.cidadesAdjacentes = new ArrayList<>();
    }

    public boolean temAdjacencia(Genoma genoma) {
        for (Pair<Genoma, Integer> cidade : this.cidadesAdjacentes) {
            if (cidade.getKey().equals(genoma)) {
                return true;
            }
        }
        return false;
    }

    public void addAdjacencia(Genoma genoma, Integer peso) {
        genoma.addAdjacencia(this, peso, false); // do destino ate origem
        this.cidadesAdjacentes.add(new Pair<>(genoma,peso)); // da origem ate o destino
    }

    // usado para evitar recursão
    private void addAdjacencia(Genoma genoma, Integer peso, boolean depth) {
        this.cidadesAdjacentes.add(new Pair<>(genoma,peso)); // da origem ate o destino
    }
}
