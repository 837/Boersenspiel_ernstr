package ch.kantibaden.projektunterricht.controller;


import ch.kantibaden.projektunterricht.dao.UserDao;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {
	private Stage dialogStage;
	
	public void setStage(Stage stage){
		this.dialogStage = stage;
	}

	@FXML
	private void initialize() {
		lbInfoLogin.setTextFill(Color.RED);
		lbInfoSignup.setTextFill(Color.RED);
	}
	
	@FXML
	private void handleLogin(){
		if(txtUsernameLogin.getText().isEmpty() || pwPasswordLogin.getText().isEmpty() ){
			lbInfoLogin.setText("Username und Passwort müssen ausgefüllt sein.");
		}else{
			if(!UserDao.login(txtUsernameLogin.getText(), pwPasswordLogin.getText())){
				lbInfoLogin.setText("Username oder Passwort falsch.");
			}else{
				dialogStage.close();
			}
		}
	}
	
	@FXML
	private void handleSignup(){
		if(txtUsernameSignup.getText().isEmpty() || pwPasswordSignup.getText().isEmpty() || txtStartkapital.getText().isEmpty()){
			lbInfoSignup.setText("Alle drei Felder müssen ausgefüllt sein.");
		}else{
			if(!UserDao.signup(txtUsernameSignup.getText(), pwPasswordSignup.getText(), txtStartkapital.getText())){
				lbInfoSignup.setText("Username wird bereits verwendet.");
			}else{
				dialogStage.close();
			}
		}
	}
	
	
	@FXML private TextField txtUsernameLogin;
	@FXML private TextField txtUsernameSignup;
	@FXML private TextField txtStartkapital;
	@FXML private Label lbInfoLogin;
	@FXML private Label lbInfoSignup;
	@FXML private PasswordField pwPasswordLogin;
	@FXML private PasswordField pwPasswordSignup;
}
