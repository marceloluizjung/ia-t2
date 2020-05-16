package com.algorithm;

public class Knot {

    private boolean blocked;
    private int custoSuperior;

    public Knot(boolean blocked) {
        this.setBlocked(blocked);
    }

    public int getCustoSuperior() {
        return custoSuperior;
    }

    public void setCustoSuperior(int custoSuperior) {
        this.custoSuperior = custoSuperior;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

}
