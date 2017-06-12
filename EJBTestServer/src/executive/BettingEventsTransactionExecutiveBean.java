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

import entity.BettingEvents;

/**
 * Exekutivna beana pre transakcie entity BettingEvents.
 * @author Matus Barabas
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BettingEventsTransactionExecutiveBean {

	private static Logger LOG;
	
	@Resource
	private EJBContext context;
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Pridanie stavky do tabulky jednotlivych stavkovych prilezitosti.
	 * @param bet
	 * @return
	 */
	public boolean addBet(BettingEvents bet) {
		UserTransaction ut = context.getUserTransaction();
		LOG = Logger.getLogger(BettingEventsTransactionExecutiveBean.class.getName());
		
		try {
			ut.begin();
			em.persist(bet);
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
