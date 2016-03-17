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
	// ���ñ�ʾ���Ƿ�ȷ��Э��
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
					"����Э�����Э�����Э�����Э�����Э�����Э�����Э�����Э�����Э�����Э�����Э�����Э��",
					Toast.LENGTH_LONG).show();
			break;
		case R.id.bt_register_click:
			Log.i("Tag", "���ע�ᰴť");
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

	// ע�᷽��
	private void register() {
		// TODO Auto-generated method stub
		HttpUtils http = new HttpUtils();
		url = getResources().getString(R.string.url_pre) + "SignServlet";
		RequestParams params = new RequestParams();

		name = etName.getText().toString();
		pwd = etPwd.getText().toString();
		pwd1 = etPwd1.getText().toString();
		// �ж��û��������Ƿ����,2�� 10λ
		if (name.length() >= 2 && name.length() <= 10) {
			// �ж������Ƿ���Ϲ淶

			if (pwd.equals(pwd1)) {
				if (pwd.length() >= 6 && pwd.length() <= 10) {
					// �˺�������Ϲ淶�����,���������壬����������
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
										// ��ȡ���������ֵ
										String result = response.result;
										// ʹ��Gson����ת��
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
						Toast.makeText(RegisterActivity.this, "�빴ѡͬ��Э��",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(RegisterActivity.this, "���벻���Ϲ淶�����������룡",
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(RegisterActivity.this, "������������벻һ�£����������룡",
						Toast.LENGTH_LONG).show();

			}

		} else {
			Toast.makeText(RegisterActivity.this, "�˺Ų����Ϲ淶�����������룡",
					Toast.LENGTH_LONG).show();
		}

	}
}
