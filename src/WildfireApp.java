import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wildfires.UI.WildfireUIControl;

public class WildfireApp extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Wildfire Simulation System");
		WildfireUIControl ui = new WildfireUIControl();
		Scene scene = new Scene(ui, 1200, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
