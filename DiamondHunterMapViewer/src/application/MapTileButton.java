package application;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * A JavaFX button which displays a map tile
 * @author psyja2
 *
 */
public class MapTileButton extends BorderPane {

	private int tileType;
	public static int IMAGESIZE = 30;
	private Canvas imageView;
	private Coordinate coord;
	private boolean isAxe = false;
	private boolean isBoat = false;
	
	public MapTileButton(int tileType, Coordinate coord)
	{
		imageView = new Canvas(IMAGESIZE, IMAGESIZE);
		this.setCenter(imageView);
		String css = "-fx-border-color: black;\n"
                + "-fx-border-insets:0;\n"
                + "-fx-border-width: 1;\n"
                + "-fx-border-style: solid;\n";
		this.setStyle(css);
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t)
	        {
	        	String css = "-fx-border-color: red;";
	    		MapTileButton.this.setStyle(css);
	        }
	    });
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent t)
	        {
	        	String css = "-fx-border-color: black;";
	    		MapTileButton.this.setStyle(css);
	        }
	    });
		
		this.coord = coord;
		this.setTileType(tileType);
	}

	/**
	 * @return the type of tile the button displays
	 */
	public int getTileType()
	{
		return tileType;
	}

	/**
	 * Set the type of tile the button should display
	 * @param tileType
	 */
	public void setTileType(int tileType)
	{
		this.tileType = tileType;
		redraw();
	}
	
	/**
	 * @return the coordinates or the button on the map
	 */
	public Coordinate getCoordinate()
	{
		return coord;
	}

	/**
	 * @return if the button is displaying an axe
	 */
	public boolean isAxe()
	{
		return isAxe;
	}

	/**
	 * Set if the button should display an axe
	 * @param isAxe
	 */
	public void setAxe(boolean isAxe)
	{
		this.isAxe = isAxe;
		redraw();
	}

	/**
	 * @return if the button is displaying a boat
	 */
	public boolean isBoat()
	{
		return isBoat;
	}

	/**
	 * Set if the button should display a boat
	 * @param isBoat
	 */
	public void setBoat(boolean isBoat)
	{
		this.isBoat = isBoat;
		redraw();
	}
	
	private void redraw()
	{
		GraphicsContext gc = imageView.getGraphicsContext2D();
		gc.clearRect(0, 0, IMAGESIZE, IMAGESIZE);
		Rectangle2D source = TileImageLoader.getInstance().getTileViewportBounds(tileType);
		gc.drawImage(TileImageLoader.getInstance().getTileset(), 
				source.getMinX(), source.getMinY(), 
				source.getWidth(), source.getHeight(), 
				0, 0, IMAGESIZE, IMAGESIZE);
		if(isAxe)
			gc.drawImage(new Image(getClass().getResourceAsStream("/axe.png")), 0, 0, IMAGESIZE, IMAGESIZE);
		if(isBoat)
			gc.drawImage(new Image(getClass().getResourceAsStream("/boat.png")), 0, 0, IMAGESIZE, IMAGESIZE);
	}

}
