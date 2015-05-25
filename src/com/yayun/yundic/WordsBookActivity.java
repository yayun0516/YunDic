package com.yayun.yundic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yayun.demo.MyDatabaseHelper;
import com.yayun.yundic.R;

import android.R.anim;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class WordsBookActivity extends Activity {
	private ListView listView;
	private MyDatabaseHelper myDatabaseHelper;

	//SimpleAdapter simpleAdapter;
	private Button btnButton, tbnshuaxin;
	List<String> listdata;
	SQLiteDatabase db;
	ArrayAdapter  adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wordsbook);
		btnButton = (Button) findViewById(R.id.delete);
		tbnshuaxin = (Button) findViewById(R.id.shuaxin);
		listView = (ListView) findViewById(R.id.listview_words);
		super.registerForContextMenu(this.listView);// ע�������Ĳ˵�

		myDatabaseHelper = new MyDatabaseHelper(WordsBookActivity.this,
				"Words.db", null, 1);
		// ���ݿ�
		 db = myDatabaseHelper.getWritableDatabase();
		Cursor c = db.query("Word", null, null, null, null, null, null);
		listdata = new ArrayList<String>();
		// ��ȡ�������
		while (c.moveToNext()) {

			listdata.add(0, c.getString(c.getColumnIndex("name")));

			String name = c.getString(c.getColumnIndex("name"));
			Log.d("11111111", name);
		}
		adapter=new ArrayAdapter<String>(WordsBookActivity.this,
				android.R.layout.simple_expandable_list_item_1, listdata);
		listView.setAdapter(adapter);

		btnButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Dialog dialog = new AlertDialog.Builder(WordsBookActivity.this)
						.setTitle("���棡")
						.setMessage("��ȷ�������")
						.setPositiveButton("���",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										SQLiteDatabase db = myDatabaseHelper
												.getWritableDatabase();
										db.delete("Word", "id > ?",
												new String[] { "0" });

									}
								})
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								}).create();
				dialog.show();

			}
		});
		tbnshuaxin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				myDatabaseHelper = new MyDatabaseHelper(WordsBookActivity.this,
						"Words.db", null, 1);
				// ���ݿ�
				SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
				Cursor c = db.query("Word", null, null, null, null, null, null);
				listdata = new ArrayList<String>();
				// ��ȡ�������
				while (c.moveToNext()) {

					listdata.add(0, c.getString(c.getColumnIndex("name")));

					String name = c.getString(c.getColumnIndex("name"));
					Log.d("11111111", name);
				}

				listView.setAdapter(new ArrayAdapter<String>(
						WordsBookActivity.this,
						android.R.layout.simple_expandable_list_item_1,
						listdata));

			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(WordsBookActivity.this,
						WordShow.class);

				String word = listdata.get(position);
				Log.d("word1", word);
				intent.putExtra("word", word);
				startActivity(intent);

			}
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				return false;
			}
		});

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, 1, 1, "ɾ��");
		menu.add(0, 2, 1, "ȡ��");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo acmiRef = (AdapterContextMenuInfo) item.getMenuInfo();//������ȡitem��Ϣ������Ҫ
		int removeIndex =acmiRef.position;
		System.out.println(removeIndex);
		System.out.println(WordsBookActivity.this.listdata.get(removeIndex));
		switch (item.getItemId()) {
		case 1:
			db = myDatabaseHelper.getWritableDatabase();
			db.delete("Word", "name=?", new String[]{WordsBookActivity.this.listdata.get(removeIndex)});
			listdata.remove(removeIndex);
			adapter.notifyDataSetChanged();//ɾ����ˢ��ListView

			break;
		case 2:

			break;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
}
