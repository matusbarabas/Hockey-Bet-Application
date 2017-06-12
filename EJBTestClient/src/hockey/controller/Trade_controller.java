package hockey.controller;

import java.sql.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import entity.PlayersInTeams;
import entity.Teams;
import facade.PlayersInTeamsFacadeBeanRemote;
import facade.TeamsFacadeBeanRemote;
import facade.TradeTransactionFacadeBeanRemote;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Trieda obsahujuca funkcionalitu o preradeni hraca z timu do timu.
 * @author Matus Barabas
 *
 */
public class Trade_controller {
	
	private static Logger LOG;
	
	@FXML
	private TextField t_name;
	@FXML
	private TextField t_new_team;
	@FXML
	private DatePicker d_picker;
	
	/**
	 * Najprv aktualizacia udaju vo forme vlozenie datumu prestupu k danemu hracovi 
	 * a potom vytvorenie noveho zaznamu uz s novym timom.
	 */
	
	@FXML
	private void make_trade(){
		Context ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			LOG = Logger.getLogger(Trade_controller.class.getName());
			LOG.error(e);
		}finally{
			TradeTransactionFacadeBeanRemote remote = null;
			TeamsFacadeBeanRemote remote1 = null;
			PlayersInTeamsFacadeBeanRemote remote2 = null;
			try {
				remote = (TradeTransactionFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//TradeTransactionFacadeBean!facade.TradeTransactionFacadeBeanRemote");
				remote1 = (TeamsFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//TeamsFacadeBean!facade.TeamsFacadeBeanRemote");
				remote2 = (PlayersInTeamsFacadeBeanRemote)ctx.lookup
						("ejb:EJBTestEAR/EJBTestServer//PlayersInTeamsFacadeBean!facade.PlayersInTeamsFacadeBeanRemote");
			} catch (NamingException e) {
				LOG = Logger.getLogger(Trade_controller.class.getName());
				LOG.error(e);
			}finally{
				PlayersInTeams player = remote2.getTeam(t_name.getText());
				remote.tradePlayer(player, Date.valueOf(d_picker.getValue()));
				Teams t = remote1.getTeam(t_new_team.getText());
				
				PlayersInTeams k = new PlayersInTeams();
				k.setPlayer(player.getPlayer());
				k.setDate_from(Date.valueOf(d_picker.getValue()));
				k.setTeam(t);			
				remote.addPlayer(k);
			}
		}
	}
	
	/**
	 * Zatvorenie okna.
	 */
	@FXML
	private void close_trade_window(){
		Players_controller.close_trade();
	}
}
