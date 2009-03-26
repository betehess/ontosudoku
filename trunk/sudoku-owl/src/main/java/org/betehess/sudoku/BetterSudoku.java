package org.betehess.sudoku;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.mindswap.pellet.IndividualIterator;
import org.mindswap.pellet.KnowledgeBase;
import org.mindswap.pellet.Node;
import org.mindswap.pellet.jena.PelletInfGraph;

import aterm.ATermAppl;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.rdf.model.Model;

public class BetterSudoku extends Sudoku {

    @Override
    public void compute() {
        Query query = getSparql();
        OntModel model = getOntModel();
        QueryExecution qexec = QueryExecutionFactory.create(query, model) ;        
        
        Model execConstruct = qexec.execConstruct();
        
        solutions = new HashMap<String, Set<Integer>>();
        for (int n = 1; n <= N; n++) {
            for (int i = 1; i <= N_SQUARE; i++) {
                for (int j = 1; j <= N_SQUARE; j++) {
                    solutions.put(
                            new Triple(n, i, j).toString(),
                            new HashSet<Integer>());
                }
            }
        }
        
        model.prepare();
        // get the Pellet KB
        KnowledgeBase kb = ((PelletInfGraph)model.getGraph()).getKB();
        // iterate over individuals
        IndividualIterator it = kb.getABox().getIndIterator();
        while (it.hasNext()) {
            org.mindswap.pellet.Individual ind = it.next();
            // find the individuals that are owl:sameAs with this
            Set<String> set = new HashSet<String>();
            int value = 0;
            for (ATermAppl nom : ind.getTypes(Node.NOM)) {
                String uri = nom.getArgument(0).toString();
                if (uri.startsWith("http://sudoku.owl#V")) {
                    int group = Integer.parseInt(uri.substring(19, 20));
                    int row = Integer.parseInt(uri.substring(21, 22));
                    int column = Integer.parseInt(uri.substring(22, 23));
                    set.add(new Triple(group, row, column).toString());
                } else {
                    value = Integer.parseInt(uri.substring(18, 19));
                }
            }
            for (String s : set) {
                Set<Integer> sol = new HashSet<Integer>();
                sol.add(value);
                solutions.put(s, sol);
            }
        }
    }
}
