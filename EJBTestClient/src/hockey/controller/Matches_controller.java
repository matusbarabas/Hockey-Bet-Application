package hockey.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import entity.BettingEvents;
import entity.Matches;
import entity.Teams;
import entity.Teams1;
import facade.BettingEventsTransactionFacadeBeanRemote;
import facade.GamesFacadeBeanRemote;
import facade.GamesTransactionFacadeBeanRemote;
import facade.TeamsFacadeBeanRemote;
import hockey.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Trieda obsahujuca funkcionalitu okna o zapasoch.
 * Vykonava vkladanie dat do databazy.
 * @author Matus Barabas
 *
 */
public class Matches_controller {
	private static Stage tradePlayerStage;
	private static Logger LOG;
	ObservableList<String> list = FXCollections.observableArrayList();
	List<Teams> t = null;
	
	@FXML
	private ComboBox<String> g_home_team;
	@FXML
	private ComboBox<String> g_guest_team;
	@FXML
	private TextField g_hg;
	@FXML
	private TextField g_vg;
	@FXML
	private CheckBox g_ot;
	@FXML
	private CheckBox g_so;
	@FXML
	private DatePicker g_date;	
	@FXML
	private TextField h_line;
	@FXML
	private TextField d_line;
	@FXML
	private TextField g_line;
	@FXML
	private DatePicker b_date;	
	
