package com.sport.entity;

public class OrderDetail {
	private int o_did;
	private OrderForm orderForm;
	private Goods goods;
	private int count;
	private double sum;

	public OrderDetail() {

	}

	public int getO_did() {
		return o_did;
	}

	public void setO_did(int o_did) {
		this.o_did = o_did;
	}

	public OrderForm getOrderForm() {
		return orderForm;
	}

	public void setOrderForm(OrderForm orderForm) {
		this.orderForm = orderForm;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

}
