package facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import entity.Matches;
import executive.GamesTransactionExecutiveBean;

/**
 * Fasadna beana pre transakcie entity Games.
 * @author Matus Barabas
 *
 */
@Stateless
public class GamesTransactionFacadeBean implements GamesTransactionFacadeBeanRemote{
	
	@EJB
	private GamesTransactionExecutiveBean gBean;

	@Override
	public boolean addGame(Matches match) {
		return gBean.addGame(match);
	}
}
