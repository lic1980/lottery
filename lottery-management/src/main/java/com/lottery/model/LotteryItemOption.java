package com.lottery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lottery_item_option")
public class LotteryItemOption {
	@Id
	@Column(updatable = false)
	private Long id;
	@Column
	private String text;
	@Column
	private int order;
	@ManyToOne
    @JoinColumn(name="lottery_item_id")
	private LotteryItem item;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public LotteryItem getItem() {
		return item;
	}
	public void setItem(LotteryItem item) {
		this.item = item;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	
}
