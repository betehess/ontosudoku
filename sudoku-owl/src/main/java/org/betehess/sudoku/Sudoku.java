package org.betehess.sudoku;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class Sudoku {

    public static final int N = 9;
    public static final int N_SQUARE = 3;

    protected String owl;
    protected String constraints;
    protected Map<String, Set<Integer>> solutions;

    public Sudoku() {
        owl = "";
        owl += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        owl += "<!DOCTYPE rdf:RDF [\n";
        owl += "<!ENTITY owl \"http://www.w3.org/2002/07/owl#\">\n";
        owl += "<!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n";
        owl += "<!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\">\n";
        owl += "<!ENTITY sudoku \"http://sudoku.owl#\">\n";
        owl += "<!ENTITY sudoku.owl \"http://sudoku.owl\">\n";
        owl += "<!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\">\n";
        owl += "]>\n";
        owl += "<rdf:RDF xml:base=\"&sudoku.owl;\"\n";
        owl += "    xmlns:owl=\"&owl;\"\n";
        owl += "    xmlns:rdf=\"&rdf;\"\n";
        owl += "    xmlns:rdfs=\"&rdfs;\"\n";
        owl += "    xmlns:sudoku=\"&sudoku;\">\n";
        owl += "<owl:Ontology rdf:about=\"\"/>\n";
        owl += createClasses();
        owl += createInstances();
        owl += createOneOf();
        owl += createAllDifferent();
        owl += createAllDifferent(N);
        
        constraints = "";
    }

    private static String createClasses() {
        String s = "<!-- classes -->\n";
        for (int n = 1; n <= N; n++) {
            for (int i = 1; i <= N_SQUARE; i++) {
                for (int j = 1; j <= N_SQUARE; j++) {
                    s += "<owl:Class rdf:about=\"#C" + n + "_" + i + j + "\"/>\n";
                }
            }
        }
        return s;
    }

    private static String createInstances() {
        String s = "<!-- instances -->\n";
        for (int n = 1; n <= N; n++) {
            for (int i = 1; i <= N_SQUARE; i++) {
                for (int j = 1; j <= N_SQUARE; j++) {
                    s += "<sudoku:C" + n + "_" + i + j + " rdf:about=\"#V" + n + "_" + i + j + "\"/>\n";
                }
            }
        }
        return s;
    }

    private static String createOneOf() {
        String s = "<!-- oneOf -->\n";
        s += "<rdf:Description rdf:about=\"http://www.w3.org/2002/07/owl#Thing\">\n";
        s += " <rdfs:subClassOf>\n";
        s += "  <rdf:Description>\n";
        s += "   <owl:oneOf rdf:parseType=\"Collection\">\n";
        for (int n = 1; n <= N; n++) {
            s += "    <rdf:Description rdf:about=\"#" + n + "\"/>\n";
        }
        s += "   </owl:oneOf>\n";
        s += "  </rdf:Description>\n";
        s += " </rdfs:subClassOf>\n";
        s += "</rdf:Description>\n";
        return s;
    }

    private static String createAllDifferent(List<int[]> l) {
        String s = "<owl:AllDifferent>\n";
        s += " <owl:distinctMembers rdf:parseType=\"Collection\">\n";
        for (int[] t : l) {
            s += "  <rdf:Description rdf:about=\"#V" + t[0] + "_" + t[1] + t[2] + "\"/>\n";
        }
        s += " </owl:distinctMembers>\n";
        s += "</owl:AllDifferent>\n";
        return s;
    }

    private static String createAllDifferent() {
        int n, i, j;
        List<int[]> l = new ArrayList<int[]>();
        String s = "<!-- allDifferent -->\n";
        s += "<!-- all same square (n) -->\n";
        for (n = 1; n <= N; n++) {
            l.clear();
            for (i = 1; i <= N_SQUARE; i++) {
                for (j = 1; j <= N_SQUARE; j++) {
                    int[] t = { n, i, j };
                    l.add(t);
                }
            }
            s += createAllDifferent(l);
        }
        s += "<!-- all same line (i) -->\n";
        for (i = 1; i <= N_SQUARE; i++) {
            l.clear();
            l.add(new int[] { 1, i, 1 });
            l.add(new int[] { 1, i, 2 });
            l.add(new int[] { 1, i, 3 });
            l.add(new int[] { 2, i, 1 });
            l.add(new int[] { 2, i, 2 });
            l.add(new int[] { 2, i, 3 });
            l.add(new int[] { 3, i, 1 });
            l.add(new int[] { 3, i, 2 });
            l.add(new int[] { 3, i, 3 });
            s += createAllDifferent(l);
            l.clear();
            l.add(new int[] { 4, i, 1 });
            l.add(new int[] { 4, i, 2 });
            l.add(new int[] { 4, i, 3 });
            l.add(new int[] { 5, i, 1 });
            l.add(new int[] { 5, i, 2 });
            l.add(new int[] { 5, i, 3 });
            l.add(new int[] { 6, i, 1 });
            l.add(new int[] { 6, i, 2 });
            l.add(new int[] { 6, i, 3 });
            s += createAllDifferent(l);
            l.clear();
            l.add(new int[] { 7, i, 1 });
            l.add(new int[] { 7, i, 2 });
            l.add(new int[] { 7, i, 3 });
            l.add(new int[] { 8, i, 1 });
            l.add(new int[] { 8, i, 2 });
            l.add(new int[] { 8, i, 3 });
            l.add(new int[] { 9, i, 1 });
            l.add(new int[] { 9, i, 2 });
            l.add(new int[] { 9, i, 3 });
            s += createAllDifferent(l);
        }
        s += "<!-- all same column (j) -->\n";
        for (j = 1; j <= N_SQUARE; j++) {
            l.clear();
            l.add(new int[] { 1, 1, j });
            l.add(new int[] { 1, 2, j });
            l.add(new int[] { 1, 3, j });
            l.add(new int[] { 4, 1, j });
            l.add(new int[] { 4, 2, j });
            l.add(new int[] { 4, 3, j });
            l.add(new int[] { 7, 1, j });
            l.add(new int[] { 7, 2, j });
            l.add(new int[] { 7, 3, j });
            s += createAllDifferent(l);
            l.clear();
            l.add(new int[] { 2, 1, j });
            l.add(new int[] { 2, 2, j });
            l.add(new int[] { 2, 3, j });
            l.add(new int[] { 5, 1, j });
            l.add(new int[] { 5, 2, j });
            l.add(new int[] { 5, 3, j });
            l.add(new int[] { 8, 1, j });
            l.add(new int[] { 8, 2, j });
            l.add(new int[] { 8, 3, j });
            s += createAllDifferent(l);
            l.clear();
            l.add(new int[] { 3, 1, j });
            l.add(new int[] { 3, 2, j });
            l.add(new int[] { 3, 3, j });
            l.add(new int[] { 6, 1, j });
            l.add(new int[] { 6, 2, j });
            l.add(new int[] { 6, 3, j });
            l.add(new int[] { 9, 1, j });
            l.add(new int[] { 9, 2, j });
            l.add(new int[] { 9, 3, j });
            s += createAllDifferent(l);
        }
        return s;
    }

    private static String createAllDifferent(int n) {
        String s = "<!-- all different values -->\n";
        s += "<owl:AllDifferent>\n";
        s += " <owl:distinctMembers rdf:parseType=\"Collection\">\n";
        for (int i = 1; i <= n; i++) {
            s += "  <rdf:Description rdf:about=\"#" + i + "\"/>\n";
        }
        s += " </owl:distinctMembers>\n";
        s += "</owl:AllDifferent>\n";
        return s;
    }
    
    private void addConstraints(int i, String v) {
        constraints += "<owl:Thing rdf:about=\"#"+i+"\">\n";
        constraints += " <owl:sameAs rdf:resource=\"#V"+v+"\"/>\n";
        constraints += "</owl:Thing>\n";
    }
    
    public void addConstraints(SudokuBox box) {
        Triple t = box.getCoordinates();
        String v = t.getGroup()+"_"+t.getRow()+t.getColumn();
        addConstraints(box.getValue(), v);
    }
    
    public void addConstraints(String constraint) {
        String[] tokens = constraint.split(";");
        int v = Integer.parseInt(tokens[0]);
        int n = Integer.parseInt(tokens[1]);
        int i = Integer.parseInt(tokens[2]);
        int j = Integer.parseInt(tokens[3]);
        addConstraints(new SudokuBox(v, new Triple(n, i, j)));
    }
    
    public void addConstraints(Set<SudokuBox> set) {
        for (SudokuBox b : set) {
            addConstraints(b);
        }
    }
    
    public void addStringConstraints(Set<String> set) {
        for (String s : set) {
            addConstraints(s);
        }
    }
    
    public String getRDF() {
        String rdf = owl;
        rdf += constraints;
        rdf += "</rdf:RDF>\n";
        return rdf;
    }
    
    public OntModel getOntModel() {
        OntModel model = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC );
        model.read(new StringReader(getRDF()), null);
        return model;
    }
    
    public Query getSparql() {
        String sparql = "";

        // PREFIX
        sparql += "PREFIX fn: <http://www.w3.org/2005/xpath-functions#>\n";
        sparql += "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
        
        // CONSTRUCT
        sparql += "CONSTRUCT {\n";
        for (int n = 1; n <= N; n++) {
            sparql += "<http://sudoku.owl#"+n+"> <betehess:boundTo> ?x"+n+".\n";
        }
        
        // WHERE + FILTER
        sparql += "} WHERE {\n";
        for (int n = 1; n <= N; n++) {
            sparql += "{ ?x"+n+" owl:sameAs <http://sudoku.owl#"+n+">.\n";
            sparql += "FILTER ( ?x"+n+" != <http://sudoku.owl#"+n+"> ) }\n";
            if (n != N)
                sparql += " UNION\n";
        }
        
        sparql += "}";

        Query query = QueryFactory.create(sparql);
        return query;
    }
    
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
        
        for (int value = 1; value <= N; value++) {
            Resource resource = execConstruct.getResource("http://sudoku.owl#"+value);
            StmtIterator iter = resource.listProperties(execConstruct.getProperty("betehess:boundTo"));
            while (iter.hasNext()) {
                String coordinates = iter.nextStatement().getObject().toString();
                int group = Integer.parseInt(coordinates.substring(19, 20));
                int row = Integer.parseInt(coordinates.substring(21, 22));
                int column = Integer.parseInt(coordinates.substring(22, 23));
                String s = new Triple(group, row, column).toString();
                Set<Integer> set = solutions.get(s);
                set.add(value);
                solutions.put(s, set);
            }
        }
    }
    
    public Set<String> getSolutions() {
        Set<String> set = new HashSet<String>();
        for (int n = 1; n <= N; n++) {
            for (int i = 1; i <= N_SQUARE; i++) {
                for (int j = 1; j <= N_SQUARE; j++) {
                    Triple t = new Triple(n, i, j);
                    Set<Integer> values = solutions.get(t.toString());
                    if (values.size() == 1) {
                        set.add(values.toArray()[0]+";"+t.getGroup()+";"+t.getRow()+";"+t.getColumn());                        
                    } else {
                        String valuesString = values.toString();
                        set.add(valuesString+";"+t.getGroup()+";"+t.getRow()+";"+t.getColumn());
                    }
                }
            }
        }
        return set;
    }
    
    public int getValue(Triple t) {
        Set<Integer> values = solutions.get(t.toString());
        return (Integer) values.toArray()[0];
    }
    
    public int getValue(int group, int row, int column) {
        return getValue(new Triple(group, row, column));
    }
    
    public String getValueString(int group, int row, int column) {
        Triple t = new Triple(group, row, column);
        Set<Integer> values = solutions.get(t.toString());
        System.out.println(solutions.toString());
        return values.toString();
    }
    
}
