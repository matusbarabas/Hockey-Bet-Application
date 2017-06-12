package facade;

import java.util.List;

import javax.ejb.Remote;

import entity.Teams;
import entity.Teams1;

/**
 * Remote rozhranie pre fasadnu beanu entity Teams a Teams1.
 * @author Matus Barabas
 *
 */
@Remote
public interface TeamsFacadeBeanRemote {
	public Teams getTeam(String name);	
	public Teams1 getTeam1(String name);	
	public List<Teams> getAllTeams();
}
