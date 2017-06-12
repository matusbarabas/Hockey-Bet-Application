package executive;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import entity.Account;

/**
 * Exekutivna beana pre transakcie entity Account.
 * @author Matus Barabas
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AccountTransactionExecutiveBean {
	
	private static Logger LOG;
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private EJBContext context;

	/**
	 * Transakcia pridania stavkovej prilezitosti do tabulky stavok.
	 * @param a
	 * @return
	 */
	public boolean addBet(Account a) {
		LOG = Logger.getLogger(AccountTransactionExecutiveBean.class.getName());
		UserTransaction ut = context.getUserTransaction();
		
		try {
			ut.begin();
			em.persist(a);
			ut.commit();
		} catch(Exception e) {
			LOG.error(e);
			try {
				ut.rollback();
			} catch(Exception ex) {
				LOG.error(e);
			}
		}
		
		return false;
	}

	/**
	 * Aktualizacie dat o danej stavke.
	 * Oznacenie stavky resp. zapasu ako vyherneho, prehraneho alebo zruseneho.
	 * Na zaklade vysledku aktualizacia o vyhranej sume penazi, uspesnosti.
	 * @param id
	 * @param result
	 * @return
	 */
	public boolean updateStats(int id, String result) {
		LOG = Logger.getLogger(AccountTransactionExecutiveBean.class.getName());
		UserTransaction ut = context.getUserTransaction();
		
		try {
			Account a = em.find(Account.class, id);
			ut.begin();
			a.setResult(result);
			if(result.equals("WIN")){
				a.setProfit(a.getMay_win() - a.getMoney_bet());
				a.setLoss(Double.parseDouble("0"));
				a.setSucces(Double.parseDouble("1"));
			}else if(result.equals("LOST")){
				a.setProfit(Double.parseDouble("0"));
				a.setLoss(a.getMoney_bet());
				a.setSucces(Double.parseDouble("0"));
			}else if(result.equals("CANC")){
				a.setProfit(Double.parseDouble("0"));
				a.setLoss(Double.parseDouble("0"));
				a.setSucces(Double.parseDouble("1"));
			}
			em.merge(a);
			ut.commit();
		} catch(Exception e) {
			LOG.error(e);
			try {
				ut.rollback();
			} catch(Exception ex) {
				LOG.error(e);
			}
		}
		
		return false;
	}

	/**
	 * Vymazanie stavky z tabulky stavok.
	 * @param id
	 * @return
	 */
	public boolean deleteBet(int id) {
		LOG = Logger.getLogger(AccountTransactionExecutiveBean.class.getName());
		UserTransaction ut = context.getUserTransaction();
		try {
			Account a = em.find(Account.class, id);
			ut.begin();
			em.remove(em.contains(a) ? a : em.merge(a));
			ut.commit();
		} catch(Exception e) {
			LOG.error(e);
			try {
				ut.rollback();
			} catch(Exception ex) {
				LOG.error(e);
			}
		}
		return false;
	}
	
	
}
