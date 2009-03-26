package org.betehess.sudoku.server;

import java.util.Set;

import org.apache.log4j.Logger;
import org.betehess.sudoku.Sudoku;
import org.betehess.sudoku.client.SampleRemoteService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SampleRemoteServiceImpl
extends RemoteServiceServlet
implements SampleRemoteService {

    private static final long serialVersionUID = 5124163539992074451L;

    private static final Logger LOG =
        Logger.getLogger(SampleRemoteServiceImpl.class);

    public Set<String> mytest(Set<String> constraints) {
        Sudoku sudoku = new Sudoku();
        sudoku.addStringConstraints(constraints);
        sudoku.compute();
        return sudoku.getSolutions();
    }
}
