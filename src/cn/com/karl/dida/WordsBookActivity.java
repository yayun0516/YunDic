package cn.com.karl.dida;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yayun.demo.MyDatabaseHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WordsBookActivity extends Activity {
	private ListView listView;
	private MyDatabaseHelper myDatabaseHelper;
	List<HashMap<String, Object>> listData;
	SimpleAdapter simpleAdapter;
	private Button btnButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.words_list);
		btnButton=(Button)findViewById(R.id.shuaxin);
		/*btnButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				listView = (ListView) findViewById(R.id.listview_words);
				myDatabaseHelper = new MyDatabaseHelper(WordsBookActivity.this, "Word",
						null, 1);
				// 数据库
				SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
				Cursor c = db.query("Word", null, null, null, null, null, null, null);
				int columnsSize = c.getColumnCount();
				listData = new ArrayList<HashMap<String, Object>>();
				// 获取表的内容
				while (c.moveToNext()) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					for (int i = 0; i < columnsSize; i++) {
						map.put("id", c.getInt(0));
						map.put("name", c.getString(1));

					}
					listData.add(map);
				}
				
			}
		});*/
		/*simpleAdapter = new SimpleAdapter(this, this.listData,
				R.layout.wordsbook, new String[] { "id", "name" }, new int[] {
						R.id.word_id, R.id.word_name });
		*/
		

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					WordsBookActivity.this);
			// builder.setIcon(R.drawable.bee);
			builder.setTitle("你确定退出吗？");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							WordsBookActivity.this.finish();
							android.os.Process.killProcess(android.os.Process
									.myPid());
							android.os.Process.killProcess(android.os.Process
									.myTid());
							android.os.Process.killProcess(android.os.Process
									.myUid());
						}
					});
			builder.setNegativeButton("返回",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							dialog.cancel();
						}
					});
			builder.show();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
