package com.sport.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sport.R;
import com.sport.ui.fragment.GoodsFragment;
import com.sport.ui.fragment.ShopFragment;

public class ShopOrGoodsActivity extends BaseActivity {

	Button btnSelectShop;
	Button btnSelectGoods;

	ShopFragment shopFragment;
	GoodsFragment goodsFragment;
	Fragment currentFragment;
	Fragment nextFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.shop_or_goods);
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_select_shop:
			nextFragment = shopFragment;
			break;
		case R.id.btn_select_goods:
			nextFragment = goodsFragment;
			break;
		}
		replaceFragment();
	}

	private void replaceFragment() {
		// TODO Auto-generated method stub
		FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
		if (currentFragment != nextFragment) {
			fragmentTransaction.hide(currentFragment).show(nextFragment)
					.commit();
			currentFragment = nextFragment;
		}
	}

	@Override
	void initView() {
		// TODO Auto-generated method stub
		btnSelectShop = (Button) findViewById(R.id.btn_select_shop);
		btnSelectGoods = (Button) findViewById(R.id.btn_select_goods);
	}

	@Override
	void initFragment() {
		// TODO Auto-generated method stub
		shopFragment = new ShopFragment();
		goodsFragment = new GoodsFragment();

		getFragmentManager().beginTransaction()
				.add(R.id.fl_shop_or_goods, shopFragment)
				.add(R.id.fl_shop_or_goods, goodsFragment).hide(shopFragment)
				.show(goodsFragment).commit();

		currentFragment = goodsFragment;
	}

	@Override
	void setListener() {
		// TODO Auto-generated method stub
		btnSelectShop.setOnClickListener(this);
		btnSelectGoods.setOnClickListener(this);
	}

}
