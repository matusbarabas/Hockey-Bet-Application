package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entity.Teams;
import entity.Teams1;

/**
 * Data access object trieda entity Teams a Teams1.
 * @author Matus Barabas
 *
 */
public class TeamsDAO {
	
	/**
	 * Vyber vsetkych informacii o timch z databazy.
	 * @param em
	 * @return
	 */
	public List<Teams> getAllTeams(EntityManager em) {
		TypedQuery<Teams> q = em.createNamedQuery("Teams.findAll", Teams.class);
		
		return q.getResultList();
	}

	/**
	 * Vyber konkretneho timu na zaklade zadanych informacii.
	 * @param em
	 * @param name
	 * @return
	 */
	public Teams getTeam(EntityManager em, String name) {
		TypedQuery<Teams> q = em.createNamedQuery("Teams.findTeam", Teams.class);
		
		return q.setParameter("name", name).getSingleResult();
	}

	/**
	 * Vyber konkretneho timu na zaklade zadanych informacii.
	 * @param em
	 * @param name
	 * @return
	 */
	public Teams1 getTeam1(EntityManager em, String name) {
		TypedQuery<Teams1> q = em.createNamedQuery("Teams1.findTeam1", Teams1.class);
		
		return q.setParameter("name", name).getSingleResult();
	}
}
