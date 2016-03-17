package com.sport.ui.adapter;

import java.util.List;

import com.lidroid.xutils.BitmapUtils;
import com.sport.R;
import com.sport.entity.temp.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentGoodsChildAdapter extends BaseAdapter {
	private List<Order> list;
	private Context context;
	private LayoutInflater inflater;

	//构造方法：context,List
	
	public CommentGoodsChildAdapter(Context context, List<Order> list) {
		super();
		this.context = context;
		this.list = list;
		this.inflater = LayoutInflater.from(context);
	}

//	public void addAll(List<Order> list) {
//		this.list = list;
//		notifyDataSetChanged();
//	}
//
//	public void clearAll() {
//		this.list.clear();
//		notifyDataSetChanged();
//	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ParentListItem listItem = null;
		if (convertView == null) {
			listItem = new ParentListItem();
			convertView = inflater.inflate(R.layout.comment_goods_details,
					parent, false);
			listItem.ivGoods = (ImageView) convertView
					.findViewById(R.id.iv_evaluate_goods_count_image);
			listItem.tvGoodsCount = (TextView) convertView
					.findViewById(R.id.tv_evaluate_goods_count_count);
			listItem.tvGoodsName = (TextView) convertView
					.findViewById(R.id.tv_evaluate_goods_count_name);
			listItem.tvGoodsPrice = (TextView) convertView
					.findViewById(R.id.tv_evaluate_goods_count_price);
			convertView.setTag(listItem);
		} else {
			listItem = (ParentListItem) convertView.getTag();
		}

		BitmapUtils bitmapUtils = new BitmapUtils(context);
		bitmapUtils.display(
				listItem.ivGoods,
				context.getResources().getString(R.string.url_pre)
						+ list.get(position).getImgPath());
		listItem.tvGoodsCount
				.setText("* " + list.get(position).getCount() + "");
		listItem.tvGoodsName.setText(list.get(position).getGoodsName());
		listItem.tvGoodsPrice.setText(list.get(position).getPrice() + "");
		return convertView;
	}

	public class ParentListItem {
		TextView tvGoodsName;
		TextView tvGoodsPrice;
		TextView tvGoodsCount;
		ImageView ivGoods;
	}
}
