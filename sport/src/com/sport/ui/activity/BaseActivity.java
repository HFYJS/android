package com.sport.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends Activity implements OnClickListener {

	// ����ע�⣺�̳д�BaseActivity���Activity�е�onCreate()�����У��������ڵ��ø��෽��֮ǰ���ò����ļ�������ʵ�����initView()�����л���ֿ�ָ���쳣
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
