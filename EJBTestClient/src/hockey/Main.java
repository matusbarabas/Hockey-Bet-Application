package hockey;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.apache.log4j.Logger;

/**
 * Trieda Main, v ktorej sa spusta aplikacia.
 * Nacitava hlavne okno.
 * @author Matus Barabas
 *
 */
public class Main extends Application {
	public static boolean language;
	public static Stage primaryStage;
	public static BorderPane mainLayout;
	private static Logger LOG;
	
	@Override
	public void start(Stage primaryStage){
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("HOCKEY-BET-PASSION");
		
		show_main_window();
	}
	
	/**
	 * Zobrazenie hlavneho okna.
	 */
	public static void show_main_window(){
		ResourceBundle r = null;
		
		if(language == true){
			r = ResourceBundle.getBundle("English");
		}else{
			r = ResourceBundle.getBundle("Slovencina");
		}
		
		try {
			mainLayout = FXMLLoader.load(Main.class.getResource("view/Main_window.fxml"), r);
		} catch (IOException e) {
			LOG.error(e);
		}
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args){
		language = true;
		launch(args);
	}
}
