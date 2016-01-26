package hw7.test;

import static org.junit.Assert.assertEquals;
import hw5.Graph;
import hw7.EdgeCost;
import hw7.MarvelPaths2;

import org.junit.Test;

public class TestBuildGraph {

		@Test
		public void smallTest(){
			Graph<String, EdgeCost> g = new Graph<String, EdgeCost>();
			MarvelPaths2 testBuildGraph = new MarvelPaths2();
			testBuildGraph.buildGraph("staffSuperheroes.tsv", g);
			assertEquals(g.getSize(), 4);
		}
	}


