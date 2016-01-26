package hw8.test;
import static org.junit.Assert.*;
import hw8.BuildingInfo;

import org.junit.Test;

public class TestBuildingInfo {
	
	@Test
	public void testConstructor() {
		BuildingInfo p = new BuildingInfo("A", "AAA");
		assertTrue(p != null);
	}
	
	@Test
	public void testGet() {
		BuildingInfo p = new BuildingInfo("A", "AAA");
		assertEquals("A", p.getShortName());
		assertEquals("AAA", p.getLongName());
	}
	
	@Test
	public void testEquals() {
		BuildingInfo p1 = new BuildingInfo("A", "AAA");
		BuildingInfo p2 = new BuildingInfo("A", "AAA");
		assertTrue(p1.equals(p2));
	}
}

