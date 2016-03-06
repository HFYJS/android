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

public class FoucsActivity extends BaseActivity {

	ListView lvFoucs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		showfoucs();
	}

	private void showfoucs() {
		// TODO Auto-generated method stub
		String url = getResources().getString(R.string.url_pre)
				+ "GetAllAttentionsByUidServlet";
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

						lvFoucs.setAdapter(new CommonAdapter<User>(
								FoucsActivity.this, R.layout.foucs_item, users) {
							@Override
							protected void setValue(ViewHolder vh, User value) {
								// TODO Auto-generated method stub
								new BitmapUtils(FoucsActivity.this).display(
										vh.getView(R.id.iv_foucs_item_img),
										getResources().getString(
												R.string.url_pre)
												+ value.getHeadPath());

								vh.setTextView(R.id.tv_foucs_item_name,
										value.getName());
								vh.setTextView(R.id.tv_foucs_item_sex,
										value.getSex());
								vh.setTextView(R.id.tv_foucs_item_age,
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
		lvFoucs = (ListView) findViewById(R.id.lv_foucs);
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
		setContentView(R.layout.foucs);
	}

}
