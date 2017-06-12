package hockey.controller;

import java.io.IOException;
import hockey.Main;
import javafx.fxml.FXML;

/**
 * Trieda, ktora je kontrolerom pre hlavnu obrazovku.
 * Vola metody inych tried na zobrazenie danych okien.
 * @author Matus Barabas
 *
 */
public class Main_controller {
	
	@FXML
	private void go_players() throws IOException{
		Players_controller.show_players();
	}
	
	@FXML
	private void go_teams() throws IOException{
		Teams_controller.show_teams();
	}
	
	@FXML
	private void go_matches() throws IOException{
		Matches_controller.show_matches();
	}
	
	@FXML
	private void go_main() throws IOException{
		Main.show_main_window();
	}
	
	@FXML
	private void go_account() throws IOException{
		Account_controller.show_account();
	}
	
	@FXML
	private void go_slovencina() throws IOException{
		Main.language = false;
		hockey.Main.show_main_window();
	}
	
	@FXML
	private void go_english() throws IOException{
		Main.language = true;
		hockey.Main.show_main_window();
	}
}
