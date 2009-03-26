package org.betehess;

import junit.framework.TestCase;

import org.betehess.sudoku.BetterSudoku;
import org.betehess.sudoku.Sudoku;
import org.betehess.sudoku.SudokuBox;
import org.betehess.sudoku.Triple;

public class AppTest extends TestCase {

    public void testSudoku() {

        Sudoku sudoku = new Sudoku();

        sudoku.addConstraints(new SudokuBox(9, new Triple(1, 1, 1)));
        sudoku.addConstraints(new SudokuBox(5, new Triple(1, 2, 1)));
        sudoku.addConstraints(new SudokuBox(3, new Triple(1, 3, 3)));

        sudoku.addConstraints(new SudokuBox(4, new Triple(2, 1, 2)));
        sudoku.addConstraints(new SudokuBox(9, new Triple(2, 2, 1)));
        sudoku.addConstraints(new SudokuBox(3, new Triple(2, 2, 2)));
        sudoku.addConstraints(new SudokuBox(8, new Triple(2, 3, 1)));

        sudoku.addConstraints(new SudokuBox(5, new Triple(3, 1, 2)));
        sudoku.addConstraints(new SudokuBox(1, new Triple(3, 1, 3)));
        sudoku.addConstraints(new SudokuBox(7, new Triple(3, 2, 1)));
        sudoku.addConstraints(new SudokuBox(4, new Triple(3, 3, 3)));

        sudoku.addConstraints(new SudokuBox(2, new Triple(4, 1, 2)));
        sudoku.addConstraints(new SudokuBox(6, new Triple(4, 1, 3)));
        sudoku.addConstraints(new SudokuBox(8, new Triple(4, 2, 1)));
        sudoku.addConstraints(new SudokuBox(1, new Triple(4, 3, 3)));

        sudoku.addConstraints(new SudokuBox(3, new Triple(5, 1, 1)));
        sudoku.addConstraints(new SudokuBox(2, new Triple(5, 2, 1)));
        sudoku.addConstraints(new SudokuBox(4, new Triple(5, 2, 3)));
        sudoku.addConstraints(new SudokuBox(7, new Triple(5, 3, 3)));

        sudoku.addConstraints(new SudokuBox(1, new Triple(6, 1, 1)));
        sudoku.addConstraints(new SudokuBox(9, new Triple(6, 2, 3)));
        sudoku.addConstraints(new SudokuBox(2, new Triple(6, 3, 1)));
        sudoku.addConstraints(new SudokuBox(4, new Triple(6, 3, 2)));

        sudoku.addConstraints(new SudokuBox(1, new Triple(7, 1, 1)));
        sudoku.addConstraints(new SudokuBox(9, new Triple(7, 2, 3)));
        sudoku.addConstraints(new SudokuBox(2, new Triple(7, 3, 1)));
        sudoku.addConstraints(new SudokuBox(5, new Triple(7, 3, 2)));

        sudoku.addConstraints(new SudokuBox(2, new Triple(8, 1, 3)));
        sudoku.addConstraints(new SudokuBox(5, new Triple(8, 2, 2)));
        sudoku.addConstraints(new SudokuBox(3, new Triple(8, 2, 3)));
        sudoku.addConstraints(new SudokuBox(9, new Triple(8, 3, 2)));

        sudoku.addConstraints(new SudokuBox(5, new Triple(9, 1, 1)));
        sudoku.addConstraints(new SudokuBox(7, new Triple(9, 2, 3)));
        sudoku.addConstraints(new SudokuBox(3, new Triple(9, 3, 3)));

        System.out.println("\n========= Print owl (sudoku constraints encoding) =========\n");
        System.out.println(sudoku.getRDF());

        System.out.println("\n========= Print SPARQL query =========\n");
        System.out.println(sudoku.getSparql());

        System.out.println("\n========= Solve sudoku =========\n");  
        long begin = System.currentTimeMillis();
        sudoku.compute();
        long end = System.currentTimeMillis();
        long time = (end-begin)/1000;
        System.out.println("Time to solve sudoku (same as inferring missing individual): " + time);

        assertEquals(9, sudoku.getValue(1, 1, 1));
        assertEquals(8, sudoku.getValue(1, 1, 2));
        assertEquals(2, sudoku.getValue(1, 1, 3));
        assertEquals(5, sudoku.getValue(1, 2, 1));
        assertEquals(6, sudoku.getValue(1, 2, 2));
        assertEquals(4, sudoku.getValue(1, 2, 3));
        assertEquals(7, sudoku.getValue(1, 3, 1));
        assertEquals(1, sudoku.getValue(1, 3, 2));
        assertEquals(3, sudoku.getValue(1, 3, 3));
    }

