package hw8;

/**
 * This class represents the name for a building. In other word, the class contains a short name and a 
 * long name for the building.
 * 
 * @specfield short name : shortName // the short name of the building
 * @specfield long name : longName // the long name of the building
 *
 * Abstract Invariant
 * 	The names of the building cannot be empty
 * 
 */

public class BuildingInfo {
	private String shortName;
	private String longName;
	
	// Abstract Function
	//	a combination of building names such that
	//		short name = shortName
	//		long name = longName
	//
	//	Representation Invariant
	//	 shortName and longName cannot be null
	
	
	/**
	 * The constructor to create a BuildingInfo
	 * @param shortName the short name of the building
	 * @param longName the long name of the building
	 * @effects create a BuildingInfo with short name and long name
	 * @modifies this
	 */
	public BuildingInfo(String shortName, String longName){
		this.shortName = shortName;
		this.longName = longName;
	}
	
	/**
	 * Return the short name of the building
	 * @return the short name of the building
	 */
	public String getShortName(){
		return shortName;
	}
	
	/**
	 * Return the long name of the building
	 * @return the long name of the building
	 */
	public String getLongName(){
		return longName;
	}
	
	@Override
	public int hashCode(){
		String s = this.shortName + this.longName;
		return s.hashCode();
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null) {
			return false;
		}
		if (!(o instanceof BuildingInfo)) {
			return false;
		}
		BuildingInfo b = (BuildingInfo) o;
		return (b.toString().equals(this.toString()));
	}
	
	@Override
	public String toString(){
		return (this.shortName + ", " + this.longName);
	}
}