	/**
	 * Inicializacia tabulky, naplnenie ComboBoxov nazvami timov.
	 */
	@FXML
	private void initialize(){
		list.clear();
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Matches_controller.class.getName());
			LOG.error(e);
		}finally{
			TeamsFacadeBeanRemote remote = null;
			try {
				remote = (TeamsFacadeBeanRemote)ctx.lookup("ejb:EJBTestEAR/EJBTestServer//TeamsFacadeBean!facade.TeamsFacadeBeanRemote");
			} catch (NamingException e) {
				LOG = Logger.getLogger(Matches_controller.class.getName());
				LOG.error(e);
			}finally{
				
				t = remote.getAllTeams();
				
				for (Teams team : t) {
					list.add(team.getName());
				}
			}
		}
		g_home_team.setItems(list);
		g_guest_team.setItems(list);

	}
	
	/**
	 * Pridane zapasu do tabulky games_14_15 na zaklade informacii zadanych pouzivatelom.
	 */
	@FXML
	private void add_to_games(){
		GamesTransactionFacadeBeanRemote remote = null;
		TeamsFacadeBeanRemote remote2 = null;
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Matches_controller.class.getName());
			LOG.error(e);
		}finally{
			try {
				remote = (GamesTransactionFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//GamesTransactionFacadeBean!facade.GamesTransactionFacadeBeanRemote");
				remote2 = (TeamsFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//TeamsFacadeBean!facade.TeamsFacadeBeanRemote");
			} catch (NamingException e) {
				LOG = Logger.getLogger(Matches_controller.class.getName());
				LOG.error(e);
			}finally{			
				Matches game = new Matches();
				game.setDate_of_match(Date.valueOf(g_date.getValue()));
				game.setGoals_home(Integer.parseInt(g_hg.getText()));
				game.setGoals_visitor(Integer.parseInt(g_vg.getText()));
				
				Teams t = remote2.getTeam(g_guest_team.getValue());
				game.setVisitor(t);
				
				Teams1 t1 = remote2.getTeam1(g_home_team.getValue());
				game.setHome(t1);
				
				if(g_ot.isSelected()){
					game.setNote("OT");
				}else if(g_so.isSelected()){
					game.setNote("SO");
				}else{
					game.setNote(null);
				}
				
				remote.addGame(game);
			}
		}
	}
	
	/**
	 * Vlozenie dat do tabulky games_14_15 a taktiez do tabulky betting_events.
	 */
	@FXML 
	private void add_to_g_and_b(){
		GamesFacadeBeanRemote remote = null;
		TeamsFacadeBeanRemote remote2 = null;
		BettingEventsTransactionFacadeBeanRemote remote3 = null;
		GamesTransactionFacadeBeanRemote remote4 = null;
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Matches_controller.class.getName());
			LOG.error(e);
		}finally{
			try {
				remote = (GamesFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//GamesFacadeBean!facade.GamesFacadeBeanRemote");
				remote2 = (TeamsFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//TeamsFacadeBean!facade.TeamsFacadeBeanRemote");
				remote3 = (BettingEventsTransactionFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//BettingEventsTransactionFacadeBean!facade.BettingEventsTransactionFacadeBeanRemote");
				remote4 = (GamesTransactionFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//GamesTransactionFacadeBean!facade.GamesTransactionFacadeBeanRemote");
			} catch (NamingException e) {
				LOG = Logger.getLogger(Matches_controller.class.getName());
				LOG.error(e);
			}finally{
				Matches game = new Matches();
				game.setDate_of_match(Date.valueOf(g_date.getValue()));
				game.setGoals_home(Integer.parseInt(g_hg.getText()));
				game.setGoals_visitor(Integer.parseInt(g_vg.getText()));
				
				Teams t = remote2.getTeam(g_guest_team.getValue());
				game.setVisitor(t);
				
				Teams1 t1 = remote2.getTeam1(g_home_team.getValue());
				game.setHome(t1);
				
				if(g_ot.isSelected()){
					game.setNote("OT");
				}else if(g_so.isSelected()){
					game.setNote("SO");
				}else{
					game.setNote(null);
				}
				
				remote4.addGame(game);
			
				Matches g = remote.getGame(Date.valueOf(g_date.getValue()), t, Integer.parseInt(g_vg.getText()), t1, Integer.parseInt(g_hg.getText()));
				
				BettingEvents bet = new BettingEvents();
				bet.setGames(g);
				bet.setHome_line(Double.parseDouble(h_line.getText()));
				bet.setDraw_line(Double.parseDouble(d_line.getText()));
				bet.setVisitor_line(Double.parseDouble(g_line.getText()));
				bet.setDate_of_bet(Date.valueOf(b_date.getValue()));
				
				remote3.addBet(bet);
			}
		}
	}
	
	/**
	 * Volanie metody na zobrazenie okna o vsetkych odohranych zapasoch.
	 */
	@FXML
	private void go_showMatches(){
		FXMLLoader loader = new FXMLLoader();
		ResourceBundle r = null;
		
		if(Main.language == true){
			r = ResourceBundle.getBundle("English");
			loader.setResources(r);
		}else{
			r = ResourceBundle.getBundle("Slovencina");
			loader.setResources(r);
		}
		loader.setLocation(Main.class.getResource("view/Matches_all.fxml"));
		BorderPane matches = null;
		try {
			matches = loader.load();
		} catch (IOException e) {
			LOG = Logger.getLogger(Matches_controller.class.getName());
			LOG.error(e);
		}
		
		tradePlayerStage = new Stage();
		tradePlayerStage.setTitle("All matches");
		tradePlayerStage.initModality(Modality.NONE);
		tradePlayerStage.initOwner(Main.primaryStage);
		Scene scene = new Scene(matches);
		tradePlayerStage.setScene(scene);
		tradePlayerStage.showAndWait();
		
	}
	
	/**
	 * Spustenie okna na pridavanie zapasov.
	 */
	public static void show_matches(){
		FXMLLoader loader = new FXMLLoader();
		ResourceBundle r = null;
		
		if(Main.language == true){
			r = ResourceBundle.getBundle("English");
			loader.setResources(r);
		}else{
			r = ResourceBundle.getBundle("Slovencina");
			loader.setResources(r);
		}
		
		loader.setLocation(Main.class.getResource("view/Matches.fxml"));
		BorderPane matches = null;
		try {
			matches = loader.load();
		} catch (IOException e) {
			LOG = Logger.getLogger(Matches_controller.class.getName());
			LOG.error(e);
		}		
		Main.mainLayout.setCenter(matches);
	}
	
	public static void close_showMatches(){
		tradePlayerStage.close();
	}
}
