package facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import entity.BettingEvents;
import executive.BettingEventsTransactionExecutiveBean;

/**
 * Fasadna beana pre transakcie entity BettingEvents.
 * @author Matus Barabas
 *
 */
@Stateless
public class BettingEventsTransactionFacadeBean implements BettingEventsTransactionFacadeBeanRemote{
	
	@EJB
	private BettingEventsTransactionExecutiveBean betBean;

	@Override
	public boolean addBet(BettingEvents bet) {
		return betBean.addBet(bet);
	}
}
