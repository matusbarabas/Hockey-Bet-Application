package hockey.controller;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import entity.Teams;
import entity.Teams1;
import facade.TeamsFacadeBeanRemote;
import facade.TeamsTransactionFacadeBeanRemote;
import hockey.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Trieda obsahujuca funkcionalitu o timoch.
 * Umoznuje pridanie timu do databazy.
 * @author Matus Barabas
 *
 */
public class Teams_controller {	
	@FXML
	private GridPane aaa;
	@FXML
	private Label info;
	@FXML 
	private TableView<Teams> table;	
	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn name;	
	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn conference;	
	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn devision;
	@FXML
	private TextField team_name;
	@FXML
	private TextField team_devision;
	@FXML
	private TextField team_conference;
	@FXML
	private TextField team_shortcut;
	
	private static Logger LOG;
	private ObservableList<Teams> list = FXCollections.observableArrayList();

	/**
	 * Inicializacia tabulky.
	 */
	@SuppressWarnings("unchecked")
	@FXML
	private void initialize(){
		name.setCellValueFactory(new PropertyValueFactory<Teams, String>("name"));
		conference.setCellValueFactory(new PropertyValueFactory<Teams, String>("conference"));
		devision.setCellValueFactory(new PropertyValueFactory<Teams, String>("devision"));
	}
	
	/**
	 * Zobrazenie vsetkych timov do tabulky.
	 */
	@FXML
	private void show(){
		List<Teams> teams;
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Teams_controller.class.getName());
			LOG.error(e);
		}finally{
			TeamsFacadeBeanRemote remote = null;
			try {
				remote = (TeamsFacadeBeanRemote)ctx.lookup("ejb:EJBTestEAR/EJBTestServer//TeamsFacadeBean!facade.TeamsFacadeBeanRemote");
			} catch (NamingException e) {
				LOG = Logger.getLogger(Teams_controller.class.getName());
				LOG.error(e);
			}finally{
				teams = remote.getAllTeams();
				list.addAll(teams);
				
				table.setItems(list);
			}
		}
	}
	
	/**
	 * Pridanie timu do databazy.
	 */
	@FXML
	private void add_team(){
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Teams_controller.class.getName());
			LOG.error(e);
		}finally{
			TeamsTransactionFacadeBeanRemote remote = null;
			try {
				remote = (TeamsTransactionFacadeBeanRemote)ctx.lookup("ejb:EJBTestEAR/EJBTestServer//TeamsTransactionFacadeBean!facade.TeamsTransactionFacadeBeanRemote");
			} catch (NamingException e) {
				LOG = Logger.getLogger(Teams_controller.class.getName());
				LOG.error(e);
			}finally{			
				Teams team = new Teams();
				team.setName(team_name.getText());
				team.setConference(team_conference.getText());
				team.setDevision(team_devision.getText());
				team.setShortcut(team_shortcut.getText());
				
				Teams1 team1 = new Teams1();
				team1.setName(team_name.getText());
				team1.setConference(team_conference.getText());
				team1.setDevision(team_devision.getText());
				team1.setShortcut(team_shortcut.getText());
				
				if(Main.language == true){
					info.setText("TEAM ADDED");
				}else{
					info.setText("TIM PRIDANY");
				}
				remote.addTeam(team);
				remote.addTeam1(team1);
			}
		}
	}
	
	/**
	 * Zobrazenie okna timov.
	 */
	public static void show_teams(){
		FXMLLoader loader = new FXMLLoader();
		ResourceBundle r = null;
		
		if(Main.language == true){
			r = ResourceBundle.getBundle("English");
			loader.setResources(r);
		}else{
			r = ResourceBundle.getBundle("Slovencina");
			loader.setResources(r);
		}
		
		loader.setLocation(Main.class.getResource("view/Teams.fxml"));
		BorderPane teams = null;
		try {
			teams = loader.load();
		} catch (IOException e) {
			LOG = Logger.getLogger(Teams_controller.class.getName());
			LOG.error(e);
		}
		Main.mainLayout.setCenter(teams);
	}
}
