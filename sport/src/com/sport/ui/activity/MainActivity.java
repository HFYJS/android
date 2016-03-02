package com.sport.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sport.R;
import com.sport.ui.fragment.ForumFragment;
import com.sport.ui.fragment.HomeFragment;
import com.sport.ui.fragment.MallFragment;
import com.sport.ui.fragment.PersonalFragment;

public class MainActivity extends BaseActivity {

	@ViewInject(R.id.rb_home)
	RadioButton rbHome;
	@ViewInject(R.id.rb_mall)
	RadioButton rbMall;
	@ViewInject(R.id.rb_forum)
	RadioButton rbForum;
	@ViewInject(R.id.rb_personal)
	RadioButton rbPersonal;

	HomeFragment homeFragment;
	MallFragment mallFragment;
	ForumFragment forumFragment;
	PersonalFragment personalFragment;
	Fragment currentFragment;
	Fragment nextFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main);
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rb_home:
			nextFragment = homeFragment;
			break;
		case R.id.rb_mall:
			nextFragment = mallFragment;
			break;
		case R.id.rb_forum:
			nextFragment = forumFragment;
			break;
		case R.id.rb_personal:
			nextFragment = personalFragment;
			break;
		}
		replaceFragment();
	}

	public void replaceFragment() {
		FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
		if (currentFragment != nextFragment) {
			fragmentTransaction.hide(currentFragment).show(nextFragment)
					.commit();
			currentFragment = nextFragment;
		}
	}

	@Override
	public void initView() {
		ViewUtils.inject(this);

		rbHome.setChecked(true);
	}

	@Override
	public void initFragment() {

		homeFragment = new HomeFragment();
		mallFragment = new MallFragment();
		forumFragment = new ForumFragment();
		personalFragment = new PersonalFragment();

		getFragmentManager().beginTransaction().add(R.id.fl_page, homeFragment)
				.add(R.id.fl_page, mallFragment)
				.add(R.id.fl_page, forumFragment)
				.add(R.id.fl_page, personalFragment).hide(mallFragment)
				.hide(forumFragment).hide(personalFragment).commit();

		currentFragment = homeFragment;
	}

	@Override
	public void setListener() {
		rbHome.setOnClickListener(this);
		rbMall.setOnClickListener(this);
		rbForum.setOnClickListener(this);
		rbPersonal.setOnClickListener(this);
	}

}
