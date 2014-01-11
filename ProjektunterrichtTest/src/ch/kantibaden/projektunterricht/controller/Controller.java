package ch.kantibaden.projektunterricht.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ch.kantibaden.projektunterricht.model.PlayerProfile;
import ch.kantibaden.projektunterricht.model.Share;
import ch.kantibaden.projektunterricht.model.ShareManager;

public class Controller {

	private PlayerProfile player;
	private ShareManager shares;

	// Menu

	// mProfil == Action Code muss noch gesetzt werden
	// mLogout == Action Code muss noch gesetzt werden

	@FXML
	private void initialize() throws IOException {
		// login here
		player = new PlayerProfile("username_here", "email_here",
				"password_here", 10000);
		shares = new ShareManager();


		// NOCH NICHT SCHÖN, FUNKTIONIERT ABER
		for (Share currentShare : shares.getShares()) {

			lvAlleAktien.getItems().add(
					currentShare.getSymbol() + " \t " + currentShare.getName()
							+ "   " + currentShare.getValue());

		}

		lblStartkapital.setText(lblStartkapital.getText() + " "
				+ player.getBeginningBalance().toString() + " CHF");
		lblMomentanesKapital.setText(lblMomentanesKapital.getText() + " "
				+ player.getBalance().toString() + " CHF");
		lblWertAllerAktien.setText(lblWertAllerAktien.getText() + " "
				+ player.getTotalShareValue().toString() + " CHF");

	
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
	@FXML private ListView<String> lvMeineAktien;

	
	//Alle Aktien
	@FXML private ListView<String> lvAlleAktien;

	
	//Details Aktie
	@FXML private Label lblDetailSymbol;
	@FXML private Label lblDetailName;
	@FXML private Label lblDetailKurs;
	@FXML private Label lblDetailAnzahl;
	@FXML private Button btKaufen;
	@FXML private Button btVerkaufen;

	@FXML private TextField txtAmount;

}
