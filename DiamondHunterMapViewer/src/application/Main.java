package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	private static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage)
	{
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 800, 600);
			
			Parent content = FXMLLoader.load(getClass().getClassLoader().getResource("application/MapViewer.fxml"));
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			root.setCenter(content);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Diamond Hunter Map Viewer");
			primaryStage.show();
			
			Main.primaryStage = primaryStage;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	static public Stage getPrimaryStage()
	{
        return Main.primaryStage;
    }
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
