package caixeiro.genetico.algoritmos;

import caixeiro.genetico.artefatos.Cromossomo;

import java.util.ArrayList;

public class Genetico {

    private ArrayList<Cromossomo> codigoGenetico;

    public Genetico() {
        this.codigoGenetico = new ArrayList<Cromossomo>();
    }

    private void addCromossomo(Cromossomo cromossomo) {
        this.codigoGenetico.add(cromossomo);
    }

    private Cromossomo recombinar(Cromossomo pai, Cromossomo mae) {
        Cromossomo filho = new Cromossomo();
        return filho;
    }

    private Cromossomo mutar(Cromossomo cromossomo) {
        return  cromossomo;
    }

    public void problemaCaixeiro(Cromossomo entrada, int populacaoInicial) {
        Cromossomo elemento;
        for (int i = 0; i < populacaoInicial; i++) {
            elemento = new Cromossomo();
            elemento.setGenomas(entrada.gerarCadeiaAleatoria());
            this.addCromossomo(elemento);
        }
        for (Cromossomo cromossomo : codigoGenetico) {
            System.out.println(cromossomo.toString() + " > " + cromossomo.getAvaliacao());
        }
    }
}
