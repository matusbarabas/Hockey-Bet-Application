package facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import entity.BettingEvents;
import executive.BettingEventsExecutiveBean;

/**
 * Fasadna beana entity BettingEvents.
 * @author Matus Barabas
 *
 */
@Stateless
public class BettingEventsFacadeBean implements BettingEventsFacadeBeanRemote{

	@EJB
	private BettingEventsExecutiveBean b;
	
	@Override
	public List<BettingEvents> showAllBets() {
		return b.getAllBets();
	}

}
