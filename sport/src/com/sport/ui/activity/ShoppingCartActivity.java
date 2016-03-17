package com.sport.ui.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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

						//
						lv.setAdapter(new ShoppingCartAdapter(
								ShoppingCartActivity.this,
								R.layout.shoppingcart_item, shoppingCart) {

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

	private class ShoppingCartAdapter extends BaseAdapter implements
			OnClickListener {
		Context context;
		int id;
		List<Map<String, String>> list;

		List<View> views = new ArrayList<View>();

		public ShoppingCartAdapter(Context context, int id,
				List<Map<String, String>> list) {
			this.context = context;
			this.id = id;
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
			ViewHolder vh = new ViewHolder();
			if (null == convertView) {
				convertView = LayoutInflater.from(context).inflate(id, parent,
						false);
				vh.cb = (CheckBox) convertView
						.findViewById(R.id.cb_shoppingcart_item_select);
				// checkbox设置tag
				vh.cb.setTag(position);
				vh.iv = (ImageView) convertView
						.findViewById(R.id.iv_shoppingcart_item_img);
				vh.tvName = (TextView) convertView
						.findViewById(R.id.tv_shoppingcart_item_name);
				vh.tvPrice = (TextView) convertView
						.findViewById(R.id.tv_shoppingcart_item_price);
				vh.tvCount = (TextView) convertView
						.findViewById(R.id.tv_shoppingcart_item_count);
				// 数量TextView设置tag
				vh.tvCount.setTag(position);
				vh.btnSub = (Button) convertView
						.findViewById(R.id.btn_shoppingcart_item_sub);
				// sub按钮设置tag
				vh.btnSub.setTag(position);
				vh.btnAdd = (Button) convertView
						.findViewById(R.id.btn_shoppingcart_item_add);
				// add按钮设置tag
				vh.btnAdd.setTag(position);

				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			// 解决焦点冲突并且为按钮添加监听事件
			vh.cb.setFocusable(false);
			vh.btnSub.setOnClickListener(this);
			vh.btnSub.setFocusable(false);
			vh.btnAdd.setOnClickListener(this);
			vh.btnAdd.setFocusable(false);

			// json解析数据源
			Goods goods = new Gson().fromJson(list.get(position).get("goods"),
					Goods.class);
			int count = Integer.parseInt(list.get(position).get("count"));

			// UI显示操作
			new BitmapUtils(context).display(
					vh.iv,
					getResources().getString(R.string.url_pre)
							+ goods.getImgPath());
			vh.tvName.setText(goods.getName());
			if (null == goods.getActivity()) {
				vh.tvPrice.setText("¥" + goods.getPrice() * count);
			} else {
				vh.tvPrice.setText("¥" + goods.getActPrice() * count);
			}
			vh.tvCount.setText(count + "");

			views.add(convertView);
			return convertView;
		}

		private class ViewHolder {
			CheckBox cb;
			ImageView iv;
			TextView tvName;
			TextView tvPrice;
			TextView tvCount;
			Button btnSub;
			Button btnAdd;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 点击"—"按钮
			case R.id.btn_shoppingcart_item_sub:
				// 获得按钮tag
				int positionSub = (int) v.getTag();

				// 根据tag获得对应的TextView(数量)并重新设置显示值
				ViewHolder vhSub = (ViewHolder) views.get(positionSub).getTag();
				int countSub = Integer.parseInt(vhSub.tvCount.getText()
						.toString());
				if (countSub > 1) {
					vhSub.tvCount.setText(countSub - 1 + "");
				}
				break;
			// 点击"+"按钮
			case R.id.btn_shoppingcart_item_add:
				int positionAdd = (int) v.getTag();

				ViewHolder vhAdd = (ViewHolder) views.get(positionAdd).getTag();
				int countAdd = Integer.parseInt(vhAdd.tvCount.getText()
						.toString());
				vhAdd.tvCount.setText(countAdd + 1 + "");
				break;
			}
		}

	}
}
