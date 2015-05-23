package cn.com.karl.dida;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.lxh.demo.DictResult3;
import org.lxh.demo.Parts;
import org.lxh.demo.RetData2;
import org.lxh.demo.Status1;
import org.lxh.demo.Symbols4;
import org.lxh.demo.Translate;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class TransActivity extends Activity implements OnClickListener {

	private ImageButton btn_type;
	private Button btn_tran;
	private Button btn_voice;
	private EditText et_value;
	private EditText et_result;
	private ProgressDialog mProgressDialog = null;
	private static Boolean isTrue = false;
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 12345;
	Matcher m = null;
	RequestQueue mQueue;
	Gson gson;
	String string;
	StringRequest stringRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trans);
		btn_type = (ImageButton) findViewById(R.id.btn_type);
		btn_tran = (Button) findViewById(R.id.btn_tran);
		btn_voice = (Button) findViewById(R.id.btn_voice);
		et_value = (EditText) findViewById(R.id.et_value);
		et_result = (EditText) findViewById(R.id.et_result);
		mProgressDialog = new ProgressDialog(this);
		gson = new Gson();
		mQueue = Volley.newRequestQueue(TransActivity.this);
		btn_tran.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				string = et_value.getText().toString().trim();
				if(CheckNet()){
					if(!TextUtils.isEmpty(string)){
				mProgressDialog.show();
				String requestUrl = getRequestUrl(string);
				stringRequest = new StringRequest(requestUrl,
						new Response.Listener<String>() {
							public void onResponse(String response) {
								
								System.out.println(response);
								Translate translate = gson.fromJson(response,
										Translate.class);
								StringBuffer buffer = new StringBuffer();// 保存所用字符串
								int returnCode = translate.getErrorCode();
								if (returnCode == 0) {
									for (String string : translate
											.getTranslation()) {
										buffer.append(string);
										buffer.append("\n");
									}
								} else {
									Toast.makeText(TransActivity.this, "搜索有误！",
											Toast.LENGTH_SHORT).show();
									mProgressDialog.dismiss();
								}
								et_result.setText(buffer);
								mProgressDialog.dismiss();
							}
						
				}, new Response.ErrorListener() {
							public void onErrorResponse(VolleyError error) {
								Log.e("TAG", error.getMessage(), error);
							}

						});
				mQueue.add(stringRequest);
				
					}else {
						Toast.makeText(TransActivity.this, "输入不能为空！",
								Toast.LENGTH_SHORT).show();
						mProgressDialog.dismiss();
					}
				}else {
					Toast.makeText(TransActivity.this, "请检查网络！",
							Toast.LENGTH_SHORT).show();
					mProgressDialog.dismiss();
				}
			}
		});
		
	}
	private String getRequestUrl(String word) {
		String url = null;
		if (word != null) {
			url = "http://fanyi.youdao.com/openapi.do?keyfrom=dfsfafasfsaddfsaf&key"//申请有道API
					+ "=814165254&type=data&doctype=json&version=1.1"
					+ "&q="
					+ java.net.URLEncoder.encode(word);// 将汉字转变成URLEncoder格式，不然返回的字符串将是乱码。
		}
		return url;
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					TransActivity.this);
			// builder.setIcon(R.drawable.bee);
			builder.setTitle("你确定退出吗？");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							TransActivity.this.finish();
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

	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
