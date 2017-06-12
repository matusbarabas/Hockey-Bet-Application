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
 * Entita BettingEvents obsahuje informacie o vsetkych stavkach spolu s kurzami.
 * @author Matus Barabas
 *
 */
@Entity
@Table(name="betting_events")
@NamedQueries({
	@NamedQuery(name="BettingEvents.findAll", 
			query="select b from BettingEvents b order by b.date_of_bet")
})
public class BettingEvents implements Serializable{
	
	private static final long serialVersionUID = 4678878935907273535L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Matches games;
	//private Integer games;
	
	private Double home_line;
	
	private Double draw_line;
	
	private Double visitor_line;
	
	private Date date_of_bet;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Matches getGames() {
		return games;
	}

	public void setGames(Matches games) {
		this.games = games;
	}

	public Double getHome_line() {
		return home_line;
	}

	public void setHome_line(Double home_line) {
		this.home_line = home_line;
	}

	public Double getDraw_line() {
		return draw_line;
	}

	public void setDraw_line(Double draw_line) {
		this.draw_line = draw_line;
	}

	public Double getVisitor_line() {
		return visitor_line;
	}

	public void setVisitor_line(Double visitor_line) {
		this.visitor_line = visitor_line;
	}

	public Date getDate_of_bet() {
		return date_of_bet;
	}

	public void setDate_of_bet(Date date_of_bet) {
		this.date_of_bet = date_of_bet;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
