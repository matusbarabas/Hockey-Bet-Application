package facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import entity.Account;
import executive.AccountExecutiveBean;

/**
 * Fasadna beana entity Account.
 * @author Matus Barabas
 *
 */
@Stateless
public class AccountFacadeBean implements AccountFacadeBeanRemote{

	@EJB
	private AccountExecutiveBean a;
	
	@Override
	public List<Account> showStats() {
		return a.getAllGames();
	}
}
