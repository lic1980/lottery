package com.lottery.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "customer_bid")
public class CustomerBid {
	@Id
	@Column(updatable = false)
	private Long id;
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date createTime;
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date bidTime;
	@Column
	@Enumerated(EnumType.ORDINAL)
	private CustomerBidStatus status;
	@Column
	private String imagePath;
	@ManyToOne
    @JoinColumn(name="customer_id")
	private Customer customer;
	@ManyToOne
    @JoinColumn(name="lottery_round_id")
	private LotteryRound lotteryRound;
	
	@ManyToMany
	@JoinTable(name="customer_bid_option",  joinColumns = { @JoinColumn(name = "customer_bid_id")}, inverseJoinColumns = {
            @JoinColumn(name = "lottery_item_option_id") })
	@OrderBy("item ASC")
	private List<LotteryItemOption> options;
	@Column
	private int times;
	@Column
	private float fee;
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public LotteryRound getLotteryRound() {
		return lotteryRound;
	}
	public void setLotteryRound(LotteryRound lotteryRound) {
		this.lotteryRound = lotteryRound;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public CustomerBidStatus getStatus() {
		return status;
	}
	public void setStatus(CustomerBidStatus status) {
		this.status = status;
	}
	public List<LotteryItemOption> getOptions() {
		return options;
	}
	public void setOptions(List<LotteryItemOption> options) {
		this.options = options;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public float getFee() {
		return fee;
	}
	public void setFee(float fee) {
		this.fee = fee;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Date getBidTime() {
		return bidTime;
	}
	public void setBidTime(Date bidTime) {
		this.bidTime = bidTime;
	}
	
	
}
