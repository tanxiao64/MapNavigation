package hw6.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import hw5.Edge;
import hw5.Graph;
import hw5.Vertex;
import hw6.MarvelPaths;

import org.junit.Test;

public class TestBFS {
	@Test
	public void testBFS(){
		Graph<String, String> g = new Graph<String, String>();
		MarvelPaths testBFS = new MarvelPaths();
		testBFS.buildGraph("staffSuperheroes.tsv", g);
		ArrayList<Vertex<String, String>> lstV = g.sortVertices();
		LinkedList<Edge<String, String>> lstE = testBFS.BFS(g, lstV.get(0), lstV.get(1));
		System.out.println(lstE);
		assertEquals(lstE.size(), 1);
		lstE = testBFS.BFS(g, lstV.get(0), lstV.get(2));
		System.out.println(lstE);
		assertEquals(lstE.size(), 1);
		lstE = testBFS.BFS(g, lstV.get(0), lstV.get(3));
		System.out.println(lstE);
		assertEquals(lstE.size(), 1);
		lstE = testBFS.BFS(g, lstV.get(1), lstV.get(2));
		System.out.println(lstE);
		assertEquals(lstE.size(), 1);
		lstE = testBFS.BFS(g, lstV.get(1), lstV.get(3));
		System.out.println(lstE);
		assertEquals(lstE.size(), 1);
		lstE = testBFS.BFS(g, lstV.get(2), lstV.get(3));
		System.out.println(lstE);
		assertEquals(lstE.size(), 1);
	}

}
