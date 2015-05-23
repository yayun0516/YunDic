package cn.com.karl.dida;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DidaActivity extends Activity {

	private ImageButton btn_voice; // ��������
	private EditText edit_search; // �༭����
	private Button btn_search; // ��������
	private TextView text_word;
	private TextView text_pron;
	private TextView btn_aduio; // ���ʷ���
	private TextView btn_add; // ��ӵ���
	private TextView text_def;
	private ListView didaListview;
	
	private String aduioPath;
	private AudioManager audioManager;// ����������
	private int maxVolume;// �������
	
	private static Context context;
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;  
	public static StringBuffer sb=new StringBuffer();
	private ProgressDialog mProgressDialog;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dida);

		context = this;

	
		
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// ����������
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
				maxVolume-2, AudioManager.FLAG_ALLOW_RINGER_MODES);
		
	}



	   @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub

			if (keyCode == KeyEvent.KEYCODE_BACK) {
				if(mProgressDialog!=null){
				mProgressDialog.dismiss();
				}
				AlertDialog.Builder builder = new AlertDialog.Builder(
						DidaActivity.this);
				//builder.setIcon(R.drawable.bee);
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
