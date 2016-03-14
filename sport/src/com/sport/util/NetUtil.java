package com.sport.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class NetUtil {
	public final static int GET = 0;
	public final static int POST = 1;

	public static void getConnection(int method, final String urlStr,
			final Handler handler, final int what) throws Exception {
		switch (method) {
		case GET:
			new Thread() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();

					HttpURLConnection conn = null;
					InputStream is = null;

					try {
						URL url = new URL(urlStr);
						conn = (HttpURLConnection) url.openConnection();
						conn.setConnectTimeout(5000);

						if (conn.getResponseCode() == 200) {
							is = conn.getInputStream();
							String result = convertStreamToString(is);

							Message msg = Message.obtain();
							msg.what = what;
							msg.obj = result;
							handler.sendMessage(msg);
						}
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}.start();
			break;
		case POST:

			break;
		default:
			throw new Exception("no such method!");
		}

	}

	private static String convertStreamToString(InputStream is) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			int b = 0;
			while ((b = is.read()) != -1) {
				baos.write(b);
			}

			return baos.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
