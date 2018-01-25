package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


/**
 * Singleton class for loading and processing map file data
 * @author Josh
 *
 */
public class MapLoader
{

	private static MapLoader mapLoader;
	private int[][] data;
	private Coordinate axe;
	private Coordinate boat;
	private int numRows;
	private int numCols;
	private boolean mapLoaded = false;
	
	/**
	 * Get the singleton instance of the MapLoader
	 * @return
	 */
	public static MapLoader getInstance()
	{
		if(mapLoader == null)
		{
			mapLoader = new MapLoader();
		}
		return mapLoader;
	}
	
	private MapLoader()
	{
		
	}
	
	/**
	 * Loads a map into memory from file
	 * @param file the map file to load
	 * @throws IOException 
	 */
	public void loadMap(File file) throws IOException
	{
		byte[] encoded = Files.readAllBytes(file.toPath());
		loadMap(new String(encoded, StandardCharsets.UTF_8));
	}
	
	/**
	 * Load a map into memory from a string of map data
	 * @param data the raw map data
	 */
	public void loadMap(String data)
	{
		String lines[] = data.split("\n");
		numCols = Integer.parseInt(lines[0]);
		numRows = Integer.parseInt(lines[1]);
		this.data = new int[numCols][numRows];
		String delims = "\\s+";
		int itemNum = 0;
		for(int row = 0; row < lines.length - 2; row++)
		{
			String line = lines[row + 2];
			if(line.contains(","))
			{
				if(itemNum == 0)
					axe = new Coordinate(line);
				else
					boat = new Coordinate(line);
				itemNum++;
			}
			else
			{
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++)
				{
					this.data[col][row] = Integer.parseInt(tokens[col]);
				}
			}
		}
		mapLoaded = true;
	}
	
	/**
	 * Saves the map data to file, will overwrite if it already exists
	 * @param file
	 * @throws IOException 
	 */
	public void saveMap(File file) throws IOException
	{
		FileWriter fw = new FileWriter(file);
		fw.write(numCols + "\n");
		fw.write(numRows + "\n");
		for(int row = 0; row < numRows; row++)
		{
			String line = "";
			for(int col = 0; col < numCols; col++)
			{
				line += this.data[col][row] + " ";
			}
			line = line.substring(0, line.length() - 1);
			line += "\n";
			fw.write(line);
		}
		if(axe != null)
			fw.write(axe.toString() + "\n");
		if(boat != null)
			fw.write(boat.toString() + "\n");
		fw.close();
	}
	
	/**
	 * Gets the map value at a particular row and column
	 * @param row
	 * @param col
	 * @return
	 */
	public int getMapValue(int col, int row)
	{
		return this.data[col][row];
	}
	
	/**
	 * @return The number of rows in the map
	 */
	public int getRows()
	{
		return numRows;
	}
	
	/**
	 * @return The number of columns in the map
	 */
	public int getCols()
	{
		return numCols;
	}
	
	/**
	 * @return Whether a map is loaded or not
	 */
	public boolean isMapLoaded()
	{
		return mapLoaded;
	}
	
	/**
	 * @return the coordinates of the axe
	 */
	public Coordinate getAxe()
	{
		return axe;
	}

	/**
	 * Set the coordinate of the axe
	 * @param axe
	 */
	public void setAxe(Coordinate axe)
	{
		this.axe = axe;
	}

	/**
	 * @return the coordinates of the boat
	 */
	public Coordinate getBoat()
	{
		return boat;
	}

	/**
	 * Set the coordinate of the boat
	 * @param boat
	 */
	public void setBoat(Coordinate boat)
	{
		this.boat = boat;
	}
	
	/**
	 * Determine if a space is solid (trees or water) or if it is
	 * a valid location for an item to be placed
	 * @param coord
	 * @return
	 */
	public boolean isSolid(Coordinate coord)
	{
		int tile = getMapValue(coord.getX(), coord.getY());
		if(tile == 1 || tile == 2 || tile == 3)
			return false;
		return true;
	}
	
}
