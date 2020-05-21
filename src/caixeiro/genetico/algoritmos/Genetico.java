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

            // Adiciona os elementos em ordem da mãe
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

    private Pair<Cromossomo, Cromossomo> selecionar() {
        int total = 0;
        int totalPercentualRoleta = 0;
        Long percentualRoleta = 0L;

        for (Cromossomo cromossomo : codigoGenetico) {
            total += cromossomo.getAvaliacao();
        }
        ArrayList<Pair<Genoma, Pair<Integer, Long>>> porcoes = new ArrayList<>();
        for (int i = 0; i < codigoGenetico.size(); i++) {
            percentualRoleta = (long)(1/((float)codigoGenetico.get(i).getAvaliacao()/total)*100)/10;
            totalPercentualRoleta += percentualRoleta;
            porcoes.add(new Pair(codigoGenetico.get(i), new Pair(codigoGenetico.get(i).getAvaliacao(), percentualRoleta)));
        }

        porcoes.sort((o1, o2) -> {if (o1.getValue().getKey() > o2.getValue().getKey()) { return -1; } else if (o1.equals(o2)) { return 0; } else { return 1; } });

        int roleta = this.seed.nextInt(totalPercentualRoleta);
        Pair<Genoma, Pair<Integer, Long>> pai = null;
        Pair<Genoma, Pair<Integer, Long>> mae = null;

        int percentual = 0;
        for (int i = 0; i < porcoes.size(); i++) {
            percentual += porcoes.get(i).getValue().getValue();
            if (percentual >= roleta) {
                totalPercentualRoleta -= porcoes.get(i).getValue().getValue();
                pai = porcoes.get(i);
                break;
            }
        }

        porcoes.remove(pai);

        roleta = this.seed.nextInt(totalPercentualRoleta);
        percentual = 0;
        for (int i = 0; i < porcoes.size(); i++) {
            percentual += porcoes.get(i).getValue().getValue();
            if (percentual >= roleta) {
                mae = porcoes.get(i);
                break;
            }
        }
        if (pai == null || mae == null) {
            return null;
        }

        return new Pair(pai.getKey(), mae.getKey());

    }

    private void setPopulacaoInicial(Cromossomo entrada, int populacaoInicial) {
        Cromossomo elemento;
        for (int i = 0; i < populacaoInicial; i++) {
            elemento = new Cromossomo();
            elemento.setGenomas(entrada.gerarCadeiaAleatoria());
            this.addCromossomo(elemento);
        }
    }

    public void problemaCaixeiro(Cromossomo entrada, int populacaoInicial, int iteracoesMelhores) {
        this.setPopulacaoInicial(entrada, populacaoInicial);


        for (Cromossomo cromossomo : codigoGenetico) {
            System.out.println(cromossomo.toString() + " > " + cromossomo.getAvaliacao());
        }

        int resultado = 0;
        int iteracao = 0;
        int melhor = 0;
        Cromossomo melhorCromossomo = null;

        do {

            System.out.println("------[#I"+iteracao+"]----------");

            Pair<Cromossomo, Cromossomo> pais = this.selecionar();

            Cromossomo pai = pais.getKey();
            Cromossomo mae = pais.getValue();

            System.out.print("Pai: ");
            System.out.println(pai + " > " + pai.getAvaliacao());
            System.out.print("Mae: ");
            System.out.println(mae + " > " + mae.getAvaliacao());

            System.out.println("----------------");

            Cromossomo filhoA = this.recombinar(pai, mae);
            Cromossomo filhoB = this.recombinar(pai, mae);

            System.out.print("Filho A: ");
            System.out.println(filhoA + " > " + filhoA.getAvaliacao());

            System.out.print("Filho B: ");
            System.out.println(filhoB + " > " + filhoB.getAvaliacao());


            System.out.println("----------------");

            filhoA = this.mutar(filhoA);

            System.out.print("Mutacao A: ");
            System.out.println(filhoA + " > " + filhoA.getAvaliacao());

            filhoB = this.mutar(filhoB);

            System.out.print("Mutacao B: ");
            System.out.println(filhoB + " > " + filhoB.getAvaliacao());

            this.codigoGenetico.clear();
            this.addCromossomo(filhoA);
            this.addCromossomo(filhoB);
            this.addCromossomo(pai);
            this.addCromossomo(mae);

            resultado = filhoB.getAvaliacao();
            if (filhoA.getAvaliacao() < filhoB.getAvaliacao()) {
                resultado = filhoA.getAvaliacao();
            }

            if (resultado < melhor || melhor == 0) {
                melhor = resultado;

                melhorCromossomo = filhoB;
                if (filhoA.getAvaliacao() < filhoB.getAvaliacao()) {
                    melhorCromossomo = filhoA;
                }
            }else if (resultado == melhor) {
                iteracao++;
            }

            System.out.println("");
            System.out.println("");

        } while (iteracao <= iteracoesMelhores);


        System.out.println("----------------");
        System.out.println("Melhor resultado depois de " + iteracao + " iteracoes com o mesmo menor valor:");
        System.out.println(melhorCromossomo.toString());
        System.out.println("Resultado: " + melhor);

    }



}
