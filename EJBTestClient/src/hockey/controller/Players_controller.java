package hockey.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import entity.PlayersInTeams;
import entity.Teams;
import facade.PlayersInTeamsFacadeBeanRemote;
import facade.TeamsFacadeBeanRemote;
import hockey.Main;
import hockey.PlayersTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Trieda, ktora obsahuje celu funkcionalitu o hracoch.
 * Robi viacere dopyty do databazy, z ktorej vybera hracov na zaklade nejakych kriterii.
 * @author Matus Barabas
 *
 */
public class Players_controller {
	
	private static Stage tradePlayerStage;
	private static Logger LOG;
	private ObservableList<PlayersTable> table_list = FXCollections.observableArrayList();
	private ObservableList<PlayersTable> list = FXCollections.observableArrayList();
	private static List<PlayersInTeams> p = null;

	@FXML
	private ComboBox<String> teams;	
	@FXML
	private TableView<PlayersTable> table;	
	@FXML
	private TableColumn<PlayersTable, String> name;
	@FXML
	private TableColumn<PlayersTable, String> team;
	@FXML
	private TableColumn<PlayersTable, Date> date_from;
	@FXML
	private TableColumn<PlayersTable, Date> date_to;
	@FXML
	private TableColumn<PlayersTable, String> pos;
	@FXML
	private TableColumn<PlayersTable, Integer> goals;	
	@FXML
	private TableColumn<PlayersTable, Integer> assist;
	@FXML
	private TableColumn<PlayersTable, Integer> points;
	
