package hockey.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import entity.Account;
import entity.BettingEvents;
import facade.AccountFacadeBeanRemote;
import facade.AccountTransactionFacadeBeanRemote;
import facade.BettingEventsFacadeBeanRemote;
import facade.PdfFacadeBeanRemote;
import hockey.StatisticsOfBetTable;
import hockey.BettingEventsTable;
import hockey.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * Trieda, ktora obsahuje celu funkcionalitu okna Account.
 * Zobrazuje data do tabuliek v okne Account.
 * Zobrazuje statistiky stavkoveho uctu.
 * @author Matus Barabas
 *
 */
public class Account_controller {
	final double MONEY_ACCOUNT = 50; //konstanta, ktora inicializuje zaciatocnu sumu na ucte
	private static Logger LOG;
	
	ObservableList<StatisticsOfBetTable> list_table1 = FXCollections.observableArrayList();
	ObservableList<BettingEventsTable> list_table2 = FXCollections.observableArrayList();
	List<BettingEvents> b;
	List<Account> a;
	BettingEvents currentevent;
	double sum = 0;
	
	@FXML
	private TableView<StatisticsOfBetTable> table1;
	@FXML
	private TableColumn<StatisticsOfBetTable, Integer> a_id;
	@FXML
	private TableColumn<StatisticsOfBetTable, String> a_home_team;
	@FXML
	private TableColumn<StatisticsOfBetTable, Integer> a_hs;
	@FXML
	private TableColumn<StatisticsOfBetTable, Integer> a_gs;
	@FXML
	private TableColumn<StatisticsOfBetTable, String> a_n;
	@FXML
	private TableColumn<StatisticsOfBetTable, String> a_guest_team;
	@FXML
	private TableColumn<StatisticsOfBetTable, Integer> a_bet;
	@FXML
	private TableColumn<StatisticsOfBetTable, Double> a_money_bet;	
	@FXML
	private TableColumn<StatisticsOfBetTable, Double> a_may_win;
	@FXML
	private TableColumn<StatisticsOfBetTable, String> a_result;
	@FXML
	private TableColumn<StatisticsOfBetTable, Date> a_date_of_bet;
	
	/***************************************************************/
	
	@FXML
	private TableView<BettingEventsTable> table2;
	@FXML
	private TableColumn<BettingEventsTable, Integer> s_id;
	@FXML
	private TableColumn<BettingEventsTable, String> s_home_team;
	@FXML
	private TableColumn<BettingEventsTable, String> s_guest_team;
	@FXML
	private TableColumn<BettingEventsTable, Double> s_h_line;	
	@FXML
	private TableColumn<BettingEventsTable, Double> s_d_line;
	@FXML
	private TableColumn<BettingEventsTable, Double> s_v_line;
	
	/***************************************************************/
	
	@FXML
	private TextField f_id;
	@FXML
	private TextField f_bet;
	@FXML
	private TextField f_money;
	@FXML
	private Label f_even_win;
	
	/***************************************************************/
	
	@FXML
	private TextField c_id;
	@FXML
	private RadioButton c_w;
	@FXML
	private RadioButton c_l;
	@FXML
	private RadioButton c_c;
	
	/***************************************************************/
	
	@FXML
	private Label act_acc;
	@FXML
	private Label benefit;
	@FXML
	private Label games_win;
	@FXML
	private Label games_lost;
	@FXML
	private Label games_canc;
	@FXML
	private Label success;
	
	/***************************************************************/
	
	/**
	 * Inicializacna metoda, ktora vola funkcie, ktore vykonavaju dopyt do databazy 
	 * a naplnia vsetky tabulky potrebnymi datami.
	 */
	@FXML
	private void initialize(){
		s_id.setCellValueFactory(new PropertyValueFactory<BettingEventsTable, Integer>("id"));
		s_home_team.setCellValueFactory(new PropertyValueFactory<BettingEventsTable, String>("home_team"));
		s_guest_team.setCellValueFactory(new PropertyValueFactory<BettingEventsTable, String>("guest_team"));
		s_h_line.setCellValueFactory(new PropertyValueFactory<BettingEventsTable, Double>("h_line"));
		s_d_line.setCellValueFactory(new PropertyValueFactory<BettingEventsTable, Double>("d_line"));
		s_v_line.setCellValueFactory(new PropertyValueFactory<BettingEventsTable, Double>("v_line"));
		
		a_id.setCellValueFactory(new PropertyValueFactory<StatisticsOfBetTable, Integer>("id"));
		a_home_team.setCellValueFactory(new PropertyValueFactory<StatisticsOfBetTable, String>("home_team"));
		a_hs.setCellValueFactory(new PropertyValueFactory<StatisticsOfBetTable, Integer>("hs"));
		a_gs.setCellValueFactory(new PropertyValueFactory<StatisticsOfBetTable, Integer>("gs"));
		a_n.setCellValueFactory(new PropertyValueFactory<StatisticsOfBetTable, String>("note"));
		a_guest_team.setCellValueFactory(new PropertyValueFactory<StatisticsOfBetTable, String>("guest_team"));
		a_bet.setCellValueFactory(new PropertyValueFactory<StatisticsOfBetTable, Integer>("bet"));
		a_money_bet.setCellValueFactory(new PropertyValueFactory<StatisticsOfBetTable, Double>("money_bet"));
		a_may_win.setCellValueFactory(new PropertyValueFactory<StatisticsOfBetTable, Double>("may_win"));
		a_result.setCellValueFactory(new PropertyValueFactory<StatisticsOfBetTable, String>("result"));
		a_date_of_bet.setCellValueFactory(new PropertyValueFactory<StatisticsOfBetTable, Date>("date_of_bet"));
		
		show_stats();
		show_betting_events();
		update_stats();
	}
	
