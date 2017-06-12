package executive;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dao.PlayersInTeamsDAO;
import entity.PlayersInTeams;

/**
 * Exekutivna beana entity PlayersInTeams.
 * @author Matus Barabas
 *
 */
@Stateless
public class PlayersInTeamsExecutiveBean {

	@PersistenceContext
	private EntityManager em;
	
	public List<PlayersInTeams> getAllPlayers() {
		PlayersInTeamsDAO playersDAO = new PlayersInTeamsDAO();
		List<PlayersInTeams> l = playersDAO.getAllPlayers(em);

		return l;
	}
	
	public List<PlayersInTeams> getAllBestPlayers() {
		PlayersInTeamsDAO playersDAO = new PlayersInTeamsDAO();
		List<PlayersInTeams> l = playersDAO.getAllBestPlayers(em);

		return l;
	}

	public List<PlayersInTeams> getAllBestForwards() {
		PlayersInTeamsDAO playersDAO = new PlayersInTeamsDAO();
		List<PlayersInTeams> l = playersDAO.getAllBestForwards(em);

		return l;
	}

	public List<PlayersInTeams> getAllBestDefence() {
		PlayersInTeamsDAO playersDAO = new PlayersInTeamsDAO();
		List<PlayersInTeams> l = playersDAO.getAllBestDefence(em);

		return l;
	}

	public PlayersInTeams getMyTeam(String name) {
		PlayersInTeamsDAO playersDAO = new PlayersInTeamsDAO();
		PlayersInTeams p = playersDAO.getMyPlayer(em, name);

		return p;
	}

}
