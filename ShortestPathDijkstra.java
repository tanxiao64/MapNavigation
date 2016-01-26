package hw7;

import hw5.Edge;
import hw5.Graph;
import hw5.Vertex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class ShortestPathDijkstra<T> {
	  /**
	   * Find the shortest path from start node to destination node by dijkstra Algorithm. 
	   * Return the shortest path in a linked list.
	   * @requires Two nodes, which are in the graph
	   * @param graph The graph in which to find the shortest path
	   * @param staVertex The start node of the shortest path
	   * @param desVertex The destination node of the shortest path
	   * @returns A linked list of edges which consists the shortest path. If no path is found, 
	   * return null.
	   */

	  public LinkedList<Edge<T, EdgeCost>> shortestPathDijkstra(Graph<T, EdgeCost> graph, 
			  Vertex<T, EdgeCost> staVertex, Vertex<T, EdgeCost> desVertex){
		  
		  Comparator<LinkedList<Edge<T, EdgeCost>>> comparator = new PriorityQueueComparator<T>();
		  PriorityQueue<LinkedList<Edge<T, EdgeCost>>> active = new PriorityQueue<>(11, comparator);
		  HashSet<Vertex<T, EdgeCost>> finished = new HashSet<>();
		  LinkedList<Edge<T, EdgeCost>> startList = new LinkedList<>();
		  startList.add(new Edge<T, EdgeCost>(staVertex, staVertex, new EdgeCost(0)));
		  active.add(startList);
		  
		  LinkedList<Edge<T, EdgeCost>> minPath;
		  Vertex<T, EdgeCost> minDest;
		  while (active.size()!=0) {
			  minPath = active.remove();
			  minDest = minPath.get(minPath.size()-1).getEndVertex();
			  if (minDest.equals(desVertex)){
				  minPath.removeFirst();
				  return new LinkedList<Edge<T, EdgeCost>>(minPath);
			  }
			  if (finished.contains(minDest)) {
				  continue;
			  }
			  HashSet<Vertex<T, EdgeCost>> childSet = new HashSet<>(); // To handle vertices with more than one edges
			  ArrayList<Vertex<T, EdgeCost>> minChildLst = new ArrayList<>();
			  ArrayList<Double> minCostLst = new ArrayList<>();
			  for (int i = 0; i < minDest.getOutEdge().size(); i++){
				  Vertex<T, EdgeCost> child = minDest.getOutEdge().get(i).getEndVertex();
				  if (childSet.contains(child)){
					  int minChildPos = minChildLst.indexOf(child);
					  double minCost = minCostLst.get(minChildPos);
					  if (minCost > minDest.getOutEdge().get(i).getEdgeCost().getCostValue()) {
						  minCostLst.set(minChildPos, minCost);
					  }
				  }else{
					  childSet.add(child);
					  minChildLst.add(child);
					  minCostLst.add(minDest.getOutEdge().get(i).getEdgeCost().getCostValue());
				  } 
				  if (!finished.contains(child)) {
					  LinkedList<Edge<T, EdgeCost>> newPath = new LinkedList<>(minPath);
					  newPath.add((minDest.getOutEdge().get(i)));
					  active.add(newPath);
				  }
			  }
			  finished.add(minDest);
		  }
		  return null;		  
	  }
	  
	  /**
	   * This class is the comparator for the priority queue.
	   * The elements in the priority queue will be compared by the total cost, which is the sum of 
	   * all costs for edges in the element(list)
	   *
	   */
	@SuppressWarnings("hiding")
	public class PriorityQueueComparator<T> implements Comparator<LinkedList<Edge<T, EdgeCost>>> 
	  {
		  @Override
		  public int compare(LinkedList<Edge<T, EdgeCost>> list1, 
				  LinkedList<Edge<T, EdgeCost>> list2) {
			  double cost1 = 0;
			  double cost2 = 0;
			  for (int i = 0; i < list1.size(); i++) {
				  cost1 = cost1 + list1.get(i).getEdgeCost().getCostValue();
			  }
			  for (int i = 0; i < list2.size(); i++) {
				  cost2 = cost2 + list2.get(i).getEdgeCost().getCostValue();
			  }
			  if (cost1 > cost2) {
				  return 1;
			  } 
			  if (cost1 < cost2){
				  return -1;
			  }
			  return 0;
		  }
		  
	  }
}

