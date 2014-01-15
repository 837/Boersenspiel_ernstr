package ch.kantibaden.projektunterricht.controller;

import java.io.IOException;
import java.math.BigDecimal;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ch.kantibaden.projektunterricht.Boersenspiel;
import ch.kantibaden.projektunterricht.dao.UserDao;
import ch.kantibaden.projektunterricht.model.PlayerProfile;
import ch.kantibaden.projektunterricht.model.Share;
import ch.kantibaden.projektunterricht.model.ShareContainer;
import ch.kantibaden.projektunterricht.model.ShareManager;

public class Controller {

	private Stage stage;
	private Boersenspiel boersenspiel;
	private PlayerProfile player = null;
	private ShareManager shares;
	private final String STARTKAPITAL = "Startkapital: ";
	private final String MOMENTANES_KAPITAL = "Momentanes Kapital: ";
	private final String WERT_ALLER_AKTIEN = "Wert aller Aktien: ";
	private Share selectedShare;

	public void setBoersenspiel(Boersenspiel b) {
		this.boersenspiel = b;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void login() throws Exception {
		boersenspiel.login(player, shares);
		player = UserDao.getUser();
		if (player == null) {
			Platform.exit();
		} else {
			tvAlleAktien.getItems().addAll(shares.getShares());

			for (ShareContainer currentShare : player.getOwnedShares()) {
				tvMeineAktien.getItems().addAll(currentShare.getShare());
			}
			lblStartkapital.setText(STARTKAPITAL + " " + player.getBeginningBalance().toString() + " CHF");
			lblMomentanesKapital.setText(MOMENTANES_KAPITAL + " " + player.getBalance().toString() + " CHF");
			lblWertAllerAktien.setText(WERT_ALLER_AKTIEN + " " + player.getTotalShareValue().toString() + " CHF");
			lblBenutzername.setText("Benutzername: " + player.getName());

			imgChart.setPreserveRatio(true);
			imgChart.fitWidthProperty().bind(stage.widthProperty());
			// imgChart.fitHeightProperty().bind(stage.heightProperty());
			bpChart.setCenter(imgChart);
		}
	}

	@FXML
	private void initialize() throws IOException {

		shares = new ShareManager();
		player = new PlayerProfile("", "", 0);

		aaSymbol.setCellValueFactory(new PropertyValueFactory<Share, String>("symbol"));
		aaName.setCellValueFactory(new PropertyValueFactory<Share, String>("name"));
		aaKurs.setCellValueFactory(new PropertyValueFactory<Share, String>("value"));
		// MeineAktien
		maSymbol.setCellValueFactory(new PropertyValueFactory<Share, String>("symbol"));
		maName.setCellValueFactory(new PropertyValueFactory<Share, String>("name"));
		maKurs.setCellValueFactory(new PropertyValueFactory<Share, String>("value"));

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
					updateDetails();
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
					selectedShare = newValue;
					updateDetails();
				}
			}

		});

	}

	@FXML
	private void handleBuy() {
		if (!txtAmount.getText().isEmpty()) {
			int amount = new Integer(txtAmount.getText());
			if (player.buy(selectedShare, amount)) {
				buySellActions();
				lbTransactionInfo.setText("Gekauft: " + amount + " * " + selectedShare.getName() + "  "
						+ selectedShare.getValue().multiply(new BigDecimal(amount)) + " CHF");
				lbTransactionInfo.setTextFill(Color.GREEN);

			} else {
				lbTransactionInfo.setText("Das können Sie nicht tun, Sie bräuchten: " + selectedShare.getValue().multiply(new BigDecimal(amount))
						+ " CHF");
				lbTransactionInfo.setTextFill(Color.RED);
			}

		}
	}

	@FXML
	private void handleSell() {
		if (!txtAmount.getText().isEmpty()) {
			int amount = new Integer(txtAmount.getText());
			int actuallAmount = player.sell(selectedShare, amount);
			if (actuallAmount > 0) {
				buySellActions();
				lbTransactionInfo.setText("Verkauft: " + actuallAmount + " * " + selectedShare.getName() + "  "
						+ selectedShare.getValue().multiply(new BigDecimal(actuallAmount)) + " CHF");
				lbTransactionInfo.setTextFill(Color.GREEN);

			} else {
				lbTransactionInfo.setText("Das können Sie nicht tun, es fehlen Ihnen die Aktien: " + selectedShare.getName());
				lbTransactionInfo.setTextFill(Color.RED);
			}
		}
	}

	@FXML
	private void handleRefresh() {
		try {
			shares.downloadAll();
			btRefresh.setText("Aktualisiere Aktien");
			btRefresh.setTextFill(Color.GREEN);
			lblWertAllerAktien.setText(WERT_ALLER_AKTIEN + " " + player.getTotalShareValue().toString() + " CHF");
		} catch (IOException e) {
			//e.printStackTrace();
			btRefresh.setText("Keine Verbindung, erneut versuchen");
			btRefresh.setTextFill(Color.RED);
		}
	}

	private void updateDetails() {
		lblDetailSymbol.setText("Symbol: " + selectedShare.getSymbol());
		lblDetailName.setText("Name: " + selectedShare.getName());
		lblDetailKurs.setText("Kurs: " + selectedShare.getValue().toString());
		lblDetailAnzahl.setText("Anzahl die ich besitze: " + player.getOwnedAmountOfShare(selectedShare));
		lblDetailTotalerWert.setText("Totaler Wert: " + player.getTotalValueOfShare(selectedShare).toString());
		lblWertAllerAktien.setText(WERT_ALLER_AKTIEN + " " + player.getTotalShareValue().toString() + " CHF");
		imgChart.setImage(selectedShare.getChart());

	}

	private void buySellActions() {
		lblDetailAnzahl.setText("Anzahl die ich besitze: " + player.getOwnedAmountOfShare(selectedShare));
		lblMomentanesKapital.setText(MOMENTANES_KAPITAL + " " + player.getBalance().toString() + " CHF");
		lblWertAllerAktien.setText(WERT_ALLER_AKTIEN + " " + player.getTotalShareValue().toString() + " CHF");
		lblDetailTotalerWert.setText("Totaler Wert: " + player.getTotalValueOfShare(selectedShare).toString());
		txtAmount.setText("");

		tvMeineAktien.getItems().clear();
		for (ShareContainer currentShare : player.getOwnedShares()) {
			tvMeineAktien.getItems().addAll(currentShare.getShare());
		}
		UserDao.saveUser(player);
	}

	public Controller() {

	}

	// Home
	@FXML
	private Label lblStartkapital;
	@FXML
	private Label lblMomentanesKapital;
	@FXML
	private Label lblWertAllerAktien;
	@FXML
	private Label lblBenutzername;
	@FXML
	private TableView<Share> tvMeineAktien;
	@FXML
	private TableColumn<Share, String> maSymbol;
	@FXML
	private TableColumn<Share, String> maName;
	@FXML
	private TableColumn<Share, String> maKurs;

	// Alle Aktien
	@FXML
	private TableView<Share> tvAlleAktien;
	@FXML
	private TableColumn<Share, String> aaSymbol;
	@FXML
	private TableColumn<Share, String> aaName;
	@FXML
	private TableColumn<Share, String> aaKurs;
	@FXML
	private Button btRefresh;

	// Details Aktie
	@FXML
	private Label lblDetailSymbol;
	@FXML
	private Label lblDetailName;
	@FXML
	private Label lblDetailKurs;
	@FXML
	private Label lblDetailAnzahl;
	@FXML
	private Label lblDetailTotalerWert;
	@FXML
	private Button btKaufen;
	@FXML
	private Button btVerkaufen;
	@FXML
	private TextField txtAmount;
	@FXML
	private ImageView imgChart;
	@FXML
	private BorderPane bpChart;
	@FXML
	private Label lbTransactionInfo;
}
