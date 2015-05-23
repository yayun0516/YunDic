package cn.com.karl.dida;

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
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.com.karl.dida.R.id;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class DidaActivity extends Activity {

	private ImageButton btn_voice; // ��������
	private EditText editText; // �༭����
	private Button btn_search; // ��������
	private TextView text_word;
	RequestQueue mQueue;
	StringRequest stringRequest;
	Gson gson;
	String str;
	String string = null;

	private String aduioPath;
	private AudioManager audioManager;// ����������
	private int maxVolume;// �������

	private static Context context;
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	public static StringBuffer sb = new StringBuffer();
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dida);
		context = this;
		editText=(EditText)findViewById(R.id.edit_search);
		btn_search=(Button)findViewById(R.id.btn_search);
		text_word=(TextView)findViewById(R.id.text_word);
		mProgressDialog=new ProgressDialog(this);

		

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// ����������
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume - 2,
				AudioManager.FLAG_ALLOW_RINGER_MODES);
		gson = new Gson();
		mQueue = Volley.newRequestQueue(DidaActivity.this);
		btn_search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mProgressDialog.show();
				string = editText.getText().toString();
				String requestUrl = getRequestUrl(string);
				stringRequest = new StringRequest(requestUrl,
						new Response.Listener<String>() {
							public void onResponse(String response) {

								Log.d("TAG", response);
								System.out.println("response=" + response);
								Status1 status = gson.fromJson(response,
										Status1.class);
								StringBuffer buffer = new StringBuffer();// ���������ַ���
								int returnCode=status.getErrNum();
								if(returnCode==0){
								RetData2 retData2 = status.getRetData();// �ڶ�������Ļ�ȡ

								System.out.println("from=" + retData2.getFrom());
								DictResult3 dictResult3;
								dictResult3 = retData2.getDictResult();// ����������Ļ�ȡ
								buffer.append("���ʣ�"
										+ dictResult3.getWord_name() + "\n");
								System.out.println("word_name="
										+ dictResult3.getWord_name());
								List<Symbols4> symbols4s = dictResult3
										.getSymbols();// ���ĸ��Ƕ�������Ŷ����ȡ��������
								buffer.append("����"
										+ symbols4s.get(0).getPh_en() + "\n");// symbols4s.get(0)���ڻ�ȡ��һ������
								List<Parts> parts = symbols4s.get(0).getParts();// ͬ�����һ��Ҳ�Ƕ�������
								for (int i = 0; i < parts.size(); i++) {
									buffer.append("part:"
											+ parts.get(i).getParts()// parts.get(i)��ȡ����List�еĸ�������
											+ "\n");
									buffer.append("���壺");
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
									text_word.setText("��ѯʧ��!�������磬��������뵥�ʡ�");
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
			builder.setTitle("��ȷ���˳���");
			builder.setPositiveButton("ȷ��",
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
			builder.setNegativeButton("����",
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
	 * ��ѯ�����Ƿ�����
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
