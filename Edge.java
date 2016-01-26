package hw5;

import hw5.Vertex;
import hw7.EdgeCost;

/**
 * This class includes an edge from one vertex to the other vertex and basic operations 
 * related to edge. This class will be supported by the class of Vertex. This class has 
 * implements to Comparable, which can be used for sorting.
 * 
 * 	@specfield start-point:Vertex // The vertex from which this edge starts
 * 	@specfield end-point:Vertex // The vertex to which this edge ends
 * 	@specfield cost: a generic type for cost // The cost of this edge
 * 
 * Abstract Invariant:
 * 	End-points of the edge cannot be null
 * 
 */
public class Edge<T0, T1> implements Comparable<Edge<T0, T1>> {
	private Vertex<T0, T1> vi;
	private Vertex<T0, T1> vj;
	private T1 cost;
	private boolean checkRepOn = false;
	
	// Abstraction Function
	//	An edge e such that
	//		e.startPoint = vi
	//		e.endPoint = vj
	//		e.cost = t1
	//
	//	Representation Function
	//		vi or vj cannot be null
	//
	
	
	private void checkRep() {
		if (checkRepOn == true) {
			assert (this.vi != null) : "end-points cannot be null";
			assert (this.vj != null) : "end-points cannot be null";
		}
	}
	
	/**
	 * Construct an Edge linked from vi to vj, with the cost of c
	 * @requires vi != null and vj != null
	 * @param vi The Vertex linked from
	 * @param vj The Vertex linked to
	 * @param c The cost of the edge
	 * @effects an new Edge from vi to vj with cost of c is created
	 * @modifies this
	 */
	public Edge(Vertex<T0, T1> vi, Vertex<T0, T1> vj, T1 c) {
		this.vi = vi;
		this.vj = vj;
		cost = c;
		checkRep();
	}
	
	/**
	 * Set the cost of this Edge
	 * @param c The new cost of the Edge 
	 * @effects Cost of this Edge is updated
	 * @modifies this
	 */
	public void setEdgeCost(T1 c){
		this.cost = c;
	}
	
	/**
	 * Get the cost of this Edge
	 * @returns The cost of this Edge
	 * 
	 */
	public T1 getEdgeCost(){
		return this.cost;
	}
	
	/**
	 * Get the start point of this Edge
	 * @returns a Vertex from which this Edge started
	 */
	public Vertex<T0, T1> getStartVertex(){
		return vi;
	}
	
	/**
	 * Get the end point of this Edge
	 * @returns a Vertex with which this Edge ended
	 */
	public Vertex<T0, T1> getEndVertex(){
		return vj;
	}		
	
	/**Return a string to present the Edge
	 * @returns a string to present the Edge in the format: vi -> vj (cost: x)
	 */
	@Override
	public String toString(){
		return (this.getStartVertex() + " -> " + this.getEndVertex() + " (Cost: " +
				this.cost + ")");
	}
	
	@Override
	public int compareTo(Edge<T0, T1> e){
		T1 s1 = this.cost;
		T1 s2 = e.getEdgeCost();
		if (s1 instanceof EdgeCost) {
			EdgeCost c1 = (EdgeCost) this.cost;
			EdgeCost c2 = (EdgeCost) this.cost;
			return c1.compareTo(c2);
		}
		return s1.toString().compareTo(s2.toString());
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Edge<?, ?>)) {
			return false;
		}
		Edge<?, ?> e = (Edge<?, ?>) o;
		if (e.toString().equals(this.toString()) && e.getStartVertex().equals(this.getStartVertex())
				&& e.getEndVertex().equals(this.getEndVertex())) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
