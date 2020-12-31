package com.lottery.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "lottery_lottery")
public class Lottery {
	@Id
	@Column(updatable = false)
	private Long id;
	@Column
	private String name;
	@Column
	private int minItemNum;
	@Column
	private int baseFee;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name="lottery_id")
	private List<LotteryItem> items;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name="lottery_id")
	private List<LotteryRound> rounds;
	
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
	public List<LotteryItem> getItems() {
		return items;
	}
	public void setItems(List<LotteryItem> items) {
		this.items = items;
	}
	public List<LotteryRound> getRounds() {
		return rounds;
	}
	public void setRounds(List<LotteryRound> rounds) {
		this.rounds = rounds;
	}
	public int getMinItemNum() {
		return minItemNum;
	}
	public void setMinItemNum(int minItemNum) {
		this.minItemNum = minItemNum;
	}
	public int getBaseFee() {
		return baseFee;
	}
	public void setBaseFee(int baseFee) {
		this.baseFee = baseFee;
	}
}
