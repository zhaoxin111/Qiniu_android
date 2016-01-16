package com.example.testqiniutoken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

public class MainActivity extends Activity implements OnClickListener {
	private Button button;
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
	}


	
	public byte[] getBytes(InputStream is)  {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[2048];
		int len;
		try {
			while ((len = is.read(b, 0, 2048)) != -1) {
				baos.write(b, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			baos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

	@Override
	public void onClick(View v) {
		try {
			
			InputStream is = getResources().getAssets().open(
					"146459-105.jpg");
			byte[] bytes = getBytes(is);
			UploadManager uploadManager = new UploadManager();
			//第二个参数就是指定在bucket里面存储文件的名字，唯一性
			uploadManager
					.put(bytes,
							"meinv",
				"8EqgYr4A18xyeUzWdDHt6OKTKJ5wW9mttLKiobF_:vMN2oe1OFKTkBHPv0TplIfTBjyw=:eyJzY29wZSI6ImFncmljdWx0dXJldGVzdCIsInJldHVybkJvZHkiOiJ7XCJrZXlcIjogJChrZXkpLCBcImltYWdlQXZlXCI6ICQoaW1hZ2VBdmUpLCBcIndpZHRoXCI6ICQoaW1hZ2VJbmZvLndpZHRoKSwgXCJoZWlnaHRcIjogJChpbWFnZUluZm8uaGVpZ2h0KX0iLCJkZWFkbGluZSI6MTQ1MjkzMzUxOX0=",
							new UpCompletionHandler() {
								@Override
								public void complete(String key,
										ResponseInfo info, JSONObject response) {
									Log.e(TAG, key);
									Log.e(TAG, info.toString());
									Log.e(TAG, "上传是否成功"+info.isOK());
									//上传失败的话response是null ！！！！！！
									if (response!=null) {
										Log.e(TAG, response.toString());
										
									}
								}
							}, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
