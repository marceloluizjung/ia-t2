package caixeiro.genetico.algoritmos;

import caixeiro.genetico.artefatos.Cromossomo;
import caixeiro.genetico.artefatos.Genoma;
import javafx.util.Pair;

import java.nio.channels.IllegalSelectorException;
import java.util.*;

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
        ArrayList<Genoma> genomasIgnorados = new ArrayList<Genoma>();
        List<Integer> posicoesIgnoradas = new LinkedList<Integer>();

        do {

            genomasFilho.clear();
            genomasIgnorados.clear();
            posicoesIgnoradas.clear();

            // Gera a relação binaria
            for (int i = 0; i < pai.getGenomas().size(); i++) {
                if (seed.nextBoolean()) {
                    genomasFilho.add(i, pai.getGenomas().get(i));
                } else {
                    genomasFilho.add(i, null);
                    posicoesIgnoradas.add(i);
                    genomasIgnorados.add(pai.getGenomas().get(i));
                }
            }

            for (int i = 0; i < mae.getGenomas().size(); i++) {
                for (Genoma genomaIgnorado : genomasIgnorados) {
                    if (mae.getGenomas().get(i).equals(genomaIgnorado)) {
                        genomasFilho.set(posicoesIgnoradas.get(0), genomaIgnorado);
                        posicoesIgnoradas.remove(0);
                    }
                }
            }

            // tratamento para garantir que novo filho não seja invalido
        } while (filho.getAvaliacao() == -1);

       return filho;
    }

    private Cromossomo mutar(Cromossomo cromossomo) {

        ArrayList<Genoma> tempGenoma;
        int posicaoGenomaA;
        int posicaoGenomaB;
        Genoma genomaA;
        Genoma genomaB;

        do {
            tempGenoma = cromossomo.getGenomas();
            posicaoGenomaA = seed.nextInt(tempGenoma.size());
            posicaoGenomaB = seed.nextInt(tempGenoma.size());

            // tratamento para o genoma nao permutar com ele mesmo
            while (posicaoGenomaA == posicaoGenomaB) {
                posicaoGenomaB = seed.nextInt(tempGenoma.size());
            }
            genomaA = tempGenoma.get(posicaoGenomaA);
            genomaB = tempGenoma.get(posicaoGenomaB);

            cromossomo.getGenomas().set(posicaoGenomaA, genomaB);
            cromossomo.getGenomas().set(posicaoGenomaB, genomaA);

            // tratamento para garantir que nova mutação não será invalida
        } while(cromossomo.getAvaliacao() == -1);

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
        filho = this.mutar(filho);
        System.out.println(filho.toString() + " > " + filho.getAvaliacao());

    }



}