	/**
	 * Inicializacia tabulky, naplnenie tablky datami o hracoh a naplnenie ComboBoxu timami.
	 */
	@FXML
	private void initialize(){
		ObservableList<String> listTeams = FXCollections.observableArrayList();
		listTeams.clear();
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Players_controller.class.getName());
			LOG.error(e);
		}finally{
			TeamsFacadeBeanRemote remote = null;
			PlayersInTeamsFacadeBeanRemote remote1 = null;
			try {
				remote = (TeamsFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//TeamsFacadeBean!facade.TeamsFacadeBeanRemote");
				
			} catch (NamingException e) {
				LOG = Logger.getLogger(Players_controller.class.getName());
				LOG.error(e);
			}finally{
				List<Teams> t = remote.getAllTeams();
				
				for (Teams team : t) {
					listTeams.add(team.getName());
				}
				
				teams.setItems(listTeams);
				
				try {
					remote1 = (PlayersInTeamsFacadeBeanRemote)ctx.lookup
							("ejb:EJBTestEAR/EJBTestServer//PlayersInTeamsFacadeBean!facade.PlayersInTeamsFacadeBeanRemote");
					
				} catch (NamingException e) {
					LOG = Logger.getLogger(Players_controller.class.getName());
					LOG.error(e);
				}finally{
					p = remote1.showPlayers();
				}
			}
		}
		
		name.setCellValueFactory(new PropertyValueFactory<PlayersTable, String>("p_name"));
		team.setCellValueFactory(new PropertyValueFactory<PlayersTable, String>("p_team"));
		date_from.setCellValueFactory(new PropertyValueFactory<PlayersTable, Date>("date_f"));
		date_to.setCellValueFactory(new PropertyValueFactory<PlayersTable, Date>("date_t"));
		pos.setCellValueFactory(new PropertyValueFactory<PlayersTable, String>("p_pos"));
		goals.setCellValueFactory(new PropertyValueFactory<PlayersTable, Integer>("p_goals"));
		assist.setCellValueFactory(new PropertyValueFactory<PlayersTable, Integer>("p_assist"));
		points.setCellValueFactory(new PropertyValueFactory<PlayersTable, Integer>("p_points"));
	}
	
	/**
	 * Spustenie okna na vymenu hracov z timu do timu.
	 */
	@FXML
	private void go_trade(){	
		FXMLLoader loader = new FXMLLoader();
		ResourceBundle r = null;
		
		if(Main.language == true){
			r = ResourceBundle.getBundle("English");
			loader.setResources(r);
		}else{
			r = ResourceBundle.getBundle("Slovencina");
			loader.setResources(r);
		}
		
		loader.setLocation(Main.class.getResource("view/Players_trade.fxml"));
		BorderPane matches = null;
		try {
			matches = loader.load();
		} catch (IOException e) {
			LOG = Logger.getLogger(Players_controller.class.getName());
			LOG.error(e);
		}
		
		tradePlayerStage = new Stage();
		tradePlayerStage.setTitle("Trade player");
		tradePlayerStage.initModality(Modality.WINDOW_MODAL);
		tradePlayerStage.initOwner(Main.primaryStage);
		Scene scene = new Scene(matches);
		tradePlayerStage.setScene(scene);
		tradePlayerStage.showAndWait();
	}
	
	/**
	 * Zobrazenie dat o vsetych hracoch alebo hracoch z konkretneho timu.
	 */
	@FXML
	private void show_players_table(){
		table_list.clear();
		
		if(teams.getValue() == null){
			for (PlayersInTeams x : p) {
				table_list.add(new PlayersTable(x.getPlayer().getName(), x.getTeam().getShortcut(), 
						x.getDate_from(), x.getDate_to(), x.getPlayer().getPos(), x.getPlayer().getGoals(), 
						x.getPlayer().getAssist(), x.getPlayer().getPoints()));
			}
		}else{
			for (PlayersInTeams x : p) {
				if(teams.getValue().equals(x.getTeam().getName())){
					table_list.add(new PlayersTable(x.getPlayer().getName(), x.getTeam().getShortcut(), 
							x.getDate_from(), x.getDate_to(), x.getPlayer().getPos(), x.getPlayer().getGoals(), 
							x.getPlayer().getAssist(), x.getPlayer().getPoints()));
				}
			}
		}
		
		table.setItems(table_list);
	}
	
	/**
	 * Zobrazenie 10 najlepsich hracov na zaklade poctu bodov.
	 */
	@FXML
	private void best_players(){
		list.clear();
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Players_controller.class.getName());
			LOG.error(e);
		}finally{
			PlayersInTeamsFacadeBeanRemote remote = null;
			try {
				remote = (PlayersInTeamsFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//PlayersInTeamsFacadeBean!facade.PlayersInTeamsFacadeBeanRemote");
				
			} catch (NamingException e) {
				LOG = Logger.getLogger(Players_controller.class.getName());
				LOG.error(e);
			}finally{
				List<PlayersInTeams> bestPl = remote.showBestPlayers();
				
				for (PlayersInTeams x : bestPl) {
					list.add(new PlayersTable(x.getPlayer().getName(), x.getTeam().getShortcut(), 
							x.getDate_from(), x.getDate_to(), x.getPlayer().getPos(), x.getPlayer().getGoals(), 
							x.getPlayer().getAssist(), x.getPlayer().getPoints()));
				}
				
				table.setItems(list);
			}
		}
	}
	
	/**
	 * Zobrazenie 10 najlepsich utocnikov na zaklade poctu bodov.
	 */
	@FXML
	private void best_forwards(){
		list.clear();
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Players_controller.class.getName());
			LOG.error(e);
		}finally{
			PlayersInTeamsFacadeBeanRemote remote = null;
			try {
				remote = (PlayersInTeamsFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//PlayersInTeamsFacadeBean!facade.PlayersInTeamsFacadeBeanRemote");
				
			} catch (NamingException e) {
				LOG = Logger.getLogger(Players_controller.class.getName());
				LOG.error(e);
			}finally{
				List<PlayersInTeams> bestPl = remote.showBestForwards();
				
				for (PlayersInTeams x : bestPl) {
					list.add(new PlayersTable(x.getPlayer().getName(), x.getTeam().getShortcut(), 
							x.getDate_from(), x.getDate_to(), x.getPlayer().getPos(), x.getPlayer().getGoals(), 
							x.getPlayer().getAssist(), x.getPlayer().getPoints()));
				}
				
				table.setItems(list);
			}
		}
	}
	
	/**
	 * Zobrazenie 10 najlepsich obrancov podla poctu bodov.
	 */
	@FXML
	private void best_defence(){
		list.clear();
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Players_controller.class.getName());
			LOG.error(e);
		}finally{
			PlayersInTeamsFacadeBeanRemote remote = null;
			try {
				remote = (PlayersInTeamsFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//PlayersInTeamsFacadeBean!facade.PlayersInTeamsFacadeBeanRemote");
				
			} catch (NamingException e) {
				LOG = Logger.getLogger(Players_controller.class.getName());
				LOG.error(e);
			}finally{
				List<PlayersInTeams> bestPl = remote.showBestDefence();
				
				for (PlayersInTeams x : bestPl) {
					list.add(new PlayersTable(x.getPlayer().getName(), x.getTeam().getShortcut(), 
							x.getDate_from(), x.getDate_to(), x.getPlayer().getPos(), x.getPlayer().getGoals(), 
							x.getPlayer().getAssist(), x.getPlayer().getPoints()));
				}
				
				table.setItems(list);
			}
		}
	}
	
	/**
	 * Zobrazi hraca, ktory ma na svojom konte najviac golov.
	 */
	@FXML
	private void most_goals(){
		table_list.clear();
		int max = 0;
		int pos = 0;
		
		for (int i = 0; i < p.size(); i++) {
			if(max < (p.get(i).getPlayer().getGoals())){
				max = p.get(i).getPlayer().getGoals();
				pos = i;
			}
		}
		table_list.add(new PlayersTable(p.get(pos).getPlayer().getName(), p.get(pos).getTeam().getShortcut(), 
				p.get(pos).getDate_from(), p.get(pos).getDate_to(), p.get(pos).getPlayer().getPos(), p.get(pos).getPlayer().getGoals(), 
				p.get(pos).getPlayer().getAssist(), p.get(pos).getPlayer().getPoints()));
		
		table.setItems(table_list);
	}
	
	/**
	 * Zobrazi hraca, ktory ma najviac asistencii.
	 */
	@FXML
	private void most_assist(){
		table_list.clear();
		int max = 0;
		int pos = 0;
		
		for (int i = 0; i < p.size(); i++) {
			if(max < (p.get(i).getPlayer().getAssist())){
				max = p.get(i).getPlayer().getAssist();
				pos = i;
			}
		}
		table_list.add(new PlayersTable(p.get(pos).getPlayer().getName(), p.get(pos).getTeam().getShortcut(), 
				p.get(pos).getDate_from(), p.get(pos).getDate_to(), p.get(pos).getPlayer().getPos(), p.get(pos).getPlayer().getGoals(), 
				p.get(pos).getPlayer().getAssist(), p.get(pos).getPlayer().getPoints()));
		
		table.setItems(table_list);
	}
	
	/**
	 * Hraci, ktory uskutocnili prestup.
	 */
	@FXML
	private void trades(){
		initialize();
		table_list.clear();
		
		for (PlayersInTeams x : p) {
			if(x.getDate_to() != null){
				table_list.add(new PlayersTable(x.getPlayer().getName(), x.getTeam().getShortcut(), 
						x.getDate_from(), x.getDate_to(), x.getPlayer().getPos(), x.getPlayer().getGoals(), 
						x.getPlayer().getAssist(), x.getPlayer().getPoints()));
			}
		}

		table.setItems(table_list);
	}
	
	/**
	 * Zobrazenie okna s informaciami a hracoch.
	 */
	public static  void show_players(){
		FXMLLoader loader = new FXMLLoader();
		ResourceBundle r = null;
		
		if(Main.language == true){
			r = ResourceBundle.getBundle("English");
			loader.setResources(r);
		}else{
			r = ResourceBundle.getBundle("Slovencina");
			loader.setResources(r);
		}
				
		loader.setLocation(Main.class.getResource("view/Players.fxml"));
		BorderPane players = null;
		try {
			players = loader.load();
		} catch (IOException e) {
			LOG = Logger.getLogger(Players_controller.class.getName());
			LOG.error(e);
		}
		Main.mainLayout.setCenter(players);
	}
	
	/**
	 * Zatvorenie okna na vymenu hracov.
	 */
	public static void close_trade(){
		tradePlayerStage.close();
	}
}
