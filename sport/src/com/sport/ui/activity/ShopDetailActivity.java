package com.sport.ui.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.sport.R;
import com.sport.entity.Goods;
import com.sport.entity.Shop;
import com.sport.myview.HeaderListView;
import com.sport.ui.adapter.CommonAdapter;
import com.sport.ui.adapter.ViewHolder;
import com.sport.util.NetUtil;

public class ShopDetailActivity extends BaseActivity {

	Shop shop;
	List<Goods> goodses = new ArrayList<Goods>();

	HeaderListView lv;
	TextView tvName;
	TextView tvSales;
	ImageView ivBack;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	void setContent() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_shop_detail);
	}

	@Override
	void initView() {
		// TODO Auto-generated method stub
		lv = (HeaderListView) findViewById(R.id.lv_activity_shop_detail);
		tvName = (TextView) lv
				.findViewById(R.id.tv_activity_shop_detail_listview_header_name);
		tvSales = (TextView) lv
				.findViewById(R.id.tv_activity_shop_detail_listview_header_sales);
		ivBack = (ImageView) lv
				.findViewById(R.id.iv_activity_shop_detail_listview_header_background);

		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		new NetUtil() {

			@Override
			public void dealResult(Result result) {
				// TODO Auto-generated method stub
				Map<String, String> map = new Gson().fromJson(result.result,
						new TypeToken<Map<String, String>>() {
						}.getType());

				shop = new Gson().fromJson(map.get("shop"), Shop.class);
				goodses = new Gson().fromJson(map.get("goodses"),
						new TypeToken<List<Goods>>() {
						}.getType());

				if (null != shop && !shop.equals("null")) {
					tvName.setText(shop.getName());

					int sum = 0;
					for (Goods goods : goodses) {
						sum += goods.getSales();
					}

					tvSales.setText("总销量：" + sum);
				}
				new NetUtil() {

					@Override
					public void dealResult(Result result) {
						// TODO Auto-generated method stub
						ivBack.setImageBitmap(result.image);
					}

				}.begin(NetUtil.BITMAP,
						NetUtil.GET,
						getResources().getString(R.string.url_pre)
								+ shop.getImgPath());

				lv.setAdapter(new CommonAdapter<Goods>(ShopDetailActivity.this,
						R.layout.activity_shop_detail_listview_item, goodses) {

					@Override
					protected void setValue(final ViewHolder vh, Goods value) {
						// TODO Auto-generated method stub
						vh.setTextView(
								R.id.tv_activity_shop_detail_listview_item_name,
								value.getName());
						vh.setTextView(
								R.id.tv_activity_shop_detail_listview_item_price,
								"￥" + value.getPrice());
						if (null == value.getActivity()) {
							vh.setTextView(
									R.id.tv_activity_shop_detail_listview_item_sales,
									"销量：" + value.getPrice());
						} else {
							vh.setTextView(
									R.id.tv_activity_shop_detail_listview_item_sales,
									"活动：" + value.getActPrice());
						}
						vh.setTextView(
								R.id.tv_activity_shop_detail_listview_item_popularity,
								"人气：" + value.getPopularity());

						String imgUrl = value.getImgPath();
						new BitmapUtils(ShopDetailActivity.this).display(
								vh.getView(R.id.iv_activity_shop_detail_listview_item_img),
								getResources().getString(R.string.url_pre)
										+ imgUrl);
					}

				});
			}

		}.begin(NetUtil.STRING, NetUtil.GET,
				getResources().getString(R.string.url_pre)
						+ "GetShopDetailBySidServlet?sid=1");
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
