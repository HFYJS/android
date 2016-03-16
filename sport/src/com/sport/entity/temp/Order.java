package com.sport.entity.temp;

public class Order {
	int oid;
	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}
	String goodsName;
	String imgPath;
	int count;
	double price;
	
	public Order(){
		
	}
	
	public Order(String goodsName, String imgPath, int count, double price) {
		super();
		this.goodsName = goodsName;
		this.imgPath = imgPath;
		this.count = count;
		this.price = price;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
