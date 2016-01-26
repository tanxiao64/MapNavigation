package hw7.test;

import static org.junit.Assert.assertEquals;
import hw5.Edge;
import hw5.Graph;
import hw5.Vertex;
import hw7.EdgeCost;
import hw7.MarvelPaths2;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

public class TestShortestPath {
		@Test
		public void testShortestPath(){
			Graph<String, EdgeCost> g = new Graph<String, EdgeCost>();
			MarvelPaths2 testShortestPath = new MarvelPaths2();
			testShortestPath.buildGraph("staffSuperheroes.tsv", g);
			ArrayList<Vertex<String, EdgeCost>> lstV = g.sortVertices();
			LinkedList<Edge<String, EdgeCost>> lstE = testShortestPath.shortestPathDijkstra(g, lstV.get(0), lstV.get(1));
			System.out.println(lstE);
			assertEquals(lstE.size(), 1);
			lstE = testShortestPath.shortestPathDijkstra(g, lstV.get(0), lstV.get(2));
			System.out.println(lstE);
			assertEquals(lstE.size(), 1);
			lstE = testShortestPath.shortestPathDijkstra(g, lstV.get(0), lstV.get(3));
			System.out.println(lstE);
			assertEquals(lstE.size(), 1);
			lstE = testShortestPath.shortestPathDijkstra(g, lstV.get(1), lstV.get(2));
			System.out.println(lstE);
			assertEquals(lstE.size(), 1);
			lstE = testShortestPath.shortestPathDijkstra(g, lstV.get(1), lstV.get(3));
			System.out.println(lstE);
			assertEquals(lstE.size(), 1);
			lstE = testShortestPath.shortestPathDijkstra(g, lstV.get(2), lstV.get(3));
			System.out.println(lstE);
			assertEquals(lstE.size(), 1);
		}

}
