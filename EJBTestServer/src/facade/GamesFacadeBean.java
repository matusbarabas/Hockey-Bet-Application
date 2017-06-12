package facade;

import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import entity.Matches;
import entity.Teams;
import entity.Teams1;
import executive.GamesExecutiveBean;

/**
 * Fasadna beana entity Games.
 * @author Matus Barabas
 *
 */
@Stateless
public class GamesFacadeBean implements GamesFacadeBeanRemote{

	@EJB
	private GamesExecutiveBean b;

	@Override
	public List<Matches> showGames() {
		return b.getAllGames();
	}

	@Override
	public Matches getGame(Date d, Teams visitor, Integer goals_visitor, Teams1 home, Integer goals_home) {
		return b.getMyGame(d, visitor, goals_visitor, home, goals_home);
	}
}
