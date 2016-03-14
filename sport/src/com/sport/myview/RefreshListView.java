package com.sport.myview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.sport.R;

public class RefreshListView extends ListView implements OnScrollListener {

	View headView;

	ImageView ivBar;
	int headHeight;

	int firstVisibleItem;
	float startY;
	float moveY;
	OnRefreshCallBack refreshCallBack;

	View footView;
	int footHeight;
	boolean loading = false;

	public int headState;
	public final int INIT = 0;
	public final int PREPAREREFERSH = 1;
	public final int ISREFERING = 2;

	public RefreshListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initHead(context);
		initFoot(context);
		setOnScrollListener(this);
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initHead(context);
		initFoot(context);
		setOnScrollListener(this);
	}

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initHead(context);
		initFoot(context);
		setOnScrollListener(this);
	}

	public void setOnRefreshCallBack(OnRefreshCallBack refreshCallBack) {
		this.refreshCallBack = refreshCallBack;
	}

	public void initHead(Context context) {
		headView = LayoutInflater.from(context).inflate(
				R.layout.refresh_head_view, null);
		addHeaderView(headView);
		headView.measure(0, 0);
		headHeight = headView.getMeasuredHeight();
		headView.setPadding(0, -headHeight, 0, 0);

		ivBar = (ImageView) headView.findViewById(R.id.iv_bar);

	}

	public void initFoot(Context context) {
		footView = LayoutInflater.from(context).inflate(
				R.layout.pull_to_refresh_footer, null);
		addFooterView(footView);
		footView.measure(0, 0);
		footHeight = footView.getMeasuredHeight();

		footView.setPadding(0, -footHeight, 0, 0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (headState == ISREFERING) {
				return false;
			}
			moveY = ev.getY();
			if (firstVisibleItem == 0 && (moveY > startY)) {
				int paddingHeight = (int) (-headHeight + (moveY - startY));
				if (paddingHeight >= 0 && headState == INIT) {
					headState = PREPAREREFERSH;
					changeState();
				} else if (headState == PREPAREREFERSH && paddingHeight < 0) {
					headState = INIT;
					changeState();
				}
				headView.setPadding(0, paddingHeight, 0, 0);
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (headState == INIT) {
				headView.setPadding(0, -headHeight, 0, 0);
			} else if (headState == PREPAREREFERSH) {
				headState = ISREFERING;
				changeState();
				if (refreshCallBack != null) {
					headView.setPadding(0, 0, 0, 0);
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							refreshCallBack.onRefresh();
							completeRefresh();
						}

					}, 2000);
				}
			}
			break;
		default:
			break;
		}

		return super.onTouchEvent(ev);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (!loading && getLastVisiblePosition() == getCount() - 1) {
			if (scrollState == SCROLL_STATE_IDLE
					|| scrollState == SCROLL_STATE_TOUCH_SCROLL) {
				footView.setPadding(0, 0, 0, 0);
				loading = true;
				if (refreshCallBack != null) {
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							refreshCallBack.onPull();
							completePull();
						}

					}, 2000);
				}
			}
		}

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.firstVisibleItem = firstVisibleItem;
	}

	// 状态改变，界面显示内容跟着修改
	public void changeState() {
		switch (headState) {
		case ISREFERING:
			TranslateAnimation translateAnimation = new TranslateAnimation(
					Animation.RELATIVE_TO_PARENT, 0,
					Animation.RELATIVE_TO_PARENT, 0,
					Animation.RELATIVE_TO_PARENT, 0,
					Animation.RELATIVE_TO_PARENT, -0.6f);
			translateAnimation.setDuration(400);
			translateAnimation.setRepeatCount(20);
			translateAnimation.setRepeatMode(Animation.REVERSE);
			ivBar.startAnimation(translateAnimation);
			break;
		}

	}

	public interface OnRefreshCallBack {
		void onRefresh();

		void onPull();
	}

	public void completeRefresh() {
		headView.setPadding(0, -headHeight, 0, 0);
		headState = INIT;
	}

	public void completePull() {
		footView.setPadding(0, -footHeight, 0, 0);
		loading = false;
	}

}
