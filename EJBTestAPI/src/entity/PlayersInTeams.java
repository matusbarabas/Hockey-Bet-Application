package entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entita PlayersInTeams obsahuje informacie o hracoch, v ktorom time sa nachadzaju a
 * taktiez datum odkedy dokedy, cize obsahuje vsetky informacie o prestupoch.
 * @author Matus Barabas
 *
 */
@Entity
@Table(name="player_in_team")
@NamedQueries({
	@NamedQuery(name = "PlayersInTeams.findAll", 
			query = "select p from PlayersInTeams p "),
	@NamedQuery(name = "PlayersInTeams.findBestPlayers", 
	query = "select p from PlayersInTeams p inner join p.player x order by x.points desc"),
	@NamedQuery(name = "PlayersInTeams.findBestForwards", 
	query = "select p from PlayersInTeams p inner join p.player x "
			+ "where x.pos = :c or x.pos = :lw or x.pos = :rw order by x.points desc"),
	@NamedQuery(name = "PlayersInTeams.findBestDefence", 
	query = "select p from PlayersInTeams p inner join p.player x "
			+ "where x.pos = :d order by x.points desc"),
	@NamedQuery(name = "PlayersInTeams.findPlayer", 
	query = "select p from PlayersInTeams p inner join p.player x "
			+ "where x.name = :name ")
})
public class PlayersInTeams implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private Date date_from;
	
	private Date date_to;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Teams team;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Players player;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate_from() {
		return date_from;
	}

	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}

	public Date getDate_to() {
		return date_to;
	}

	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}

	public Teams getTeam() {
		return team;
	}

	public void setTeam(Teams team) {
		this.team = team;
	}

	public Players getPlayer() {
		return player;
	}

	public void setPlayer(Players player) {
		this.player = player;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
