package executive;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dao.BetDAO;
import entity.BettingEvents;

/**
 * Exekutivna beana entity BettingEvents
 * @author Matus Barabas
 *
 */
@Stateless
public class BettingEventsExecutiveBean {

	@PersistenceContext
	private EntityManager em;
	
	public List<BettingEvents> getAllBets() {
		BetDAO betDAO = new BetDAO();
		List<BettingEvents> bets = betDAO.getAllBetEvents(em);

		return bets;
	}

}
