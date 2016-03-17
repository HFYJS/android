package com.sport.ui.activity;

import java.lang.reflect.Type;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.sport.R;
import com.sport.entity.User;

public class LoginActivity extends BaseActivity {
	EditText etName;
	EditText etPwd;
	Button btLogin;
	Button btRegister;
	Button btFound;

	String url;
	String name;
	String pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.bt_login_click:
			login();
			break;
		case R.id.bt_login_register:
			Log.i("Log", "register");
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.bt_login_found:
			break;
		}
	}

	@Override
	void setContent() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_login);

	}

	@Override
	void initView() {
		etName = (EditText) findViewById(R.id.et_login_name);
		etPwd = (EditText) findViewById(R.id.et_login_pwd);
		btLogin = (Button) findViewById(R.id.bt_login_click);
		btRegister = (Button) findViewById(R.id.bt_login_register);

	}

	@Override
	void initFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	void setListener() {
		// TODO Auto-generated method stub
		btLogin.setOnClickListener(this);
		btRegister.setOnClickListener(this);

	}

	private void login() {
		// TODO Auto-generated method stub
		HttpUtils http = new HttpUtils();

		url = getResources().getString(R.string.url_pre) + "UserLoginServlet";
		System.out.println(url);

		name = etName.getText().toString();
		pwd = etPwd.getText().toString();

		RequestParams params = new RequestParams();
		params.addBodyParameter("name", name);
		params.addBodyParameter("pwd", pwd);

		if (name != null & pwd != null) {

			http.send(HttpMethod.POST, url, params,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// TODO Auto-generated method stub
							Log.i("Log", "Failure");

						}

						@Override
						public void onSuccess(ResponseInfo<String> response) {
							// TODO Auto-generated method stub
							Log.i("Log", "Success");
							String result = response.result;
							Gson gson = new Gson();
							Type type = new TypeToken<User>() {
							}.getType();
							User user = gson.fromJson(result, type);
							if (user != null) {
								Toast.makeText(LoginActivity.this,
										user.getName(), Toast.LENGTH_SHORT)
										.show();
							} else {
								Toast.makeText(LoginActivity.this, "用户名密码有误！",
										Toast.LENGTH_SHORT).show();
							}
						}

					});
		} else {
			Toast.makeText(LoginActivity.this, "账号密码不可为空！", Toast.LENGTH_SHORT)
					.show();
		}
	}

}
