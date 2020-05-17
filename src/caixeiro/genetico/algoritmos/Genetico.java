package caixeiro.genetico.algoritmos;

import caixeiro.genetico.artefatos.Cromossomo;
import caixeiro.genetico.artefatos.Genoma;

import java.nio.channels.IllegalSelectorException;
import java.util.ArrayList;
import java.util.Random;

public class Genetico {

    private ArrayList<Cromossomo> codigoGenetico;
    private Random seed;

    public Genetico() {
        this.codigoGenetico = new ArrayList<Cromossomo>();
        this.seed = new Random();
    }

    private void addCromossomo(Cromossomo cromossomo) {
        this.codigoGenetico.add(cromossomo);
    }

    private Cromossomo recombinar(Cromossomo pai, Cromossomo mae) {
        Cromossomo filho = new Cromossomo();
        ArrayList<Genoma> genomasFilho = filho.getGenomas();

        // Gera a relação binaria
        ArrayList<Boolean> relacao = new ArrayList<Boolean>();
        for (int i = 0; i < (pai.getGenomas().size()-1); i++) {
            if(seed.nextBoolean()) {
                genomasFilho.add(i, pai.getGenomas().get(i));
            }
        }
        //TODO: CONTINUAR


        return filho;
    }

    private Cromossomo mutar(Cromossomo cromossomo) {
        ArrayList<Genoma> tempGenoma = cromossomo.getGenomas();
        int posicaoGenomaA = seed.nextInt(tempGenoma.size());
        int posicaoGenomaB = seed.nextInt(tempGenoma.size());
        while (posicaoGenomaA == posicaoGenomaB) {
            posicaoGenomaB = seed.nextInt(tempGenoma.size());
        }
        Genoma genomaA = tempGenoma.get(posicaoGenomaA);
        Genoma genomaB = tempGenoma.get(posicaoGenomaB);

        cromossomo.getGenomas().set(posicaoGenomaA, genomaB);
        cromossomo.getGenomas().set(posicaoGenomaB, genomaA);

        // tratamento para garantir que nova mutação não será invalida
        while(cromossomo.getAvaliacao() == -1) {
            cromossomo.setGenomas(tempGenoma);
            posicaoGenomaA = seed.nextInt(tempGenoma.size());
            posicaoGenomaB = seed.nextInt(tempGenoma.size());
            while (posicaoGenomaA == posicaoGenomaB) {
                posicaoGenomaB = seed.nextInt(tempGenoma.size());
            }
            genomaA = tempGenoma.get(posicaoGenomaA);
            genomaB = tempGenoma.get(posicaoGenomaB);

            cromossomo.getGenomas().set(posicaoGenomaA, genomaB);
            cromossomo.getGenomas().set(posicaoGenomaB, genomaA);
        }

        return  cromossomo;
    }

    private void setPopulacaoInicial(Cromossomo entrada, int populacaoInicial) {
        Cromossomo elemento;
        for (int i = 0; i < populacaoInicial; i++) {
            elemento = new Cromossomo();
            elemento.setGenomas(entrada.gerarCadeiaAleatoria());
            this.addCromossomo(elemento);
        }
    }

    public void problemaCaixeiro(Cromossomo entrada, int populacaoInicial) {
        this.setPopulacaoInicial(entrada, populacaoInicial);
        for (Cromossomo cromossomo : codigoGenetico) {
            System.out.println(cromossomo.toString() + " > " + cromossomo.getAvaliacao());
        }

        System.out.println("----------------");

        Cromossomo teste = codigoGenetico.get(0);
        System.out.println(teste.toString() + " > " + teste.getAvaliacao());
        teste = this.mutar(teste);
        System.out.println(teste.toString() + " > " + teste.getAvaliacao());

        System.out.println("----------------");


        Cromossomo cromossomoA = codigoGenetico.get(0);
        Cromossomo cromossomoB = codigoGenetico.get(1);

        Cromossomo filho = this.recombinar(cromossomoA, cromossomoB);

        System.out.println(filho.toString() + " > " + filho.getAvaliacao());

    }



}
