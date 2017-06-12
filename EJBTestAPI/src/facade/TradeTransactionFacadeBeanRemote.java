package facade;

import java.sql.Date;

import javax.ejb.Remote;

import entity.PlayersInTeams;

/**
 * Remote rozhranie pre fasadnu beanu obsahujucu transakcie entity Players, v ktorej sa vykonavaju vymeny hracov.
 * @author Matus Barabas
 *
 */
@Remote
public interface TradeTransactionFacadeBeanRemote {
	public boolean tradePlayer(PlayersInTeams player, Date date_to);
	public boolean addPlayer(PlayersInTeams pl);
}
