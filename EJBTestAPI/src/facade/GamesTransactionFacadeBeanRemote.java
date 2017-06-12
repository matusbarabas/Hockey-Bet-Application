package facade;

import javax.ejb.Remote;

import entity.Matches;

/**
 * Remote rozhranie pre fasadnu beanu obsahujucu transakcie entity Games.
 * @author Matus Barabas
 *
 */
@Remote
public interface GamesTransactionFacadeBeanRemote {
	public boolean addGame(Matches match);
}
