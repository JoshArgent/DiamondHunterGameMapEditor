package application;

/**
 * An interface to be implemented by classes
 * which handle when a boat or axe is placed
 * @author Josh
 *
 */
public interface ItemPlaceHandler
{

	/**
	 * Fired when an axe is placed
	 * @param coord the coordinates of where it was placed
	 */
	public void placeAxe(Coordinate coord);
	
	/**
	 * Fired when an boat is placed
	 * @param coord the coordinates of where it was placed
	 */
	public void placeBoat(Coordinate coord);
	
}
