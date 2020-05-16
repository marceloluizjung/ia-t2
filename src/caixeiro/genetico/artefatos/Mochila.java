package caixeiro.genetico.artefatos;

import caixeiro.genetico.exceptions.TamanhoException;

import java.util.ArrayList;
import java.util.List;

public class Mochila {

    private int tamanho;
    private int tamanhoMaximo;
    private ArrayList<ItemMochila> itens;

    public Mochila() {
        this.itens = new ArrayList<ItemMochila>();
    }

    public Mochila(int tamanhoMaximo) {
        this.itens = new ArrayList<ItemMochila>();
        this.tamanhoMaximo = tamanhoMaximo;
    }

    public void addItem(ItemMochila item) throws TamanhoException {
        if (item.getTamanho() + this.tamanho > this.tamanhoMaximo) {
            throw new TamanhoException();
        }

        this.itens.add(item);
        tamanho += item.getTamanho();
    }

    public int getTamanhoMaximo() {
        return tamanhoMaximo;
    }

    public void setTamanhoMaximo(int tamanhoMaximo) {
        this.tamanhoMaximo = tamanhoMaximo;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
}
