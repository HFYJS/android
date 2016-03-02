package com.sport.ui.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewHolder {

	private SparseArray<View> views;
	private View convertView;

	private ViewHolder(Context context, int itemLayoutId, View convertView,
			ViewGroup parent) {
		this.views = new SparseArray<View>();
		this.convertView = LayoutInflater.from(context).inflate(itemLayoutId,
				parent, false);
		this.convertView.setTag(this);
	}

	public static ViewHolder getViewHolder(Context context, int itemLayoutId,
			View convertView, ViewGroup parent) {
		if (null == convertView) {
			return new ViewHolder(context, itemLayoutId, convertView, parent);
		} else {
			return (ViewHolder) convertView.getTag();
		}
	}

	public View getView(int id) {
		View view = views.get(id);
		if (null == view) {
			view = convertView.findViewById(id);
		}
		return view;
	}

	public View getConvertView() {
		// TODO Auto-generated method stub
		return this.convertView;
	}

	protected void setTextView(int textViewId, String text) {
		((TextView) getView(textViewId)).setText(text);
	}
}
