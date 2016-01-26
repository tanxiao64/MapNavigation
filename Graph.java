package hw5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import hw5.Vertex;


/**
 * This class represents a directed labeled multigraph, with basic operations. The class of 
 * Vertex will support this class. A vertex can be added directly into this class, with no 
 * edges. Or, a vertex can also be added into this class by adding a edge connected a new 
 * vertex. This graph class does not allow identical edges and no vertex in this graph contains
 * equal information.
 * 
 * 	@specfield adjacency-list:list<Vertex>	// The vertices included in the graph
 * 	
 * 	Abstract Invariant
 * 		Any vertex in the graph cannot be null
 * 		Vertices are unique
 * 		No identical edges (same end-points and cost)
 */

public class Graph<T0, T1> {
	private ArrayList<Vertex<T0, T1>> graph;
	private HashSet<Vertex<T0, T1>> setOfNode;
	private boolean checkRepOn = false;
	
	// Abstraction Function:
	// 	 a graph, g, such that
	//		Vertices in g are stored in ArrayList<Vertex> 
	//		Vertices in g are connected by edges which have costs and could be more than 
	//		one 
	//
	// Representation Invariant:
	//	Every vertex in the graph cannot be null
	// 	No same vertices in ArrayList<Vertex>
	//	No identical edges(same end-points and cost)
	// 
	
	private void checkRep() {
		if (checkRepOn == true) {
			for (int i = 0; i < graph.size(); i++) {
				assert (graph.get(i) != null) : "node cannot be null";
				Set<Edge<T0, T1>> setEdge1 = new HashSet<Edge<T0, T1>>(graph.get(i).getInEdge());
				assert (setEdge1.size() == graph.get(i).getInEdge().size()) : "No identical in the graph";
				Set<Edge<T0, T1>> setEdge2 = new HashSet<Edge<T0, T1>>(graph.get(i).getOutEdge());
				assert (setEdge2.size() == graph.get(i).getOutEdge().size()) : "No identical in the graph";
			}
			Set<Vertex<T0, T1>> setVertex = new HashSet<Vertex<T0, T1>>(graph);
			assert (setVertex.size() == graph.size()) : "No same nodes in the graph";
		}
	}
	
	/**	Creates a new empty Graph with no vertex and no edge 
	 * @effects a new Graph with no vertex and no edge
	 * @modifies this
	 */
	public Graph(){
		graph = new ArrayList<Vertex<T0, T1>>();
		setOfNode = new HashSet<Vertex<T0, T1>>();
		checkRep();
	}
	
	/**	Add an isolated vertex into the Graph
	 * @requires v is not equal to any vertex in the graph
	 * @param v The vertex to be added in the Graph 
	 * @returns true if the vertex is added successfully, otherwise false
	 * @effects new vertex is added in the Graph 
	 * @modifies this
	 */
	public boolean addVertex(Vertex<T0, T1> v){
		checkRep();
		if (setOfNode.contains(v)) {
			return false;
		} else {
			checkRep();
			setOfNode.add(v);
			return graph.add(v);
		}
	}
	
	/** Return a specified Vertex in the graph
	 * @requires the graph has such a Vertex with information == c
	 * @param c The information of the Vertex
	 * @returns a Vertex with the same information of c
	 * 
	 */
	public Vertex<T0, T1> getVertex(T0 c) {
		return graph.get(graph.indexOf(new Vertex<T0, T1>(c)));
	}
	
	/** Remove a vertex from the Graph
	 * @requires v is in the graph 
	 * @param v The vertex to be removed from the Graph
	 * @returns true if the vertex is removed successfully, otherwise false
	 * @effects one vertex in the Graph is removed 
	 * @modifies this
	 */
	public boolean removeVertex(Vertex<T0, T1> v){
		checkRep();
		// Delete all outward edges recorded in other adjacent vertices
		for (int i = 0; i < v.getInEdge().size(); i++) {
			Edge<T0, T1> edgeToBeRemoved = v.getInEdge().get(i);
			Vertex<T0, T1> vStart = edgeToBeRemoved.getStartVertex();
			vStart.removeEdge(edgeToBeRemoved);
		}
		// Delete all inward edges recorded in other adjacent vertices
		for (int i = 0; i < v.getOutEdge().size(); i++) {
			Edge<T0, T1> edgeToBeRemoved = v.getOutEdge().get(i);
			Vertex<T0, T1> vEnd = edgeToBeRemoved.getEndVertex();
			vEnd.removeEdge(edgeToBeRemoved);
		}
		setOfNode.remove(v);
		return graph.remove(v);
	}
	
