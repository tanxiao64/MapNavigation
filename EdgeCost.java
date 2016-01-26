package hw7;
/**
 * 
 * This class represents the cost of edges in HW7. Specifically, the cost is a fraction number which 
 * numerator equals 1, i.e, cost = 1/denominator. The cost from one node to itself is 0.
 *
 * @specfield: denominator: denom // the denominator of the cost 
 * @specfield: value: value // the value of the cost
 * 
 * Abstract Invariant 
 * 	No cost value is greater than 1
 * 	The cost form one node to itself is 0;
 * 	Denominator should be positive integer, or zero for zero-cost
 *  Numerator should always equals 1
 *  
 */
public class EdgeCost implements Comparable<EdgeCost>{
	private int denom = -1;
	private double value;
	
	// Abstract Function
	//	a fraction number such that
	//		denominator = denom
	//		numerator = 1
	// 		value of the fraction number = value
	//
	//	Representation Invariant
	//	 cost should be non-negative
	
	/**
	 * Constructor 
	 * @requires d is a non-negative integer
	 * @param d zero, or the denominator of the new cost.
	 * @modifies this
	 * @effects create a fractional cost or a zero cost
	 */	
	
	public EdgeCost(int d){
		denom = d;
		if (d !=0 ){
			value = 1.0/d;
		}
	}
	
	/**
	 * Constructor
	 * @requires v is non-negative
	 * @param v The value of the cost
	 * @modifies this
	 * @effects create a cost with a double value
	 */
	public EdgeCost(double v){
		value = v;
	}
	
	/**
	 * Return the denominator of the cost, or zero for zero-cost
	 * @return the denominator of the cost, or zero for zero-cost
	 */
	public int getCostDenom(){
		return denom;
	}
	
	/**
	 * Return the value of the cost
	 * @return the value of the cost in double
	 */
	public double getCostValue(){
		if (denom != 0){
			return value;
		} else {
			return 0;
		}
	}
	
	/**
	 * Set the denominator of the cost
	 * @requires d is a non-negative integer
	 * @param d the denominator
	 * @modifies this
	 * @effects modifies the denominator of the cost
	 */
	public void setCostDenom(int d){
		denom = d;
		if (d != 0 ){
			value = 1.0/d;
		}
	}
	
	@Override
	public int compareTo(EdgeCost cost){
		if (this.getCostValue() > cost.getCostValue()) { return 1;}
		if (this.getCostValue() < cost.getCostValue()) { return -1;}
		return 0;
	}
	
	@Override
	public String toString(){
		if (denom !=0) {
			if (denom == -1) {
				return Double.toString(value);
			} else {
				return ("1/" + denom);
			}
		} else {
			return "0";
		}
	}
}
