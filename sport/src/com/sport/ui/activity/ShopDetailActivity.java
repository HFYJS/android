package com.sport.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources.NotFoundException;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sport.R;
import com.sport.entity.Goods;
import com.sport.myview.HeaderListView;
import com.sport.ui.adapter.CommonAdapter;
import com.sport.ui.adapter.ViewHolder;
import com.sport.util.NetUtil;

public class ShopDetailActivity extends BaseActivity {

	List<Goods> goodses = new ArrayList<Goods>();

	HeaderListView lv;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			
		}

	};

	Handler handler2 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			if (msg.what == 2) {
				goodses = new Gson().fromJson(msg.obj.toString(),
						new TypeToken<List<Goods>>() {
						}.getType());

				lv.setAdapter(new CommonAdapter<Goods>(ShopDetailActivity.this,
						R.layout.activity_shop_detail_listview_item, goodses) {

					@Override
					protected void setValue(ViewHolder vh, Goods value) {
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
					}

				});
			}
		}

	};

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

		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		try {
			NetUtil.getConnection(NetUtil.GET,
					getResources().getString(R.string.url_pre)
							+ "GetShopInfoBySidServlet?sid=1", handler, 1);
			NetUtil.getConnection(NetUtil.GET,
					getResources().getString(R.string.url_pre)
							+ "GetAllGoodsesBySidServlet?sid=1", handler2, 2);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
