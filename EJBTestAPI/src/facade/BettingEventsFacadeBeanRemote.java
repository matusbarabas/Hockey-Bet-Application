package facade;

import java.util.List;

import javax.ejb.Remote;

import entity.BettingEvents;

/**
 * Remote rozhranie pre fasadnu beanu entity BettingEvents.
 * @author Matus Barabas
 *
 */
@Remote
public interface BettingEventsFacadeBeanRemote {
	public List<BettingEvents> showAllBets();
}
