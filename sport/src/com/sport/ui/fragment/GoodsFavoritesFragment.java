package com.sport.ui.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sport.entity.Goods;
import com.sport.ui.adapter.CommonAdapter;
import com.sport.ui.adapter.ViewHolder;

public class GoodsFavoritesFragment extends BaseFragment {

	ListView lvGoods;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_favorites_goods, container,
				false);
	}

	private void getGoodses() {
		// TODO Auto-generated method stub
		String url = getResources().getString(R.string.url_pre)
				+ "GetAllFavoritesGoodsesByUidServlet";
		HttpUtils http = new HttpUtils();

		RequestParams requestParams = new RequestParams();
		requestParams.addQueryStringParameter("uid", "1");

		http.send(HttpRequest.HttpMethod.GET, url, requestParams,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String result = arg0.result;
						List<Goods> goodses = new Gson().fromJson(result,
								new TypeToken<List<Goods>>() {
								}.getType());
						lvGoods.setAdapter(new CommonAdapter<Goods>(
								GoodsFavoritesFragment.this.getActivity(),
								R.layout.fragment_favorites_goods_item, goodses) {

							@Override
							protected void setValue(ViewHolder vh, Goods value) {
								// TODO Auto-generated method stub
								new BitmapUtils(GoodsFavoritesFragment.this
										.getActivity()).display(
										vh.getView(R.id.iv_favorites_goods_item_image),
										getResources().getString(
												R.string.url_pre)
												+ value.getImgPath());
								vh.setTextView(
										R.id.tv_favorites_goods_item_name,
										value.getName());
								vh.setTextView(
										R.id.tv_favorites_goods_item_price,
										value.getPrice() + "");
								vh.setTextView(
										R.id.tv_favorites_goods_item_sales,
										value.getSales() + "");
							}

						});
					}
				});

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		lvGoods = (ListView) getActivity()
				.findViewById(R.id.lv_favorites_goods);

		getGoodses();
	}

}
