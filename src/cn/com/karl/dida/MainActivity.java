package cn.com.karl.dida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lxh.demo.MoreActivity;
import org.lxh.demo.TestActivity;

import com.yayun.demo.MyDatabaseHelper;

import cn.com.karl.dida.R;
import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	private TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		tabHost = this.getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		intent = new Intent().setClass(this, DidaActivity.class);
		spec = tabHost.newTabSpec("´Êµä").setIndicator("´Êµä").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, WordsBookActivity.class);
		spec = tabHost.newTabSpec("Éú´Ê").setIndicator("Éú´Ê").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, TransActivity.class);
		spec = tabHost.newTabSpec("·­Òë").setIndicator("·­Òë").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, TestActivity.class);
		spec = tabHost.newTabSpec("²âÊÔ").setIndicator("²âÊÔ").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, MoreActivity.class);
		spec = tabHost.newTabSpec("¸ü¶à").setIndicator("¸ü¶à").setContent(intent);
		tabHost.addTab(spec);

		RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.main_radio);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radio_button0:// ´Êµä
					tabHost.setCurrentTabByTag("´Êµä");
					break;
				case R.id.radio_button1:// Éú´Ê
					tabHost.setCurrentTabByTag("Éú´Ê");

					break;
				case R.id.radio_button2:// ·­Òë
					tabHost.setCurrentTabByTag("·­Òë");
					break;
				case R.id.radio_button3:// ²âÊÔ
					tabHost.setCurrentTabByTag("²âÊÔ");
					break;
				case R.id.radio_button4:// ¸ü¶à
					tabHost.setCurrentTabByTag("¸ü¶à");
					break;
				default:
					tabHost.setCurrentTabByTag("´Êµä");
					break;
				}
			}
		});
	}
}
