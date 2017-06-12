package executive;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dao.GamesDAO;
import entity.Matches;
import entity.Teams;
import entity.Teams1;

/**
 * Exekutivna beana entity Games.
 * @author Matus Barabas
 *
 */
@Stateless
public class GamesExecutiveBean {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Matches> getAllGames(){
		GamesDAO gamesDAO = new GamesDAO();
		List<Matches> m = gamesDAO.getAllGames(em);

		return m;
	}

	public Matches getMyGame(Date d, Teams visitor, Integer goals_visitor, Teams1 home, Integer goals_home) {
		GamesDAO gameDAO = new GamesDAO();
		Matches game = gameDAO.getGame(em, d, visitor, goals_visitor, home, goals_home);
		
		return game;
	}
}
