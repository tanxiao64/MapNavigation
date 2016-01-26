package hw7;

import hw5.Edge;
import hw5.Graph;
import hw5.Vertex;
import hw6.MarvelParser;
import hw6.MarvelParser.MalformedDataException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MarvelPaths2 {
	
	  /**
	   * Add all nodes and edges information from a file into a graph to build a graph with weighted 
	   * edges
	   * @param filename The name of the importing file
	   * @param graph The graph to store all the information from the file
	   * @effects Add all nodes and edges with costs into the graph
	   * @modifies graph
	   */
	  public void buildGraph(String filename, Graph<String, EdgeCost> graph){
		  filename = "src/hw7/data/" + filename;
		  Set<String> characters = new HashSet<String>();
		  Map<String, List<String>> books = new HashMap<String, List<String>>();
		  try {
			MarvelParser.parseData1(filename, characters, books, graph);
		} catch (MalformedDataException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
		}
	  }
	  
	  /**
	   * Find the shortest path from start node to destination node by dijkstra Algorithm. 
	   * Return the shortest path in a linked list.
	   * @param graph The graph in which to find the shortest path
	   * @param staVertex The start node of the shortest path
	   * @param desVertex The destination node of the shortest path
	   * @returns A linked list of edges which consists the shortest path. If no path is found, 
	   * return null.
	   */
	  // pre: Takes two nodes(start node and destination node) and a graph
	  // post: Returns the shortest path in the provided graph from the start node to the destination 
	  // node.
	  public LinkedList<Edge<String, EdgeCost>> shortestPathDijkstra(Graph<String, EdgeCost> graph, 
			  Vertex<String, EdgeCost> staVertex, Vertex<String, EdgeCost> desVertex){
		  ShortestPathDijkstra<String> s = new ShortestPathDijkstra<>();
		  return s.shortestPathDijkstra(graph, staVertex, desVertex);	 
	  }
}
		  

