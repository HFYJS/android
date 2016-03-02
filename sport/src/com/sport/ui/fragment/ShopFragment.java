package com.sport.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sport.R;
import com.sport.entity.Shop;
import com.sport.ui.adapter.CommonAdapter;
import com.sport.ui.adapter.ViewHolder;

public class ShopFragment extends BaseFragment {

	ListView lvShop;

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
		lvShop = (ListView) getActivity().findViewById(
				R.id.lv_fragment_shop_or_goods_shop);

		List<Shop> shops = new ArrayList<Shop>();
		shops.add(new Shop());
		shops.add(new Shop());
		shops.add(new Shop());
		shops.add(new Shop());
		shops.add(new Shop());

		lvShop.setAdapter(new CommonAdapter<Shop>(getActivity(),
				R.layout.fragment_shop_or_goods_shop_item, shops) {

			@Override
			protected void setValue(ViewHolder vh, Shop value) {
				// TODO Auto-generated method stub
				
			}

		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
