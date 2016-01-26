package hw5;

import hw5.Edge;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class represents a vertex with its inward and outward edges. It also includes some
 * basic operations. This class will be supported by the class of Edge. This class has 
 * implements to Comparable, which can be used for sorting.
 * 
 * 	@specfield information: data // The information in this Vertex
 * 	@specfield inward-edges:ArrayList<Edge> // list of edges connect to this vertex
 * 	@specfield outward-edges:ArrayList<Edge> // list of edges connect from this vertex
 * 
 * Abstract Invariant
 * 	Vertex != null
 * 	Any edges from and to the vertex != null
 */
public class Vertex<T0,T1> implements Comparable<Vertex<T0,T1>>{
	private T0 data;
	private ArrayList<Edge<T0,T1>> inEdge;
	private ArrayList<Edge<T0,T1>> outEdge;
	private HashSet<Edge<T0,T1>> inEdgeSet;
	private HashSet<Edge<T0,T1>> outEdgeSet;
	private boolean checkRepOn = false;
	
	// Abstraction Function
	//	 a vertex v such that
	//		v.information = T0
	//		v.inwardEdges = inEdge
	//		v.outwardEdges = outEdge
	//
	// Representation Invariant
	//	No edge in inEdge or outEdge is null  
	
	private void checkRep() {
		if (checkRepOn == true) {
			for (int i = 0; i < inEdge.size(); i++){
				assert (inEdge.get(i) != null) : "edges cannot be null";
			}
			for (int i = 0; i < outEdge.size(); i++){
				assert (outEdge.get(i) != null) : "edges cannot be null";
			}
		}
	}
	
	/**
	 * Construct a new empty Vertex
	 * @effects construct a new empty Vertex
	 * @modifies this
	 */
	public Vertex() {
		inEdge = new ArrayList<Edge<T0,T1>>();
		inEdgeSet = new HashSet<Edge<T0, T1>>();
		outEdge = new ArrayList<Edge<T0, T1>>();
		outEdgeSet = new HashSet<Edge<T0, T1>>();
		checkRep();
	}
	
	/**
	 * Construct a new Vertex with information
	 * @param T0 The information to be added on the Vertex
	 * @effects construct a Vertex with information
	 * @modifies this
	 */
	public Vertex(T0 t0){
		data = t0;
		inEdge = new ArrayList<Edge<T0, T1>>(); // List of Vertices connected to this
		inEdgeSet = new HashSet<Edge<T0, T1>>();
		outEdge = new ArrayList<Edge<T0, T1>>();	// List of vertices connected from this
		outEdgeSet = new HashSet<Edge<T0, T1>>();
		checkRep();
	}
	
	/** 
	 * Set the information to this Vertex 
	 * @param T0 The information to be added
	 * @modifies this
	 * @effects Set the information in this Vertex
	 */
	public void setInfo(T0 T0){
		data = T0;
	}
	
	/**
	 * Return the information of this Vertex
	 * @requires information of this Vertex != null
	 * @returns The information of this Vertex
	 */
	public T0 getInfo(){
		return this.data;
	}
	
	/**
	 * Remove a directed edge from this vertex
	 * @requires the edge exists in this Vertex
	 * @param e	The edge to be removed from the Vertex
	 * @returns	true if the edge was removed successfully, otherwise false
	 * @effects Delete a directed edge connected to this Vertex
	 * @modifies this
	 */
	public boolean removeEdge(Edge<T0, T1> e) {
		checkRep();
		// The edge started from this Vertex
		if (e.getStartVertex().equals(this)){
			outEdgeSet.remove(e);
			return outEdge.remove(e);
		}
		// The edge ended with this Vertex
		if (e.getEndVertex().equals(this)) {
			inEdgeSet.remove(e);
			return inEdge.remove(e);
		}
		checkRep();
		return false; // the edge is not connected with this Vertex
	}
	
	/**
	 * Return all edges connected to this Vertex
	 * @returns A ArrayList contains all edges connected to this Vertex
	 */
	public ArrayList<Edge<T0, T1>> getInEdge() {
		return inEdge;
	}
	
	/**
	 * Return all edges connected from this Vertex
	 * @returns A ArrayList contains all edges connected from this Vertex
	 */
	public ArrayList<Edge<T0, T1>> getOutEdge() {
		return outEdge;
	}
	
	
	/** Connect this to a specified vertex(add an Edge to the inward edge list)
	 * @requires The new edge is not equal to any existed edges(same cost, same end-points)
	 * @param v The vertex connected from this
	 * @param c	Cost on the new edge from this to the specified vertex
	 * @effects Add an Edge to the inward edge list
	 * @returns True if the new edge is added successfully, otherwise false
	 * @modifies this
	 */
	public boolean connectVertexFrom(Vertex<T0, T1> v, T1 c){
		checkRep();
		Edge<T0, T1> e = new Edge<T0, T1>(v, this, c);
		if (inEdgeSet.contains(e)) {
			return false;
		} else{
			inEdge.add(e);
			inEdgeSet.add(e);
			checkRep();
			return true;
		}
	}
	
	/** Connect a specified vertex to this(add an Edge to the outward edge list)
	 * @requires The new edge is not equal to any existed edges(same cost, same end-points)
	 * @param v The vertex connected to this
	 * @param c	Cost on the new edge from the specified vertex to this
	 * @effects Add an Edge to the outward edge list
	 * @returns True if the new edge is added successfully, otherwise false
	 * @modifies this
	 */
	public boolean connectVertexTo(Vertex<T0, T1> v, T1 c){
		checkRep();
		Edge<T0,T1> e = new Edge<T0, T1>(this, v, c);
		if (outEdgeSet.contains(e)) {
			return false;
		} else{
			outEdge.add(e);
			outEdgeSet.add(e);
			checkRep();
			return true;
		}
	}
	
	
	/**Return the information of this Vertex to present the Vertex
	 * @returns a string to present the Vertex 
	 */
	@Override
	public String toString(){
		return data.toString();
	}
	
	@Override
	public int compareTo(Vertex<T0, T1> v) {
		String s1 = (String)this.data;
		String s2 = (String)v.getInfo();
		return s1.toString().compareTo(s2.toString());
	}
	
	@Override
	public boolean equals(Object v){
		if (v == null) {
			return false;
		}
		if (!(v instanceof Vertex<?, ?>)){
			return false;
		}
		Vertex<?, ?> vertex = (Vertex<?, ?>) v;
		return this.toString().equals(vertex.toString());
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
