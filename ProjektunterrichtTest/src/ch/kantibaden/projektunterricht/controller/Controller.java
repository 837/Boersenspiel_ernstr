package ch.kantibaden.projektunterricht.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Controller {
	
	
	//Home
	@FXML
	private Label lblStartkapital;
	
	@FXML
	private Label lblMomentanesKapital;
	
	@FXML
	private Label lblWertAllerAktien;
	
	@FXML
	private TableView<String> tvMeineAktien;
	
	
	//Alle Aktien
	@FXML
	private TableView<String> tvAlleAktien;
	
	
	
	
	
	//Details Aktie
	@FXML
	private Label lblDetailSymbol;
	
	@FXML
	private Label lblDetailName;
	
	@FXML
	private Label lblDetailKurs;
	
	@FXML
	private Label lblDetailAnzahl;
	
	@FXML
	private Button btKaufen;
	
	@FXML
	private Button btVerkaufen;
	
	@FXML
	private TextField txtAmount;
	
	
	//Menu
	
	//mProfil  == Action Code muss noch gesetzt werden
	//mLogout  == Action Code muss noch gesetzt werden
	
	
	@FXML
	private void initialize() {
		//Wird beim aufruf des Controllers ausgeführt, sprich beim start des programmes
	}

	public Controller() {

	}
}
