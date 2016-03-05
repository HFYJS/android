package com.sport.ui.adapter;

import java.util.List;

import android.content.Context;

public abstract class MultiLayoutAdapter<T> extends CommonAdapter<T> {

	private int layoutCount;

	public MultiLayoutAdapter(Context context, int itemLayoutId, List<T> list,
			int layoutCount) {
		super(context, itemLayoutId, list);
		this.layoutCount = layoutCount;
	}

	@Override
	protected void setValue(ViewHolder vh, T value) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return super.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return this.layoutCount;
	}

}