	/**
	 * Zobrazenie stavok spolu s kurzami do tabulky.
	 */
	private void show_betting_events(){
		list_table2.clear();

		Context ctx = null;
		BettingEventsFacadeBeanRemote remote = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Account_controller.class.getName());
			LOG.error(e);
		}finally{
			try {
				remote = (BettingEventsFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//BettingEventsFacadeBean!facade.BettingEventsFacadeBeanRemote");
			} catch (NamingException e) {
				LOG = Logger.getLogger(Account_controller.class.getName());
				LOG.error(e);
			}finally{
				b = remote.showAllBets();
				
				for (BettingEvents x : b) {
					list_table2.add(new BettingEventsTable
							(x.getId(), x.getGames().getHome().getName(), x.getGames().getVisitor().getName(), 
									x.getHome_line(), x.getDraw_line(), x.getVisitor_line()));
				}
				
				table2.setItems(list_table2);
			}
		}
	}
	
	/**
	 * Vypocitanie eventualnej vyhry, podla kurzu v tabulke.
	 */
	@FXML
	private void eventual_win(){
		currentevent = b.stream().filter(a -> a.getId() == Integer.parseInt(f_id.getText())).findFirst().get();
		
		switch(Integer.parseInt(f_bet.getText())){
			case(1):{
				sum = currentevent.getHome_line() * Double.parseDouble(f_money.getText());
				break;
			}
			case(0):{
				sum = currentevent.getDraw_line() * Double.parseDouble(f_money.getText());
				break;
			}
			case(2):{
				sum = currentevent.getVisitor_line() * Double.parseDouble(f_money.getText());
				break;
			}	
		}
		f_even_win.setText(String.valueOf(sum) + "€");
	}	
	
	/**
	 * Pridanie stavkovej prileztosti do uctu spolu so vsetkymi informaciami.
	 */
	@FXML
	private void add_to_account(){
		AccountTransactionFacadeBeanRemote remote = null;
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Account_controller.class.getName());
			LOG.error(e);
		}finally{	
			try {
				remote = (AccountTransactionFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//AccountTransactionFacadeBean!facade.AccountTransactionFacadeBeanRemote");
			} catch (NamingException e) {
				LOG = Logger.getLogger(Account_controller.class.getName());
				LOG.error(e);
			}finally{		
				Account acc = new Account();
				acc.setBetting_event(currentevent);
				acc.setBet(Integer.parseInt(f_bet.getText()));
				acc.setMoney_bet(Double.parseDouble(f_money.getText()));
				acc.setMay_win(sum);
				
				remote.addBet(acc);
				show_stats();
			}
		}
	}
	
	/**
	 * Zobrazenie tabulky o statistikach na ucte.
	 */
	private void show_stats(){
		list_table1.clear();

		Context ctx = null;
		AccountFacadeBeanRemote remote = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Account_controller.class.getName());
			LOG.error(e);
		}finally{
			try {
				remote = (AccountFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//AccountFacadeBean!facade.AccountFacadeBeanRemote");
			} catch (NamingException e) {
				LOG = Logger.getLogger(Account_controller.class.getName());
				LOG.error(e);
			}finally{
				a = remote.showStats();
				
				for (Account x : a) {
					list_table1.add(new StatisticsOfBetTable(x.getId(), 
							x.getBetting_event().getGames().getHome().getShortcut(), 
							x.getBetting_event().getGames().getGoals_home(), 
							x.getBetting_event().getGames().getGoals_visitor(),
							x.getBetting_event().getGames().getNote(), 
							x.getBetting_event().getGames().getVisitor().getShortcut(), 
							x.getBet(), x.getMoney_bet(), x.getMay_win(), x.getResult(), 
							x.getBetting_event().getDate_of_bet()));
				}
				
				table1.setItems(list_table1);
			}
		}
	}
	
	/**
	 * Aktualizacia udajov v tabulke o statistikach na ucte, oznacenie zapasu ako vitazneho,
	 * prehraneho alebo zruseneho a na zaklade tejto hodnoty naplnenie troch dalsich stlpcov.
	 */
	@FXML
	private void save(){
		String pom = "";
		
		if(c_w.isSelected()){
			pom = "WIN";
		}else if(c_l.isSelected()){
			pom = "LOST";
		}else if(c_c.isSelected()){
			pom = "CANC";
		}
		
		Context ctx = null;
		AccountTransactionFacadeBeanRemote remote = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Account_controller.class.getName());
			LOG.error(e);
		}finally{
			try {
				remote = (AccountTransactionFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//AccountTransactionFacadeBean!facade.AccountTransactionFacadeBeanRemote");
			} catch (NamingException e) {
				LOG = Logger.getLogger(Account_controller.class.getName());
				LOG.error(e);
			}finally{
				remote.updateStats(Integer.parseInt(c_id.getText()), pom);	
			}
		}
		
		show_stats();
		update_stats();
	}
	
	/**
	 * Aktualizacia udajov o statistikach na ucte.
	 */
	@FXML
	private void update_stats(){
		double actsum = (a.stream().mapToDouble(e -> e.getProfit()).sum()) - (a.stream().mapToDouble(e -> e.getLoss()).sum());
		double win = a.stream().filter(e -> e.getResult().equals("WIN")).count();
		double lost = a.stream().filter(e -> e.getResult().equals("LOST")).count();
		double canc = a.stream().filter(e -> e.getResult().equals("CANC")).count();
		
		act_acc.setText((round(actsum, 2) + MONEY_ACCOUNT) + " €");
		benefit.setText((round(actsum, 2)) + " €");
		games_win.setText(Double.toString(win));
		games_lost.setText(Double.toString(lost));
		games_canc.setText(Double.toString(canc));
		success.setText(round(((win+canc)/(win + lost + canc)) * 100, 2) + " %");
	}
	
	/**
	 * Vymazanie stavkovej prilezitosti z tabulky.
	 */
	@FXML
	private void delete(){
		Context ctx = null;
		AccountTransactionFacadeBeanRemote remote = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Account_controller.class.getName());
			LOG.error(e);
		}finally{
			try {
				remote = (AccountTransactionFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//AccountTransactionFacadeBean!facade.AccountTransactionFacadeBeanRemote");
			} catch (NamingException e) {
				LOG = Logger.getLogger(Account_controller.class.getName());
				LOG.error(e);
			}finally{
				remote.deleteBet(Integer.parseInt(c_id.getText()));
			}
		}
				
		show_stats();
		update_stats();
	}
	
	/**
	 * Zobrazenie okna Account.
	 */
	public static void show_account(){
		FXMLLoader loader = new FXMLLoader();
		ResourceBundle r = null;
		
		if(Main.language == true){
			r = ResourceBundle.getBundle("English");
			loader.setResources(r);
		}else{
			r = ResourceBundle.getBundle("Slovencina");
			loader.setResources(r);
		}
		loader.setLocation(Main.class.getResource("view/Account.fxml"));
		BorderPane account = null;
			try {
				account = loader.load();
			} catch (IOException e) {
				LOG = Logger.getLogger(Account_controller.class.getName());
				LOG.error(e);
			}

		Main.mainLayout.setCenter(account);
	}
	
	/**
	 * Funkcia na zaokruhlovanie cisel na dany pocet desatinnych miest.
	 * @param value
	 * @param places
	 * @return
	 */
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	/**
	 * Funkcia na vytvorenie PDF suboru, ktory obsahuje statistiku o stavkach.
	 */
	@FXML
	private void createPdf(){
		Context ctx = null;
		PdfFacadeBeanRemote remote = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Account_controller.class.getName());
			LOG.error(e);
		}finally{
			try {
				remote = (PdfFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//PdfFacadeBean!facade.PdfFacadeBeanRemote");
			} catch (NamingException e) {
				LOG = Logger.getLogger(Account_controller.class.getName());
				LOG.error(e);
			}finally{
				ResourceBundle r = null;
				
				if(Main.language == true){
					r = ResourceBundle.getBundle("English");
				}else{
					r = ResourceBundle.getBundle("Slovencina");
				}
				boolean b = remote.createNewPdf();
				if(b == true){
					JOptionPane.showMessageDialog(null, r.getString("pdf"));
				}
			}
		}
	}
}
