package hw5.test;

import static org.junit.Assert.*;
import hw5.Vertex;
import hw5.Edge;

import org.junit.Test;


public final class VertexTest {
	
	@Test
	public void testZeroArgConstructor(){
		new Vertex<String, String>();
	}
	
	@Test
	public void testOneArgConstructor(){
		new Vertex<String, String>("A");
		new Vertex<String, String>("1");
		new Vertex<String, String>("ABC");
	}
	
	@Test
	public void testEquals(){
		assertEquals(new Vertex<String, String>("A"), new Vertex<String, String>("A"));
		assertEquals(new Vertex<String, String>("1"), new Vertex<String, String>("1"));
		assertEquals(new Vertex<String, String>("ABC"), new Vertex<String, String>("ABC"));
	}
	
	@Test
	public void testSetInfo(){
		Vertex<String, String> v1 = new Vertex<String, String>();
		v1.setInfo("A");
		assertEquals(new Vertex<String, String>("A"), v1);
		
		Vertex<String, String> v2 = new Vertex<String, String>("1");
		v2.setInfo("ABC");
		assertEquals(new Vertex<String, String>("ABC"), v2);
	}
	
	@Test
	public void testGetInfo(){
		Vertex<String, String> v1 = new Vertex<String, String>();
		v1.setInfo("A");
		assertEquals("A", v1.getInfo());
		
		Vertex<String, String> v2 = new Vertex<String, String>("1");
		assertEquals("1", v2.getInfo());
	}
	
	@Test
	public void testToString(){
		Vertex<String, String> v = new Vertex<String, String>("A");
		assertEquals(v.toString(), "A");
	}
	
	@Test
	public void testConnectVertexFrom(){
		Vertex<String, String> v = new Vertex<String, String>("A");
		assertTrue(v.connectVertexFrom(new Vertex<String, String>("B"), "1"));
		assertFalse(v.connectVertexFrom(new Vertex<String, String>("B"), "1"));
	}
	
	@Test
	public void testGetInEdge(){
		Vertex<String, String> v = new Vertex<String, String>("A");
		v.connectVertexFrom(new Vertex<String, String>("B"), "1");
		assertEquals(v.getInEdge().get(0), new Edge<String, String>(new Vertex<String, String>("B"), new Vertex<String, String>("A"), "1"));
		v.connectVertexFrom(new Vertex<String, String>("C"), "2");
		assertEquals(v.getInEdge().size(), 2);
		assertEquals(v.getInEdge().get(1), new Edge<String, String>(new Vertex<String, String>("C"), new Vertex<String, String>("A"), "2"));
	}
	
	@Test
	public void testConnectVertexTo(){
		Vertex<String, String> v = new Vertex<String, String>("A");
		assertTrue(v.connectVertexTo(new Vertex<String, String>("B"), "1"));
		assertFalse(v.connectVertexTo(new Vertex<String, String>("B"), "1"));
	}
	
	@Test
	public void testGetOutEdge(){
		Vertex<String, String> v = new Vertex<String, String>("A");
		v.connectVertexTo(new Vertex<String, String>("B"), "1");
		assertEquals(v.getOutEdge().get(0), new Edge<String, String>(new Vertex<String, String>("A"), new Vertex<String, String>("B"), "1"));
		v.connectVertexTo(new Vertex<String, String>("C"), "2");
		assertEquals(v.getOutEdge().size(), 2);
		assertEquals(v.getOutEdge().get(1), new Edge<String, String>(new Vertex<String, String>("A"), new Vertex<String, String>("C"), "2"));
	}
	
	@Test
	public void testRemoveEdge(){
		Vertex<String, String> v = new Vertex<String, String>("A");
		v.connectVertexTo(new Vertex<String, String>("B"), "1");
		assertEquals(v.getOutEdge().size(), 1);
		assertFalse(v.removeEdge(new Edge<String, String>(new Vertex<String, String>("B"), new Vertex<String, String>("A"), "1")));
		assertEquals(v.getOutEdge().size(), 1);
		assertTrue(v.removeEdge(new Edge<String, String>(new Vertex<String, String>("A"), new Vertex<String, String>("B"), "1")));
		assertEquals(v.getOutEdge().size(), 0);
	}
}
