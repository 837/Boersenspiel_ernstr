package ch.kantibaden.projektunterricht.controller;

import java.io.IOException;

import ch.kantibaden.projektunterricht.model.PlayerProfile;
import ch.kantibaden.projektunterricht.model.Share;
import ch.kantibaden.projektunterricht.model.ShareContainer;
import ch.kantibaden.projektunterricht.model.ShareManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Controller {
	
	private PlayerProfile player;
	private ShareManager shares;
	
	
	
	//Menu
	
	//mProfil  == Action Code muss noch gesetzt werden
	//mLogout  == Action Code muss noch gesetzt werden
	
	
	@FXML
	private void initialize() throws IOException {
		//login here
		player = new PlayerProfile("username_here", "email_here", "password_here", 10000);
		shares = new ShareManager();
		
		tvAlleAktien.getItems().addAll(shares.getShares());
		tvMeineAktien.getItems().addAll(player.getOwnedShares());
		
		lblStartkapital.setText(lblStartkapital.getText() + " " + player.getBeginningBalance().toString()+" CHF");
		lblMomentanesKapital.setText(lblMomentanesKapital.getText() + " " + player.getBalance().toString()+" CHF");
		lblWertAllerAktien.setText(lblWertAllerAktien.getText() + " " + player.getTotalShareValue().toString()+" CHF");
		
		
	}

	public Controller() {

	}
	
	
	//Home
	@FXML private Label lblStartkapital;
	@FXML private Label lblMomentanesKapital;
	@FXML private Label lblWertAllerAktien;
	@FXML private TableView<ShareContainer> tvMeineAktien;
	@FXML private TableColumn<ShareContainer, String> maSymbol;
	@FXML private TableColumn<ShareContainer, String> maName;
	@FXML private TableColumn<ShareContainer, String> maKurs;
	
	//Alle Aktien
	@FXML private TableView<Share> tvAlleAktien;
	@FXML private TableColumn<Share, String> aaName;
	@FXML private TableColumn<Share, String> aaSymbol;
	@FXML private TableColumn<Share, String> aaKurs;
	
	//Details Aktie
	@FXML private Label lblDetailSymbol;
	@FXML private Label lblDetailName;
	@FXML private Label lblDetailKurs;
	@FXML private Label lblDetailAnzahl;
	@FXML private Button btKaufen;
	@FXML private Button btVerkaufen;
	@FXML private TextField txtAmount;
}
