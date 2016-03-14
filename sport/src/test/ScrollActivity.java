package test;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.sport.R;
import com.sport.ui.adapter.CommonAdapter;
import com.sport.ui.adapter.ViewHolder;

public class ScrollActivity extends Activity {

	ListView lvTest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		lvTest = (ListView) findViewById(R.id.lv_test);

		List<String> list = new ArrayList<String>();
		list.add("hfy1");
		list.add("hfy2");
		list.add("hfy3");
		list.add("hfy4");
		list.add("hfy5");
		list.add("hfy6");
		list.add("hfy7");
		list.add("hfy8");
		list.add("hfy9");

		lvTest.setAdapter(new CommonAdapter<String>(this, R.layout.test_item,
				list) {

			@Override
			protected void setValue(ViewHolder vh, String value) {
				// TODO Auto-generated method stub
				vh.setTextView(R.id.tv_test_item, value);
			}

		});
	}

}
