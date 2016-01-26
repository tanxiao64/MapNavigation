package hw8;

import hw5.Graph;
import hw5.Vertex;
import hw6.MarvelParser.MalformedDataException;
import hw7.EdgeCost;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/** 
 * This class will read the dataset of buildings and paths, and return a graph with buidlings as nodes 
 * andpaths as edges. It will also return maps which map the building to its coordinate and map the short
 * name to the long name.
 * 
 */
public class CampusPaser {

	// This class does not contain RI and AF

	  /**
	   * Reads the buildings and paths dataset.
	   * 
	   * @requires filenameBuildings and filenamePaths are valid file paths
	   * @param filenameBuildings the file of buildings that will be read
	   * @param filenamePaths the file of buildings that will be read
	   * @param	graph Build a graph with nodes of Point and edges of EdgeCost
	   * @param mapBuildingsToPoints Map the building to its coordinate
	   * @param mapShortToLongName Map the short name to the long name
	   * @modifies graph
	   * @effects fills mapBuildingsToPoints with a map from a building to its coordinate
	   * @effects fills mapShortToLongName with a map from the short name to the long name
	   * @effects fills nodes of Point and edges of EdgeCost into the graph
	   * @throws MalformedDataException if the file is not well-formed
	   */
	  public static void parseData(String filenameBuildings, String filenamePaths, 
			  Graph<Point, EdgeCost> graph, HashMap<BuildingInfo, Point> mapBuildingsToPoints, 
			  HashMap<String, String> mapShortToLongName) throws MalformedDataException {
	    BufferedReader reader = null;
	    
	    // Read building information
	    try {
	        reader = new BufferedReader(new FileReader(filenameBuildings));

	        String inputLine;
	        while ((inputLine = reader.readLine()) != null) {

	            // Ignore comment lines.
	            if (inputLine.startsWith("#")) {
	                continue;
	            }

	            String[] tokens = inputLine.split("\t");
	            if (tokens.length != 4) {
	                throw new MalformedDataException("Line should contain exactly 3 tabs: "
	                                                 + inputLine);
	            }

	            String shortName = tokens[0];
	            String longName = tokens[1];
	            String xCoord = tokens[2];
	            String yCoord = tokens[3];
	            Point p = new Point(Double.parseDouble(xCoord), Double.parseDouble(yCoord));
	            BuildingInfo bInfo = new BuildingInfo(shortName, longName);
	            // Add the keys into the maps
	            mapBuildingsToPoints.put(bInfo, p);
	            mapShortToLongName.put(shortName, longName);
	            
	            // Add the parsed data to the graph.
	            graph.addVertex(new Vertex<Point, EdgeCost>(p));
	            
	        }  
	    } catch (IOException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                System.err.println(e.toString());
	                e.printStackTrace(System.err);
	            }
	        }
	    }
	    
	    // Read paths information
	    reader = null;
	    try {
	        reader = new BufferedReader(new FileReader(filenamePaths));

	        String inputLine = reader.readLine();
	        while (inputLine != null) {

	            // Ignore comment lines.
	            if (inputLine.startsWith("#")) {
	                continue;
	            }
	            
	            if (!inputLine.startsWith("\t")){
	            	// Deal with start vertex
	            	String[] tokens = inputLine.split(",");
		            if (tokens.length != 2) {
		                throw new MalformedDataException("Line should contain exactly 1 comma: "
		                                                 + inputLine);
		            }
		            String xStart = tokens[0];
		            String yStart = tokens[1];
		            Point pStart = new Point(Double.parseDouble(xStart), Double.parseDouble(yStart));		        
		            Vertex<Point, EdgeCost> vStart = new Vertex<Point, EdgeCost>(pStart);
		            if (graph.containsVertex(vStart)) {
		            	vStart = graph.getVertex(pStart);
		            } else {
		            	graph.addVertex(vStart);
		            }
		            
		            inputLine = reader.readLine();	
		            
		            // Deal with end vertex
		            while (inputLine != null && inputLine.startsWith("\t")) {
		            	String[] tokens1 = inputLine.split(": ");
		            	String[] tokens2 = tokens1[0].split(",");
			            if (tokens1.length != 2 || tokens2.length != 2) {
			                throw new MalformedDataException("Line should contain exactly 1 comma and 1 colon: "
			                                                 + inputLine);
			            }
		            	String distance = tokens1[1];
		            	String xEnd = tokens2[0];
		            	String yEnd = tokens2[1];
		            	Point pEnd = new Point(Double.parseDouble(xEnd), Double.parseDouble(yEnd));
		            	Vertex<Point, EdgeCost> vEnd = new Vertex<Point, EdgeCost>(pEnd);
			            if (graph.containsVertex(vEnd)) {
			            	vEnd = graph.getVertex(pEnd);
			            } else {
			            	graph.addVertex(vEnd);
			            }
		            	
		            	// Add the end vertex and connect it to the start vertex
		            	graph.addVertex(vEnd);	
		            	graph.connectBothSide(vStart, vEnd, new EdgeCost(Double.parseDouble(distance)));
		            	inputLine = reader.readLine();
		            }
	            }
        
	        }  
	    } catch (IOException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                System.err.println(e.toString());
	                e.printStackTrace(System.err);
	            }
	        }
	    }
	  }
}
