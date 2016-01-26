package hw5.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import hw5.Vertex;
import hw5.Graph;

import org.junit.Test;

public final class GraphTest {

	@Test
	public void testZeroArgConstructor(){
		new Graph<String, String>();
	}
	
	@Test
	public void testAddVertex(){
		Graph<String, String> g = new Graph<String, String>();
		assertTrue(g.addVertex(new Vertex<String, String>("A")));
		assertFalse(g.addVertex(new Vertex<String, String>("A")));
	}
	
	@Test//
	public void testRemoveVertex(){
		Graph<String, String> g = new Graph<String, String>();
		g.addVertex(new Vertex<String, String>("A"));
		assertFalse(g.removeVertex(new Vertex<String, String>("B")));
		assertTrue(g.removeVertex(new Vertex<String, String>("A")));
		assertFalse(g.removeVertex(new Vertex<String, String>("A")));
	}
	
	@Test
	public void testGetSize(){
		Graph<String, String> g = new Graph<String, String>();
		assertEquals(g.getSize(), 0);
		g.addVertex(new Vertex<String, String>("A"));
		assertEquals(g.getSize(), 1);
		g.removeVertex(new Vertex<String, String>("A"));
		assertEquals(g.getSize(), 0);
	}
	
	@Test
	public void testContainsVertex() {
		Graph<String, String> g = new Graph<String, String>();
		g.addVertex(new Vertex<String, String>("A"));
		assertTrue(g.containsVertex(new Vertex<String, String>("A")));
		g.removeVertex(new Vertex<String, String>("A"));
		assertFalse(g.containsVertex(new Vertex<String, String>("A")));
	}
	
	@Test
	public void testGetVertex(){
		Graph<String, String> g = new Graph<String, String>();
		g.addVertex(new Vertex<String, String>("A"));
		assertEquals(g.getVertex("A"), new Vertex<String, String>("A"));
	}
	
	@Test
	public void TestConnectFromTo() {
		Graph<String, String> g = new Graph<String, String>();
		g.addVertex(new Vertex<String, String>("A"));
		assertTrue(g.connectFromTo(g.getVertex("A"), new Vertex<String, String>("B"), "1"));
		assertFalse(g.connectFromTo(g.getVertex("A"), new Vertex<String, String>("B"), "1"));
		assertTrue(g.containsVertex(g.getVertex("B")));
		assertEquals(g.getSize(), 2);
		assertTrue(g.connectFromTo(g.getVertex("A"), new Vertex<String, String>("C"), "2"));
		assertTrue(g.containsVertex(g.getVertex("C")));
		assertEquals(g.getSize(), 3);
		g.removeVertex(g.getVertex("B"));
		assertEquals(g.getSize(), 2);
		g.removeVertex(g.getVertex("C"));
		assertEquals(g.getSize(), 1);
	}
	
	@Test
	public void TestGetInwardVertex() {
		Graph<String, String> g = new Graph<String, String>();
		Vertex<String, String> vA = new Vertex<String, String>("A");
		Vertex<String, String> vB = new Vertex<String, String>("B");
		Vertex<String, String> vC = new Vertex<String, String>("C");
		g.addVertex(vA);
		g.connectFromTo(vA, vB, "1");
		assertEquals(g.getInwardVertex(vB).get(0), new Vertex<String, String>("A"));
		assertEquals(g.getInwardVertex(vA).size(), 0);
		g.connectFromTo(vA, vC, "2");
		assertEquals(g.getInwardVertex(vC).get(0), new Vertex<String, String>("A"));
		assertEquals(g.getInwardVertex(vA).size(), 0);
	}
	
	@Test
	public void TestGetOutwardVertex() {
		Graph<String, String> g = new Graph<String, String>();
		Vertex<String, String> vA = new Vertex<String, String>("A");
		Vertex<String, String> vB = new Vertex<String, String>("B");
		Vertex<String, String> vC = new Vertex<String, String>("C");
		g.addVertex(vA);
		g.connectFromTo(vA, vB, "1");
		assertEquals(g.getOutwardVertex(vA).get(0), new Vertex<String, String>("B"));
		assertEquals(g.getOutwardVertex(vB).size(), 0);
		g.connectFromTo(vA, vC, "2");
		assertEquals(g.getOutwardVertex(vA).get(1), new Vertex<String, String>("C"));
		assertEquals(g.getOutwardVertex(vC).size(), 0);
	}
	
	@Test
	public void TestSortVertices(){
		Graph<String, String> g = new Graph<String, String>();
		Vertex<String, String> vA = new Vertex<String, String>("A");
		Vertex<String, String> vB = new Vertex<String, String>("B");
		Vertex<String, String> vC = new Vertex<String, String>("C");
		g.addVertex(vC);
		g.connectFromTo(vC, vA, "1");
		g.connectFromTo(vC, vB, "2");
		ArrayList<Vertex<String, String>> sortedList = g.sortVertices();
		assertEquals(sortedList.get(0), new Vertex<String, String>("A"));
		assertEquals(sortedList.get(1), new Vertex<String, String>("B"));
		assertEquals(sortedList.get(2), new Vertex<String, String>("C"));
	}
	
}
