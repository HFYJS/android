package com.sport.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends Activity implements OnClickListener {

	// 必须注意：继承此BaseActivity类的Activity中的onCreate()方法中，必须在在调用父类方法之前设置布局文件，否则实现类的initView()方法中会出现空指针异常
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();
		initFragment();
		setListener();
	}

	abstract void initView();

	abstract void initFragment();

	abstract void setListener();
}
