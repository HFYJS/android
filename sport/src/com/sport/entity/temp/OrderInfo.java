package com.sport.entity.temp;

import java.util.Date;


import com.sport.entity.Address;
import com.sport.entity.Shop;
import com.sport.entity.State;

public class OrderInfo {
	int oid;
	Address address;
	int sum;
	double total;
	Date date;
	String note;
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	State state;
	Shop shop;

	public OrderInfo() {

	}

	public OrderInfo(int oid, int sum, double total) {
		super();
		this.oid = oid;
		this.sum = sum;
		this.total = total;
	}


	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
