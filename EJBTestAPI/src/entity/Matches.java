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
 * Entita Matches obsahuje informacie o vsetkych odohranych zapasoch.
 * @author Matus Barabas
 *
 */
@Entity
@Table(name="games_14_15")
@NamedQueries({
	@NamedQuery(name = "Matches.findAll", 
				query = "select m from Matches m "),
	@NamedQuery(name = "Matches.findGame", 
				query = "select m from Matches m where m.date_of_match = :d and "
						+ "m.visitor = :vis and m.goals_visitor = :g_vis and "
						+ "m.home = :home and m.goals_home = :g_home")
})
public class Matches implements Serializable{
		private static final long serialVersionUID = 4678878935907273535L;
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Integer id;
		
		Date date_of_match;
		
		@ManyToOne
		@JoinColumn(nullable = false)
		private Teams visitor;
		
		private Integer goals_visitor;
		
		@ManyToOne
		@JoinColumn(nullable = false)
		private Teams1 home;
		
		private Integer goals_home;
		
		private String note;
		
		public Matches(){
			
		}
		
		public Matches(Integer id, Date date_of_match, Teams visitor, Integer goals_visitor, Teams1 home,
				Integer goals_home, String note) {
			super();
			this.id = id;
			this.date_of_match = date_of_match;
			this.visitor = visitor;
			this.goals_visitor = goals_visitor;
			this.home = home;
			this.goals_home = goals_home;
			this.note = note;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Date getDate_of_match() {
			return date_of_match;
		}

		public void setDate_of_match(Date date_of_match) {
			this.date_of_match = date_of_match;
		}

		public Teams getVisitor() {
			return visitor;
		}

		public void setVisitor(Teams visitor) {
			this.visitor = visitor;
		}

		public Integer getGoals_visitor() {
			return goals_visitor;
		}

		public void setGoals_visitor(Integer goals_visitor) {
			this.goals_visitor = goals_visitor;
		}

		public Teams1 getHome() {
			return home;
		}

		public void setHome(Teams1 home) {
			this.home = home;
		}

		public Integer getGoals_home() {
			return goals_home;
		}

		public void setGoals_home(Integer goals_home) {
			this.goals_home = goals_home;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
}
