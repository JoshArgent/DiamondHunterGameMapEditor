package application;

/**
 * Class which represents a x and y coordinate pair
 * Also provides functionality to load a string coordinate. eg. 12,21
 * @author Josh
 *
 */
public class Coordinate
{
	
	private int x;
	private int y;
	
	public Coordinate(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	public Coordinate(String coordinate)
	{
		String t[] = coordinate.split(",");
		if(t.length == 2)
		{
			setX(Integer.valueOf(t[0]));
			setY(Integer.valueOf(t[1]));
		}
		else
			throw new RuntimeException("Invalid coordinate string");
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	
	@Override
	public String toString()
	{
		return x + "," + y;
	}
	
}
