package com.lottery.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "lottery_item")
public class LotteryItem {
	@Id
	@Column(updatable = false)
	private Long id;
	@Column
	private String name;
	@Column
	private int minOptionNum;
	@Column
	private int order;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="lottery_item_id")
	private List<LotteryItemOption> options;
	@ManyToOne
    @JoinColumn(name="lottery_id")
	private Lottery lottery;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<LotteryItemOption> getOptions() {
		return options;
	}
	public void setOptions(List<LotteryItemOption> options) {
		this.options = options;
	}
	public Lottery getLottery() {
		return lottery;
	}
	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}
	public int getMinOptionNum() {
		return minOptionNum;
	}
	public void setMinOptionNum(int minOptionNum) {
		this.minOptionNum = minOptionNum;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	
}
