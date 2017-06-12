package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entity.BettingEvents;

/**
 * Data access object trieda entity BettingEvents.
 * @author Matus Barabas
 *
 */
public class BetDAO {

	/**
	 * Vyber vsetkych dat o stavkovych prilezitostiach z databazy.
	 * @param em
	 * @return
	 */
	public List<BettingEvents> getAllBetEvents(EntityManager em) {
		TypedQuery<BettingEvents> q = em.createNamedQuery("BettingEvents.findAll", BettingEvents.class);
		return q.getResultList();
	}

}
