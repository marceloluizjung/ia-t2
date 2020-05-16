package caixeiro.genetico.algoritmos;

import caixeiro.genetico.artefatos.ItemMochila;
import caixeiro.genetico.artefatos.Mochila;

import java.util.ArrayList;

public class Genetico {

    private ArrayList<ItemMochila> variaveis;

    public Genetico() {
        this.variaveis = new ArrayList<ItemMochila>();
    }

    public void addVariavel(ItemMochila variavel) {
        this.variaveis.add(variavel);
    }

    public void problemaMochila(Mochila mochila) {

    }
}
