package com.sport.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

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

		List<Goods> goodses = new ArrayList<Goods>();
		goodses.add(new Goods());
		goodses.add(new Goods());
		goodses.add(new Goods());
		goodses.add(new Goods());
		goodses.add(new Goods());
		goodses.add(new Goods());

		gvGoods.setAdapter(new CommonAdapter<Goods>(getActivity(),
				R.layout.fragment_shop_or_goods_goods_item, goodses) {

			@Override
			protected void setValue(ViewHolder vh, Goods value) {
				// TODO Auto-generated method stub

			}

		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
