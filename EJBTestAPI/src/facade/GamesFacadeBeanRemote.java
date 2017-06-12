package facade;

import java.sql.Date;
import java.util.List;

import javax.ejb.Remote;

import entity.Matches;
import entity.Teams;
import entity.Teams1;

/**
 * Remote rozhranie pre fasadnu beanu entity Games.
 * @author Matus Barabas
 *
 */
@Remote
public interface GamesFacadeBeanRemote {
	public List<Matches> showGames();
	public Matches getGame(Date d, Teams visitor, Integer goals_visitor, Teams1 home, Integer goals_home);
}
