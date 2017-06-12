package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entita Teams1 obsahuje vsetky timy spolu s informaciou, v ktorej konferencii a divizii 
 * sa dany tim nachadza.
 * @author Matus Barabas
 *
 */
@Entity
@Table(name="TEAMS1")
@NamedQueries({
	@NamedQuery(name="Teams1.findAll", 
			query="select t from Teams1 t "),
	@NamedQuery(name="Teams1.findTeam1", 
	query="select t from Teams1 t where t.name = :name")
})
public class Teams1 implements Serializable{
	
	private static final long serialVersionUID = 4678878935907273535L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String conference;
	
	private String devision;
	
	private String shortcut;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConference() {
		return conference;
	}

	public void setConference(String conference) {
		this.conference = conference;
	}

	public String getDevision() {
		return devision;
	}

	public void setDevision(String devision) {
		this.devision = devision;
	}

	public String getShortcut() {
		return shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}
}
