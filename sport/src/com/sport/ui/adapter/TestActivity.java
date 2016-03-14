//package com.sport.ui.adapter;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.ListView;
//
//import com.google.gson.Gson;
//import com.sport.R;
//
//public class TestActivity extends Activity {
//
//	ListView lvTest;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_test);
//
//		lvTest = (ListView) findViewById(R.id.lv_test);
//
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		// 数据1
//		Map<String, String> map1 = new HashMap<String, String>();
//		map1.put("type", "student");
//		Student student1 = new Student();
//		student1.setName("HFY");
//		student1.setAge(12);
//		map1.put("data", new Gson().toJson(student1));
//		list.add(map1);
//		// 数据2
//		Map<String, String> map2 = new HashMap<String, String>();
//		map2.put("type", "teacher");
//		Teacher teacher1 = new Teacher();
//		teacher1.setName("T1");
//		teacher1.setAge(52);
//		teacher1.setAddress("jiangsu");
//		map2.put("data", new Gson().toJson(teacher1));
//		list.add(map2);
//		// 数据3
//		Map<String, String> map3 = new HashMap<String, String>();
//		map3.put("type", "student");
//		Student student2 = new Student();
//		student2.setName("JS");
//		student2.setAge(14);
//		map3.put("data", new Gson().toJson(student2));
//		list.add(map3);
//		
//		lvTest.setAdapter(new XCommonAdapter(this,list,new Integer[2]{1,2}));
//	}
//
//}
