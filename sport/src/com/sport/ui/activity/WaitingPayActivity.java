package com.sport.ui.activity;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.sport.ui.adapter.CommentGoodsChildAdapter;

public class WaitingPayActivity extends Activity {
	ListView evaView;
	RelativeLayout rlback;
	WaitingPayAdapter adapter;
	Map<String, String> orderInfo = new HashMap<String, String>();
	List<OrderInfo> orderList;

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_waiting_pay);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		evaView = (ListView) findViewById(R.id.WaitingPayListView);
		rlback = (RelativeLayout) findViewById(R.id.rl_activity_waiting_pay);
		rlback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		initData();
	}

	private void initData() {

		HttpUtils http = new HttpUtils();
		String url = getResources().getString(R.string.url_pre)
				+ "GetWaitPayOrderByUidServlet";
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
				List<List<Order>> orderdetailList = new Gson().fromJson(
						orderdetailStr, new TypeToken<List<List<Order>>>() {
						}.getType());
				adapter = new WaitingPayAdapter(orderList,
						WaitingPayActivity.this, orderdetailList);

				evaView.setAdapter(adapter);
				Log.i("log", "SetonItem");
			}
		});
	}

	class WaitingPayAdapter extends BaseAdapter implements OnClickListener {

		private List<OrderInfo> list;
		List<List<Order>> orderdetailList;
		Context context;
		Map<String, String> orderMap;
		private LayoutInflater inflater;

		// private CommentGoodsChildAdapter childAdapter;

		public WaitingPayAdapter(List<OrderInfo> list, Context context,
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
				convertView = inflater.inflate(R.layout.order_waiting_pay_item,
						parent, false);
				childListViewItem.btnwaitingpay = (Button) convertView
						.findViewById(R.id.bt_waitingpay_goods__item_pay);
				childListViewItem.tvCount = (TextView) convertView
						.findViewById(R.id.tv_waitingpay_goods_item_goodscount);
				childListViewItem.tvAllPrice = (TextView) convertView
						.findViewById(R.id.tv_waitingpay_goods__item_goodsprice);
				childListViewItem.tvState = (TextView) convertView
						.findViewById(R.id.tv_waitingpay_goods_item_dealstatus);
				childListViewItem.btnShopName = (TextView) convertView
						.findViewById(R.id.bt_waitingpay_goods_item_shop_name);
				childListViewItem.ivShopImage = (ImageView) convertView
						.findViewById(R.id.iv_waitingpay_goods_item_shop_image);
				childListViewItem.rlshop = (RelativeLayout) convertView
						.findViewById(R.id.rl_waitingpay_goods_shop);
				childListViewItem.lv = (ListView) convertView
						.findViewById(R.id.lv_waitingpay_goods_item_goodscount);

				childListViewItem.rlshop.setTag(position);

				childListViewItem.btnwaitingpay.setTag(position);
				childListViewItem.lv.setTag(position);

				childListViewItem.btnwaitingpay.setOnClickListener(this);
				childListViewItem.rlshop.setOnClickListener(this);

				// Ƕ��listview

				convertView.setTag(childListViewItem);
			} else {
				childListViewItem = (ChildListViewItem) convertView.getTag();
			}

			childListViewItem.tvCount.setText("�� "
					+ list.get(position).getSum() + " ����Ʒ");
			childListViewItem.tvAllPrice.setText("��"
					+ list.get(position).getTotal() + "");
			childListViewItem.tvState.setText(list.get(position).getState()
					.getName());
			childListViewItem.btnShopName.setText(list.get(position).getShop()
					.getName());
			BitmapUtils bitmapUtils = new BitmapUtils(context);
			bitmapUtils.display(childListViewItem.ivShopImage,
					context.getResources().getString(R.string.url_pre)
							+ list.get(position).getShop().getImgPath());
			// listview���tag��Position ��

			// adapter�ɶ�Ӧ��tag��ȷ����orderdetailList.get(listview.getTag())

			CommentGoodsChildAdapter childAdapter = new CommentGoodsChildAdapter(
					context, orderdetailList.get((Integer) childListViewItem.lv
							.getTag()));
			childListViewItem.lv.setAdapter(childAdapter);

			return convertView;
		}

		public class ChildListViewItem {

			Button btnwaitingpay;
			TextView tvAllPrice, tvCount, tvState, btnShopName;
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
