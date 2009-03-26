package org.betehess.sudoku;

public class Triple {

    private int group;
    private int row;
    private int column;

    public Triple(int group, int row, int column) {
        this.group = group;
        this.row = row;
        this.column = column;
    }
    
    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    
    @Override
    public String toString() {
        return "["+this.group+this.row+this.column+"]";
    }
}
