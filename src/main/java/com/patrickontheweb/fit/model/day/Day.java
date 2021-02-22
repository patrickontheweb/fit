package com.patrickontheweb.fit.model.day;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.patrickontheweb.fit.model.BaseEntity;
import com.patrickontheweb.fit.model.user.User;

@Entity
@Table(name = "DAY")
public class Day extends BaseEntity {

	private User user;
	private Date date;
	private BigDecimal weight;
	private BigDecimal sleepHours;
	private String comment;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "DATE")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "WEIGHT")
	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	@Column(name = "SLEEP_HOURS")
	public BigDecimal getSleepHours() {
		return sleepHours;
	}

	public void setSleepHours(BigDecimal sleepHours) {
		this.sleepHours = sleepHours;
	}

	@Column(name = "COMMENT")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}