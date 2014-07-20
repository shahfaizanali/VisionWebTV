package com.vision.webtv;

import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import com.vision.webtv.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LauncherActivity extends Activity {

	WebView mainWebView;
	ProgressBar progress;
	ProgressDialog dialog;
	Boolean isInternetPresent;
	private static final String SOAP_ACTION = "http://tempuri.org/getChannalList"; 
	private static final String METHOD_NAME = "getChannalList";
	private static final String NAMESPACE = "http://tempuri.org/"; 
	private static final String URL = "http://interactive.on-the-web.tv:8066/loginService.asmx";
	String url = "http://interactive.on-the-web.tv:8066";
	private String [] links;
	private String[] numbrs;
	private String channel_no = "";
	private boolean is_start = false;
	private Timer timer ;
	
	String res;

	/** Called when the activity is first created. */
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		mainWebView = (WebView) findViewById(R.id.webView1);
		mainWebView.setBackgroundColor(Color.parseColor("#003366"));
		progress = (ProgressBar) findViewById(R.id.progressBar1);
		WebSettings webSettings = mainWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		mainWebView.getSettings().setLoadWithOverviewMode(true);
		mainWebView.getSettings().setUseWideViewPort(true);
		mainWebView.getSettings().setBuiltInZoomControls(true);
		mainWebView.getSettings().setSupportZoom(true);
		mainWebView.setWebViewClient(new MyCustomWebViewClient());
		 String imei="";
		 
		  try
		  {
			  WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			  WifiInfo info = manager.getConnectionInfo();
			  imei = info.getMacAddress();
		  }
		  catch (Exception eee)
		  {
		  }
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();

		if (isInternetPresent == true) {

			mainWebView.loadUrl(url+"/?id="+imei);

		

		SoapObject request =new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("mac", imei); 
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(request);
		envelope.dotNet=true;
	
		
		AndroidHttpTransport ht = new AndroidHttpTransport(URL);

		try
		{
			
			ht.call(SOAP_ACTION, envelope);
		
			SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			
			res=response.toString();
			
			String[] arr = res.split("&");
			links = arr[0].split(" ");
			numbrs = arr[2].split(" ");
			Log.i("res",res);
			
			
		} 
		catch (Exception ex) {
			Log.i("value","11");
			Log.i("value",ex.toString());
			//ex.printStackTrace();
			
		}
		} else {
			internetAlert();
		}


	}

	private int get_index(String url)
	{
		for(int i=0;i<links.length;i++)
		{
			Log.i("value",url.trim()+" "+links[i].trim());
			if(url.trim().equals(links[i].trim()))
				return i;
			
			
		}
		return 0;
		
	}
	private int get_num(String num)
	{
		for(int i=0;i<numbrs.length;i++)
		{
			if(num.trim().equals(numbrs[i].trim()))
				return i;
			
			
		}
		return -1;
		
	}
	private class MyCustomWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (isInternetPresent == true) {
				if (url.startsWith("rtmp") || url.startsWith("rtsp")) {
					Intent intent = new Intent(getBaseContext(),
							StreamPlayer.class);
					intent.putExtra("key", url);
					int indx =get_index( url);
				
					intent.putExtra("indx",indx+"" );
					intent.putExtra("res", res);
					
					startActivity(intent);
				} else {
					view.loadUrl(url);

				}

			} else {
				internetAlert();
			}
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			if (mainWebView.getVisibility() != View.VISIBLE) {
				mainWebView.setVisibility(View.VISIBLE);
			}
			progress.setVisibility(View.GONE);

		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
			loadError();
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					// Do something after 3sec
					finish();
				}
			}, 3000);

			return;
		}
	};

	@Override
	public void onBackPressed() {
		if (mainWebView.canGoBack() == true) {
			mainWebView.goBack();
		} else {
			exit();
		}

	}

	public void exit() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Note")
				.setMessage("Are you sure you want to Exit Vision Web TV?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}

						}).setNegativeButton("No", null).show();
	}

	public void internetAlert() {

		AlertDialog.Builder alertDialogInternet = new AlertDialog.Builder(
				LauncherActivity.this);

		// Setting Dialog Title
		alertDialogInternet.setTitle(" Alert");

		// Setting Dialog Message
		alertDialogInternet.setMessage("No internet Connection");

		// Setting Negative "NO" Button
		alertDialogInternet.setNegativeButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						finish();

					}
				});
		alertDialogInternet.show();
	}

	private void loadError() {
		findViewById(R.id.progressBar1).setVisibility(View.GONE);
		String html = "<html><body><table width=\"100%\" height=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
				+ "<tr>"
				+ "<td><div align=\"center\"><font color=\"white\" size=\"20pt\">Vision Web TV is down due to maintenance. Try again shortly.</font></div></td>"
				+ "</tr>" + "</table><html><body>";
		System.out.println("html " + html);

		String base64 = android.util.Base64.encodeToString(html.getBytes(),
				android.util.Base64.DEFAULT);
		mainWebView.loadData(base64, "text/html; charset=utf-8", "base64");
		System.out.println("loaded html");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater mymenu = getMenuInflater();
		mymenu.inflate(R.menu.launcher, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		String url = mainWebView.getUrl();
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.exit:
			exit();
			break;
		case R.id.refresh:
			mainWebView.loadUrl(url);
			break;
		case R.id.signout:
			mainWebView
					.loadUrl("http://interactive.on-the-web.tv:8066/SignOut.aspx");
			break;

		default:
			return super.onOptionsItemSelected(item);

		}

		return false;
	}
	private void change_channel()
	{
		runOnUiThread(new Runnable() {
		     @Override
		     public void run() {

		 		try {
		 			
					// TODO Auto-generated method stub
					if (channel_no != "") {
						int n=get_num(channel_no);
						if (n!=-1) {
							Intent intent = new Intent(getBaseContext(),
									StreamPlayer.class);
							intent.putExtra("key", links[n]);
							int indx =get_index( url);
						
							intent.putExtra("indx",n+"" );
							intent.putExtra("res", res);
							
							startActivity(intent);
							

						} else
							Toast.makeText(getApplicationContext(),
									"Channel Not Found! Please try other",
									Toast.LENGTH_SHORT).show();

						channel_no = "";
						is_start = false;
					}
				} catch (Exception e) {
					// TODO: handle exception
					Log.i("MYERR",e.getMessage());
				}

		    }
		});
	}


}
