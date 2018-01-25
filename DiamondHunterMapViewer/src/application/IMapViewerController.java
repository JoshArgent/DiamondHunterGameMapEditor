package application;

import javafx.fxml.FXML;

public interface IMapViewerController
{
	@FXML
	public void open();
	
	@FXML
	public void save();
	
	@FXML
	public void saveAs();
	
	@FXML
	public void launchGame();
	
	@FXML
	public void close();
	
	@FXML
	public void insertAxe();
	
	@FXML
	public void insertBoat();
	
	@FXML
	public void about();
	
}
