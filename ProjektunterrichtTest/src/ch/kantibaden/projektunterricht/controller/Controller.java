package ch.kantibaden.projektunterricht.controller;

import java.io.IOException;
import java.math.BigDecimal;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import ch.kantibaden.projektunterricht.model.PlayerProfile;
import ch.kantibaden.projektunterricht.model.Share;
import ch.kantibaden.projektunterricht.model.ShareContainer;
import ch.kantibaden.projektunterricht.model.ShareManager;

public class Controller {
	
	private PlayerProfile player;
	private ShareManager shares;
	private final String STARTKAPITAL = "Startkapital: ";
	private final String MOMENTANES_KAPITAL = "Momentanes Kapital: ";
	private final String WERT_ALLER_AKTIEN = "Wert aller Aktien: ";
	private Share selectedShare;
	
	
	
	@FXML
	private void initialize() throws IOException {
		// login here
		
		player = new PlayerProfile("username_here", "email_here", "password_here", 20000);
		shares = new ShareManager();
		
		// sagt den Zellen nach welchem Wert sie in der Aktie schauen müssen.
		// AlleAktien
		aaSymbol.setCellValueFactory(new PropertyValueFactory<Share, String>("symbol"));
		aaName.setCellValueFactory(new PropertyValueFactory<Share, String>("name"));
		aaKurs.setCellValueFactory(new PropertyValueFactory<Share, String>("value"));
		// MeineAktien
		maSymbol.setCellValueFactory(new PropertyValueFactory<Share, String>("symbol"));
		maName.setCellValueFactory(new PropertyValueFactory<Share, String>("name"));
		maKurs.setCellValueFactory(new PropertyValueFactory<Share, String>("value"));
		
		// befüllt alleAktien tabelle
		tvAlleAktien.getItems().addAll(shares.getShares());
		
		// befüllt meineAktien tabelle
		for (ShareContainer currentShare : player.getOwnedShares()) {
			tvMeineAktien.getItems().addAll(currentShare.getShare());
		}
		
		// adding an eventfilter to txtAmount(textfield)
		txtAmount.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent inputevent) {
				// filtering all non numeric characters with regex
				if (!inputevent.getCharacter().matches("\\d")) {
					inputevent.consume();// == no input
				}
				if (txtAmount.getText().length() + 1 > 6) {
					inputevent.consume();// == no input
				}
			}
		});
		
		// adding an eventListener for tvAlleAktien, used to select "selected".
		tvAlleAktien.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Share>() {
			
			@Override
			public void changed(ObservableValue<? extends Share> observableValue, Share oldValue, Share newValue) {
				// Check whether item is selected and set value of selected item
				// to Label
				if (tvAlleAktien.getSelectionModel().getSelectedItem() != null) {
					selectedShare = (Share) newValue;
					
					lblDetailSymbol.setText("Symbol: " + selectedShare.getSymbol());
					lblDetailName.setText("Name: " + selectedShare.getName());
					lblDetailKurs.setText("Kurs: " + selectedShare.getValue().toString());
					lblDetailAnzahl.setText("Anzahl die ich besitze: " + player.getOwnedSharesByShare(selectedShare));
					
				}
			}
		});
		
		// adding an eventListener for tvMeineAktien, used to select "selected".
		tvMeineAktien.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Share>() {

			@Override
			public void changed(ObservableValue<? extends Share> observableValue, Share oldValue, Share newValue) {
				// Check whether item is selected and set value of selected item
				// to Label

				if (tvMeineAktien.getSelectionModel().getSelectedItem() != null) {
					selectedShare =  newValue;
					
					lblDetailSymbol.setText("Symbol: " + selectedShare.getSymbol());
					lblDetailName.setText("Name: " + selectedShare.getName());
					lblDetailKurs.setText("Kurs: " + selectedShare.getValue().toString());
					lblDetailAnzahl.setText("Anzahl die ich besitze: " + player.getOwnedSharesByShare(selectedShare));
				}
			}
		});

		// kaufenButton Listener
		btKaufen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (!txtAmount.getText().isEmpty()) {
					int amount = new Integer(txtAmount.getText());
					
					player.buy(selectedShare, amount);
					
					// update labels
					lblDetailAnzahl.setText("Anzahl die ich besitze: " + player.getOwnedSharesByShare(selectedShare));
					lblMomentanesKapital.setText(MOMENTANES_KAPITAL + " " + player.getBalance().toString() + " CHF");
					lblWertAllerAktien.setText(WERT_ALLER_AKTIEN + " " + player.getTotalShareValue().toString() + " CHF");
					
					txtAmount.setText("");
					
					// befüllt meineAktien tabelle
					tvMeineAktien.getItems().clear();
					for (ShareContainer currentShare : player.getOwnedShares()) {
						tvMeineAktien.getItems().addAll(currentShare.getShare());
					}
				}
			}
		});
		
		// verkaufenButton Listener
		btVerkaufen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (!txtAmount.getText().isEmpty()) {
					int amount = new Integer(txtAmount.getText());
					
					player.sell(selectedShare, amount);
					
					// update labels
					lblDetailAnzahl.setText("Anzahl die ich besitze: " + player.getOwnedSharesByShare(selectedShare));
					lblMomentanesKapital.setText(MOMENTANES_KAPITAL + " " + player.getBalance().toString() + " CHF");
					lblWertAllerAktien.setText(WERT_ALLER_AKTIEN + " " + player.getTotalShareValue().toString() + " CHF");
					
					txtAmount.setText("");
					
					// befüllt meineAktien tabelle
					tvMeineAktien.getItems().clear();
					for (ShareContainer currentShare : player.getOwnedShares()) {
						tvMeineAktien.getItems().addAll(currentShare.getShare());
					}
				}
			}
		});
		
		lblStartkapital.setText(STARTKAPITAL + " " + player.getBeginningBalance().toString() + " CHF");
		lblMomentanesKapital.setText(MOMENTANES_KAPITAL + " " + player.getBalance().toString() + " CHF");
		lblWertAllerAktien.setText(WERT_ALLER_AKTIEN + " " + player.getTotalShareValue().toString() + " CHF");
		
	}
	
	public Controller() {
		
	}
	
	// Home
	@FXML private Label lblStartkapital;
	@FXML private Label lblMomentanesKapital;
	@FXML private Label lblWertAllerAktien;
	@FXML private TableView<Share> tvMeineAktien;
	@FXML private TableColumn<Share, String> maSymbol;
	@FXML private TableColumn<Share, String> maName;
	@FXML private TableColumn<Share, String> maKurs;
	
	// Alle Aktien
	@FXML private TableView<Share> tvAlleAktien;
	@FXML private TableColumn<Share, String> aaSymbol;
	@FXML private TableColumn<Share, String> aaName;
	@FXML private TableColumn<Share, String> aaKurs;
	
	// Details Aktie
	@FXML private Label lblDetailSymbol;
	@FXML private Label lblDetailName;
	@FXML private Label lblDetailKurs;
	@FXML private Label lblDetailAnzahl;
	@FXML private Button btKaufen;
	@FXML private Button btVerkaufen;
	
	@FXML private TextField txtAmount;
	
}
