package ch.kantibaden.projektunterricht;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class EingabeView {

	private Scene scene;

	private GridPane grid;
	private Text scenetitle;

	private Label vornameLB;
	private TextField vornameTF;

	private Label nachnameLB;
	private TextField nachnameTF;

	private Text meldungT;

	private Button okBtn;
	private Button addBtn;
	private HBox hbBtn;

	public EingabeView() {
		// Layout
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		// Ueberschrift
		scenetitle = new Text("Hallo AxxG-Leser");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		// Vorname
		vornameLB = new Label("Vorname:");
		grid.add(vornameLB, 0, 1);

		vornameTF = new TextField();
		grid.add(vornameTF, 1, 1);

		// Nachname
		nachnameLB = new Label("Nachname:");
		grid.add(nachnameLB, 0, 2);

		nachnameTF = new TextField();
		grid.add(nachnameTF, 1, 2);

		// Buttons
		addBtn = new Button("eintragen");
		okBtn = new Button("Alle anzeigen");

		// Buttongruppe
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(addBtn);
		hbBtn.getChildren().add(okBtn);
		grid.add(hbBtn, 1, 4);

		// Meldung
		meldungT = new Text();
		grid.add(meldungT, 1, 6);

		scene = new Scene(grid, 300, 300);
	}

	public void show(Stage stage) {
		stage.setTitle("AxxG - JavaFX MVC Beispiel");
		stage.setScene(scene);
		stage.show();
	}

	// nur Getter von Elementen anlegen, die veraendert werden und/oder dynamisch sind 
	public TextField getVornameTF() {
		return vornameTF;
	}

	public TextField getNachnameTF() {
		return nachnameTF;
	}

	public Text getMeldungT() {
		return meldungT;
	}

	public Button getOkBtn() {
		return okBtn;
	}

	public Button getAddBtn() {
		return addBtn;
	}

}
