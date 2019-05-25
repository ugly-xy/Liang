package com.zb.models;

import java.io.Serializable;

public class Stake implements Serializable {

	private static final long serialVersionUID = -9154065144623451215L;
	private Integer choice;
	private Integer coin;

	public Stake() {

	}

	public Stake(Integer choice, Integer coin) {
		this.choice = choice;
		this.coin = coin;
	}

	public Integer getChoice() {
		return choice;
	}

	public void setChoice(Integer choice) {
		this.choice = choice;
	}

	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

}
