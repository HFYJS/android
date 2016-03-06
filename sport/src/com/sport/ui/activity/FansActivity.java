package com.sport.ui.activity;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sport.R;
import com.sport.entity.User;
import com.sport.ui.adapter.CommonAdapter;
import com.sport.ui.adapter.ViewHolder;

public class FansActivity extends BaseActivity {

	ListView lvFans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		showFans();
	}

	private void showFans() {
		// TODO Auto-generated method stub
		String url = getResources().getString(R.string.url_pre)
				+ "GetAllFansByUidServlet";

		HttpUtils httpUtils = new HttpUtils();
		RequestParams requestParams = new RequestParams();
		requestParams.addQueryStringParameter("uid", "1");

		httpUtils.send(HttpRequest.HttpMethod.GET, url, requestParams,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String result = arg0.result;
						List<User> users = new Gson().fromJson(result,
								new TypeToken<List<User>>() {
								}.getType());

						lvFans.setAdapter(new CommonAdapter<User>(
								FansActivity.this, R.layout.fans_item, users) {

							@Override
							protected void setValue(ViewHolder vh, User value) {
								// TODO Auto-generated method stub
								new BitmapUtils(FansActivity.this).display(
										vh.getView(R.id.iv_fans_item_img),
										getResources().getString(
												R.string.url_pre)
												+ value.getHeadPath());

								vh.setTextView(R.id.tv_fans_item_name,
										value.getName());
								vh.setTextView(R.id.tv_fans_item_sex,
										value.getSex());
								vh.setTextView(R.id.tv_fans_item_age,
										value.getAge() + "");
							}

						});
					}

				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	void initView() {
		// TODO Auto-generated method stub
		lvFans = (ListView) findViewById(R.id.lv_fans);
	}

	@Override
	void initFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContent() {
		// TODO Auto-generated method stub
		setContentView(R.layout.fans);
	}

}
