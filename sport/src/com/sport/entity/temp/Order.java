package com.sport.entity.temp;

import com.sport.entity.Goods;

public class Order {
	int oid;
	int sid;
	double allprice;
	public double getAllprice() {
		return allprice;
	}

	public void setAllprice(double allprice) {
		this.allprice = allprice;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	Goods goods;

	int count;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Order() {

	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
