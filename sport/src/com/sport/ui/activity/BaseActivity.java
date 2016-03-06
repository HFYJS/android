package com.sport.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.Window;

public abstract class BaseActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContent();

		initView();
		initFragment();
		setListener();
	}

	abstract void setContent();

	abstract void initView();

	abstract void initFragment();

	abstract void setListener();
}
