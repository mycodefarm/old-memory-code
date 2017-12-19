package com.jimo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "argments")
public class Argments {
	private int num;
	private int days;
	private int lock_days;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getLock_days() {
		return lock_days;
	}

	public void setLock_days(int lock_days) {
		this.lock_days = lock_days;
	}

}
