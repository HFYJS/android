package com.sport.ui.fragment;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.sport.myview.RefreshListView;
import com.sport.myview.RefreshListView.OnRefreshCallBack;
import com.sport.ui.adapter.CommonAdapter;
import com.sport.ui.adapter.ViewHolder;

public class ShopFragment extends BaseFragment implements OnRefreshCallBack {

	RefreshListView lvShop;

	CommonAdapter<Map<String, String>> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_shop_or_goods_shop,
				container, false);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		lvShop = (RefreshListView) getActivity().findViewById(
				R.id.lv_fragment_shop_or_goods_shop);

		lvShop.setOnRefreshCallBack(this);

		ShowShops();
	}

	public void ShowShops() {
		HttpUtils http = new HttpUtils();
		String url = getResources().getString(R.string.url_pre)
				+ "ShowPagedShopsByCatidServlet";

		RequestParams params = new RequestParams();
		params.addQueryStringParameter("catid", "1");
		params.addQueryStringParameter("pageStart", "0");
		params.addQueryStringParameter("pageSize", "4");

		http.send(HttpRequest.HttpMethod.GET, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String results = responseInfo.result;
						Gson gson = new Gson();
						List<Map<String, String>> shops = gson.fromJson(
								results,
								new TypeToken<List<Map<String, String>>>() {
								}.getType());

						adapter = new CommonAdapter<Map<String, String>>(
								getActivity(),
								R.layout.fragment_shop_or_goods_shop_item,
								shops) {

							@Override
							protected void setValue(ViewHolder vh,
									Map<String, String> value) {
								// TODO Auto-generated method stub
								new BitmapUtils(ShopFragment.this.getActivity()).display(
										vh.getView(R.id.iv_shop_or_goods_shopitem),
										getResources().getString(
												R.string.url_pre)
												+ value.get("path"));
								vh.setTextView(
										(R.id.tv_shop_or_goods_shopitem_name),
										value.get("name"));
								vh.setTextView(
										(R.id.tv_shop_or_goods_shopitem_amount),
										"上新：" + value.get("count"));
								vh.setTextView(
										(R.id.tv_shop_or_goods_shopitem_popularity),
										"销量：" + value.get("sales"));
							}
						};

						lvShop.setAdapter(adapter);
					}
				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		if (null != adapter) {
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onPull() {
		// TODO Auto-generated method stub

	}

}
