package com.sport.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sport.R;
import com.sport.entity.Hobby;
import com.sport.entity.User;

public class PersonalInfoActivity extends BaseActivity {

	@ViewInject(R.id.iv_personl_info_background)
	ImageView ivBackground;
	@ViewInject(R.id.iv_personl_info_head)
	ImageView ivHead;
	@ViewInject(R.id.tv_personl_info_name)
	TextView tvName;
	@ViewInject(R.id.tv_personl_info_sex)
	TextView tvSex;
	@ViewInject(R.id.tv_personl_info_age)
	TextView tvAge;
	@ViewInject(R.id.tv_personl_info_tel)
	TextView tvTel;
	@ViewInject(R.id.tv_personl_info_race)
	TextView tvRace;
	@ViewInject(R.id.btn_personl_info_back)
	Button btnBack;
	@ViewInject(R.id.tv_personl_info_native)
	TextView tvNative;
	@ViewInject(R.id.btn_personal_info_edit)
	Button btnEdit;
	@ViewInject(R.id.rl_hobby)
	RelativeLayout rlHobby;

	String userStr;
	List<Button> btnList = new ArrayList<Button>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getInfo();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_personal_info_edit:	
				Intent intent = new Intent(this, PersonalInfoEditActivity.class);
				startActivityForResult(intent, 1);
			break;
		case R.id.btn_personl_info_back:
			finish();
			break;
		}

	}

	public void getInfo() {
		HttpUtils http = new HttpUtils();
		String url = getResources().getString(R.string.url_pre)
				+ "GetUserInfoByUidServlet";
		String userId = "1";
		http.configCurrentHttpCacheExpiry(0); 

		RequestParams paras = new RequestParams();
		paras.addQueryStringParameter("uid", userId);

		http.send(HttpMethod.GET, url, paras, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				Log.i("log", "t");
				userStr = arg0.result;
				List<String> list;

				Gson gson = new Gson();
				if (userStr != null) {
					list = gson.fromJson(userStr,
							new TypeToken<List<String>>() {
							}.getType());
					User user = new Gson().fromJson((String) list.get(0),
							User.class);
					List<Hobby> hobbies = new Gson().fromJson(
							(String) list.get(1), new TypeToken<List<Hobby>>() {
							}.getType());
					String userName = user.getName();
					String userAge = user.getAge() + "";
					String userSex = user.getSex();

					tvName.setText(userName);
					tvSex.setText(userSex);
					tvAge.setText(userAge + "Ëê");
					tvRace.setText(user.getRace());
					tvNative.setText(user.getNati());
					tvTel.setText(user.getTel());

					new BitmapUtils(PersonalInfoActivity.this).display(
							ivBackground,
							getResources().getString(R.string.url_pre)
									+ user.getBackPath());
					new BitmapUtils(PersonalInfoActivity.this).display(
							ivHead,
							getResources().getString(R.string.url_pre)
									+ user.getHeadPath());
					
					if(btnList.size() != 0){
					
						for(Button b : btnList){
							rlHobby.removeView(b);
						}
						btnList.clear();
					}
					for (int i = 0; i < hobbies.size(); i++) {
						Button button = new Button(PersonalInfoActivity.this);
						button.setText(hobbies.get(i).getName());
						button.setTextColor(getResources().getColor(
								R.drawable.white));
						button.setBackgroundColor(getResources().getColor(
								R.drawable.maincolor));
//						button.setPadding(200, 20, 20, 20);
						rlHobby.addView(button);		
						LayoutParams params = (LayoutParams) button.getLayoutParams();
	
						for(int j=0;j<=i;j++){
							if(i<6){	
								params.leftMargin = 150*j;							
							}else{
								params.leftMargin = 150*(j-2);
								params.topMargin = 50*j;
							}
						}				
						btnList.add(button);
					}
					
				}
			}
		});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode==RESULT_OK&&requestCode==1){
			
			Log.i("myHobbies", "ÕýÔÚË¢ÐÂ");
			getInfo();
			
		}
	}

	@Override
	void initView() {
		ViewUtils.inject(this);
	}

	@Override
	void initFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	void setListener() {
		btnEdit.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	}

	@Override
	void setContent() {
		// TODO Auto-generated method stub
		setContentView(R.layout.personal_info);
	}
	
}
