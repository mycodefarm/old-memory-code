package com.jimo.model;

import java.util.List;

public class PowerResult {
	private List<PowerData> havedPower;
	private List<PowerData> notHavedPower;

	public List<PowerData> getHavedPower() {
		return havedPower;
	}

	public void setHavedPower(List<PowerData> havedPower) {
		this.havedPower = havedPower;
	}

	public List<PowerData> getNotHavedPower() {
		return notHavedPower;
	}

	public void setNotHavedPower(List<PowerData> notHavedPower) {
		this.notHavedPower = notHavedPower;
	}
}
