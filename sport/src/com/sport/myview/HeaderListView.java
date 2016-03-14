package com.sport.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.sport.R;

public class HeaderListView extends ListView {

	public HeaderListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public HeaderListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initHeaderView(context);
	}

	private void initHeaderView(Context context) {
		// TODO Auto-generated method stub
		View headerView = LayoutInflater.from(context).inflate(
				R.layout.activity_shop_detail_listview_header, null);

		headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				650));
		addHeaderView(headerView);
	}

	public HeaderListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

}
