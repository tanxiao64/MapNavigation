package hw6;

import hw5.Edge;
import hw5.Graph;
import hw5.Vertex;
import hw6.MarvelParser.MalformedDataException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MarvelPaths {

	  /**
	   * Add all nodes and edges information from a file into a graph 
	   * @param filename The name of the importing file
	   * @param graph The graph to store all the information from the file
	   * @effects Add all nodes and edges into the graph
	   * @modifies graph
	   */
	  public void buildGraph(String filename, Graph<String, String> graph){
		  filename = "src/hw6/data/" + filename;
		  Set<String> characters = new HashSet<String>();
		  Map<String, List<String>> books = new HashMap<String, List<String>>();
		  try {
			MarvelParser.parseData(filename, characters, books, graph);
		} catch (MalformedDataException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
		}
	  }

	  
	  /**
	   * Breadth first search the graph and return the shortest path from start node to destination node
	   * @param graph The graph to be search
	   * @param staVertex The start node
	   * @param desVertex The destination node
	   * @returns a list of edges from start node to destination node
	   */
	  public LinkedList<Edge<String, String>> BFS(Graph<String, String> graph, 
			  Vertex<String, String> staVertex, Vertex<String, String> desVertex){
		  HashMap<Vertex<String, String>, LinkedList<Edge<String, String>>> map = 
				  new HashMap<Vertex<String, String>, LinkedList<Edge<String, String>>>();
		  Queue<Vertex<String, String>> bfsQueue = new LinkedList<Vertex<String, String>>();
		  bfsQueue.add(staVertex);
		  map.put(staVertex, new LinkedList<Edge<String, String>>());
		  Vertex<String, String> curVertex;
		  while (bfsQueue.size() != 0) {
			  curVertex = bfsQueue.poll();
			  if (curVertex.equals(desVertex)) {
				  return new LinkedList<Edge<String, String>>(map.get(curVertex));
			  }
			  ArrayList<Vertex<String, String>> lstChildVertex = 
					  new ArrayList<Vertex<String, String>>(graph.getOutwardVertex(curVertex));
			  Collections.sort(lstChildVertex);
			  for (int i = 0; i < lstChildVertex.size(); i++){
				  Vertex<String, String> nextVertex = lstChildVertex.get(i);
				  if (!map.containsKey(nextVertex)) {
					  ArrayList<Edge<String, String>> lstFromCurVToNextV = 
							  new ArrayList<Edge<String, String>>();
					  for (int j = 0; j < curVertex.getOutEdge().size(); j++) {
						  if (curVertex.getOutEdge().get(j).getEndVertex().equals(nextVertex)) {
							  lstFromCurVToNextV.add(curVertex.getOutEdge().get(j));
						  }
					  }
					  Collections.sort(lstFromCurVToNextV);
					  LinkedList<Edge<String, String>> path = 
							  new LinkedList<Edge<String, String>>(map.get(curVertex)); 
					  path.add(lstFromCurVToNextV.get(0));
					  map.put(nextVertex, path);
					  bfsQueue.add(nextVertex);
				  }
			  }
		  }
		  return null;
	  }
	  
}
