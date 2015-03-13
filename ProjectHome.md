ontosudoku demonstrates inference capabalities over OWL.

It encodes Sudoku constraints into OWL and uses Pellet and Jena to solve it.

A GWT-based GUI is provided.

Quick test: (you need Java 1.6 and Maven 2)

---


$ sudo apt-get install libstdc++5 # needed on Ubuntu

$ svn checkout http://ontosudoku.googlecode.com/svn/trunk/ ontosudoku-read-only

$ cd ontosudoku-read-only/

$ mvn clean install

$ cd sudoku-gwt/

$ mvn com.totsp.gwt:maven-googlewebtoolkit2-plugin:gwt