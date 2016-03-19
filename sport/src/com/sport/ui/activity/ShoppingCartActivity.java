package com.sport.ui.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.sport.util.NetUtil;

public class ShoppingCartActivity extends BaseActivity implements
		OnItemClickListener {

	CheckBox cbSelectAll;
	TextView tvPrice;
	Button btnEdit;
	Button btnPay;

	List<Map<String, String>> shoppingCart;

	ListView lv;

	List<String> isChecked = new ArrayList<String>();

	Map<String, Double> prices = new HashMap<String, Double>();

	ShoppingCartAdapter shoppingCartAdapter;

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
						shoppingCart = new Gson().fromJson(result,
								new TypeToken<List<Map<String, String>>>() {
								}.getType());

						//
						shoppingCartAdapter = new ShoppingCartAdapter(
								ShoppingCartActivity.this,
								R.layout.shoppingcart_item, shoppingCart) {

						};
						lv.setAdapter(shoppingCartAdapter);
					}
				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_shoppingcart_edit:
			if (btnEdit.getText().equals("编辑")) {
				// 点击编辑后，改变显示控件
				btnEdit.setText("完成");
				btnPay.setText("删除");

				shoppingCartAdapter.change();
			} else {
				// 点击完成后，改变显示控件之后还要更新数据库
				btnEdit.setText("编辑");
				btnPay.setText("结算");

				List<Map<String, String>> texts = shoppingCartAdapter.change();
				new NetUtil() {

					@Override
					public void dealResult(Result result) {
						// TODO Auto-generated method stub

					}

				}.begin(NetUtil.STRING, NetUtil.POST,
						getResources().getString(R.string.url_pre)
								+ "UpdateShoppingCartServlet", "info="
								+ new Gson().toJson(texts));
			}
			break;
		case R.id.btn_shoppingcart_pay:
			if (btnPay.getText().equals("结算")) {

			} else {
				String[] cartids = new String[isChecked.size()];
				for (int i = 0; i < cartids.length; i++) {
					cartids[i] = isChecked.get(i);
				}
				Log.i("log", "" + shoppingCartAdapter.positions.size());
				deleteShoppingCartsByCartids(cartids);

				// 更新数据源
				Log.i("log", "" + shoppingCartAdapter.positions.size());
				for (String s : isChecked) {
					for (Map<String, String> map : shoppingCart) {
						if (map.get("catid").equals(s)) {
							shoppingCartAdapter.list.remove(map);
							break;
						}
					}
					shoppingCartAdapter.positions.remove(s);
					shoppingCartAdapter.views.remove(s);
					Set<Entry<String, Double>> sets = prices.entrySet();
					for (Entry<String, Double> set : sets) {
						if (set.getKey().equals(s)) {
							prices.remove(set.getKey());
							break;
						}
					}
				}

				isChecked.clear();
				shoppingCartAdapter.notifyDataSetChanged();
				Log.i("log", "" + shoppingCartAdapter.positions.size());
				tvPrice.setText("￥0.0");
			}

			break;
		case R.id.cb_shoppingcart_select_all:
			if (cbSelectAll.isChecked()) {
				// 先清空已选中列表
				isChecked.clear();
				// 遍历已创建的view，设置全部checkbox为已选中
				Set<Entry<String, View>> sets = shoppingCartAdapter.views
						.entrySet();
				for (Entry<String, View> set : sets) {
					((ShoppingCartAdapter.ViewHolder) set.getValue().getTag()).cb
							.setChecked(true);
				}

				// 遍历adapter中的数据源把各个view上的价格存到map中
				Double price = 0.0;

				for (Map<String, String> map : shoppingCartAdapter.list) {
					isChecked.add(map.get("catid"));

					Goods goods = new Gson().fromJson(map.get("goods"),
							Goods.class);
					if (null == goods.getActivity()) {
						price += goods.getPrice()
								* Integer.parseInt(map.get("count"));
						prices.put(
								map.get("catid"),
								goods.getPrice()
										* Double.parseDouble(map.get("count")));
					} else {
						price += goods.getActPrice()
								* Integer.parseInt(map.get("count"));
						prices.put(map.get("catid"), goods.getActPrice()
								* Double.parseDouble(map.get("count")));
					}
				}

				// 然后显示总价到“合计”textview上
				tvPrice.setText("￥"
						+ new BigDecimal(price).setScale(1,
								BigDecimal.ROUND_HALF_UP));
			} else {
				// 清空已选中列表
				isChecked.clear();
				// 遍历已创建的view，设置全部checkbox为未选中
				Set<Entry<String, View>> sets = shoppingCartAdapter.views
						.entrySet();
				for (Entry<String, View> set : sets) {
					((ShoppingCartAdapter.ViewHolder) set.getValue().getTag()).cb
							.setChecked(false);
				}
				tvPrice.setText("￥0.0");

			}
			break;
		}
	}

	private void deleteShoppingCartsByCartids(String[] cartids) {
		// TODO Auto-generated method stub
		new NetUtil() {

			@Override
			public void dealResult(Result result) {
				// TODO Auto-generated method stub

			}

		}.begin(NetUtil.STRING, NetUtil.POST,
				getResources().getString(R.string.url_pre)
						+ "DeleteShoppingCartsByCartidsServlet", "cartids="
						+ new Gson().toJson(cartids));

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
		btnEdit = (Button) findViewById(R.id.btn_shoppingcart_edit);
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
		btnEdit.setOnClickListener(this);
		btnPay.setOnClickListener(this);
		cbSelectAll.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	public void refreshPrice() {
		if (isChecked.size() == shoppingCart.size()) {
			cbSelectAll.setChecked(true);
		} else {
			cbSelectAll.setChecked(false);
		}

		Double sum = 0.0;
		for (String s : isChecked) {
			sum += prices.get(s);
		}
		tvPrice.setText("￥"
				+ new BigDecimal(sum).setScale(1, BigDecimal.ROUND_HALF_UP));
	}

	private class ShoppingCartAdapter extends BaseAdapter implements
			OnClickListener {
		Context context;
		int id;
		List<Map<String, String>> list;

		// catid——》convertview
		Map<String, View> views = new HashMap<String, View>();
		// catid——》position
		Map<String, Integer> positions = new HashMap<String, Integer>();

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
				vh.iv = (ImageView) convertView
						.findViewById(R.id.iv_shoppingcart_item_img);
				vh.tvName = (TextView) convertView
						.findViewById(R.id.tv_shoppingcart_item_name);
				vh.tvPrice = (TextView) convertView
						.findViewById(R.id.tv_shoppingcart_item_price);
				vh.tvCount = (TextView) convertView
						.findViewById(R.id.tv_shoppingcart_item_count);
				vh.btnSub = (Button) convertView
						.findViewById(R.id.btn_shoppingcart_item_sub);
				vh.btnAdd = (Button) convertView
						.findViewById(R.id.btn_shoppingcart_item_add);
				vh.tvX = (TextView) convertView
						.findViewById(R.id.tv_shoppingcart_item_x);

				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			// checkbox设置tag
			vh.cb.setTag(list.get(position).get("catid"));
			// 数量TextView设置tag
			vh.tvCount.setTag(list.get(position).get("catid"));
			// sub按钮设置tag
			vh.btnSub.setTag(list.get(position).get("catid"));
			// add按钮设置tag
			vh.btnAdd.setTag(list.get(position).get("catid"));
			vh.tvX.setTag(list.get(position).get("catid"));

			// 解决焦点冲突并且为按钮添加点击监听事件
			vh.cb.setFocusable(false);
			vh.btnSub.setOnClickListener(this);
			vh.btnSub.setFocusable(false);
			vh.btnAdd.setOnClickListener(this);
			vh.btnAdd.setFocusable(false);
			vh.cb.setOnClickListener(this);

			// json解析数据源
			Goods goods = new Gson().fromJson(list.get(position).get("goods"),
					Goods.class);
			int count = Integer.parseInt(list.get(position).get("count"));

			// UI显示操作
			vh.cb.setChecked(false);
			for (String cb : isChecked) {
				if (cb.equals(vh.cb.getTag())) {
					vh.cb.setChecked(true);
					break;
				}
			}
			new BitmapUtils(context).display(
					vh.iv,
					getResources().getString(R.string.url_pre)
							+ goods.getImgPath());
			vh.tvName.setText(goods.getName());
			if (null == goods.getActivity()) {
				vh.tvPrice.setText("￥"
						+ new BigDecimal(goods.getPrice() * count).setScale(1,
								BigDecimal.ROUND_HALF_UP));
			} else {
				vh.tvPrice.setText("￥"
						+ new BigDecimal(goods.getActPrice() * count).setScale(
								1, BigDecimal.ROUND_HALF_UP));
			}
			vh.tvCount.setText("" + count);

			views.put(list.get(position).get("catid"), convertView);
			positions.put(list.get(position).get("catid"), position);

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
			TextView tvX;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 点击"—"按钮
			case R.id.btn_shoppingcart_item_sub:
				// 获得按钮tag(catid)
				String positionSub = (String) v.getTag();

				// 根据tag获得对应的TextView(数量)并重新设置显示值
				ViewHolder vhSub = (ViewHolder) views.get(positionSub).getTag();
				int countSub = Integer.parseInt(vhSub.tvCount.getText()
						.toString());

				if (countSub > 1) {
					for (Map<String, String> map : list) {
						if (map.get("catid").equals(positionSub)) {
							map.put("count", countSub - 1 + "");
							break;
						}
					}
					vhSub.tvCount.setText(countSub - 1 + "");
					vhSub.tvPrice.setText("￥"
							+ new BigDecimal(
									Integer.parseInt(vhSub.tvCount.getText()
											.toString())
											* (Double.parseDouble(vhSub.tvPrice
													.getText().toString()
													.substring(1)) / countSub))
									.setScale(1, BigDecimal.ROUND_HALF_UP));
				}
				break;
			// 点击"+"按钮
			case R.id.btn_shoppingcart_item_add:
				String positionAdd = (String) v.getTag();

				ViewHolder vhAdd = (ViewHolder) views.get(positionAdd).getTag();
				int countAdd = Integer.parseInt(vhAdd.tvCount.getText()
						.toString());
				for (Map<String, String> map : list) {
					if (map.get("catid").equals(positionAdd)) {
						map.put("count", countAdd + 1 + "");
					}
				}
				vhAdd.tvCount.setText(countAdd + 1 + "");
				vhAdd.tvPrice.setText("￥"
						+ new BigDecimal(Integer.parseInt(vhAdd.tvCount
								.getText().toString())
								* (Double.parseDouble(vhAdd.tvPrice.getText()
										.toString().substring(1)) / countAdd))
								.setScale(1, BigDecimal.ROUND_HALF_UP));
				break;
			case R.id.cb_shoppingcart_item_select:
				String positionCb = (String) v.getTag();
				int length = isChecked.size();
				Log.i("log", "isChecked.size()=" + length);// 0
				for (String cb : isChecked) {
					if (cb.equals(positionCb)) {
						isChecked.remove(cb);

						prices.remove(positionCb);
						break;
					}
				}
				if (isChecked.size() == length) {
					CheckBox cb = ((ViewHolder) views.get(positionCb).getTag()).cb;
					isChecked.add((String) cb.getTag());

					prices.put(
							positionCb,
							Double.parseDouble(((ViewHolder) views.get(
									positionCb).getTag()).tvPrice.getText()
									.toString().substring(1)));
				}

				refreshPrice();
				break;
			}
		}

		// 根据btnEdit状态来改变"+","-"按钮显示与否
		public List<Map<String, String>> change() {
			// TODO Auto-generated method stub
			List<Map<String, String>> texts = new ArrayList<Map<String, String>>();

			Set<Entry<String, View>> sets = views.entrySet();
			for (Entry<String, View> set : sets) {
				ViewHolder vh = (ViewHolder) set.getValue().getTag();
				if (btnEdit.getText().equals("编辑")) {
					vh.btnSub.setVisibility(View.INVISIBLE);
					vh.btnAdd.setVisibility(View.INVISIBLE);
					vh.tvX.setVisibility(View.VISIBLE);

					Map<String, String> map = new HashMap<String, String>();
					map.put("cartid", set.getKey());
					map.put("count",
							list.get(positions.get(set.getKey())).get("count"));
					texts.add(map);
				} else {
					vh.btnSub.setVisibility(View.VISIBLE);
					vh.btnAdd.setVisibility(View.VISIBLE);
					vh.tvX.setVisibility(View.INVISIBLE);
				}

			}

			return texts;
		}
	}
}
