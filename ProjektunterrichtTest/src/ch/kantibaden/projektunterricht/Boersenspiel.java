package ch.kantibaden.projektunterricht;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import ch.kantibaden.projektunterricht.model.PlayerProfile;
import ch.kantibaden.projektunterricht.model.ShareManager;


public class Boersenspiel extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		// session scope /application scope Beans initialisieren!
		// muss von Controller zu Controller weitergegeben werden
		DataBean dataBean = new DataBean(primaryStage);

		// Ersten Controller aufrufen
		EingabeVC eingabeVC = new EingabeVC(dataBean);
		eingabeVC.show();
	}
}

//JAVAFX MIT MVC
// http://blog.axxg.de/model-view-controller-mit-javafx/

//	private static PlayerProfile player;
//	private static ShareManager manager;
//
//	public Boersenspiel() {
//	
//	}
//
//	public static void main(String[] args) {
//		player = new PlayerProfile("Matthias Ernst", "ernst.matthias@hotmail.com", "abc123", 10000);
//
//		try {
//			manager = new ShareManager();
//		} catch (IOException e) {
//			System.out.println("Fehler in Boersenspiel.java manager=new ShareManager()");
//			e.printStackTrace();
//		}
//	}

