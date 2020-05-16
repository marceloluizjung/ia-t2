package com.algorithm;

public class Position {
    private int line;
    private int column;

    public Position(int line, int column) {
        this.setLine(line);
        this.setColumn(column);
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
