package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entita Account, ktora sluzi na zaznamenavanie vsetkych stavok a statistik stavkoveho uctu.
 * @author Matus Barabas
 *
 */
@Entity
@Table(name="account")
@NamedQueries({
	@NamedQuery(name="Account.findAll", 
			query="select a from Account a")
})
public class Account implements Serializable{
	
	private static final long serialVersionUID = 4678878935907273535L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private BettingEvents betting_event;
	
	private Integer bet;
	
	private Double money_bet;
	
	private Double may_win;
	
	private String result;
	
	private Double profit;
	
	private Double loss;
	
	private Double succes;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BettingEvents getBetting_event() {
		return betting_event;
	}

	public void setBetting_event(BettingEvents betting_event) {
		this.betting_event = betting_event;
	}

	public Integer getBet() {
		return bet;
	}

	public void setBet(Integer bet) {
		this.bet = bet;
	}

	public Double getMoney_bet() {
		return money_bet;
	}

	public void setMoney_bet(Double money_bet) {
		this.money_bet = money_bet;
	}

	public Double getMay_win() {
		return may_win;
	}

	public void setMay_win(Double may_win) {
		this.may_win = may_win;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Double getLoss() {
		return loss;
	}

	public void setLoss(Double loss) {
		this.loss = loss;
	}

	public Double getSuccess() {
		return succes;
	}

	public void setSucces(Double succes) {
		this.succes = succes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
}
