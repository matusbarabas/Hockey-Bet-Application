package facade;

import java.sql.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import entity.PlayersInTeams;
import executive.TradeTransactionExecutiveBean;

/**
 * Fasadna beana pre transakcie entity PlayersInTeams.
 * @author Matus Barabas
 *
 */
@Stateless
public class TradeTransactionFacadeBean implements TradeTransactionFacadeBeanRemote {

	@EJB
	private TradeTransactionExecutiveBean t;

	@Override
	public boolean tradePlayer(PlayersInTeams player, Date date_to) {
		return t.tradeMyPlayer(player, date_to);
	}

	@Override
	public boolean addPlayer(PlayersInTeams pl) {
		return t.addMyPlayer(pl);
	}
	
	
}
