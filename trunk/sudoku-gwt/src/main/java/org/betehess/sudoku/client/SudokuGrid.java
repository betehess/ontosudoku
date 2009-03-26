package org.betehess.sudoku.client;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

public class SudokuGrid extends Composite {

    public static final String SOLUTION = "solution";
    public static final String COLLISION = "collision";
    public static final String SETBYUSER = "setbyuser";
    
    public static final int N = 9;
    public static final int N_SQUAREROOT = 3;
    private Grid grid = new Grid(N, N);
    
    private HTML debug;
    
    public void setDebug(HTML debug) {
        this.debug = debug;
    }

    private static int getRow(int n, int i, int j) {
        return (n-1)/3*3+(i-1);
    }

    private static int getColumn(int n, int i, int j) {
        return (n-1)%3*3+(j-1);
    }
    
    private static boolean contains(String[] tokens, String s) {
        for (String token : tokens) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }

    private static boolean contains(String styles, String s) {
        return contains(styles.split(" "), s);
    }

    private static boolean contains(TextBox tb, String s) {
        return contains(tb.getStyleName(), s);
    }
    
    public void setConstraint(int value, int n, int i, int j) {
        int row = getRow(n, i, j);
        int col = getColumn(n, i, j);
        TextBox tb = (TextBox) grid.getWidget(row, col);
        tb.setStyleName(SETBYUSER);
        tb.setText(value+"");
    }

    public void setSolutions(Set<String> solutions) {
        for (String sol : solutions) {
            String[] tokens = sol.split(";");
            String v = tokens[0];
            int n = Integer.parseInt(tokens[1]);
            int i = Integer.parseInt(tokens[2]);
            int j = Integer.parseInt(tokens[3]);
            
            int row = getRow(n, i, j);
            int col = getColumn(n, i, j);
            
            TextBox tb = (TextBox) grid.getWidget(row, col);
            String[] style = tb.getStyleName().split(" ");
            if (! contains(style, SETBYUSER) || "".equals(tb.getText())) {
                tb.removeStyleName(SETBYUSER);
                tb.removeStyleName(SOLUTION);
                tb.removeStyleName(COLLISION);
                if (v.length() == 1) {
                    tb.addStyleName(SOLUTION);
                } else {
                    tb.setMaxLength(10);
                    tb.addStyleName(COLLISION);
                }
                tb.setText(v+"");
            }
        }
        
    }

    private static String getTriple(int row, int col) {
        int n = row/3*3+col/3+1;
        int i = row%3+1;
        int j = col%3+1;
        return n+";"+i+";"+j+";";
    }
    
    public SudokuGrid() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                TextBox tb = new TextBox();
                
                tb.setText("");
                tb.setMaxLength(1);
                tb.setWidth("50");
                tb.setHeight("50");
                tb.setTextAlignment(TextBoxBase.ALIGN_CENTER);
                
                tb.addKeyboardListener(new KeyboardListenerAdapter() {
                    public void onKeyPress(Widget sender, char keyCode, int modifiers) {
                        if ((!Character.isDigit(keyCode)) && (keyCode != (char) KEY_TAB)
                                && (keyCode != (char) KEY_BACKSPACE) && (keyCode != (char) KEY_DELETE)
                                && (keyCode != (char) KEY_ENTER) && (keyCode != (char) KEY_HOME)
                                && (keyCode != (char) KEY_END) && (keyCode != (char) KEY_LEFT)
                                && (keyCode != (char) KEY_UP) && (keyCode != (char) KEY_RIGHT)
                                && (keyCode != (char) KEY_DOWN)) {
                            ((TextBox) sender).cancelKey();
                        }
                        sender.removeStyleName(SOLUTION);
                        sender.removeStyleName(COLLISION);
                        sender.addStyleName(SETBYUSER);
                    }
                });                    
                grid.setWidget(i, j, tb);
            }
        }
        
        // init !!!
        initWidget(grid);
    }
    
    public Set<String> getConstraints() {
        Set<String> set = new HashSet<String>();
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                TextBox tb = (TextBox) grid.getWidget(row, col);
                if (! "".equals(tb.getText())
                        && contains(tb, SETBYUSER)) {
                    int value = Integer.parseInt(tb.getText());
                    String s = getTriple(row, col);
                    set.add(value+";"+s);
                }
            }
        }
        return set;
    }
}
