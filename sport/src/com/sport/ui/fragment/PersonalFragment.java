package com.sport.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.sport.R;
import com.sport.ui.activity.PersonalInfoActivity;
import com.sport.ui.activity.PersonalInfoEditActivity;

public class PersonalFragment extends BaseFragment implements OnClickListener{


	Button btnshowInfo;
	Button btnFoucs;
	Button btnFans;
	Button btnLogOff;
	Button btnPost;
	Button btnPay;
	Button btnTackGoods;
	Button btnComment;
	RelativeLayout rlMyfavor;
	RelativeLayout rlEditInfo;
	RelativeLayout rlLogistics;
	RelativeLayout rlNear;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.fragment_personal, container, false);	
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
	}
	@Override
	public void initView() {
//		ViewUtils.inject(getActivity());
		btnshowInfo = (Button) getActivity().findViewById(R.id.btn_fragment_personal_showInfo);
		btnFoucs = (Button) getActivity().findViewById(R.id.btn_fragment_personal_foucs);
		btnFans = (Button) getActivity().findViewById(R.id.btn_fragment_personal_fans);
		btnLogOff = (Button) getActivity().findViewById(R.id.btn_fragment_personal_logoff);
		btnPost = (Button) getActivity().findViewById(R.id.btn_fragment_personal_post);
		btnPay = (Button) getActivity().findViewById(R.id.btn_fragment_personal_pay);
		btnTackGoods = (Button) getActivity().findViewById(R.id.btn_fragment_personal_takegoods);
		btnComment = (Button) getActivity().findViewById(R.id.btn_fragment_personal_comment);
		rlMyfavor = (RelativeLayout) getActivity().findViewById(R.id.rl_fragment_personal_myfavorites);
		rlEditInfo = (RelativeLayout) getActivity().findViewById(R.id.rl_fragment_personal_editInfo);
		rlLogistics = (RelativeLayout) getActivity().findViewById(R.id.rl_fragment_personal_logistics);
		rlNear = (RelativeLayout) getActivity().findViewById(R.id.rl_fragment_personal_nearby);
		setOnclick();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_fragment_personal_showInfo:
			Log.i("log", "OK");
			Intent showInfoInt = new Intent(getActivity(),PersonalInfoActivity.class);
			startActivity(showInfoInt);
			break;
		case R.id.btn_fragment_personal_logoff:
			
			break;
		case R.id.btn_fragment_personal_foucs:
			
			break;
		case R.id.btn_fragment_personal_fans:
			
			break;
		case R.id.btn_fragment_personal_post:
			
			break;
		case R.id.btn_fragment_personal_pay:
			
			break;
		case R.id.btn_fragment_personal_takegoods:
			
			break;
		case R.id.btn_fragment_personal_comment:
			
			break;
		case R.id.rl_fragment_personal_myfavorites:
			
			break;
		case R.id.rl_fragment_personal_editInfo:	
			Intent editInfoInt = new Intent(getActivity(),PersonalInfoEditActivity.class);
			startActivity(editInfoInt);
			break;
		case R.id.rl_fragment_personal_logistics:
			
			break;
		case R.id.rl_fragment_personal_nearby:
			
			break;
		default:
			break;
		}
	}


	public void setOnclick() {
		btnshowInfo.setOnClickListener(this);
		btnComment.setOnClickListener(this);
		btnFans.setOnClickListener(this);
		btnFoucs.setOnClickListener(this);
		btnLogOff.setOnClickListener(this);
		btnPay.setOnClickListener(this);
		btnPost.setOnClickListener(this);
		btnTackGoods.setOnClickListener(this);
		rlEditInfo.setOnClickListener(this);
		rlLogistics.setOnClickListener(this);
		rlMyfavor.setOnClickListener(this);
		rlNear.setOnClickListener(this);
	}

}
