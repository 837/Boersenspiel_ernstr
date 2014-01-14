package ch.kantibaden.projektunterricht;

import java.io.IOException;

import ch.kantibaden.projektunterricht.controller.Controller;
import ch.kantibaden.projektunterricht.controller.LoginController;
import ch.kantibaden.projektunterricht.model.PlayerProfile;
import ch.kantibaden.projektunterricht.model.ShareManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Boersenspiel extends Application {
	Parent root = null;
	private Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Börsenspiel");
		this.primaryStage = primaryStage;
	
		//main app
		try {
			// loads the fxml
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/BoersenspielView.fxml"));
			root = (Parent) loader.load();
			Scene mainScene = new Scene(root);
			primaryStage.setScene(mainScene);
			// macht die Stage sichtbar
			
			primaryStage.setResizable(true);
			Controller c =  loader.getController();
			c.setBoersenspiel(this);
			//c.setStage(primaryStage);
			c.login();
			primaryStage.show();
			
		} catch (IOException e) {
			System.out.println(e.getMessage());//"Wenn hier ein Fehler ist, dann liegt es wahrscheinlich an der Bennenung eines ViewObjektes. (fx:id)");
		}
		
	}
	
	public void login(PlayerProfile player, ShareManager shares){
		try {
			// Load the fxml file and create a new stage for the popup
			FXMLLoader loader = new FXMLLoader(Boersenspiel.class.getResource("view/LoginView.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Login");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			dialogStage.setScene(scene);
			
			
			LoginController controller = loader.getController();
			controller.setStage(dialogStage);
			controller.setPlayerShares(player, shares);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			
			
		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
