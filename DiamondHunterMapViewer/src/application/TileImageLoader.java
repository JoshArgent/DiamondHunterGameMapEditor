package application;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * Class to handle the loading of map tile images
 * @author psyja2
 *
 */
public class TileImageLoader {

	private static TileImageLoader tileImageLoader;
	public static int TILESIZE = 16;
	private int numTilesAcross;
	private Image tileSet;
	
	/**
	 * Get the singleton instance of the TileImageLoader
	 * @return
	 */
	public static TileImageLoader getInstance()
	{
		if(tileImageLoader == null)
		{
			tileImageLoader = new TileImageLoader();
		}
		return tileImageLoader;
	}
	
	private TileImageLoader()
	{
		loadTiles("/testtileset.gif");
	}
	
	private void loadTiles(String s)
	{
		tileSet = new Image(getClass().getResourceAsStream(s));
		numTilesAcross = (int) (tileSet.getWidth() / TILESIZE);
	}
	
	/**
	 * Gets the bounds for a particular map tile within the tileset
	 * @param type the type of tile wanted
	 * @return the bounds of the tile
	 */
	public Rectangle2D getTileViewportBounds(int type)
	{
		int r = type / numTilesAcross;
		int c = type % numTilesAcross;
		int y = (r == 0) ? 0 : TILESIZE;
		return new Rectangle2D(c * TILESIZE, y, TILESIZE, TILESIZE);
	}
	
	/**
	 * @return the whole tileset image
	 */
	public Image getTileset()
	{
		return tileSet;
	}
	
}
