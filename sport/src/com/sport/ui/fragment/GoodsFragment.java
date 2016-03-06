package com.sport.ui.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sport.R;
import com.sport.entity.Goods;
import com.sport.ui.adapter.CommonAdapter;
import com.sport.ui.adapter.ViewHolder;

public class GoodsFragment extends BaseFragment {

	GridView gvGoods;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_shop_or_goods_goods,
				container, false);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		gvGoods = (GridView) getActivity().findViewById(
				R.id.gv_fragment_shop_or_goods_goods);

		showGoodses();

	}

	private void showGoodses() {
		// TODO Auto-generated method stub
		HttpUtils httpUtils = new HttpUtils();
		String url = getActivity().getResources().getString(R.string.url_pre)
				+ "GetAllGoodsesServlet";
		httpUtils.send(HttpRequest.HttpMethod.POST, url,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						Gson gson = new Gson();
						List<Goods> goodses = gson.fromJson(arg0.result,
								new TypeToken<List<Goods>>() {
								}.getType());

						gvGoods.setAdapter(new CommonAdapter<Goods>(
								getActivity(),
								R.layout.fragment_shop_or_goods_goods_item,
								goodses) {

							@Override
							protected void setValue(ViewHolder vh, Goods value) {
								// TODO Auto-generated method stub
								new BitmapUtils(GoodsFragment.this
										.getActivity()).display(
										vh.getView(R.id.iv_shop_or_goods_goodsitem),
										getActivity().getResources().getString(
												R.string.url_pre)
												+ value.getImgPath());
								vh.setTextView(
										R.id.tv_shop_or_goods_goodsitem_name,
										value.getName());
								vh.setTextView(
										R.id.tv_shop_or_goods_goodsitem_price,
										value.getPrice() + "");
								vh.setTextView(
										R.id.tv_shop_or_goods_goodsitem_sales,
										value.getSales() + "");
							}

						});
					}
				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
