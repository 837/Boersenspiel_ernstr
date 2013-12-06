package ch.kantibaden.projektunterricht;

import javafx.stage.Stage;


public class DataBean {   
	   private Stage primaryStage = null;   
	   private Map<string , String> namePwMap = null;
	    
	   public DataBean(Stage primaryStage) {
	      this.primaryStage = primaryStage;
	      this.namePwMap = new HashMap<>();
	   }
	 
	   public Map</string><string , String> getNamePwMap() {
	      return namePwMap;
	   }
	 
	   public Stage getPrimaryStage() {
	      return primaryStage;
	   }
	}