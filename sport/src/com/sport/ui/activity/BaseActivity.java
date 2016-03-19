package com.sport.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseActivity extends Activity implements OnClickListener {

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContent();
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		initView();
		initFragment();
		setListener();
	}

	abstract void setContent();

	abstract void initView();

	abstract void initFragment();

	abstract void setListener();
}
