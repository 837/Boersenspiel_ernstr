package ch.kantibaden.projektunterricht;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Boersenspiel extends Application {
  
	@Override
	public void start(Stage primaryStage) {
	Parent root = null;
		
		try {
			//loads the fxml
			root = FXMLLoader.load(getClass().getResource("view/BoersenspielView.fxml"));
		} catch (IOException e) {
			System.out.println("Wenn hier ein Fehler ist, dann liegt es wahrscheinlich an der Bennenung eines ViewObjektes. (fx:id)");
			e.printStackTrace();
		}
		
		//erstellt eine neue Scene
		Scene scene = new Scene(root);
	
		
		primaryStage.setScene(scene);
		
		//macht die Stage sichtbar
		primaryStage.show();
		primaryStage.setResizable(true);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}