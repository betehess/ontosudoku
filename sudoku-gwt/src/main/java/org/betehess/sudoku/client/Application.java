package org.betehess.sudoku.client;

import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Application implements EntryPoint {

    public static void setConstraints(SudokuGrid sudoku) {
        sudoku.setConstraint(9, 1, 1, 1);
        sudoku.setConstraint(5, 1, 2, 1);
        sudoku.setConstraint(3, 1, 3, 3);

        sudoku.setConstraint(4, 2, 1, 2);
        sudoku.setConstraint(9, 2, 2, 1);
        sudoku.setConstraint(3, 2, 2, 2);
        sudoku.setConstraint(8, 2, 3, 1);

        sudoku.setConstraint(5, 3, 1, 2);
        sudoku.setConstraint(1, 3, 1, 3);
        sudoku.setConstraint(7, 3, 2, 1);
        sudoku.setConstraint(4, 3, 3, 3);

        sudoku.setConstraint(2, 4, 1, 2);
        sudoku.setConstraint(6, 4, 1, 3);
        sudoku.setConstraint(8, 4, 2, 1);
        sudoku.setConstraint(1, 4, 3, 3);

        sudoku.setConstraint(3, 5, 1, 1);
        sudoku.setConstraint(2, 5, 2, 1);
        sudoku.setConstraint(4, 5, 2, 3);
        sudoku.setConstraint(7, 5, 3, 3);

        sudoku.setConstraint(1, 6, 1, 1);
        sudoku.setConstraint(9, 6, 2, 3);
        sudoku.setConstraint(2, 6, 3, 1);
        sudoku.setConstraint(4, 6, 3, 2);

        sudoku.setConstraint(1, 7, 1, 1);
        sudoku.setConstraint(9, 7, 2, 3);
        sudoku.setConstraint(2, 7, 3, 1);
        sudoku.setConstraint(5, 7, 3, 2);

        sudoku.setConstraint(2, 8, 1, 3);
        sudoku.setConstraint(5, 8, 2, 2);
        sudoku.setConstraint(3, 8, 2, 3);
        sudoku.setConstraint(9, 8, 3, 2);

        sudoku.setConstraint(5, 9, 1, 1);
        sudoku.setConstraint(7, 9, 2, 3);
        sudoku.setConstraint(3, 9, 3, 3);
    }
    
    public void onModuleLoad() {
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.addStyleName("widePanel");
        vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        RootPanel.get().add(vPanel);
        
        final HTML debug = new HTML();
        
        final SudokuGrid sudokuGrid = new SudokuGrid();
        vPanel.add(sudokuGrid);
        setConstraints(sudokuGrid);
        sudokuGrid.setDebug(debug);
        
        Button button = new Button("GO !!!");
        vPanel.add(button);
        
        vPanel.add(debug);
        
        button.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {

                SampleRemoteServiceAsync sampleRemoteService =
                    (SampleRemoteServiceAsync) GWT.create(SampleRemoteService.class);

                ServiceDefTarget endpoint = (ServiceDefTarget) sampleRemoteService;

                String moduleRelativeURL = GWT.getModuleBaseURL() + "sampleRemoteService";
                endpoint.setServiceEntryPoint(moduleRelativeURL);

                AsyncCallback<Set<String>> callback = new AsyncCallback<Set<String>>() {
                    public void onSuccess(Set<String> set) {
                        sudokuGrid.setSolutions(set);
                    }

                    public void onFailure(Throwable caught) {
                        debug.setText("DAMMIT! This didnt work."+caught.getMessage());
                    }
                };

                sampleRemoteService.mytest(sudokuGrid.getConstraints(), callback);
            }
        });

    }
}
