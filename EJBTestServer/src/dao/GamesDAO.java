package dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entity.Matches;
import entity.Teams;
import entity.Teams1;

/**
 * Data access object trieda entity Games.
 * @author Matus Barabas
 *
 */
public class GamesDAO {
	/**
	 * Vyber vsetkych informacii o zapasoch z databazy.
	 * @param em
	 * @return
	 */
	public List<Matches> getAllGames(EntityManager em) {
		TypedQuery<Matches> q = em.createNamedQuery("Matches.findAll", Matches.class);
		return q.getResultList();
	}

	/**
	 * Vyber konkretneho zapasu z databazy na zaklade zadanych kriterii.
	 * @param em
	 * @param d
	 * @param visitor
	 * @param goals_visitor
	 * @param home
	 * @param goals_home
	 * @return
	 */
	public Matches getGame(EntityManager em, Date d, Teams visitor, Integer goals_visitor, Teams1 home, Integer goals_home) {
		TypedQuery<Matches> q = em.createNamedQuery("Matches.findGame", Matches.class);
		q.setParameter("d", d);
		q.setParameter("vis", visitor);
		q.setParameter("g_vis", goals_visitor);
		q.setParameter("home", home);
		q.setParameter("g_home", goals_home);
		
		return q.getSingleResult();
	}
}
