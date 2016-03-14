package com.sport.ui.adapter;

import java.util.Map;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class XViewHolder {

	private SparseArray<View> views;
	private View convertView;
	private Map<String,View> types;

	private XViewHolder(Context context, int itemLayoutId, View convertView,
			ViewGroup parent) {
		this.views = new SparseArray<View>();
		this.convertView = LayoutInflater.from(context).inflate(itemLayoutId,
				parent, false);
		this.convertView.setTag(this);
	}

	public static XViewHolder getXViewHolder(Context context, int itemLayoutId,
			View convertView, ViewGroup parent) {
		if (null == convertView) {
			
			return new XViewHolder(context, itemLayoutId, convertView, parent);
		} else {
			return (XViewHolder) convertView.getTag();
		}
	}

	public View getView(int id) {
		View view = views.get(id);
		if (null == view) {
			view = convertView.findViewById(id);
			views.put(id, view);
		}
		return view;
	}

	public View getConvertView() {
		// TODO Auto-generated method stub
		return this.convertView;
	}

	public void setTextView(int textViewId, String text) {
		((TextView) getView(textViewId)).setText(text);
	}
}