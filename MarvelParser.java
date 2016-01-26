package hw6;
import java.io.*;
import java.util.*;

import hw5.*;
import hw7.EdgeCost;
/**
 * Parser utility to load the Marvel Comics dataset.
 */
public class MarvelParser {
    /**
     * A checked exception class for bad data files
     */
    @SuppressWarnings("serial")
    public static class MalformedDataException extends Exception {
        public MalformedDataException() { }

        public MalformedDataException(String message) {
            super(message);
        }

        public MalformedDataException(Throwable cause) {
            super(cause);
        }

        public MalformedDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }

  /**
   * Reads the Marvel Universe dataset.
   * Each line of the input file contains a character name and a comic
   * book the character appeared in, separated by a tab character
   * 
   * @requires filename is a valid file path
   * @param filename the file that will be read
   * @param characters list in which all character names will be stored;
   *          typically empty when the routine is called
   * @param books map from titles of comic books to characters that
   *          appear in them; typically empty when the routine is called
   * @param	graph Build a graph with nodes of characters and edges of books
   * @modifies characters, books, graph
   * @effects fills characters with a list of all unique character names
   * @effects fills books with a map from each comic book to all characters
   *          appearing in it
   * @effects fills nodes of characters and edges of books into the graph
   * @throws MalformedDataException if the file is not well-formed:
   *          each line contains exactly two tokens separated by a tab,
   *          or else starting with a # symbol to indicate a comment line.
   */
  public static void parseData(String filename, Set<String> characters,
      Map<String, List<String>> books, Graph<String, String> graph) throws MalformedDataException {
    // Why does this method accept the Collections to be filled as
    // parameters rather than making them a return value? To allows us to
    // "return" two different Collections. If only one or neither Collection
    // needs to be returned to the caller, feel free to rewrite this method
    // without the parameters. Generally this is better style.
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(filename));

        // Construct the collections of characters and books, one
        // <character, book> pair at a time.
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {

            // Ignore comment lines.
            if (inputLine.startsWith("#")) {
                continue;
            }

            // Parse the data, stripping out quotation marks and throwing
            // an exception for malformed lines.
            inputLine = inputLine.replace("\"", "");
            String[] tokens = inputLine.split("\t");
            if (tokens.length != 2) {
                throw new MalformedDataException("Line should contain exactly one tab: "
                                                 + inputLine);
            }

            String character = tokens[0];
            String book = tokens[1];

            // Add the parsed data to the character and book collections.
            characters.add(character);
            if (!books.containsKey(book)) {
                books.put(book, new ArrayList<String>());
            }
            if (!books.get(book).contains(character)) {
            	List<String> lstInBook = books.get(book);
            	Vertex<String, String> newVertex;
            	if (!graph.containsVertex(new Vertex<String, String>(character))){
            		newVertex = new Vertex<String, String>(character);
            		graph.addVertex(newVertex);
            	} else{
            		newVertex = graph.getVertex(character);
            	}
            	for (int i = 0; i < lstInBook.size(); i++) {
            		Vertex<String, String> oldVertex = graph.getVertex(lstInBook.get(i));
            		graph.connectBothSide(oldVertex, newVertex, book);      
            	}
            	books.get(book).add(character);
            }
        }
    } catch (IOException e) {
        System.err.println(e.toString());
        e.printStackTrace(System.err);
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println(e.toString());
                e.printStackTrace(System.err);
            }
        }
    }
  }
  
  /**
   * Reads the Marvel Universe dataset.
   * Each line of the input file contains a character name and a comic
   * book the character appeared in, separated by a tab character
   * 
   * @requires filename is a valid file path
   * @param filename the file that will be read
   * @param characters list in which all character names will be stored;
   *          typically empty when the routine is called
   * @param books map from titles of comic books to characters that
   *          appear in them; typically empty when the routine is called
   * @param	graph Build a graph with nodes of characters and edges of COSTS
   * @modifies characters, books, graph
   * @effects fills characters with a list of all unique character names
   * @effects fills books with a map from each comic book to all characters
   *          appearing in it
   * @effects fills nodes of characters and edges of COSTS into the graph
   * @throws MalformedDataException if the file is not well-formed:
   *          each line contains exactly two tokens separated by a tab,
   *          or else starting with a # symbol to indicate a comment line.
   */
  public static void parseData1(String filename, Set<String> characters,
      Map<String, List<String>> books, Graph<String, EdgeCost> graph) throws MalformedDataException {

    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(filename));

        String inputLine;
        while ((inputLine = reader.readLine()) != null) {

            // Ignore comment lines.
            if (inputLine.startsWith("#")) {
                continue;
            }

            // Parse the data, stripping out quotation marks and throwing
            // an exception for malformed lines.
            inputLine = inputLine.replace("\"", "");
            String[] tokens = inputLine.split("\t");
            if (tokens.length != 2) {
                throw new MalformedDataException("Line should contain exactly one tab: "
                                                 + inputLine);
            }

            String character = tokens[0];
            String book = tokens[1];

            // Add the parsed data to the character and book collections.
            characters.add(character);
            if (!books.containsKey(book)) {
                books.put(book, new ArrayList<String>());
            }
            
            if (!books.get(book).contains(character)) {
            	List<String> lstInBook = books.get(book);
            	Vertex<String, EdgeCost> newVertex;
            	if (!graph.containsVertex(new Vertex<String, EdgeCost>(character))){
            		newVertex = new Vertex<String, EdgeCost>(character);
            		graph.addVertex(newVertex);
                	for (int i = 0; i < lstInBook.size(); i++) {
                		Vertex<String, EdgeCost> oldVertex = graph.getVertex(lstInBook.get(i));
                		graph.connectBothSide(oldVertex, newVertex, new EdgeCost(1));      
                	}
            	} else{
            		newVertex = graph.getVertex(character);
                	for (int i = 0; i < lstInBook.size(); i++) {
                		Vertex<String, EdgeCost> oldVertex = graph.getVertex(lstInBook.get(i));
                		int edgePos = -1;
                		for (int j = 0; j < oldVertex.getOutEdge().size(); j++){
                			if (oldVertex.getOutEdge().get(j).getEndVertex().equals(newVertex)) {
                				edgePos = j;
                				break;
                			}
                		}
                		if (edgePos != -1) {
                			EdgeCost cost = oldVertex.getOutEdge().get(edgePos).getEdgeCost();
                			cost.setCostDenom(cost.getCostDenom() + 1);
                			oldVertex.getOutEdge().get(edgePos).setEdgeCost(cost);
                       		for (int j = 0; j < oldVertex.getInEdge().size(); j++){
                    			if (oldVertex.getInEdge().get(j).getStartVertex().equals(newVertex)) {
                    				edgePos = j;
                    				break;
                    			}
                    		}
                    		oldVertex.getInEdge().get(edgePos).setEdgeCost(cost);
                		} else {
                			graph.connectBothSide(oldVertex, newVertex, new EdgeCost(1));
                		}             		
                	}
            	}
            	books.get(book).add(character);
            }
        }
    } catch (IOException e) {
        System.err.println(e.toString());
        e.printStackTrace(System.err);
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println(e.toString());
                e.printStackTrace(System.err);
            }
        }
    }
  }

}
