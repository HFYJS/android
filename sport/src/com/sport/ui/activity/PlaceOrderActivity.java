package com.sport.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sport.R;
import com.sport.entity.Address;
import com.sport.entity.Goods;
import com.sport.entity.Shop;
import com.sport.entity.State;
import com.sport.entity.User;
import com.sport.entity.temp.Order;
import com.sport.entity.temp.OrderInfo;
import com.sport.myview.CommentGoodsChildLV;
import com.sport.ui.activity.PlaceOrderActivity.PlaceOrderAdapter.ChildListViewItem;
import com.sport.ui.adapter.CommentGoodsChildAdapter;

//购物车传的值：收件人信息，order，orderinfo
public class PlaceOrderActivity extends Activity {
	ChildListViewItem childListViewItem = null;
	CommentGoodsChildLV evaView;
	LinearLayout lladress;
	LinearLayout llback;
	PlaceOrderAdapter adapter;
	Map<String, String> orderInfo = new HashMap<String, String>();
	List<OrderInfo> orderList;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_place_order);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		evaView = (CommentGoodsChildLV) findViewById(R.id.lv_activity_place_order);
		lladress = (LinearLayout) findViewById(R.id.ll_activity_place_order_adress);
		llback = (LinearLayout) findViewById(R.id.ll_activity_place_order_back);
		llback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		lladress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到收件人信息
				saveOrder();
			}
		});
		initData();
	}

	public void saveOrder() {
		Address address = new Address();
		address.setAid(1);
		Shop shop1 = new Shop();
		shop1.setSid(1);
		User user = new User();
		user.setUid(1);
		address.setUser(user);
		List<OrderInfo> test = new ArrayList<OrderInfo>();
		OrderInfo orderInfo1 = new OrderInfo();
		orderInfo1.setAddress(address);
		orderInfo1.setShop(shop1);
		orderInfo1.setNote("22");
		orderInfo1.setTotal(20);
		State state = new State();
		state.setStateid(1);
		orderInfo1.setState(state);
		Address address1 = new Address();
		address1.setAid(1);
		User user1 = new User();
		user1.setUid(1);
		address1.setUser(user1);
		OrderInfo orderInfo2 = new OrderInfo();
		orderInfo2.setAddress(address1);
		Shop shop2 = new Shop();
		shop2.setSid(2);
		orderInfo2.setShop(shop2);
		orderInfo2.setNote("23");
		orderInfo2.setTotal(20);
		State state1 = new State();
		state1.setStateid(2);
		orderInfo2.setState(state1);
		test.add(orderInfo1);
		test.add(orderInfo2);
		String str = new Gson().toJson(test);
		List<List<Order>> orderdetailList = new ArrayList<List<Order>>();
		List<Order> or1 = new ArrayList<Order>();
		Order order1 = new Order();
		order1.setAllprice(20);
		order1.setCount(1);
		Goods goods1 = new Goods();
		goods1.setGid(1);
		goods1.setSid(1);
		order1.setGoods(goods1);
		order1.setSid(1);
		or1.add(order1);
		Order order2 = new Order();
		order2.setAllprice(30);
		order2.setCount(2);
		Goods goods2 = new Goods();
		goods2.setGid(3);
		goods2.setSid(1);
		order2.setGoods(goods2);
		order2.setSid(1);
		or1.add(order2);
		List<Order> or2 = new ArrayList<Order>();
		Order order3 = new Order();
		order3.setAllprice(3);
		order3.setCount(3);
		Goods goods3 = new Goods();
		goods3.setGid(10);
		goods3.setSid(2);
		order3.setGoods(goods3);
		order3.setSid(1);
		or2.add(order3);
		orderdetailList.add(or1);
		orderdetailList.add(or2);
		String ostr = new Gson().toJson(orderdetailList);

		HttpUtils http = new HttpUtils();
		String url = getResources().getString(R.string.url_pre)
				+ "CreateOrderServlet";
		RequestParams params = new RequestParams();
		params.addBodyParameter("order", ostr);
		Log.i("log", orderInfo.get("orderlist").isEmpty() + "");
		params.addBodyParameter("orderinfo", str);
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				Toast.makeText(PlaceOrderActivity.this, "创建成功",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void initData() {

		HttpUtils http = new HttpUtils();
		String url = getResources().getString(R.string.url_pre)
				+ "GetAllOrdersByUidServlet";
		String uid = "1";
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("uid", uid);
		http.send(HttpMethod.GET, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Log.i("log", "onFailure");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String orderResult = responseInfo.result;
				Log.i("log", orderResult);
				orderInfo = new Gson().fromJson(orderResult,
						new TypeToken<Map<String, String>>() {
						}.getType());
				String orderStr = orderInfo.get("orderinfolist");

				orderList = new Gson().fromJson(orderStr,
						new TypeToken<List<OrderInfo>>() {
						}.getType());
				String orderdetailStr = orderInfo.get("orderlist");
				// 订单order的list
				List<List<Order>> orderdetailList = new Gson().fromJson(
						orderdetailStr, new TypeToken<List<List<Order>>>() {
						}.getType());
				adapter = new PlaceOrderAdapter(orderList,
						PlaceOrderActivity.this, orderdetailList);

				evaView.setAdapter(adapter);
				Log.i("log", "SetonItem");

			}
		});
	}

	class PlaceOrderAdapter extends BaseAdapter implements OnClickListener {

		private List<OrderInfo> list;
		List<List<Order>> orderdetailList;
		Context context;
		Map<String, String> orderMap;
		private LayoutInflater inflater;

		// private CommentGoodsChildAdapter childAdapter;

		public PlaceOrderAdapter(List<OrderInfo> list, Context context,
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

			if (convertView == null) {
				childListViewItem = new ChildListViewItem();
				convertView = inflater.inflate(R.layout.place_order_item,
						parent, false);
				childListViewItem.tvCount = (TextView) convertView
						.findViewById(R.id.tv_place_order_item_goodscount);
				childListViewItem.tvAllPrice = (TextView) convertView
						.findViewById(R.id.tv_place_order__item_goodsprice);

				childListViewItem.btnShopName = (TextView) convertView
						.findViewById(R.id.bt_place_order_item_shop_name);
				childListViewItem.etNote = (EditText) convertView
						.findViewById(R.id.et_place_order__item_note);

				childListViewItem.ivShopImage = (ImageView) convertView
						.findViewById(R.id.iv_place_order_item_shop_image);
				childListViewItem.rlshop = (RelativeLayout) convertView
						.findViewById(R.id.rl_place_order_shop);
				childListViewItem.lv = (ListView) convertView
						.findViewById(R.id.lv_place_order_item_goodscount);

				childListViewItem.rlshop.setTag(position);

				childListViewItem.lv.setTag(position);

				childListViewItem.rlshop.setOnClickListener(this);

				// 嵌套listview

				convertView.setTag(childListViewItem);
			} else {
				childListViewItem = (ChildListViewItem) convertView.getTag();
			}

			childListViewItem.tvCount.setText("共 "
					+ list.get(position).getSum() + " 件商品");
			childListViewItem.tvAllPrice.setText("￥"
					+ list.get(position).getTotal() + "");
			childListViewItem.btnShopName.setText(list.get(position).getShop()
					.getName());
			BitmapUtils bitmapUtils = new BitmapUtils(context);
			bitmapUtils.display(childListViewItem.ivShopImage,
					context.getResources().getString(R.string.url_pre)
							+ list.get(position).getShop().getImgPath());
			// listview添加tag（Position ）

			// adapter由对应的tag来确定：orderdetailList.get(listview.getTag())

			CommentGoodsChildAdapter childAdapter = new CommentGoodsChildAdapter(
					context, orderdetailList.get((Integer) childListViewItem.lv
							.getTag()));
			childListViewItem.lv.setAdapter(childAdapter);

			return convertView;
		}

		class ChildListViewItem {

			TextView tvAllPrice, tvCount, btnShopName;
			EditText etNote;
			ListView lv;
			ImageView ivShopImage;
			RelativeLayout rlshop;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

		}

	}
}
