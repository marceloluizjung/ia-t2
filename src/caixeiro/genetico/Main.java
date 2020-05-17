package caixeiro.genetico;

import caixeiro.genetico.algoritmos.Genetico;
import caixeiro.genetico.artefatos.Cromossomo;
import caixeiro.genetico.artefatos.Genoma;

public class Main {
    public static void main(String[] args) {
        Cromossomo entrada = new Cromossomo();
        Genetico algoritmo = new Genetico();

        Genoma blumenau = new Genoma(1, "Blumenau");
        Genoma gaxpar = new Genoma(2, "Gaxpar");
        Genoma indaial = new Genoma(3, "Indaial");
        Genoma joinville = new Genoma(3, "Joinville");
        Genoma pomerode = new Genoma(4, "Pomerode");

        blumenau.addAdjacencia(gaxpar, 3);
        blumenau.addAdjacencia(indaial, 2);
        blumenau.addAdjacencia(pomerode, 7);

        gaxpar.addAdjacencia(joinville, 6);
        gaxpar.addAdjacencia(indaial, 4);
        gaxpar.addAdjacencia(pomerode, 3);

        indaial.addAdjacencia(joinville, 5);
        indaial.addAdjacencia(pomerode, 8);

        joinville.addAdjacencia(pomerode, 5);

        entrada.addGenoma(blumenau);
        entrada.addGenoma(indaial);
        entrada.addGenoma(joinville);
        entrada.addGenoma(gaxpar);
        entrada.addGenoma(pomerode);

        algoritmo.problemaCaixeiro(entrada, 3);

    }
}
