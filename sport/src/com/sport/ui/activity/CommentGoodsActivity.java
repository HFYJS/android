package com.sport.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sport.R;
import com.sport.entity.OrderDetail;
import com.sport.entity.OrderForm;
import com.sport.entity.temp.Order;
import com.sport.entity.temp.OrderInfo;
import com.sport.ui.adapter.CommentGoodsAdapter;
import com.sport.ui.adapter.CommentGoodsChildAdapter;

public class CommentGoodsActivity extends Activity {
	ListView evaView;
	CommentGoodsAdapter adapter;
	CommentGoodsChildAdapter childadpter;
	// List<String> orderInfo;
	Map<String, String> orderInfo = new HashMap<String, String>();
	List<OrderForm> orderlist = new ArrayList<OrderForm>();
	List<OrderDetail> list1 = new ArrayList<OrderDetail>();
	int count = 0;
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_goods);
		evaView = (ListView) findViewById(R.id.evaluateGoodListView);
		initData();

		// childadpter = new EvaluateGoodsCountAdapter(this,list1);

		// evaView.setOnRefreshCallBack(new OnRefreshCallBack() {
		//
		// @Override
		// public void onRefresh() {
		// handler.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// orderlist.clear();
		// //添加新的数据:products
		// refreshData();
		// //改变listview数据显示
		// if(adapter!=null){
		// adapter.notifyDataSetChanged();
		// }
		// //调用刷新完成方法
		// evaView.completeRefresh();
		// Toast.makeText(CommentGoodsActivity.this, "更新完成", 1).show();
		// }
		//
		// }, 2000);
		//
		// }
		//
		// @Override
		// public void onPull() {
		// handler.postDelayed(new Runnable() {
		// public void run() {
		// List<OrderForm> addlist = new ArrayList<OrderForm>();
		//
		// for(int i=0;i<=10;i++){
		// OrderForm order = new OrderForm("交易失败", i);
		// addlist.add(order);
		// }
		//
		// if(addlist!=null&&addlist.size()>0&&count<3){
		//
		// orderlist.addAll(addlist);
		// //更新界面
		// if(adapter!=null){
		// adapter.notifyDataSetChanged();
		// }
		// count++;//加载次数
		//
		// }else{
		// //没有数据，提示加载完成
		// Toast.makeText(CommentGoodsActivity.this, "没有更多数据", 1).show();
		// }
		// evaView.completePull();
		// }
		// }, 2000);
		//
		// }
		// });
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
				// TODO Auto-generated method stub
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
				List<OrderInfo> orderList = new Gson().fromJson(orderStr,
						new TypeToken<List<OrderInfo>>() {
						}.getType());

				// orderMap = new Gson().fromJson(
				// orderResult, new TypeToken<Map<String,String>>() {
				// }.getType());
				String orderdetailStr = orderInfo.get("orderlist");
				List<List<Order>> orderdetailList = new Gson().fromJson(
						orderdetailStr, new TypeToken<List<List<Order>>>() {
						}.getType());
				adapter = new CommentGoodsAdapter(orderList,
						CommentGoodsActivity.this, orderdetailList);

				evaView.setAdapter(adapter);
			}
		});
	}

}