	/**	Return the number of vertices in the Graph
	 * @returns the number of vertices in the Graph 
	 */
	public int getSize(){
		return graph.size();
	}
	
	/** Return whether a vertex is in the Graph
	 * @param v The vertex to be checked
	 * @returns true if the vertex is in the Graph, otherwise false
	 */
	public boolean containsVertex(Vertex<T0, T1> v){
		return setOfNode.contains(v);
	}
	
	/** Connect two vertices with a directed edge. i.e. Create a directed edge from v1 to v2. 
	 * @requires v1 is in the Graph. The edge from v1 to v2 is not equal to any other edges 
	 * from v1 to v2.
	 * @param v1 The vertex started from
	 * @param v2 The vertex ended with
	 * @param c The cost of the directed edge
	 * @returns true if such a edge was successfully created 
	 * @effects If v2 is not in the graph, a new vertex and a new directed edge from v1 to v2 is 
	 * added into the Graph. If v2 is in the graph, a new directed edge from v1 to v2 is added to 
	 * the graph
	 * @modifies this
	 */
	public boolean connectFromTo(Vertex<T0, T1> v1, Vertex<T0, T1> v2, T1 c){
		checkRep();
		if (this.containsVertex(v2)) {
			//Create an edge from v1 to v2; Record the edge in v2
			if (v1.connectVertexTo(v2, c) && v2.connectVertexFrom(v1, c)) {
				checkRep();
				return true;
			} else {
				return false;
			}
		} else {
			//Create an edge from v1 to v2; Add v2 to the graph; Record the edge in v2
			if (v1.connectVertexTo(v2, c) && graph.add(v2)&& v2.connectVertexFrom(v1, c)) {
				setOfNode.add(v2);
				checkRep();
				return true;
			} else {
				return false;
			}
		}
	}
	
	/** Connect two vertices with an undirected edge. i.e. Create an undirected edge from v1 to v2. 
	 * @requires v1, v2 both are in the Graph. The edge from v1 to v2 is not equal to any other edges 
	 * from v1 to v2.
	 * @param v1 One of the end-vertices
	 * @param v2 One of the end-vertices
	 * @param c The cost of the undirected edge
	 * @returns true if such a edge was successfully created 
	 * @effects a new undirected edge from v1 to v2 is added to the graph
	 * @modifies this
	 */
	public boolean connectBothSide(Vertex<T0, T1> v1, Vertex<T0, T1> v2, T1 c){
		checkRep();
		if (v1.connectVertexTo(v2, c) && v2.connectVertexFrom(v1, c) && 
				v2.connectVertexTo(v1, c) && v1.connectVertexFrom(v2, c)){
			checkRep();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the list of vertices that connect to a specified vertex
	 * @requires v exists in the graph
	 * @param v The specified vertex to be connect
	 * @returns a list of vertices connect to a specified vertex 
	 */
	public ArrayList<Vertex<T0, T1>> getInwardVertex(Vertex<T0, T1> v){
		ArrayList<Vertex<T0, T1>> lst = new ArrayList<Vertex<T0, T1>>();
		for (int i=0; i < v.getInEdge().size(); i++) {
			if (!lst.contains(v.getInEdge().get(i).getStartVertex())){
				lst.add(v.getInEdge().get(i).getStartVertex());
			}
		}
		return lst;
	}
	
	/**
	 * Returns the list of vertices that connect from a specified vertex
	 * @requires v exists in the graph
	 * @param v The specified vertex from which other vertices connect
	 * @returns a list of vertices connect from a specified vertex 
	 */
	public ArrayList<Vertex<T0, T1>> getOutwardVertex(Vertex<T0, T1> v){
		ArrayList<Vertex<T0, T1>> lst = new ArrayList<Vertex<T0, T1>>();
		for (int i=0; i < v.getOutEdge().size(); i++) {
			if (!lst.contains(v.getOutEdge().get(i).getEndVertex())){
				lst.add(v.getOutEdge().get(i).getEndVertex());
			}
		}
		return lst;
	}	
	
	/** 
	 * Sort the vertices in the graph by the information of the vertex and return the sorted list
	 * @returns a sorted list of vertices which sorted by the information of the vertex
	 * @effects	vertices in this graph will be sorted by their information after this method
	 * @modifies this
	 */
	public ArrayList<Vertex<T0, T1>> sortVertices() {
		checkRep();
		Collections.sort(graph);
		checkRep();
		return graph;
	}
}