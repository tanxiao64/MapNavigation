package hw8;

/**
 * This class represents a 2-D point with x and y 
 * 
 * @specfield x coordinate: x
 * @specfield y coordinate: y
 * 
 * Abstraction Invariant
 * 	The coordinate cannot be empty
 */
public class Point {
	private double x;
	private double y;
	
	// Abstract Function
	//	a 2-D point such that
	//		x coordinate = x
	//		y coordinate = y
	//
	//	Representation Invariant
	//	 x and y cannot be null
	
	/**
	 * The constructor to create a point with x and y
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @effects create a point with x and y
	 * @modifies this
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Return the x coordinate of the point
	 * @return the x coordinate of the point
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * Return the y coordinate of the point
	 * @return the y coordinate of the point
	 */
	public double getY(){
		return y;
	}
	
	
	@Override
	public int hashCode(){
		return (int)(x) * 7+ (int)(y) * 3;
	}
	
	@Override 
	public String toString(){
		return "(" + Double.toString(x) + "," + Double.toString(y) + ")";
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null) {
			return false;
		}
		if (!(o instanceof Point)) {
			return false;
		}
		Point p = (Point) o;
		return (p.getX() - this.x < 0.000001 && p.getY() - this.y < 0.000001);
	}

}
