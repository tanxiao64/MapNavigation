package hw8;

import java.util.HashMap;
import java.util.LinkedList;
import hw5.Edge;
import hw5.Graph;
import hw5.Vertex;
import hw6.MarvelParser.MalformedDataException;
import hw7.EdgeCost;
import hw7.ShortestPathDijkstra;

/**
 * This class contains methods to build graph and find the shortest path. It also contains the main 
 * method.
 * 
 */
public class CampusPaths {

	// This class does not contain RI and AF
	
	/**
	 * Build a graph from the dateset of buildings and paths
	 *  
	 * @requires filenameBuildings and filenamePaths are valid file paths
	 * @param filenameBuildings the file of buildings that will be read
	 * @param filenamePaths the file of buildings that will be read
	 * @param graph Build a graph with nodes of Point and edges of EdgeCost
	 * @param mapBuildingsToPoints Map the building to its coordinate
	 * @param mapShortToLongName Map the short name to the long name
	 * @modifies graph
	 * @effects fills mapBuildingsToPoints with a map from a building to its coordinate
	 * @effects fills mapShortToLongName with a map from the short name to the long name
	 * @effects fills nodes of Point and edges of EdgeCost into the graph
	 * @throws MalformedDataException if the file is not well-formed
	 */
	public static void buildGraph(String filenameBuildings, String filenamePaths, Graph<Point, EdgeCost> graph, 
			HashMap<BuildingInfo, Point> mapBuildingsToPoints, HashMap<String, String> mapShortToLongName) 
					throws MalformedDataException{
		filenameBuildings ="src/hw8/data/" + filenameBuildings; 
		filenamePaths ="src/hw8/data/" + filenamePaths;
		try {
			CampusPaser.parseData(filenameBuildings, filenamePaths, graph, mapBuildingsToPoints, mapShortToLongName);
		} catch (MalformedDataException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
		}
	}
	
	/**
	 * Find the shortest path from the start building coordinate to the destination building coordinate
	 * Return the shortest path in a linked list.
	 * @requires Two nodes, which are in the graph
	 * @param graph The graph in which to find the shortest path
	 * @param staVertex The start node of the shortest path
	 * @param desVertex The destination node of the shortest path
	 * @returns A linked list of edges which consists the shortest path. If no path is found, 
	 * return null.
	 */
	public static LinkedList<Edge<Point, EdgeCost>> shortestPathDijkstra(Graph<Point, EdgeCost> graph, 
			Vertex<Point, EdgeCost> staVertex, Vertex<Point, EdgeCost> desVertex){
		ShortestPathDijkstra<Point> c = new ShortestPathDijkstra<>();
		return c.shortestPathDijkstra(graph, staVertex, desVertex);	 
	}
	  
	/**
	 * The main method which will build a graph from the datasets and read and print in console
	 * 
	 */
	public static void main(String[] args) {
		Graph<Point, EdgeCost> graph = new Graph<>();
		HashMap<BuildingInfo, Point> mapBuildingsToPoints = new HashMap<>();
		HashMap<String, String> mapShortToLongName = new HashMap<>();
		try {
		CampusPaths.buildGraph("campus_buildings.dat", "campus_paths.dat", graph, mapBuildingsToPoints, mapShortToLongName);
		}catch (MalformedDataException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
		}
		ViewController.handleData(graph, mapBuildingsToPoints, mapShortToLongName);
	}

}
