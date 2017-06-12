package facade;

import java.util.List;

import javax.ejb.Remote;

import entity.Account;

/**
 * Remote rozhranie pre fasadnu beanu entity Account.
 * @author Matus Barabas
 *
 */
@Remote
public interface AccountFacadeBeanRemote {
	public List<Account> showStats();
}
