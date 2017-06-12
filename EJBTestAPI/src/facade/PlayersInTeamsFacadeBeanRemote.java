package facade;

import java.util.List;

import javax.ejb.Remote;

import entity.PlayersInTeams;

/**
 * Remote rozhranie pre fasadnu beanu entity PlayersInTeams.
 * @author Matus Barabas
 *
 */
@Remote
public interface PlayersInTeamsFacadeBeanRemote {
	public List<PlayersInTeams> showPlayers();
	public List<PlayersInTeams> showBestPlayers();
	public List<PlayersInTeams> showBestForwards();
	public List<PlayersInTeams> showBestDefence();
	public PlayersInTeams getTeam(String name);
}
