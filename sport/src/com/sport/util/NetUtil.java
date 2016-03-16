package com.sport.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;

public abstract class NetUtil {
	public final static int GET = 0;
	public final static int POST = 1;

	public final static String STRING = "string";
	public final static String BITMAP = "bitmap";

	LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
			(int) Runtime.getRuntime().maxMemory() / 8) {

		@Override
		protected int sizeOf(String key, Bitmap value) {
			// TODO Auto-generated method stub
			return super.sizeOf(key, value);
		}

	};

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (msg.what) {
			case 1:
				Result resultString = new Result();
				resultString.result = msg.obj.toString();
				dealResult(resultString);
				break;
			case 2:
				Result resultBitmap = new Result();
				resultBitmap.image = (Bitmap) msg.obj;
				dealResult(resultBitmap);
				break;
			}
		}

	};

	public void execute(final String type, int method, final String urlStr) {
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
						Message msg = Message.obtain();
						switch (type) {
						case STRING:
							msg.what = 1;
							msg.obj = convertStreamToString(is);
							break;
						case BITMAP:
							msg.what = 2;
							Bitmap image = convertStreamToBitmap(is);
							msg.obj = image;
							setBitmapInLruCache(urlStr, image);
							break;
						}

						handler.sendMessage(msg);
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (null != is) {
						try {
							is.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		}.start();
	}

	public abstract void dealResult(Result result);

	public class Result {
		public String result;
		public Bitmap image;
	}

	private String convertStreamToString(InputStream is) {
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

	private Bitmap convertStreamToBitmap(InputStream is) {
		// TODO Auto-generated method stub
		return BitmapFactory.decodeStream(is);
	}

	public void begin(String type, int method, String urlStr) {
		if (type.equals(BITMAP)) {
			Bitmap image = lruCache.get(urlStr);
			if (null != image) {
				Result result = new Result();
				result.image = image;
				dealResult(result);
				return;
			}
			execute(type, method, urlStr);
			return;
		}

		execute(type, method, urlStr);
	}

	public void setBitmapInLruCache(String key, Bitmap value) {
		lruCache.put(key, value);
	}

	public Bitmap getBitmapFromLruCache(String key) {
		return lruCache.get(key);
	}
}
