package com.sport.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.sport.R;
import com.sport.entity.temp.Order;
import com.sport.entity.temp.OrderInfo;

public class CommentGoodsAdapter extends BaseAdapter {
	private List<OrderInfo> list;
	List<Order> orderList1 = new ArrayList<Order>();
	String orderResult;
	List<List<Order>> orderdetailList;
	Context context;
	Map<String, String> orderMap;
	private LayoutInflater inflater;
	private CommentGoodsChildAdapter childAdapter;

	public CommentGoodsAdapter(List<OrderInfo> list, Context context,
			List<List<Order>> orderdetailList) {
		super();
		this.list = list;
		this.context = context;
		this.orderdetailList = orderdetailList;
		childAdapter = new CommentGoodsChildAdapter(context);
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChildListViewItem childListViewItem = new ChildListViewItem();
		convertView = inflater.inflate(R.layout.comment_goods_item, null);
		if (convertView != null) {
			childListViewItem.tvCount = (TextView) convertView
					.findViewById(R.id.tv_evaluate_goods_item_goodscount);
			childListViewItem.tvAllPrice = (TextView) convertView
					.findViewById(R.id.tv_evaluate_goods__item_goodsprice);
			childListViewItem.tvState = (TextView) convertView
					.findViewById(R.id.tv_evaluate_goods_item_dealstatus);
			childListViewItem.btnShopName = (Button) convertView
					.findViewById(R.id.bt_evaluate_goods_item_shop_name);
			childListViewItem.btnDel = (Button) convertView
					.findViewById(R.id.bt_evaluate_goods__item_delete);
			childListViewItem.btnQuery = (Button) convertView
					.findViewById(R.id.bt_evaluate_goods__item_logistics);
			childListViewItem.btnEvaluate = (Button) convertView
					.findViewById(R.id.bt_evaluate_goods__item_evaluate);
			childListViewItem.ivShopImage = (ImageView) convertView
					.findViewById(R.id.iv_evaluate_goods_item_shop_image);
			childListViewItem.lv = (ListView) convertView
					.findViewById(R.id.lv_evaluate_goods_item_goodscount);
			convertView.setTag(childListViewItem);
		} else {
			childListViewItem = (ChildListViewItem) convertView.getTag();
		}
		//
		// orderMap = new Gson().fromJson(
		// orderResult, new TypeToken<Map<String,String>>() {
		// }.getType());
		// String orderStr = orderMap.get("orderlist");
		// List<List<Order>> orderList = new Gson().fromJson(orderStr,
		// new TypeToken<List<List<Order>>>(){}.getType() );
		//
		// Log.i("log",list.get(position).getOid()+"");
		//
		// for(Order o: orderList){
		// // Log.i("log",o.getOid()+"");
		//
		// if(o.getOid()==list.get(position).getOid()){
		//
		// orderList1.add(o);
		// // Log.i("log",o.getOid()+"");
		// }
		//
		// }
		childAdapter.addAll(orderdetailList.get(position));
		childListViewItem.lv.setAdapter(childAdapter);

		childListViewItem.tvCount.setText("共 " + list.get(position).getSum()
				+ " 件商品");
		childListViewItem.tvAllPrice.setText("￥"
				+ list.get(position).getTotal() + "");
		childListViewItem.tvState.setText(list.get(position).getStateName());
		childListViewItem.btnShopName.setText(list.get(position).getShopName());
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		bitmapUtils.display(childListViewItem.ivShopImage, context
				.getResources().getString(R.string.url_pre)
				+ list.get(position).getShopImage());
		return convertView;
	}

	public class ChildListViewItem {

		Button btnDel, btnQuery, btnShopName, btnEvaluate;
		TextView tvAllPrice, tvCount, tvState;
		ListView lv;
		ImageView ivShopImage;
	}
}
