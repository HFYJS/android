package com.sport.ui.activity;

import java.lang.reflect.Type;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class RegisterActivity extends BaseActivity {
	EditText etName;
	EditText etPwd;
	EditText etPwd1;
	Button btRegister;
	Button btAgr;
	CheckBox cbAgr;

	String url;
	String name;
	String pwd;
	String pwd1;
	// 设置标示，是否确认协议
	Boolean flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.cb_register_agreement:
			if (cbAgr.isChecked()) {
				flag = true;
			} else {
				flag = false;
			}
			break;
		case R.id.bt_register_agreement:
			Toast.makeText(RegisterActivity.this,
					"服务协议服务协议服务协议服务协议服务协议服务协议服务协议服务协议服务协议服务协议服务协议服务协议",
					Toast.LENGTH_LONG).show();
			break;
		case R.id.bt_register_click:
			Log.i("Tag", "点击注册按钮");
			register();
			break;
		default:
			break;
		}

	}

	@Override
	void setContent() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_register);
	}

	@Override
	void initView() {
		// TODO Auto-generated method stub
		etName = (EditText) findViewById(R.id.et_register_name);
		etPwd = (EditText) findViewById(R.id.et_register_pwd);
		etPwd1 = (EditText) findViewById(R.id.et_register_pwd1);
		cbAgr = (CheckBox) findViewById(R.id.cb_register_agreement);
		btAgr = (Button) findViewById(R.id.bt_register_agreement);
		btRegister = (Button) findViewById(R.id.bt_register_click);
	}

	@Override
	void initFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	void setListener() {
		// TODO Auto-generated method stub
		cbAgr.setOnClickListener(this);
		btAgr.setOnClickListener(this);
		btRegister.setOnClickListener(this);

	}

	// 注册方法
	private void register() {
		// TODO Auto-generated method stub
		HttpUtils http = new HttpUtils();
		url = getResources().getString(R.string.url_pre) + "SignServlet";
		RequestParams params = new RequestParams();

		name = etName.getText().toString();
		pwd = etPwd.getText().toString();
		pwd1 = etPwd1.getText().toString();
		// 判断用户名密码是否可用,2到 10位
		if (name.length() >= 2 && name.length() <= 10) {
			// 判断密码是否符合规范

			if (pwd.equals(pwd1)) {
				if (pwd.length() >= 6 && pwd.length() <= 10) {
					// 账号密码符合规范情况下,加入请求体，并发送请求
					if (flag == true) {
						params.addBodyParameter("name", name);
						params.addBodyParameter("pwd", pwd);
						http.send(HttpMethod.POST, url, params,
								new RequestCallBack<String>() {

									@Override
									public void onFailure(HttpException arg0,
											String arg1) {
										// TODO Auto-generated method stub
										Log.i("Log", "failure");

									}

									@Override
									public void onSuccess(
											ResponseInfo<String> response) {
										// TODO Auto-generated method stub
										Log.i("Log", "sucess");
										// 获取传输回来的值
										String result = response.result;
										// 使用Gson进行转换
										Gson gson = new Gson();
										// Type type = new
										// TypeToken<User>(){}.getType();
										// gson.fromJson(result, type);
										User user = gson.fromJson(result,
												User.class);
										Toast.makeText(RegisterActivity.this,
												user.getName(),
												Toast.LENGTH_LONG).show();

									}
								});
					} else {
						Toast.makeText(RegisterActivity.this, "请勾选同意协议",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(RegisterActivity.this, "密码不符合规范，请重新输入！",
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(RegisterActivity.this, "两次输入的密码不一致，请重新输入！",
						Toast.LENGTH_LONG).show();

			}

		} else {
			Toast.makeText(RegisterActivity.this, "账号不符合规范，请重新输入！",
					Toast.LENGTH_LONG).show();
		}

	}
}
