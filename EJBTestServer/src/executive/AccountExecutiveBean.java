package executive;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dao.AccountDAO;
import entity.Account;

/**
 * Exekutivna beana entity Account.
 * @author Matus Barabas
 *
 */
@Stateless
public class AccountExecutiveBean {

	@PersistenceContext
	private EntityManager em;
	
	public List<Account> getAllGames() {
		AccountDAO accountDAO = new AccountDAO();
		List<Account> s = accountDAO.getAllStats(em);

		return s;
	}

}
