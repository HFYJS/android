package com.sport.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommonAdapter<T> extends BaseAdapter {

	private Context context;
	private int itemLayoutId;
	private List<T> list;

	public CommonAdapter(Context context, int itemLayoutId, List<T> list) {
		this.context = context;
		this.itemLayoutId = itemLayoutId;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = ViewHolder.getViewHolder(context, itemLayoutId,
				convertView, parent);
		setValue(vh, list.get(position));
		return vh.getConvertView();
	}

	protected abstract void setValue(ViewHolder vh, T value);

}
