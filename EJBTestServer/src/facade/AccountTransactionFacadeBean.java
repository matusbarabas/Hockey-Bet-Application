package facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import entity.Account;
import executive.AccountTransactionExecutiveBean;

/**
 * Fasadna beana pre transakcie entity Account.
 * @author Matus Barabas
 *
 */
@Stateless
public class AccountTransactionFacadeBean implements AccountTransactionFacadeBeanRemote{

	@EJB
	private AccountTransactionExecutiveBean accBean;
	
	@Override
	public boolean addBet(Account a) {
		return accBean.addBet(a);
	}

	@Override
	public boolean updateStats(int id, String result) {
		return accBean.updateStats(id, result);
	}

	@Override
	public boolean deleteBet(int id) {
		return accBean.deleteBet(id);
	}


}
