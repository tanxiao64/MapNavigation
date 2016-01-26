package hw8.test;
import static org.junit.Assert.*;
import hw8.Point;

import org.junit.Test;

public class TestPoint {
	
	@Test
	public void testConstructor() {
		Point p = new Point(1, 1);
		assertTrue(p != null);
	}
	
	@Test
	public void testGet() {
		Point p = new Point(1, 2);
		assertTrue(p.getX() - 1 < 0.000001);
		assertTrue(p.getY() - 2 < 0.000001);
	}
	
	@Test
	public void testEquals() {
		Point p1 = new Point(1, 2);
		Point p2 = new Point(1, 2);
		assertTrue(p1.equals(p2));
	}
}
