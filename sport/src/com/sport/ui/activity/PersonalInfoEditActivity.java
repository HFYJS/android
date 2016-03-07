package com.sport.ui.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

	String userStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_personal_info_edit_save:
			break;
		case R.id.btn_personal_info_edit_background:
			break;
		case R.id.btn_personal_info_edit_head:
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
				+ "UpdateUserInfoByUidServlet";

		RequestParams params = new RequestParams();
		params.addBodyParameter("user", new Gson().toJson(user));
		// paras.addBodyParameter("hobbies", userStr);

		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> result) {

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
	}

	private void initData() {
		Intent intent = getIntent();
		String userInfo = intent.getStringExtra("userInfo");

		Gson gson = new Gson();
		List<String> list = gson.fromJson(userInfo,
				new TypeToken<List<String>>() {
				}.getType());
		User user = new Gson().fromJson((String) list.get(0), User.class);
		List<Hobby> hobbies = new Gson().fromJson((String) list.get(1),
				new TypeToken<List<Hobby>>() {
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

	@Override
	void setContent() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_edit_personinfo);
	}
}
