package com.sport.ui.activity;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

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
import com.sport.entity.Goods;
import com.sport.ui.adapter.CommonAdapter;
import com.sport.ui.adapter.ViewHolder;

public class ShoppingCartActivity extends BaseActivity implements
		OnItemClickListener {

	CheckBox cbSelectAll;
	TextView tvPrice;
	Button btnPay;

	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		showShoppingCart();
	}

	private void showShoppingCart() {
		// TODO Auto-generated method stub
		String url = getResources().getString(R.string.url_pre)
				+ "GetShoppingCartByUidServlet";

		HttpUtils http = new HttpUtils();

		RequestParams params = new RequestParams();
		params.addQueryStringParameter("uid", "1");

		http.send(HttpRequest.HttpMethod.GET, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String result = arg0.result;
						List<Map<String, String>> shoppingCart = new Gson()
								.fromJson(
										result,
										new TypeToken<List<Map<String, String>>>() {
										}.getType());

						lv.setAdapter(new CommonAdapter<Map<String, String>>(
								ShoppingCartActivity.this,
								R.layout.shoppingcart_item, shoppingCart) {

							@Override
							protected void setValue(ViewHolder vh,
									Map<String, String> value) {
								// TODO Auto-generated method stub
								Goods goods = new Gson().fromJson(
										value.get("goods"), Goods.class);
								int count = Integer.parseInt(value.get("count"));

								new BitmapUtils(ShoppingCartActivity.this).display(
										vh.getView(R.id.iv_shoppingcart_item_img),
										getResources().getString(
												R.string.url_pre)
												+ goods.getImgPath());
								vh.setTextView(R.id.tv_shoppingcart_item_name,
										goods.getName());
								vh.setTextView(R.id.tv_shoppingcart_item_price,
										"¥" + goods.getPrice() * count);
								vh.setTextView(R.id.tv_shoppingcart_item_count,
										count + "");
							}
						});
					}
				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_shoppingcart_pay:
			break;
		}
	}

	@Override
	void setContent() {
		// TODO Auto-generated method stub
		setContentView(R.layout.shoppingcart);
	}

	@Override
	void initView() {
		// TODO Auto-generated method stub
		cbSelectAll = (CheckBox) findViewById(R.id.cb_shoppingcart_select_all);
		tvPrice = (TextView) findViewById(R.id.tv_shoppingcart_price);
		btnPay = (Button) findViewById(R.id.btn_shoppingcart_pay);

		lv = (ListView) findViewById(R.id.iv_shoppingcart);
	}

	@Override
	void initFragment() {
		// TODO Auto-generated method stub
	}

	@Override
	void setListener() {
		// TODO Auto-generated method stub
		btnPay.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

}
