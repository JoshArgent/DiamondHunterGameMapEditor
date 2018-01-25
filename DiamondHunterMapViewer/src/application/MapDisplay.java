package application;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MapDisplay extends GridPane
{
	
	private boolean placeAxe;
	private boolean placeBoat;
	private ImageCursor axeCursor;
	private ImageCursor boatCursor;
	private ArrayList<ItemPlaceHandler> itemPlaceHandlers = new ArrayList<ItemPlaceHandler>();
	
	public MapDisplay()
	{
		this.setHgap(0);
		this.setVgap(0);
		updateDisplay();
		axeCursor = new ImageCursor(new Image(getClass().getResourceAsStream("/axe.png")), 30, 30);
		boatCursor = new ImageCursor(new Image(getClass().getResourceAsStream("/boat.png")), 30, 30);
	}
	
	/**
	 * Reload the map display from the map data
	 */
	public void updateDisplay()
	{
		// Check if a map has been loaded, if not don't display anything
		if(!MapLoader.getInstance().isMapLoaded())
			return;
		
		// Setup grid pane constraints to loaded map rows
		this.getColumnConstraints().clear();
		this.getRowConstraints().clear();
		for (int i = 0; i < MapLoader.getInstance().getCols(); i++)
		{
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / MapLoader.getInstance().getCols());
            this.getColumnConstraints().add(colConst);
        }
		for (int i = 0; i < MapLoader.getInstance().getRows(); i++)
		{
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / MapLoader.getInstance().getRows());
            this.getRowConstraints().add(rowConst);         
        }
		
		// Populate the grid pane
		for(int row = 0; row < MapLoader.getInstance().getRows(); row++)
		{
			for(int col = 0; col < MapLoader.getInstance().getCols(); col++)
			{
				int tile = MapLoader.getInstance().getMapValue(col, row);
				MapTileButton b = new MapTileButton(tile, new Coordinate(col, row));
				
				// Check if the button should display an item
				if(MapLoader.getInstance().getAxe() != null && MapLoader.getInstance().getAxe().getX() == col && MapLoader.getInstance().getAxe().getY() == row)
					b.setAxe(true);
				if(MapLoader.getInstance().getBoat() != null && MapLoader.getInstance().getBoat().getX() == col && MapLoader.getInstance().getBoat().getY() == row)
					b.setBoat(true);
				
				// Manage the onClick event
				b.setOnMouseClicked(new EventHandler<MouseEvent>()
				{
			        @Override
			        public void handle(MouseEvent event)
			        {
			        	MapTileButton source = (MapTileButton) event.getSource();
			        	if(!MapLoader.getInstance().isSolid(source.getCoordinate()) && placeAxe)
			        	{
			        		removeAllAxes();
			        		source.setAxe(true);
			        		fireItemPlaceHandlersAxe(source.getCoordinate());
			        	}
			        	if(!MapLoader.getInstance().isSolid(source.getCoordinate()) && placeBoat)
			        	{
			        		removeAllBoats();
			        		source.setBoat(true);
			        		fireItemPlaceHandlersBoat(source.getCoordinate());
			        	}
			        }
			    });
				
				// Add the button to the grid
				this.add(b, col, row);
			}
		}
	}

	/**
	 * Allow the user to place the axe on the map
	 */
	public void canPlaceAxe()
	{
		this.placeAxe = true;
		this.placeBoat = false;
		this.setCursor(axeCursor);
	}

	/**
	 * Allow the user to place the boat on the map
	 */
	public void canPlaceBoat()
	{
		this.placeBoat = true;
		this.placeAxe = false;
		this.setCursor(boatCursor);
	}
	
	/**
	 * Stop the user from placing a boat or axe on the map
	 */
	public void canPlaceNone()
	{
		this.placeBoat = false;
		this.placeAxe = false;
		this.setCursor(Cursor.DEFAULT);
	}
	
	/**
	 * Add an ItemPlaceHandler to the display
	 * @param handler
	 */
	public void addItemPlaceHandler(ItemPlaceHandler handler)
	{
		itemPlaceHandlers.add(handler);
	}
	
	/**
	 * Remove an ItemPlaceHandler from the display
	 * @param handler
	 */
	public void removeItemPlaceHandler(ItemPlaceHandler handler)
	{
		itemPlaceHandlers.remove(handler);
	}
	
	/**
	 * Fire the ItemPlaceHandlers to tell them a boat has been placed
	 * @param coord
	 */
	private void fireItemPlaceHandlersBoat(Coordinate coord)
	{
		for(ItemPlaceHandler handler : itemPlaceHandlers)
		{
			handler.placeBoat(coord);
		}
		canPlaceNone();
	}
	
	/**
	 * Fire the ItemPlaceHandlers to tell them an axe has been placed
	 * @param coord
	 */
	private void fireItemPlaceHandlersAxe(Coordinate coord)
	{
		for(ItemPlaceHandler handler : itemPlaceHandlers)
		{
			handler.placeAxe(coord);
		}
		canPlaceNone();
	}
	
	/**
	 * Remove all axes from the display
	 */
	private void removeAllAxes()
	{
		for(Node node : this.getChildren())
		{
			if(node instanceof MapTileButton)
			{
				((MapTileButton) node).setAxe(false);
			}
		}
	}
	
	/**
	 * Remove all boats from the display
	 */
	private void removeAllBoats()
	{
		for(Node node : this.getChildren())
		{
			if(node instanceof MapTileButton)
			{
				((MapTileButton) node).setBoat(false);
			}
		}
	}
	
}
