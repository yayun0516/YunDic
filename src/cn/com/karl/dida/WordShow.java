package cn.com.karl.dida;

import java.util.List;

import org.lxh.demo.DictResult3;
import org.lxh.demo.Parts;
import org.lxh.demo.RetData2;
import org.lxh.demo.Status1;
import org.lxh.demo.Symbols4;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class WordShow extends Activity {
	private TextView textView;
	RequestQueue mQueue;
	StringRequest stringRequest;
	Gson gson;
	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wordshow);
		textView = (TextView) findViewById(R.id.text_word);
		Intent intent = super.getIntent();
		String word = intent.getStringExtra("word");
		Log.d("word", word);
		gson = new Gson();
		progressDialog=new ProgressDialog(this);
		mQueue = Volley.newRequestQueue(WordShow.this);
		
				if (CheckNet()) {
					progressDialog.show();

					String requestUrl = getRequestUrl(word);
					stringRequest = new StringRequest(requestUrl,
							new Response.Listener<String>() {
								public void onResponse(String response) {

									Log.d("TAG", response);
									System.out.println("response=" + response);
									Status1 status = gson.fromJson(response,
											Status1.class);
									StringBuffer buffer = new StringBuffer();// 保存所用字符串
									int returnCode = status.getErrNum();
									if (returnCode == 0) {
										RetData2 retData2 = status.getRetData();// 第二个对象的获取

										System.out.println("from="
												+ retData2.getFrom());
										DictResult3 dictResult3;
										dictResult3 = retData2.getDictResult();// 第三个对象的获取
										buffer.append("单词："
												+ dictResult3.getWord_name()
												+ "\n");
										System.out.println("word_name="
												+ dictResult3.getWord_name());
										List<Symbols4> symbols4s = dictResult3
												.getSymbols();// 第四个是对象数组哦，获取对象数组
										buffer.append("音标["
												+ symbols4s.get(0).getPh_en()
												+ "]"+"\n");// symbols4s.get(0)用于获取第一个对象
										List<Parts> parts = symbols4s.get(0)
												.getParts();// 同理，最后一个也是对象数组
										for (int i = 0; i < parts.size(); i++) {
											buffer.append("part:"
													+ parts.get(i).getParts()// parts.get(i)获取对象List中的各个对象
													+ "\n");
											buffer.append("词义：");
											for (int j = 0; j < parts.get(i)
													.getMeans().length; j++) {
												String[] aStrings = parts
														.get(i).getMeans();
												buffer.append(aStrings[j]);
											}
											buffer.append("\n");
										}

										WordShow.this.textView.setText(buffer);
										progressDialog.dismiss();

									}

								}
							}, new Response.ErrorListener() {
								public void onErrorResponse(VolleyError error) {
									Log.e("TAG", error.getMessage(), error);
								}

							});
					mQueue.add(stringRequest);

				}
				else{
					Toast.makeText(this, "检查网络！", Toast.LENGTH_SHORT);
					progressDialog.dismiss();
				}

			}

		

	

	private String getRequestUrl(String word) {
		String url = null;
		if (word != null) {
			url = "http://apistore.baidu.com/microservice/dictionary?query="
					+ word + "&from=en&to=zh";
		}
		return url;
	}

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
