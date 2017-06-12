package facade;

import javax.ejb.Remote;

import entity.Account;

/**
 * Remote rozhranie pre fasadnu beanu obsahujucu transakcie entity Account.
 * @author Matus Barabas
 *
 */
@Remote
public interface AccountTransactionFacadeBeanRemote {
	public boolean addBet(Account a);
	public boolean updateStats(int id, String result);
	public boolean deleteBet(int id);
}
