package ch.kantibaden.projektunterricht;

import java.io.IOException;

import ch.kantibaden.projektunterricht.dao.UserDao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.control.Dialogs.DialogResponse;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Boersenspiel extends Application {
	private boolean loggedIn = false;
	private Parent root = null;

	@Override
	public void start(Stage primaryStage) {

		if (showLogin(primaryStage)) {
			primaryStage.setScene(new Scene(root));
			// macht die Stage sichtbar
			primaryStage.show();
			primaryStage.setResizable(true);
		}
	}

	private boolean showLogin(Stage stage) {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));
		final TextField username = new TextField();
		username.setPromptText("Username");
		final TextField startkapital = new TextField();
		startkapital.setPromptText("Startkapital");
		startkapital.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent inputevent) {
				// filtering all non numeric characters with regex
				if (!inputevent.getCharacter().matches("\\d")) {
					inputevent.consume();// == no input
				}
				if (startkapital.getText().length() + 1 > 15) {
					inputevent.consume();// == no input
				}
			}
		});

		final PasswordField password = new PasswordField();
		password.setPromptText("Password");
		final Button btlogin = new Button();
		btlogin.setText("Login");
		final Button btSignUp = new Button();
		btSignUp.setText("Sign up");

		final Label infolabel = new Label();
		infolabel.setText("");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Startkapital:"), 0, 1);
		grid.add(startkapital, 1, 1);
		grid.add(new Label("Password:"), 0, 2);
		grid.add(password, 1, 2);

		grid.add(btlogin, 0, 5);
		grid.add(btSignUp, 1, 5);

		grid.add(infolabel, 1, 7);

		btlogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
					if (UserDao.login(username.getText(), password.getText())) {

						if (!loadData()) {
							loggedIn = false;
							infolabel.setText("Problem mit dem Netzwerk, probieren Sie es erneut.");
							infolabel.textFillProperty().set(Color.RED);
						} else {
							loggedIn = true;
							infolabel.setText("User gefunden, bitte klicke auf OK um fortzufahren.");
							infolabel.textFillProperty().set(Color.GREEN);
						}
					} else {
						infolabel.setText("User nicht gefunden, probieren Sie erneut.");
						infolabel.textFillProperty().set(Color.RED);
					}
				} else {
					infolabel.setText("Bitte alle Felder ausfüllen.");
					infolabel.textFillProperty().set(Color.RED);
				}
			}
		});

		btSignUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (!username.getText().isEmpty() && !password.getText().isEmpty() && !startkapital.getText().isEmpty()) {
					if (UserDao.signup(username.getText(), password.getText(), startkapital.getText())) {
						if (!loadData()) {
							loggedIn = false;
							infolabel.setText("Problem mit dem Netzwerk, probieren Sie es erneut.");
							infolabel.textFillProperty().set(Color.RED);
						} else {
							loggedIn = true;
							infolabel.setText("User erstellt, bitte klicke auf OK um fortzufahren.");
							infolabel.textFillProperty().set(Color.GREEN);
						}
					} else {
						infolabel.setText("Username wird bereits verwendet, \n bitte wählen Sie einen anderen.");
						infolabel.textFillProperty().set(Color.RED);
					}
				} else {
					infolabel.setText("Bitte alle Felder ausfüllen.");
					infolabel.textFillProperty().set(Color.RED);
				}
			}
		});

		Callback<Void, Void> myCallback = new Callback<Void, Void>() {
			@Override
			public Void call(Void param) {

				return null;
			}
		};

		DialogResponse resp = Dialogs.showCustomDialog(stage, grid, "Bitte verifizieren Sie sich.", "Einloggen", DialogOptions.OK_CANCEL, myCallback);

		System.out.println("Login: User clicked: " + resp);

		if (resp.equals(DialogResponse.OK)) {
			return loggedIn;
		} else {
			return false;
		}
	}

	private boolean loadData() {
		try {
			// loads the fxml
			root = FXMLLoader.load(getClass().getResource("view/BoersenspielView.fxml"));
			return true;
		} catch (IOException e) {
			System.out.println("Wenn hier ein Fehler ist, dann liegt es wahrscheinlich an der Bennenung eines ViewObjektes. (fx:id)");
		}

		return false;
	}

	public static void main(String[] args) {
		launch(args);
	}
}