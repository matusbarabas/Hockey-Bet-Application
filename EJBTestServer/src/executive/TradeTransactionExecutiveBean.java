package executive;

import java.sql.Date;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import entity.PlayersInTeams;

/**
 * Exekutivna beana pre transakcie entity PlayersInTeams.
 * @author Matus Barabas
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class TradeTransactionExecutiveBean {

	private static Logger LOG;
	
	@PersistenceContext
	private EntityManager em;
	
	@Resource
	private EJBContext context;
	
	/**
	 * Aktualizacia konkretneho zaznamu.
	 * Pridanie datumu ukoncenia posobenia hraca v danom time.
	 * @param player
	 * @param date_to
	 * @return
	 */
	public boolean tradeMyPlayer(PlayersInTeams player, Date date_to) {
		LOG = Logger.getLogger(TradeTransactionExecutiveBean.class.getName());
		UserTransaction ut = context.getUserTransaction();
		
		try {
			ut.begin();
			player.setDate_to(date_to);				
			em.merge(player);
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
	 * Pridanie noveho zaznamu o hracovi s novym timom.
	 * @param pl
	 * @return
	 */
	public boolean addMyPlayer(PlayersInTeams pl) {
		LOG = Logger.getLogger(TradeTransactionExecutiveBean.class.getName());
		UserTransaction ut = context.getUserTransaction();
		try {
			ut.begin();			
			em.persist(pl);
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