    public void testBetterSudoku() {

        BetterSudoku sudoku = new BetterSudoku();

        sudoku.addConstraints(new SudokuBox(9, new Triple(1, 1, 1)));
        sudoku.addConstraints(new SudokuBox(5, new Triple(1, 2, 1)));
        sudoku.addConstraints(new SudokuBox(3, new Triple(1, 3, 3)));

        sudoku.addConstraints(new SudokuBox(4, new Triple(2, 1, 2)));
        sudoku.addConstraints(new SudokuBox(9, new Triple(2, 2, 1)));
        sudoku.addConstraints(new SudokuBox(3, new Triple(2, 2, 2)));
        sudoku.addConstraints(new SudokuBox(8, new Triple(2, 3, 1)));

        sudoku.addConstraints(new SudokuBox(5, new Triple(3, 1, 2)));
        sudoku.addConstraints(new SudokuBox(1, new Triple(3, 1, 3)));
        sudoku.addConstraints(new SudokuBox(7, new Triple(3, 2, 1)));
        sudoku.addConstraints(new SudokuBox(4, new Triple(3, 3, 3)));

        sudoku.addConstraints(new SudokuBox(2, new Triple(4, 1, 2)));
        sudoku.addConstraints(new SudokuBox(6, new Triple(4, 1, 3)));
        sudoku.addConstraints(new SudokuBox(8, new Triple(4, 2, 1)));
        sudoku.addConstraints(new SudokuBox(1, new Triple(4, 3, 3)));

        sudoku.addConstraints(new SudokuBox(3, new Triple(5, 1, 1)));
        sudoku.addConstraints(new SudokuBox(2, new Triple(5, 2, 1)));
        sudoku.addConstraints(new SudokuBox(4, new Triple(5, 2, 3)));
        sudoku.addConstraints(new SudokuBox(7, new Triple(5, 3, 3)));

        sudoku.addConstraints(new SudokuBox(1, new Triple(6, 1, 1)));
        sudoku.addConstraints(new SudokuBox(9, new Triple(6, 2, 3)));
        sudoku.addConstraints(new SudokuBox(2, new Triple(6, 3, 1)));
        sudoku.addConstraints(new SudokuBox(4, new Triple(6, 3, 2)));

        sudoku.addConstraints(new SudokuBox(1, new Triple(7, 1, 1)));
        sudoku.addConstraints(new SudokuBox(9, new Triple(7, 2, 3)));
        sudoku.addConstraints(new SudokuBox(2, new Triple(7, 3, 1)));
        sudoku.addConstraints(new SudokuBox(5, new Triple(7, 3, 2)));

        sudoku.addConstraints(new SudokuBox(2, new Triple(8, 1, 3)));
        sudoku.addConstraints(new SudokuBox(5, new Triple(8, 2, 2)));
        sudoku.addConstraints(new SudokuBox(3, new Triple(8, 2, 3)));
        sudoku.addConstraints(new SudokuBox(9, new Triple(8, 3, 2)));

        sudoku.addConstraints(new SudokuBox(5, new Triple(9, 1, 1)));
        sudoku.addConstraints(new SudokuBox(7, new Triple(9, 2, 3)));
        sudoku.addConstraints(new SudokuBox(3, new Triple(9, 3, 3)));

        System.out.println("\n========= Print owl (sudoku constraints encoding) =========\n");
        System.out.println(sudoku.getRDF());

        System.out.println("\n========= Print SPARQL query =========\n");
        System.out.println(sudoku.getSparql());

        System.out.println("\n========= Solve sudoku =========\n");  
        long begin = System.currentTimeMillis();
        sudoku.compute();
        long end = System.currentTimeMillis();
        long time = (end-begin)/1000;
        System.out.println("Time to solve sudoku (same as inferring missing individual): " + time);

        assertEquals(9, sudoku.getValue(1, 1, 1));
        assertEquals(8, sudoku.getValue(1, 1, 2));
        assertEquals(2, sudoku.getValue(1, 1, 3));
        assertEquals(5, sudoku.getValue(1, 2, 1));
        assertEquals(6, sudoku.getValue(1, 2, 2));
        assertEquals(4, sudoku.getValue(1, 2, 3));
        assertEquals(7, sudoku.getValue(1, 3, 1));
        assertEquals(1, sudoku.getValue(1, 3, 2));
        assertEquals(3, sudoku.getValue(1, 3, 3));
    }

}
