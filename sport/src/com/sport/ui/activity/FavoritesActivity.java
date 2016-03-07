package com.sport.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sport.R;
import com.sport.ui.fragment.GoodsFavoritesFragment;
import com.sport.ui.fragment.PostFavoritesFragment;

public class FavoritesActivity extends BaseActivity {

	Button btnGoods;
	Button btnPost;

	Fragment goodsFavoritesFragment;
	Fragment postFavoritesFragment;
	Fragment currentFragment;
	Fragment nextFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_favorites_goods:
			nextFragment = goodsFavoritesFragment;
			break;
		case R.id.btn_favorites_post:
			nextFragment = postFavoritesFragment;
			break;
		}
		replaceFragment();
	}

	private void replaceFragment() {
		// TODO Auto-generated method stub
		FragmentTransaction fragmentManager = getFragmentManager()
				.beginTransaction();
		if (currentFragment != nextFragment) {
			fragmentManager.hide(currentFragment).show(nextFragment).commit();
		}
		currentFragment = nextFragment;
	}

	@Override
	void initView() {
		// TODO Auto-generated method stub
		btnGoods = (Button) findViewById(R.id.btn_favorites_goods);
		btnPost = (Button) findViewById(R.id.btn_favorites_post);
	}

	@Override
	void initFragment() {
		// TODO Auto-generated method stub
		goodsFavoritesFragment = new GoodsFavoritesFragment();
		postFavoritesFragment = new PostFavoritesFragment();

		getFragmentManager().beginTransaction()
				.add(R.id.fl_favorites, goodsFavoritesFragment)
				.add(R.id.fl_favorites, postFavoritesFragment)
				.hide(postFavoritesFragment).show(goodsFavoritesFragment)
				.commit();

		currentFragment = goodsFavoritesFragment;
	}

	@Override
	void setListener() {
		// TODO Auto-generated method stub
		btnGoods.setOnClickListener(this);
		btnPost.setOnClickListener(this);
	}

	@Override
	void setContent() {
		// TODO Auto-generated method stub
		setContentView(R.layout.favorites);
	}

}
