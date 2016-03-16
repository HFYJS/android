package com.sport.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

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

public class PersonalInfoEditActivity extends BaseActivity {
	
	@ViewInject(R.id.btn_personal_info_edit_back)
	Button btnBack;
	@ViewInject(R.id.btn_personal_info_edit_background)
	Button btnBackground;
	@ViewInject(R.id.btn_personal_info_edit_head)
	Button btnHead;
	@ViewInject(R.id.btn_personal_info_edit_save)
	Button btnSave;
	@ViewInject(R.id.et_personal_info_edit_name)
	EditText etName;
	@ViewInject(R.id.et_personal_info_edit_sex)
	EditText etSex;
	@ViewInject(R.id.et_personal_info_edit_age)
	EditText etAge;
	@ViewInject(R.id.et_personal_info_edit_race)
	EditText etRace;
	@ViewInject(R.id.et_personal_info_edit_native)
	EditText etNative;
	@ViewInject(R.id.et_personal_info_edit_tel)
	EditText etTel;
	@ViewInject(R.id.rl_personal_info_edit_hobby)
	RelativeLayout rlHobby;

	String userStr;
	List<Hobby> oldhobbies;
	List<Integer> myHobbyBtns = new ArrayList<Integer>();//被选中的button
	List<String> myHobbies = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_personal_info_edit_save:
			saveInfo();
			break;
		case R.id.btn_personal_info_edit_background:
			break;
		case R.id.btn_personal_info_edit_head:
			break;
		case R.id.btn_personal_info_edit_back:
			Intent intent = new Intent();
			setResult(RESULT_OK, intent);
			finish();
			break;
		}
		
	}

	public void saveInfo() {
		String name = etName.getText().toString();
		int age = Integer.parseInt(etAge.getText().toString());
		String sex = etSex.getText().toString();
		String race = etRace.getText().toString();
		String place = etNative.getText().toString();
		String tel = etTel.getText().toString();

		User user = new User();
		user.setUid(1);
		user.setName(name);
		user.setAge(age);
		user.setSex(sex);
		user.setRace(race);
		user.setNati(place);
		user.setTel(tel);

		HttpUtils http = new HttpUtils();
		String url = getResources().getString(R.string.url_pre)
				+ "UpdateUserInfoServlet";

		RequestParams params = new RequestParams();
		params.addBodyParameter("user", new Gson().toJson(user));
		params.addBodyParameter("hobbies", new Gson().toJson(myHobbies));

		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> result) {
				Toast.makeText(PersonalInfoEditActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	void initView() {
		ViewUtils.inject(this);

		initData();
	}

	@Override
	void initFragment() {

	}

	@Override
	void setListener() {
		btnBackground.setOnClickListener(this);
		btnHead.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	}

	private void initData() {
		getInfo();
	}

	@Override
	void setContent() {
		setContentView(R.layout.activity_edit_personinfo);
	}
	
	public void getInfo(){
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
				Toast.makeText(PersonalInfoEditActivity.this, "服务器访问失败", Toast.LENGTH_SHORT).show();	
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				if(result != null){
					Gson gson = new Gson();
					List<String> list = gson.fromJson(result,
							new TypeToken<List<String>>() {
							}.getType());
					User user = new Gson().fromJson((String) list.get(0), User.class);
					oldhobbies = new Gson().fromJson(
							(String) list.get(1), new TypeToken<List<Hobby>>() {
							}.getType());		
					etName.setText(user.getName());
					etSex.setText(user.getSex());
					etAge.setText(user.getAge() + "");
					etRace.setText(user.getRace());
					etNative.setText(user.getNati());
					etTel.setText(user.getTel());

					new BitmapUtils(PersonalInfoEditActivity.this)
							.display(
									btnBackground,
									getResources().getString(R.string.url_pre)
											+ user.getBackPath());
					new BitmapUtils(PersonalInfoEditActivity.this)
							.display(btnHead, getResources().getString(R.string.url_pre)
									+ user.getHeadPath());
				}		
				
				getAllHobbies();
			}
		});
	}
	public void getAllHobbies() {
		HttpUtils http = new HttpUtils();
		String url = getResources().getString(R.string.url_pre)
				+ "GetAllHobbiesServlet";

		http.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
			}
		
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				Log.i("log", "t");
				String hobbyStr = responseInfo.result;
				if (hobbyStr != null) {
					List<Hobby> hobbies = new Gson().fromJson(
							hobbyStr, new TypeToken<List<Hobby>>() {
							}.getType());
					List<Integer> hobbyId  = new ArrayList<Integer>();
					for(Hobby hobby : oldhobbies){
						hobbyId.add(hobby.getHobid());
					}
					for (int i = 0; i < hobbies.size(); i++) {			
						Button button = new Button(PersonalInfoEditActivity.this);
						button.setTag(i);
						button.setText(hobbies.get(i).getName());
						if(hobbyId.contains(hobbies.get(i).getHobid())){
							button.setTextColor(getResources().getColor(
									R.drawable.sysbackground));
							button.setBackgroundColor(getResources().getColor(
									R.drawable.maincolor));
							myHobbyBtns.add((Integer) button.getTag());
							myHobbies.add(button.getText().toString());
						}else{
							button.setTextColor(getResources().getColor(
									R.drawable.maincolor));
							button.setBackgroundColor(getResources().getColor(
									R.drawable.sysbackground));
						}	
						
						button.setOnClickListener(new OnClickListener() {
						
							@Override
							public void onClick(View v) {
								Button btn = (Button) v;
								if(myHobbyBtns.contains((Integer) btn.getTag())){
									btn.setTextColor(getResources().getColor(
											R.drawable.maincolor));
									btn.setBackgroundColor(getResources().getColor(
											R.drawable.sysbackground));		
									myHobbyBtns.remove((Integer) btn.getTag());			
									myHobbies.remove(btn.getText().toString());
								}else{
									btn.setTextColor(getResources().getColor(
											R.drawable.sysbackground));
									btn.setBackgroundColor(getResources().getColor(
											R.drawable.maincolor));
									myHobbyBtns.add((Integer) btn.getTag());
									myHobbies.add(btn.getText().toString());
								}
								
							}
						});
//						button.setPadding(200, 20, 20, 20);
						rlHobby.addView(button);		
						LayoutParams params = (LayoutParams) button.getLayoutParams();
						//兴趣的位置
						for(int j=0;j<=i;j++){
							if(i<6){	
								params.leftMargin = 150*j;							
							}else{
								params.leftMargin = 150*(j-2);
								params.topMargin = 50*j;
							}
						}				
					}
				}
			}
		});

	}
}
