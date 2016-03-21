package com.sport.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lidroid.xutils.BitmapUtils;
import com.sport.R;
import com.sport.entity.temp.Order;
import com.sport.entity.temp.OrderInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CommentGoodsAdapter extends BaseAdapter implements OnClickListener{
	private List<OrderInfo> list;
	List<List<Order>> orderdetailList;
	Context context;
	Map<String, String> orderMap;
	private LayoutInflater inflater;
//	private CommentGoodsChildAdapter childAdapter;

	public CommentGoodsAdapter(List<OrderInfo> list, Context context,
			List<List<Order>> orderdetailList) {
		super();
		this.list = list;
		this.context = context;
		this.orderdetailList = orderdetailList;
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
		ChildListViewItem childListViewItem = null;
		
		if (convertView == null) {
			childListViewItem = new ChildListViewItem();
			convertView = inflater.inflate(R.layout.comment_goods_item, parent,
					false);
			childListViewItem.tvCount = (TextView) convertView
					.findViewById(R.id.tv_evaluate_goods_item_goodscount);
			childListViewItem.tvAllPrice = (TextView) convertView
					.findViewById(R.id.tv_evaluate_goods__item_goodsprice);
			childListViewItem.tvState = (TextView) convertView
					.findViewById(R.id.tv_evaluate_goods_item_dealstatus);
			childListViewItem.btnShopName = (TextView) convertView
					.findViewById(R.id.bt_evaluate_goods_item_shop_name);
			childListViewItem.btnDel = (Button) convertView
					.findViewById(R.id.bt_evaluate_goods__item_delete);
			childListViewItem.btnQuery = (Button) convertView
					.findViewById(R.id.bt_evaluate_goods__item_logistics);
			childListViewItem.btnEvaluate = (Button) convertView
					.findViewById(R.id.bt_evaluate_goods__item_evaluate);
			childListViewItem.ivShopImage = (ImageView) convertView
					.findViewById(R.id.iv_evaluate_goods_item_shop_image);
			childListViewItem.rlshop = (RelativeLayout) convertView.findViewById(R.id.rl_evaluate_goods_shop);
			childListViewItem.lv = (ListView) convertView
					.findViewById(R.id.lv_evaluate_goods_item_goodscount);
			childListViewItem.llorder = (LinearLayout) convertView
					.findViewById(R.id.ll_evaluate_goods_item_goodscount);
			
			childListViewItem.rlshop.setTag(position);
			childListViewItem.btnDel.setTag(position);
			childListViewItem.btnQuery.setTag(position);
			childListViewItem.btnEvaluate.setTag(position);
			childListViewItem.lv.setTag(position);
			
			childListViewItem.btnDel.setOnClickListener(this);
			childListViewItem.btnQuery.setOnClickListener(this);
			childListViewItem.btnEvaluate.setOnClickListener(this);
			childListViewItem.rlshop.setOnClickListener(this);
			childListViewItem.llorder.setOnClickListener(this);
			//嵌套listview
			
			
			convertView.setTag(childListViewItem);
		} else {
			childListViewItem = (ChildListViewItem) convertView.getTag();
		}
		
		
		childListViewItem.tvCount.setText("共 " + list.get(position).getSum()
				+ " 件商品");
		childListViewItem.tvAllPrice.setText("￥"
				+ list.get(position).getTotal() + "");
		childListViewItem.tvState.setText(list.get(position).getState().getName());
		childListViewItem.btnShopName.setText(list.get(position).getShop().getName());
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		bitmapUtils.display(childListViewItem.ivShopImage, context
				.getResources().getString(R.string.url_pre)
				+ list.get(position).getShop().getImgPath());
		//listview添加tag（Position ）
		
		//adapter由对应的tag来确定：orderdetailList.get(listview.getTag())
		
		CommentGoodsChildAdapter childAdapter = new CommentGoodsChildAdapter(context,orderdetailList.get((Integer) childListViewItem.lv.getTag()));
		childListViewItem.lv.setAdapter(childAdapter);
		
//		childListViewItem.lv.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View v, int position,
//					long id) {
//				String goodsname = orderdetailList.get((Integer) parent.getTag()).get(position).getGoodsName();
//				Toast.makeText(context, goodsname, 1).show();
//			}
//		});
		
		
		return convertView;
	}

	public class ChildListViewItem {

		Button btnDel, btnQuery,  btnEvaluate;
		TextView tvAllPrice, tvCount, tvState, btnShopName;
		ListView lv;
		ImageView ivShopImage;
		RelativeLayout rlshop;
		LinearLayout llorder;
	}

	@Override
	public void onClick(View v) {
		int oid = list.get((Integer) v.getTag()).getOid();
		switch (v.getId()) {
		case R.id.rl_evaluate_goods_shop:
			String shopname = list.get((Integer) v.getTag()).getShop().getName();
			Toast.makeText(context, "商店"+shopname,Toast.LENGTH_SHORT).show();
			break;
		case R.id.ll_evaluate_goods_item_goodscount:
//			String shopname = orderdetailList.get((Integer) childListViewItem.lv.getTag()).get(oid)
			Toast.makeText(context, "商店"+oid,Toast.LENGTH_SHORT).show();
			break;
		case R.id.bt_evaluate_goods__item_delete:
//			list.remove((Integer) v.getTag());
			Toast.makeText(context, "删除订单"+oid,Toast.LENGTH_SHORT).show();
			break;
		case R.id.bt_evaluate_goods__item_logistics:
			
			break;
		case R.id.bt_evaluate_goods__item_evaluate:
	
			break;
		}
	}
}
