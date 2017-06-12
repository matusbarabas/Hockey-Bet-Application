package facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import entity.Teams;
import entity.Teams1;
import executive.TeamsTransactionExecutiveBean;

/**
 * Fasadna beana pre transakcie entity Teams a Teams1.
 * @author Matus Barabas
 *
 */
@Stateless
public class TeamsTransactionFacadeBean implements TeamsTransactionFacadeBeanRemote {

	@EJB
	private TeamsTransactionExecutiveBean teamBean;
	
	@Override
	public boolean addTeam(Teams teams) {
		return teamBean.addTeam(teams);
	}


	@Override
	public boolean addTeam1(Teams1 teams1) {
		return teamBean.addTeam1(teams1);
	}
	
}
