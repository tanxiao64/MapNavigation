package hw5.test;

import static org.junit.Assert.*;
import hw5.Vertex;
import hw5.Edge;

import org.junit.Test;

public final class EdgeTest {
	@Test
	public void testThreeArgConstructor(){
		new Edge<String, String>(new Vertex<String, String>("A"), new Vertex<String, String>("B"), "1");
		new Edge<String, String>(new Vertex<String, String>("A"), new Vertex<String, String>("A"), "1");	// start and end vertices are the same
	}
	
	@Test
	public void testEquals(){
		assertEquals(new Edge<String, String>(new Vertex<String, String>("A"), new Vertex<String, String>("B"), "1"),
				new Edge<String, String>(new Vertex<String, String>("A"), new Vertex<String, String>("B"), "1"));
	}
	
	@Test
	public void testSetEdgeCost(){
		Edge<String, String> e = new Edge<String, String>(new Vertex<String, String>("A"), new Vertex<String, String>("B"), "1");
		e.setEdgeCost("2");
		assertEquals(new Edge<String, String>(new Vertex<String, String>("A"), new Vertex<String, String>("B"), "2"), e);
	}
	
	@Test
	public void testGetEdgeCost(){
		Edge<String, String> e = new Edge<String, String>(new Vertex<String, String>("A"), new Vertex<String, String>("B"), "1");
		assertEquals(e.getEdgeCost(),"1");
	}
	
	@Test
	public void testToString(){
		Edge<String, String> e = new Edge<String, String>(new Vertex<String, String>("A"), new Vertex<String, String>("B"), "1");
		assertEquals(e.toString(), "A -> B (Cost: 1)");
	}
	
	@Test 
	public void testGetStartVertex(){
		Edge<String, String> e = new Edge<String, String>(new Vertex<String, String>("A"), new Vertex<String, String>("B"), "1");
		assertEquals(e.getStartVertex(), new Vertex<String, String>("A"));
	}
	
	@Test
	public void testGetEndVertex(){
		Edge<String, String> e = new Edge<String, String>(new Vertex<String, String>("A"), new Vertex<String, String>("B"), "1");
		assertEquals(e.getEndVertex(), new Vertex<String, String>("B"));
	}
}
