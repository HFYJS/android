package com.sport.ui.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.sport.entity.temp.Order;
import com.sport.entity.temp.OrderInfo;
import com.sport.myview.CommentGoodsChildLV;
import com.sport.ui.adapter.OrderDetailsAdapter;

//购物车传的值：收件人信息，order，orderinfo
public class OrderDetailsActivity extends Activity {

	CommentGoodsChildLV evaView;
	TextView tvstate, tvlogic, tvuser, tvaddress, tvtel, tvshopname,
			tvallprice;
	ImageView ivshop;
	LinearLayout llshop;
	LinearLayout llback;
	OrderDetailsAdapter adapter;
	Map<String, String> orderInfo = new HashMap<String, String>();
	OrderInfo order;
	List<Order> orderdetailList;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_details);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		evaView = (CommentGoodsChildLV) findViewById(R.id.lv_activity_order_detail);
		llshop = (LinearLayout) findViewById(R.id.ll_activity_order_detail_shopname);
		llback = (LinearLayout) findViewById(R.id.ll_activity_order_detail_back);
		tvstate = (TextView) findViewById(R.id.tv_activity_order_detail_state);
		tvlogic = (TextView) findViewById(R.id.tv_activity_order_detail_logic);
		tvuser = (TextView) findViewById(R.id.tv_activity_order_detail_user);
		tvaddress = (TextView) findViewById(R.id.tv_activity_order_detail_address);
		tvtel = (TextView) findViewById(R.id.tv_activity_order_detail_usernumber);
		tvshopname = (TextView) findViewById(R.id.tv_activity_order_detail_shopname);
		// ivshop = (ImageView)
		// findViewById(R.id.iv_activity_order_detail_shop);
		tvallprice = (TextView) findViewById(R.id.tv_activity_order_detail_allprice);
		llback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		llshop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到商店信息

			}
		});
		initData();
	}

	public void saveOrder() {

	}

	private void initData() {

		HttpUtils http = new HttpUtils();
		String url = getResources().getString(R.string.url_pre)
				+ "GetOrderDetailsByOidServlet";
		String oid = "1";
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("oid", oid);
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
				String orderStr = orderInfo.get("orderinfo");
				order = new Gson().fromJson(orderStr,
						new TypeToken<OrderInfo>() {
						}.getType());
				String orderdetailStr = orderInfo.get("orderlist");
				// 订单order的list
				orderdetailList = new Gson().fromJson(orderdetailStr,
						new TypeToken<List<Order>>() {
						}.getType());
				adapter = new OrderDetailsAdapter(OrderDetailsActivity.this,
						orderdetailList);

				evaView.setAdapter(adapter);

				tvstate.setText(order.getState().getName());
				// tvlogic.setText();
				tvuser.setText(order.getAddress().getName());
				tvaddress.setText(order.getAddress().getAddress());
				tvtel.setText(order.getAddress().getTel());
				tvshopname.setText(order.getShop().getName());
				tvallprice.setText("总金额：￥" + order.getTotal() + "");
				BitmapUtils bitmapUtils = new BitmapUtils(
						OrderDetailsActivity.this);
				bitmapUtils.display(ivshop, OrderDetailsActivity.this
						.getResources().getString(R.string.url_pre)
						+ order.getShop().getImgPath());
				evaView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View v,
							int position, long id) {
						String gname = orderdetailList.get(position).getGoods()
								.getName();
						Toast.makeText(OrderDetailsActivity.this, gname, 1)
								.show();
					}
				});
				Log.i("log", "SetonItem");
			}
		});
	}

}
