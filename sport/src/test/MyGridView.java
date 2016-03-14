package test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.sport.R;

public class MyGridView extends ListView {

	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initHeadView(context);
	}

	public MyGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void initHeadView(Context context) {
		View headView = LayoutInflater.from(context).inflate(
				R.layout.test_headview, null);
		addHeaderView(headView);
	}
}
