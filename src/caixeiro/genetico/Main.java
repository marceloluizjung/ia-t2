package caixeiro.genetico;

import caixeiro.genetico.algoritmos.Genetico;
import caixeiro.genetico.artefatos.ItemMochila;
import caixeiro.genetico.artefatos.Mochila;

public class Main {
    public static void main(String[] args) {

        Mochila mochila = new Mochila(5);

        ItemMochila notebook = new ItemMochila();
        notebook.setNome("Notebook");
        notebook.setPeso(3000);
        notebook.setTamanho(4);

        ItemMochila tablet = new ItemMochila();
        tablet.setNome("Tablet");
        tablet.setPeso(1500);
        tablet.setTamanho(3);

        ItemMochila celular = new ItemMochila();
        celular.setNome("Celular");
        celular.setPeso(1000);
        celular.setTamanho(2);

        ItemMochila colar = new ItemMochila();
        colar.setNome("Colar");
        colar.setPeso(700);
        colar.setTamanho(2);

        ItemMochila anel = new ItemMochila();
        anel.setNome("Anel");
        anel.setPeso(400);
        anel.setTamanho(1);

        Genetico algoritmoGenetico = new Genetico();
        algoritmoGenetico.addVariavel(notebook);
        algoritmoGenetico.addVariavel(tablet);
        algoritmoGenetico.addVariavel(celular);
        algoritmoGenetico.addVariavel(colar);
        algoritmoGenetico.addVariavel(anel);

        algoritmoGenetico.problemaMochila(mochila);

    }
}
