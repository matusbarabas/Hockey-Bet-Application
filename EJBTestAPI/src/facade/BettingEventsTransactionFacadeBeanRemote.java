package facade;

import javax.ejb.Remote;

import entity.BettingEvents;

/**
 * Remote rozhranie pre fasadnu beanu obsahujucu transakcie entity BettingEvents.
 * @author Matus Barabas
 *
 */
@Remote
public interface BettingEventsTransactionFacadeBeanRemote {
	public boolean addBet(BettingEvents bet);
}
