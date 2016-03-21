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
	TextView tvGSales;// ������
	TextView tvSAddress;
	TextView tvCount;//
	TextView tvAssess;// ��������
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
		// ��ʼ���ؼ�
		tvGName = (TextView) findViewById(R.id.tv_gdetail_gname);// ��Ʒ��
		tvGPrice = (TextView) findViewById(R.id.tv_gdetail_price);// ��Ʒ�۸�
		tvGSales = (TextView) findViewById(R.id.tv_gdetail_sales);// ������
		tvSAddress = (TextView) findViewById(R.id.tv_gdetail_address);// �̼ҵ���
		tvCount = (TextView) findViewById(R.id.tv_gdetail_assess_count);// ��������
		tvAssess = (TextView) findViewById(R.id.tv_gdetail_assess);// ��������
		ivUpic = (ImageView) findViewById(R.id.iv_gdetail_user_pic);// �˿�ͷ��
		tvUName = (TextView) findViewById(R.id.tv_gdetail_user_name);// �˿�����
		tdate = (TextView) findViewById(R.id.tv_gdetail_assess_date);// ��������

		// ��XUtils��ȡ������������������,get
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

				// Ϊ�ؼ���ֵ
				tvGName.setText(map.get("gName"));
				tvGPrice.setText(map.get("gPrice"));
				tvGSales.setText("����" + map.get("gSales") + "��");// ������
				tvSAddress.setText(map.get("sAddress").replace("�й�", ""));
				tvCount.setText("��������(" + map.get("count") + ")");
				tvAssess.setText(map.get("content"));// ��������
				tvUName.setText(map.get("userName"));
				tdate.setText(map.get("date"));
				// ��ʾ�û�ͷ��
				BitmapUtils bmu = new BitmapUtils(GoodDetailActivity.this);
				bmu.display(ivUpic, getResources().getString(R.string.url_pre)
						+ map.get("userPic"));

				/*
				 * ����viewPager�Լ���ʾ�����ݿ�ȡ����ͼƬ
				 */

				// ��Ӧ��viewPager
				viewPager = (ViewPager) findViewById(R.id.pager);

				// ��Splite�ָ�
				String[] gPics = map.get("gPic").split(",");
				// ���ò���
				for (int i = 0; i < gPics.length; i++) {
					// �½�һ��imageView
					ImageView view1 = new ImageView(GoodDetailActivity.this);

					view1.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT));
					// ��ȡͼƬ
					BitmapUtils bitmapUtils = new BitmapUtils(
							GoodDetailActivity.this);
					// ����ͼƬ��view1��
					bitmapUtils.display(view1,
							getResources().getString(R.string.url_pre)
									+ gPics[i]);
					viewList.add(view1);
				}

				// ����pagerAdapter
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
