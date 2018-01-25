package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.neet.DiamondHunter.Main.Game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;


public class MapViewerController implements Initializable, IMapViewerController, ItemPlaceHandler
{

	@FXML MenuItem openButton;
	@FXML MenuItem saveButton;
	@FXML MenuItem saveAsButton;
	@FXML MenuItem launchGameButton;
	@FXML MenuItem closeButton;
	@FXML MenuItem insertAxeButton;
	@FXML MenuItem insertBoatButton;
	@FXML MenuItem aboutButton;
	@FXML BorderPane mainContainer;
	@FXML ScrollPane scrollPane;
	private MapDisplay mapDisplay;
	private File openFile;
	
	@Override
	public void initialize(URL url, ResourceBundle res)
	{
		mapDisplay = new MapDisplay();
		scrollPane.setContent(mapDisplay);
		mapDisplay.addItemPlaceHandler(this);
	}
	
	@Override
	public void open()
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Map File");
		fileChooser.getExtensionFilters().addAll(
			    new FileChooser.ExtensionFilter("Map", "*.map")
			);
		File file = fileChooser.showOpenDialog(Main.getPrimaryStage());
		if(file != null)
		{
			try
			{
				// Load the map file
				MapLoader.getInstance().loadMap(file);
				// Display the map
				mapDisplay.updateDisplay();
				// Set window constraints
				mapDisplay.setMaxWidth(MapLoader.getInstance().getCols() * MapTileButton.IMAGESIZE);
				mapDisplay.setMaxHeight(MapLoader.getInstance().getRows() * MapTileButton.IMAGESIZE);
				// Set the open file
				openFile = file;
				// Set application title
				Main.getPrimaryStage().setTitle("Diamond Hunter Map Viwer - " + file.getName());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void save()
	{
		if(openFile != null)
		{
			try
			{
				MapLoader.getInstance().saveMap(openFile);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			saveAs();
		}
	}

	@Override
	public void saveAs()
	{
		// Check that a map is loaded
		if(!MapLoader.getInstance().isMapLoaded())
		{
			alert("You must open a map file before you can save it!");
			return;
		}
		// Open save dialog
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Map File");
		fileChooser.getExtensionFilters().addAll(
			    new FileChooser.ExtensionFilter("Map", "*.map")
			);
		File file = fileChooser.showSaveDialog(Main.getPrimaryStage());
		if(file != null)
		{
			try
			{
				MapLoader.getInstance().saveMap(file);
				openFile = file;
				// Set application title
				Main.getPrimaryStage().setTitle("Diamond Hunter Map Viwer - " + file.getName());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void launchGame()
	{
		// Check that a map is loaded
		if(!MapLoader.getInstance().isMapLoaded())
		{
			alert("You must open a map file before you can launch a game!");
			return;
		}
		// Save the map
		save();
		// Get the map file location
		String map = openFile.getAbsolutePath();
		// Launch the game in a new thread
		Thread thread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				Game.main(new String[] { map });				
			}
					
		});
		thread.start(); 
	}

	@Override
	public void close()
	{
		Main.getPrimaryStage().close();
	}

	@Override
	public void insertAxe()
	{
		if(MapLoader.getInstance().isMapLoaded())
			mapDisplay.canPlaceAxe();
		else
			alert("You must open a map file before you can place an item!");
	}

	@Override
	public void insertBoat()
	{
		if(MapLoader.getInstance().isMapLoaded())
			mapDisplay.canPlaceBoat();
		else
			alert("You must open a map file before you can place an item!");
	}

	@Override
	public void about()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Diamond Hunter Map Viewer");
		alert.setContentText("The map viewer allows you to view and edit map files\n" +
				"Use the menu bar at the top to select different options.\n" +
				"To place an item (axe/boat) go to Insert > Boat/Axe\n" +
				"You can launch a game running your map by going to File > Launch Game\n");
		alert.showAndWait();
	}

	@Override
	public void placeAxe(Coordinate coord)
	{
		MapLoader.getInstance().setAxe(coord);
	}

	@Override
	public void placeBoat(Coordinate coord)
	{
		MapLoader.getInstance().setBoat(coord);
	}
	
	private void alert(String message)
	{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Diamond Hunter Map Viewer");
		alert.setContentText(message);
		alert.showAndWait();
	}

}
