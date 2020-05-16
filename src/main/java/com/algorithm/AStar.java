package com.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AStar {

    private List<Position> openList = new ArrayList<>();
    private List<Position> closeList = new ArrayList<>();
    private List<Position> positions = new ArrayList();
    private HashMap<Position, Knot> map = new HashMap<>();
    private int g;
    private List<Position> minimumCostPathList = new ArrayList<>();

    public AStar(List<Position> positions, HashMap<Position, Knot> map) {
        this.positions = positions;
        this.map = map;
    }

    public List<Position> aStarAlgorithm(Position positionStart, Position positionEnd) {
        //1º - Pegar o nó inicial e conhecer seus vizinhos
        List<Position> neighbors = this.getNeighbor(positionStart);

        //2º - Calcular a distância (Custo superior de cada um dos vizinhos) distância manhatan
        this.costAndmanhatanDistance(neighbors, positionStart, positionEnd);

        //3º - Pegar o menor vizinho da lista aberta
        AtomicReference<Position> positionSmallerCost = new AtomicReference<>();
        neighbors.forEach(position -> {
            Knot knot = this.map.get(position);
            if (positionSmallerCost.get() == null) {
                positionSmallerCost.set(position);
            } else {
                Knot knotSmallerCost = this.map.get(positionSmallerCost.get());
                if (knot.getCustoSuperior() < knotSmallerCost.getCustoSuperior()) positionSmallerCost.set(position);
            }
        });
        if (positionSmallerCost.get() == null) {
            this.openList.forEach(position -> {
                Knot knot = this.map.get(position);
                if (positionSmallerCost.get() == null) {
                    positionSmallerCost.set(position);
                } else {
                    Knot knotSmallerCost = this.map.get(positionSmallerCost.get());
                    if (knot.getCustoSuperior() < knotSmallerCost.getCustoSuperior()) positionSmallerCost.set(position);
                }
            });
        }
        //Verificação de loop
        if (!positionStart.equals(positionEnd) && neighbors.size() == 0 && this.openList.size() == 0 && positionSmallerCost.get() == null)
            throw new IllegalArgumentException("Não tenho para onde ir");
        //Ver se é sequência, se for sequência blza, senão limpa, add o start e vai contando dali.
        if (this.minimumCostPathList.size() == 0) {
            this.minimumCostPathList.add(positionStart);
            this.minimumCostPathList.add(positionSmallerCost.get());
        } else {
            if (!positionStart.equals(positionEnd)) {
                Position lastPosition = this.minimumCostPathList.get(this.minimumCostPathList.size() - 1);
                int lineDiference = Math.abs(lastPosition.getLine() - positionSmallerCost.get().getLine());
                int column = Math.abs(lastPosition.getColumn() - positionSmallerCost.get().getColumn());
                if (!positionStart.equals(positionEnd) && neighbors.size() == 0) {
                    lastPosition = this.minimumCostPathList.get(0);
                    this.minimumCostPathList.clear();
                    this.minimumCostPathList.add(lastPosition);
                }
                if (lineDiference == 1 || column == 1) {
                    this.minimumCostPathList.add(positionSmallerCost.get());
                }
            }
        }
        if (positionStart.equals(positionEnd)) return this.minimumCostPathList;
        return this.aStarAlgorithm(positionSmallerCost.get(), positionEnd);
    }

    private List<Position> getNeighbor(Position positionReference) {
        List<Position> neighbors = new ArrayList<>();

        positions.forEach(position -> {
            //Vizinho superior
            if ((positionReference.getLine() - 1) == position.getLine() && positionReference.getColumn() == position.getColumn()) {
                if (!this.map.get(position).isBlocked() && !this.openList.contains(position) && !this.closeList.contains(position))
                    neighbors.add(position);
            }
            //Vizinho inferior
            else if ((positionReference.getLine() + 1) == position.getLine() && positionReference.getColumn() == position.getColumn()) {
                if (!this.map.get(position).isBlocked() && !this.openList.contains(position) && !this.closeList.contains(position))
                    neighbors.add(position);
            }
            //Vizinho lateral esquerda
            else if (positionReference.getLine() == position.getLine() && (positionReference.getColumn() - 1) == position.getColumn()) {
                if (!this.map.get(position).isBlocked() && !this.openList.contains(position) && !this.closeList.contains(position))
                    neighbors.add(position);
            }
            //Vizinho lateral direita
            else if (positionReference.getLine() == position.getLine() && (positionReference.getColumn() + 1) == position.getColumn()) {
                if (!this.map.get(position).isBlocked() && !this.openList.contains(position) && !this.closeList.contains(position))
                    neighbors.add(position);
            }
        });
        this.openList.addAll(neighbors);
        if (this.openList.contains(positionReference)) this.openList.remove(positionReference);
        this.closeList.add(positionReference);
        return neighbors;
    }

    private void costAndmanhatanDistance(List<Position> neighbors, Position positionStart, Position positionEnd) {
        this.g++;
        neighbors.forEach(position -> {
            int manhatanResult = (Math.abs(position.getLine() - positionEnd.getLine()) + Math.abs(position.getColumn() - positionEnd.getColumn())) + 1;
            Knot knot = this.map.get(position);
            knot.setCustoSuperior(this.g + manhatanResult);
            this.map.replace(position, knot);
        });
    }

}
