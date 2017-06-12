package executive;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dao.TeamsDAO;
import entity.Teams;
import entity.Teams1;

/**
 * Exekutivna beana entity Teams.
 * @author Matus Barabas
 *
 */
@Stateless
public class TeamsExecutiveBean {

	@PersistenceContext
	private EntityManager em;
	
	public List<Teams> getAllTeams() {
		TeamsDAO teamDAO = new TeamsDAO();
		List<Teams> teams = teamDAO.getAllTeams(em);
		
		return teams;
	}

	public Teams getMyTeam(String name) {
		TeamsDAO teamDAO = new TeamsDAO();
		Teams team = teamDAO.getTeam(em, name);
		
		return team;
	}

	public Teams1 getMyTeam1(String name) {
		TeamsDAO teamDAO = new TeamsDAO();
		Teams1 team = teamDAO.getTeam1(em, name);
		
		return team;
	}
	
}
