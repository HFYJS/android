package com.sport.ui.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
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

public class GoodDetailActivity extends BaseActivity {

	private List<View> viewList = new ArrayList<View>();;
	private ViewPager viewPager;
	TextView tvGName;
	TextView tvGPrice;
	TextView tvGSales;// 月销量
	TextView tvSAddress;
	TextView tvCount;//
	TextView tvAssess;// 评价详情
	ImageView ivUpic;
	TextView tvUName;
	TextView tdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	void setContent() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_good_detail);
	}

	@Override
	void initView() {
		// 初始化控件
		tvGName = (TextView) findViewById(R.id.tv_gdetail_gname);// 商品名
		tvGPrice = (TextView) findViewById(R.id.tv_gdetail_price);// 商品价格
		tvGSales = (TextView) findViewById(R.id.tv_gdetail_sales);// 月销量
		tvSAddress = (TextView) findViewById(R.id.tv_gdetail_address);// 商家地区
		tvCount = (TextView) findViewById(R.id.tv_gdetail_assess_count);// 评价总数
		tvAssess = (TextView) findViewById(R.id.tv_gdetail_assess);// 评价详情
		ivUpic = (ImageView) findViewById(R.id.iv_gdetail_user_pic);// 顾客头像
		tvUName = (TextView) findViewById(R.id.tv_gdetail_user_name);// 顾客姓名
		tdate = (TextView) findViewById(R.id.tv_gdetail_assess_date);// 评价日期

		// 用XUtils获取服务器传过来的数据,get
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("gid", "1");

		String url = getResources().getString(R.string.url_pre)
				+ "ShowGoodsDetailServlet";
		Log.i("Log", url);
		http.send(HttpMethod.GET, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("Log", "Failure");
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				String result = responseInfo.result;
				Gson gson = new Gson();
				Type type = new TypeToken<Map<String, String>>() {
				}.getType();
				Map<String, String> map = gson.fromJson(result, type);

				// 为控件赋值
				tvGName.setText(map.get("gName"));
				tvGPrice.setText(map.get("gPrice"));
				tvGSales.setText("月销" + map.get("gSales") + "笔");// 月销量
				tvSAddress.setText(map.get("sAddress").replace("中国", ""));
				tvCount.setText("宝贝评价(" + map.get("count") + ")");
				tvAssess.setText(map.get("content"));// 评价详情
				tvUName.setText(map.get("userName"));
				tdate.setText(map.get("date"));
				// 显示用户头像
				BitmapUtils bmu = new BitmapUtils(GoodDetailActivity.this);
				bmu.display(ivUpic, getResources().getString(R.string.url_pre)
						+ map.get("userPic"));

				/*
				 * 加载viewPager以及显示从数据库取出的图片
				 */

				// 对应的viewPager
				viewPager = (ViewPager) findViewById(R.id.pager);

				// 用Splite分割
				String[] gPics = map.get("gPic").split(",");
				// 设置布局
				for (int i = 0; i < gPics.length; i++) {
					// 新建一个imageView
					ImageView view1 = new ImageView(GoodDetailActivity.this);

					view1.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT));
					// 获取图片
					BitmapUtils bitmapUtils = new BitmapUtils(
							GoodDetailActivity.this);
					// 加载图片到view1上
					bitmapUtils.display(view1,
							getResources().getString(R.string.url_pre)
									+ gPics[i]);
					viewList.add(view1);
				}

				// 设置pagerAdapter
				PagerAdapter pagerAdapter = new PagerAdapter() {

					@Override
					public boolean isViewFromObject(View arg0, Object arg1) {
						// TODO Auto-generated method stub
						return arg0 == arg1;
					}

					@Override
					public int getCount() {
						// TODO Auto-generated method stub
						return viewList.size();
					}

					@Override
					public void destroyItem(ViewGroup container, int position,
							Object object) {
						// TODO Auto-generated method stub
						container.removeView(viewList.get(position));
					}

					@Override
					public Object instantiateItem(ViewGroup container,
							int position) {
						// TODO Auto-generated method stub

						container.addView(viewList.get(position));

						return viewList.get(position);
					}
				};

				viewPager.setAdapter(pagerAdapter);
			}
		});

	}

	@Override
	void initFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	void setListener() {
		// TODO Auto-generated method stub

	}

}
