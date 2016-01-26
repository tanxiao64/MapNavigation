package hw8.test;
import static org.junit.Assert.*;

import java.util.HashMap;

import hw5.Graph;
import hw6.MarvelParser.MalformedDataException;
import hw7.EdgeCost;
import hw8.BuildingInfo;
import hw8.CampusPaser;
import hw8.Point;

import org.junit.Test;

public class TestCampusPaser {
	
	@Test
	public void testCampusPaser(){
		String filenameBuildings = "src/hw8/data/" + "myBuildings.dat";
		String filenamePaths = "src/hw8/data/" + "myPaths.dat";
		Graph<Point, EdgeCost> graph = new Graph<>();
		HashMap<BuildingInfo, Point> mapBuildingsToPoints = new HashMap<>();
		HashMap<String, String> mapShortToLongName = new HashMap<>();
		try {
			CampusPaser.parseData(filenameBuildings, filenamePaths, graph, mapBuildingsToPoints, mapShortToLongName);
		} catch (MalformedDataException e) {
			System.err.println(e.toString());
			e.printStackTrace(System.err);
		}
		assertEquals(4, graph.getSize());
		assertEquals(4, mapBuildingsToPoints.size());
		assertEquals(4, mapShortToLongName.size());
	}
}
