package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entity.PlayersInTeams;

/**
 * Data access object trieda entity PlayersInteams.
 * @author Matus Barabas
 *
 */
public class PlayersInTeamsDAO {

	/**
	 * Vyber vsetkych hracov z databazy.
	 * @param em
	 * @return
	 */
	public List<PlayersInTeams> getAllPlayers(EntityManager em) {
		TypedQuery<PlayersInTeams> q = em.createNamedQuery("PlayersInTeams.findAll", PlayersInTeams.class);
		return q.getResultList();
	}

	/**
	 * Vyber 10 najlepsich hracov podla poctu bodov z databazy.
	 * @param em
	 * @return
	 */
	public List<PlayersInTeams> getAllBestPlayers(EntityManager em) {
		TypedQuery<PlayersInTeams> q = em.createNamedQuery("PlayersInTeams.findBestPlayers", PlayersInTeams.class);
		return q.setMaxResults(10).getResultList();
	}

	/**
	 * Vyber 10 najlepsich utocnikov podla poctu bodov z databazy.
	 * @param em
	 * @return
	 */
	public List<PlayersInTeams> getAllBestForwards(EntityManager em) {
		TypedQuery<PlayersInTeams> q = em.createNamedQuery("PlayersInTeams.findBestForwards", PlayersInTeams.class);
		q.setParameter("c", "C");
		q.setParameter("lw", "LW");
		q.setParameter("rw", "RW");
		return q.setMaxResults(10).getResultList();
	}

	/**
	 * Vyber 10 najlepsich obrancov podla poctu bodov z databazy.
	 * @param em
	 * @return
	 */
	public List<PlayersInTeams> getAllBestDefence(EntityManager em) {
		TypedQuery<PlayersInTeams> q = em.createNamedQuery("PlayersInTeams.findBestDefence", PlayersInTeams.class);
		q.setParameter("d", "D");
		return q.setMaxResults(10).getResultList();
	}

	/**
	 * Vyber konkretneho hraca na zaklade zadaneho mena hraca.
	 * @param em
	 * @param name
	 * @return
	 */
	public PlayersInTeams getMyPlayer(EntityManager em, String name) {
		TypedQuery<PlayersInTeams> q = em.createNamedQuery("PlayersInTeams.findPlayer", PlayersInTeams.class);
		q.setParameter("name", name);
		return q.getSingleResult();
	}

}
