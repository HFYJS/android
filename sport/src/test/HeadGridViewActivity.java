package test;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.sport.R;
import com.sport.ui.adapter.CommonAdapter;
import com.sport.ui.adapter.ViewHolder;

public class HeadGridViewActivity extends Activity {

	MyGridView gv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_head_grid);

		gv = (MyGridView) findViewById(R.id.gv_test);

		List<String> lists = new ArrayList<String>();
		lists.add("test1");
		lists.add("test2");
		lists.add("test3");
		lists.add("test4");
		lists.add("test5");
		lists.add("test6");
		lists.add("test7");
		lists.add("test8");
		lists.add("test9");

		gv.setAdapter(new CommonAdapter<String>(this, R.layout.test_item, lists) {

			@Override
			protected void setValue(ViewHolder vh, String value) {
				// TODO Auto-generated method stub
				vh.setTextView(R.id.tv_test_item, value);
			}

		});
	}

}
