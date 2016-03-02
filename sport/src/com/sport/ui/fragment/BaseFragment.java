package com.sport.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View.OnClickListener;

public abstract class BaseFragment extends Fragment implements OnClickListener{

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initView();
	}

	// findViewById
	public abstract void initView();
}
