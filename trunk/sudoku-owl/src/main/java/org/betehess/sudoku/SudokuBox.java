package org.betehess.sudoku;

public class SudokuBox {

    private int value;
    private Triple coordinates;

    public SudokuBox(int value, Triple coordinates) {
        this.value = value;
        this.setCoordinates(coordinates);
    }
    
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setCoordinates(Triple coordinates) {
        this.coordinates = coordinates;
    }

    public Triple getCoordinates() {
        return coordinates;
    }
    
    @Override
    public String toString() {
        return "["+coordinates+"->"+value+"]";
    }
    
}
