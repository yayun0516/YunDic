package com.yayun.yundic;

import java.util.ArrayList;
import java.util.List;

import org.lxh.demo.DictResult3;
import org.lxh.demo.Parts;
import org.lxh.demo.RetData2;
import org.lxh.demo.Status1;
import org.lxh.demo.Symbols4;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.yayun.demo.MyDatabaseHelper;
import com.yayun.yundic.R;
import com.yayun.yundic.R.id;

public class DidaActivity extends Activity {

	private ImageButton btn_voice; // 语音服务
	private EditText editText; // 编辑单词
	private Button btn_search; // 搜索单词
	private TextView text_word;
	private ImageButton wordButton;
	RequestQueue mQueue;
	StringRequest stringRequest;
	Gson gson;
	String str;
	String string = null;

	private String aduioPath;
	private AudioManager audioManager;// 音量管理者
	private int maxVolume;// 最大音量

	private static Context context;
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	public static StringBuffer sb = new StringBuffer();
	private ProgressDialog mProgressDialog;
	MyDatabaseHelper myDatabaseHelper;
	List<String> listdata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dida);
		wordButton=(ImageButton)findViewById(R.id.btn_word);
		wordButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent=new Intent(DidaActivity.this,WordsBookActivity.class);
				startActivity(intent);
				
			}
		});
		context = this;
		editText=(EditText)findViewById(R.id.edit_search);
		btn_search=(Button)findViewById(R.id.btn_search);
		text_word=(TextView)findViewById(R.id.text_word);
		mProgressDialog=new ProgressDialog(this);
		myDatabaseHelper=new MyDatabaseHelper(this, "Words.db", null, 1);

		

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 获得最大音量
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume - 2,
				AudioManager.FLAG_ALLOW_RINGER_MODES);
		gson = new Gson();
		mQueue = Volley.newRequestQueue(DidaActivity.this);
		btn_search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(CheckNet()){
				
				
				string = editText.getText().toString().trim();
				if(TextUtils.isEmpty(string)){
					Toast.makeText(DidaActivity.this, "请输入不为空的单词！", Toast.LENGTH_SHORT).show();
				}else {
					//数据库操作
					// 数据库
					SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
					Cursor c = db.query("Word", null, null, null, null, null, null);
					listdata = new ArrayList<String>();
					// 获取表的内容
					while (c.moveToNext()) {

						listdata.add(0, c.getString(c.getColumnIndex("name")));

						String name = c.getString(c.getColumnIndex("name"));
						Log.d("11111111", name);
					}
					if(!listdata.contains(string)){

					//SQLiteDatabase db=myDatabaseHelper.getWritableDatabase();
					ContentValues contentValues=new ContentValues();
					contentValues.put("name",string);
					db.insert("Word", null, contentValues);
					}
					mProgressDialog.show();
					String requestUrl = getRequestUrl(string);
					stringRequest = new StringRequest(requestUrl,
							new Response.Listener<String>() {
								public void onResponse(String response) {

									Log.d("TAG", response);
									System.out.println("response=" + response);
									Status1 status = gson.fromJson(response,
											Status1.class);
									StringBuffer buffer = new StringBuffer();// 保存所用字符串
									int returnCode=status.getErrNum();
									if(returnCode==0){
									RetData2 retData2 = status.getRetData();// 第二个对象的获取

									System.out.println("from=" + retData2.getFrom());
									DictResult3 dictResult3;
									dictResult3 = retData2.getDictResult();// 第三个对象的获取
									buffer.append("单词："
											+ dictResult3.getWord_name() + "\n");
									System.out.println("word_name="
											+ dictResult3.getWord_name());
									List<Symbols4> symbols4s = dictResult3
											.getSymbols();// 第四个是对象数组哦，获取对象数组
									buffer.append("音标["
											+ symbols4s.get(0).getPh_en()
											+ "]"+"\n");// symbols4s.get(0)用于获取第一个对象
									List<Parts> parts = symbols4s.get(0).getParts();// 同理，最后一个也是对象数组
									for (int i = 0; i < parts.size(); i++) {
										buffer.append("part:"
												+ parts.get(i).getParts()// parts.get(i)获取对象List中的各个对象
												+ "\n");
										buffer.append("词义：");
										for (int j = 0; j < parts.get(i).getMeans().length; j++) {
											String[] aStrings = parts.get(i)
													.getMeans();
											buffer.append(aStrings[j]);
										}
										buffer.append("\n");
									}

									text_word.setText(buffer);
									mProgressDialog.dismiss();
									}
									else {
										mProgressDialog.dismiss();
										text_word.setText("查询失败，请输入正确的单词！");
										mProgressDialog.dismiss();
									}

									

								}
							}, new Response.ErrorListener() {
								public void onErrorResponse(VolleyError error) {
									Log.e("TAG", error.getMessage(), error);
								}

							});
					mQueue.add(stringRequest);
					
				}
				
			}else{
				mProgressDialog.dismiss();
				Toast.makeText(DidaActivity.this, "检查网络连接！", Toast.LENGTH_LONG).show();
			}
			}
		});
		

	}

	private String getRequestUrl(String word) {
		String url = null;
		if (word != null) {
			url = "http://apistore.baidu.com/microservice/dictionary?query="
					+ word + "&from=en&to=zh";
		}
		return url;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mProgressDialog != null) {
				mProgressDialog.dismiss();
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(
					DidaActivity.this);
			// builder.setIcon(R.drawable.bee);
			builder.setTitle("你确定退出吗？");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							DidaActivity.this.finish();
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

	/**
	 * 查询网络是否连接
	 * 
	 * @return
	 */
	private Boolean CheckNet() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		} else {
			return false;
		}
	}

}
