package com.sport.entity.temp;

import java.util.Date;

public class OrderInfo {
	int oid;
	int sid;
	String shopName;
	int sum;
	double total;
	Date date;
	String stateName;
	String shopImage;

	public OrderInfo() {

	}

	public OrderInfo(int oid, String shopName, int sum, double total) {
		super();
		this.oid = oid;
		this.shopName = shopName;
		this.sum = sum;
		this.total = total;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getShopImage() {
		return shopImage;
	}

	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
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

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
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
