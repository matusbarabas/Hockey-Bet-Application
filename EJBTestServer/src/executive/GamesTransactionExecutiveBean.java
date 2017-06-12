package executive;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.logging.Logger;

import entity.Matches;

/**
 * Exekutivna beana pre transakcie entity Games.
 * @author Matus Barabas
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class GamesTransactionExecutiveBean {

	private static Logger LOG;
	
	@Resource
	private EJBContext context;
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * Pridanie zapasu do tabulky zapasov v databaze.
	 * @param match
	 * @return
	 */
	public boolean addGame(Matches match) {
		LOG = Logger.getLogger(GamesTransactionExecutiveBean.class.getName());
		UserTransaction ut = context.getUserTransaction();
		
		try {
			ut.begin();
			em.persist(match);
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
