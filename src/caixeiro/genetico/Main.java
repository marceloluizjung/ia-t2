package caixeiro.genetico;

import caixeiro.genetico.algoritmos.Genetico;
import caixeiro.genetico.artefatos.Cromossomo;
import caixeiro.genetico.artefatos.Genoma;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private static Cromossomo entrada;

    public static void actionPerformed() throws IOException {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.FILES_ONLY);
        f.showSaveDialog(null);

        File file = f.getSelectedFile();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String linha = bufferedReader.readLine();
        linha = linha.replaceAll("\"", "");

        Genoma cidade;
        String[] separador;
        String[] ligacoes;
        Integer origem, destino, peso;

        int nLinha = 0;
        int nCidade = 0;
        while (linha != null) {
            separador = linha.split(";");

            if (nLinha == 0) {
                for (String nomeCidade : separador) {
                    cidade = new Genoma(nCidade, nomeCidade);
                    entrada.addGenoma(cidade);
                    nCidade++;
                }
            } else if (nLinha == 1) {
                for (String ligacao : separador) {
                    ligacoes = ligacao.split(" ");
                    origem = Integer.valueOf(ligacoes[0]);
                    destino = Integer.valueOf(ligacoes[1]);
                    peso = Integer.valueOf(ligacoes[2]);
                    entrada.addLigacao(origem, destino, peso);
                }
            }

            linha = bufferedReader.readLine();
            nLinha++;
        }
        bufferedReader.close();
    }

    public static void main(String[] args) {
        entrada = new Cromossomo();
        Genetico algoritmo = new Genetico();

        try {
            actionPerformed();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Genoma blumenau = new Genoma(0, "Blumenau");
        Genoma gaxpar = new Genoma(1, "Gaxpar");
        Genoma indaial = new Genoma(2, "Indaial");
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
        entrada.addGenoma(pomerode);*/

        algoritmo.problemaCaixeiro(entrada, 4, 10);

    }
}
