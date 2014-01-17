package ch.kantibaden.projektunterricht.controller;

import java.io.IOException;

import ch.kantibaden.projektunterricht.dao.UserDao;
import ch.kantibaden.projektunterricht.model.PlayerProfile;
import ch.kantibaden.projektunterricht.model.ShareManager;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {
	private Stage dialogStage;
	private PlayerProfile player;
	private ShareManager shares;

	public void setStage(Stage stage) {
		this.dialogStage = stage;
	}

	public void setPlayerShares(PlayerProfile player, ShareManager shares) {
		this.player = player;
		this.shares = shares;
	}

	@FXML
	private void initialize() {
		lbInfoLogin.setTextFill(Color.RED);
		lbInfoSignup.setTextFill(Color.RED);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				txtUsernameLogin.requestFocus();
			}
		});

		txtStartkapital.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent inputevent) {
				// filtering all non numeric characters with regex
				if (!inputevent.getCharacter().matches("\\d")) {
					inputevent.consume();// == no input
				}
				if (txtStartkapital.getText().length() + 1 > 15) {
					inputevent.consume();// == no input
				}
			}
		});
	}

	@FXML
	private void handleLogin() {
		lbInfoLogin.setText("");
		if (txtUsernameLogin.getText().isEmpty() || pwPasswordLogin.getText().isEmpty()) {
			lbInfoLogin.setText("Username und Passwort müssen ausgefüllt sein.");
		} else if (!UserDao.login(txtUsernameLogin.getText(), pwPasswordLogin.getText())) {
			lbInfoLogin.setText("Username oder Passwort falsch.");
		} else {
			player = UserDao.getUser();
			try {
				shares.loadPlayerShares(player.getOwnedShares());
				dialogStage.close();
			} catch (IOException e) {
				lbInfoLogin.setText("Verbindung fehlgeschlagen.");
			}
		}
	}

	@FXML
	private void handleSignup() {
		lbInfoSignup.setText("");
		if (txtUsernameSignup.getText().isEmpty() || pwPasswordSignup.getText().isEmpty() || txtStartkapital.getText().isEmpty()) {
			lbInfoSignup.setText("Alle drei Felder müssen ausgefüllt sein.");
		} else if (UserDao.getUser() == null && !UserDao.signup(txtUsernameSignup.getText(), pwPasswordSignup.getText(), txtStartkapital.getText())) {
			lbInfoSignup.setText("Username wird bereits verwendet.");
		} else {
			player = UserDao.getUser();
			try {
				shares.loadPlayerShares(player.getOwnedShares());
				dialogStage.close();
			} catch (IOException e) {
				lbInfoSignup.setText("Verbindung fehlgeschlagen.");
			}
		}
	}

	@FXML
	private TextField txtUsernameLogin;
	@FXML
	private TextField txtUsernameSignup;
	@FXML
	private TextField txtStartkapital;
	@FXML
	private Label lbInfoLogin;
	@FXML
	private Label lbInfoSignup;
	@FXML
	private PasswordField pwPasswordLogin;
	@FXML
	private PasswordField pwPasswordSignup;
}
