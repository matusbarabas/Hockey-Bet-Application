package facade;

import javax.ejb.Remote;

import entity.Teams;
import entity.Teams1;

/**
 * Remote rozhranie pre fasadnu beanu obsahujucu transakcie entity Teams a Teams1.
 * @author Matus Barabas
 *
 */
@Remote
public interface TeamsTransactionFacadeBeanRemote {
	public boolean addTeam(Teams teams);
	public boolean addTeam1(Teams1 teams1);
}