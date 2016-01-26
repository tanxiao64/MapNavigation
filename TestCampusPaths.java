package hw8.test;

import static org.junit.Assert.assertEquals;
import hw5.Edge;
import hw5.Graph;
import hw5.Vertex;
import hw6.MarvelParser.MalformedDataException;
import hw7.EdgeCost;
import hw8.BuildingInfo;
import hw8.CampusPaths;
import hw8.Point;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;

public class TestCampusPaths {

	@Test
	public void testBuildGraph(){
		String filenameBuildings = "myBuildings.dat";
		String filenamePaths = "myPaths.dat";
		Graph<Point, EdgeCost> graph = new Graph<>();
		HashMap<BuildingInfo, Point> mapBuildingsToPoints = new HashMap<>();
		HashMap<String, String> mapShortToLongName = new HashMap<>();
		try {
			CampusPaths.buildGraph(filenameBuildings, filenamePaths, graph, mapBuildingsToPoints, mapShortToLongName);
		} catch (MalformedDataException e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		}
		assertEquals(4, graph.getSize());
	}
	
	@Test
	public void testShortestPath() {
		String filenameBuildings = "myBuildings.dat";
		String filenamePaths = "myPaths.dat";
		Graph<Point, EdgeCost> graph = new Graph<>();
		HashMap<BuildingInfo, Point> mapBuildingsToPoints = new HashMap<>();
		HashMap<String, String> mapShortToLongName = new HashMap<>();
		try {
			CampusPaths.buildGraph(filenameBuildings, filenamePaths, graph, mapBuildingsToPoints, mapShortToLongName);
		} catch (MalformedDataException e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		}
		Vertex<Point, EdgeCost> vStart = graph.getVertex(new Point(0, 0));
		Vertex<Point, EdgeCost> vEnd = graph.getVertex(new Point(1, 1));
		LinkedList<Edge<Point, EdgeCost>> list = CampusPaths.shortestPathDijkstra(graph, vStart , vEnd);
		assertEquals(2, list.size());
	}
}
