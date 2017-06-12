package hockey;

/**
 * Trieda na pripravu dat do tabulky o odohranych zapasoch.
 * @author Matus Barabas
 *
 */
public class GamesTable{
	private Integer id;
	private String h_t;
	private String v_t;
	private Integer goals_home;
	private Integer goals_visitor;
	private String note;
	private String date_of_match;
	
	public GamesTable(Integer id, String h_t, String v_t, Integer goals_home, Integer goals_visitor, String note,
			String date_of_match) {
		super();
		this.id = id;
		this.h_t = h_t;
		this.v_t = v_t;
		this.goals_home = goals_home;
		this.goals_visitor = goals_visitor;
		this.note = note;
		this.date_of_match = date_of_match;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getH_t() {
		return h_t;
	}

	public void setH_t(String h_t) {
		this.h_t = h_t;
	}

	public String getV_t() {
		return v_t;
	}

	public void setV_t(String v_t) {
		this.v_t = v_t;
	}

	public Integer getGoals_home() {
		return goals_home;
	}

	public void setGoals_home(Integer goals_home) {
		this.goals_home = goals_home;
	}

	public Integer getGoals_visitor() {
		return goals_visitor;
	}

	public void setGoals_visitor(Integer goals_visitor) {
		this.goals_visitor = goals_visitor;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDate_of_match() {
		return date_of_match;
	}

	public void setDate_of_match(String date_of_match) {
		this.date_of_match = date_of_match;
	}
}
