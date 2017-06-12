package facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import entity.Teams;
import entity.Teams1;
import executive.TeamsExecutiveBean;

/**
 * Fasadna beana entity Teams a Teams1.
 * @author Matus Barabas
 *
 */
@Stateless
public class TeamsFacadeBean implements TeamsFacadeBeanRemote {

	@EJB
	private TeamsExecutiveBean t;
	
	@Override
	public List<Teams> getAllTeams() {
		return t.getAllTeams();
	}

	@Override
	public Teams getTeam(String name) {
		return t.getMyTeam(name);
	}

	@Override
	public Teams1 getTeam1(String name) {
		return t.getMyTeam1(name);
	}

}
