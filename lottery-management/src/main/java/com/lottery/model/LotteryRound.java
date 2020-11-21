package com.lottery.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lottery_round")
public class LotteryRound {
	@Id
	@Column(updatable = false)
	private Long id;
	@ManyToOne
	@JoinColumn(name="lottery_id")
	private Lottery lottery;
	@Column
	private Date cutOffTime;
	@Column
	private Date openingTime;
	@ManyToMany
	@JoinTable(name="lottery_round_result",  joinColumns = { @JoinColumn(name = "lottery_round_id")}, inverseJoinColumns = {
            @JoinColumn(name = "lottery_item_option_id") })
	private List<LotteryItemOption> result;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Date getCutOffTime() {
		return cutOffTime;
	}
	public void setCutOffTime(Date cutOffTime) {
		this.cutOffTime = cutOffTime;
	}
	public Date getOpeningTime() {
		return openingTime;
	}
	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}
	public List<LotteryItemOption> getResult() {
		return result;
	}
	public void setResult(List<LotteryItemOption> result) {
		this.result = result;
	}
	public Lottery getLottery() {
		return lottery;
	}
	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}
	
}
