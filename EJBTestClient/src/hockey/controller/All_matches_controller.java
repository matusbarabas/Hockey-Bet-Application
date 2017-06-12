package hockey.controller;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import entity.Matches;
import entity.Teams;
import facade.GamesFacadeBeanRemote;
import facade.TeamsFacadeBeanRemote;
import hockey.GamesTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Trieda obsahujuca funkcionalitu okna o vsetkych odohranych zapasoch.
 * Zobrazuje prehlad vsetkych zapasov alebo zapasov konkretnych timov.
 * @author Matus Barabas
 *
 */
public class All_matches_controller {
	
	private static Logger LOG;
	
	ObservableList<String> list = FXCollections.observableArrayList();
	ObservableList<GamesTable> table_list = FXCollections.observableArrayList();
	
	@FXML
	private ComboBox<String> teams;	
	@FXML
	private TableView<GamesTable> table;	
	@FXML
	private TableColumn<GamesTable, Integer> id;
	@FXML
	private TableColumn<GamesTable, String> home_team;
	@FXML
	private TableColumn<GamesTable, Integer> home_score;
	@FXML
	private TableColumn<GamesTable, Integer> guest_score;
	@FXML
	private TableColumn<GamesTable, String> guest_team;	
	@FXML
	private TableColumn<GamesTable, String> ot_so;
	@FXML
	private TableColumn<GamesTable, String> datum;
	
	/**
	 * Inicializacia tabulky a naplnenie ComboBoxu vsetkymi timami.
	 * 
	 */
	@FXML
	private void initialize(){
		list.clear();
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(All_matches_controller.class.getName());
			LOG.error(e);
		}finally{
			TeamsFacadeBeanRemote remote = null;
			try {
				remote = (TeamsFacadeBeanRemote)ctx.lookup("ejb:EJBTestEAR/EJBTestServer//TeamsFacadeBean!facade.TeamsFacadeBeanRemote");
			} catch (NamingException e) {
				LOG = Logger.getLogger(All_matches_controller.class.getName());
				LOG.error(e);
			}finally{
				List<Teams> t = remote.getAllTeams();
				
				for (Teams team : t) {
					list.add(team.getName());
				}
			}
		}

		id.setCellValueFactory(new PropertyValueFactory<GamesTable, Integer>("id"));
		home_team.setCellValueFactory(new PropertyValueFactory<GamesTable, String>("h_t"));
		guest_team.setCellValueFactory(new PropertyValueFactory<GamesTable, String>("v_t"));
		home_score.setCellValueFactory(new PropertyValueFactory<GamesTable, Integer>("goals_home"));
		guest_score.setCellValueFactory(new PropertyValueFactory<GamesTable, Integer>("goals_visitor"));
		ot_so.setCellValueFactory(new PropertyValueFactory<GamesTable, String>("note"));
		datum.setCellValueFactory(new PropertyValueFactory<GamesTable, String>("date_of_match"));
		
		teams.setItems(list);
	}
	
	/**
	 * Zobrazenie prehladu vsetkych zapasov.
	 * Zobrazenie zapasov konkretneho timu.
	 */
	@FXML
	private void show_games(){
		table_list.clear();
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(All_matches_controller.class.getName());
			LOG.error(e);
		}finally{
			GamesFacadeBeanRemote remote = null;
			try {
				remote = (GamesFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//GamesFacadeBean!facade.GamesFacadeBeanRemote");
			} catch (NamingException e) {
				LOG = Logger.getLogger(All_matches_controller.class.getName());
				LOG.error(e);
			}finally{
				List<Matches> games = remote.showGames();
				
				if(teams.getValue() == null){
					for (Matches m : games) {
						table_list.add(new GamesTable(m.getId(), m.getHome().getName(), m.getVisitor().getName(), m.getGoals_home(),
								m.getGoals_visitor(), m.getNote(), m.getDate_of_match().toString()));
					}
				}else{
					for (Matches m : games) {
						if((m.getHome().getName().equals(teams.getValue())) || (m.getVisitor().getName().equals(teams.getValue()))){
							table_list.add(new GamesTable(m.getId(), m.getHome().getName(), m.getVisitor().getName(), m.getGoals_home(),
									m.getGoals_visitor(), m.getNote(), m.getDate_of_match().toString()));
						}
					}
				}
			}
		}
		
		table.setItems(table_list);
	}
	
	/**
	 * Zatvorenie okna.
	 */
	@FXML
	private void close_all_matches(){
		Matches_controller.close_showMatches();
	}	
}
