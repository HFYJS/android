package com.sport.ui.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sport.R;
import com.sport.entity.temp.Order;
import com.sport.entity.temp.OrderInfo;
import com.sport.ui.adapter.CommentGoodsAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class WaitingCommentActivity extends Activity {
	ListView evaView;
	RelativeLayout rlback;
	CommentGoodsAdapter adapter;
	Map<String, String> orderInfo = new HashMap<String, String>();
	List<OrderInfo> orderList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_goods);
		evaView = (ListView) findViewById(R.id.evaluateGoodListView);
		rlback = (RelativeLayout) findViewById(R.id.rl_activity_comment_goods);
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
				+ "GetWaitCommentOrderServlet";
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
				adapter = new CommentGoodsAdapter(orderList,
						WaitingCommentActivity.this, orderdetailList);

				evaView.setAdapter(adapter);
				Log.i("log","SetonItem");
			}
		});
	}
}
