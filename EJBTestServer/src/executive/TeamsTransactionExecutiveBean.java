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

import entity.Teams;
import entity.Teams1;

/**
 * Exekutivna beana pre transakcie entity Teams.
 * @author Matus Barabas
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class TeamsTransactionExecutiveBean {

	private static Logger LOG;
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private EJBContext context;

	/**
	 * Pridanie timu do tabulky timov v databaze.
	 * @param teams
	 * @return
	 */
	public boolean addTeam(Teams teams) {
		LOG = Logger.getLogger(TeamsTransactionExecutiveBean.class.getName());
		UserTransaction ut = context.getUserTransaction();
		
		try {
			ut.begin();
			em.persist(teams);
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
	 * Pridanie timu do tabulky timov v databaze.
	 * @param teams
	 * @return
	 */
	public boolean addTeam1(Teams1 teams1) {
		LOG = Logger.getLogger(TeamsTransactionExecutiveBean.class.getName());
		UserTransaction ut = context.getUserTransaction();
		
		try {
			ut.begin();
			em.persist(teams1);
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
