package facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import entity.PlayersInTeams;
import executive.PlayersInTeamsExecutiveBean;

/**
 * Fasadna beana entity PlayersInTeams.
 * @author Matus Barabas
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class PlayersInTeamsFacadeBean implements PlayersInTeamsFacadeBeanRemote{

	@EJB
	private PlayersInTeamsExecutiveBean p;

	@Override
	public List<PlayersInTeams> showPlayers() {
		return p.getAllPlayers();
	}

	@Override
	public List<PlayersInTeams> showBestPlayers() {
		return p.getAllBestPlayers();
	}

	@Override
	public List<PlayersInTeams> showBestForwards() {
		return p.getAllBestForwards();
	}

	@Override
	public List<PlayersInTeams> showBestDefence() {
		return p.getAllBestDefence();
	}

	@Override
	public PlayersInTeams getTeam(String name) {
		return p.getMyTeam(name);
	}
	
	
}
