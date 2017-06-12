package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entity.Account;

/**
 * Data access object trieda entity Account.
 * @author Matus Barabas
 *
 */
public class AccountDAO {

	/**
	 * Vyber vsektych dat o stavkach z databazy.
	 * @param em
	 * @return
	 */
	public List<Account> getAllStats(EntityManager em) {
		TypedQuery<Account> q = em.createNamedQuery("Account.findAll", Account.class);
		return q.getResultList();
	}

}
