package hw8;

import hw5.Edge;
import hw5.Graph;
import hw5.Vertex;
import hw7.EdgeCost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class perform the function of interacting with users. In other word, the class contains the View
 * and the Controller. More specifically, the class will print the instructions to guide the users to 
 * input the commands and respond to the commands in the console.
 * 
 */
public class ViewController {
	
	// This class does not contain RI and AF
	
	/**
	 * The method will print instructions on the console and respond to the commands entered by users in 
	 * the console.
	 * @requires the graph has already been built
	 * @param graph The graph to be executed
	 * @param mapBuildingsToPoints Map from buildings to points
	 * @param mapShortToLongName Map from short name of buildings to long name of buildings
	 */
	public static void handleData(Graph<Point, EdgeCost> graph, 
			HashMap<BuildingInfo, Point> mapBuildingsToPoints, 
			HashMap<String, String> mapShortToLongName){
		System.out.println("Menu:");
		System.out.println("\t" + "r to find a route");
		System.out.println("\t" + "b to see a list of all buildings");
		System.out.println("\t" + "q to quit");
		System.out.println();
		System.out.print("Enter an option ('m' to see the menu): ");
		
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String s = bufferRead.readLine();
		    while (s != null) {
		    	if (s.startsWith("#")) {
		    		System.out.println(s);
		    		s = bufferRead.readLine();
		    		continue;
		    	}
		    	if (s.trim().isEmpty()){
		    		System.out.println();
		    		s = bufferRead.readLine();
		    		continue;
		    	}
		    	
		    	if (s.equals("m")) {
		    		System.out.println("Menu:");
		    		System.out.println("\t" + "r to find a route");
		    		System.out.println("\t" + "b to see a list of all buildings");
		    		System.out.println("\t" + "q to quit");
		    		System.out.println();
		    		System.out.print("Enter an option ('m' to see the menu): " );
		    		s = bufferRead.readLine();
		    		continue;
		    	}
		    		
		    	if (s.equals("r")) {
		    		System.out.print("Abbreviated name of starting building: ");
		    		boolean fristShortNameCanBeFound = false;
		    		boolean secondShortNameCanBeFound = false;
		    		s = bufferRead.readLine();
		    		String s1, s2;
		    		Vertex<Point, EdgeCost> vStart = new Vertex<>();
		    		String longNameStart = null;
		    		s1 = s;
		    		if (mapShortToLongName.containsKey(s)) {
		    			longNameStart = mapShortToLongName.get(s);
		    			BuildingInfo bInfoStart = new BuildingInfo(s, longNameStart);
		    			Point pStart = mapBuildingsToPoints.get(bInfoStart);
		    			vStart = graph.getVertex(pStart);
		    			fristShortNameCanBeFound = true;
		    		} 
		    		
		    		System.out.print("Abbreviated name of ending building: ");
		    		s = bufferRead.readLine();
		    		Vertex<Point, EdgeCost> vEnd = new Vertex<>();
		    		String longNameEnd = null;
		    		s2 = s;
		    		if (mapShortToLongName.containsKey(s)) {
		    			longNameEnd = mapShortToLongName.get(s);
		    			BuildingInfo bInfoEnd = new BuildingInfo(s, longNameEnd);
		    			Point pEnd = mapBuildingsToPoints.get(bInfoEnd);
		    			vEnd = graph.getVertex(pEnd);
		    			secondShortNameCanBeFound = true;
		    		}
		    		if (fristShortNameCanBeFound == false) {
		    			System.out.println("Unknown building: " + s1);
		    		}
		    		if (secondShortNameCanBeFound == false) {
		    			System.out.println("Unknown building: " + s2);
		    		}
		    		
		    			
		    		if (fristShortNameCanBeFound && secondShortNameCanBeFound){
		    			LinkedList<Edge<Point, EdgeCost>> paths = new LinkedList<>();
		    			paths = CampusPaths.shortestPathDijkstra(graph, vStart, vEnd);
		    			printPath(paths, longNameStart, longNameEnd);
		    		}
		    			
		    		System.out.println();
		    		System.out.print("Enter an option ('m' to see the menu): " );
		    		s = bufferRead.readLine();
		    		continue;
		    	}
		    		
		    	if (s.equals("b")) {
		    		ArrayList<String> lstShortName = new ArrayList<>();
		    		for (String key : mapShortToLongName.keySet()) {
		    			lstShortName.add(key);
		    		}
		    		Collections.sort(lstShortName);
		    		for (int i = 0; i < lstShortName.size(); i++) {
		    			System.out.println("\t" + lstShortName.get(i) + ": " 
		    					+ mapShortToLongName.get(lstShortName.get(i)));
		    		}
		    		System.out.println();
		    		System.out.print("Enter an option ('m' to see the menu): " );
		    		s = bufferRead.readLine();
		    		continue;
		    	}
		    	
		    	if (s.equals("q")) {
		    			return;
		    	}
		  
		    	System.out.println("Unknown option");
	    		System.out.println();
	    		System.out.print("Enter an option ('m' to see the menu): " );
		    	s = bufferRead.readLine();	    	
		    }
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * The method will print the paths with directions and distances from one building to the other. 
	 * And then print the total distance in the last line.
	 * 
	 * @param paths The paths to be printed
	 * @param longNameStart The long name of the start building
	 * @param longNameEnd The long name of the end building
	 */
	public static void printPath(LinkedList<Edge<Point, EdgeCost>> paths,
				String longNameStart, String longNameEnd){
		
		System.out.println("Path from " + longNameStart + " to " + longNameEnd + ":");
		double totalDist = 0;
		for (int i = 0; i < paths.size(); i++){
			double xEnd = paths.get(i).getEndVertex().getInfo().getX();
			double yEnd = paths.get(i).getEndVertex().getInfo().getY();
			double xStart = paths.get(i).getStartVertex().getInfo().getX();
			double yStart = paths.get(i).getStartVertex().getInfo().getY();
			double theta = Math.atan2(yEnd - yStart, xEnd - xStart);
			String direction = null;
			if (theta <= -7.0 * Math.PI /8.0 ) {
				direction = "W";
			}
			if (theta > -7.0 * Math.PI /8.0 && theta < -5.0 * Math.PI /8.0) {
				direction = "NW";
			}
			if (theta >= -5.0 * Math.PI /8.0 && theta <= -3.0 * Math.PI /8.0) {
				direction = "N";
			}
			if (theta > -3.0 * Math.PI /8.0 && theta < -1.0 * Math.PI /8.0) {
				direction = "NE";
			}
			if (theta >= -1.0 * Math.PI /8.0 && theta <= 1.0 * Math.PI /8.0) {
				direction = "E";
			}
			if (theta > 1.0 * Math.PI /8.0 && theta < 3.0 * Math.PI /8.0) {
				direction = "SE";
			}
			if (theta >= 3.0 * Math.PI /8.0 && theta <= 5.0 * Math.PI /8.0) {
				direction = "S";
			}
			if (theta > 5.0 * Math.PI /8.0 && theta < 7.0 * Math.PI /8.0) {
				direction = "SW";
			}
			if (theta >= 7.0 * Math.PI /8.0) {
				direction = "W";
			}
			double distance = paths.get(i).getEdgeCost().getCostValue();
			totalDist += distance;
			System.out.println("\t" + "Walk "+ 
					String.format("%.0f", distance) + " feet " + direction + 
					" to (" + String.format("%.0f", xEnd) + ", " +
					String.format("%.0f", yEnd) +")");
		}
		System.out.println("Total distance: " + String.format("%.0f", totalDist) + " feet");
	}
}
