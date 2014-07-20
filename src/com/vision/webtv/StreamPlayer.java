/*
 * Copyright (C) 2013 yixia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vision.webtv;

import java.util.Timer;
import java.util.TimerTask;

import com.vision.webtv.R;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.widget.VideoView;

public class StreamPlayer extends Activity implements OnInfoListener,
		OnBufferingUpdateListener {

	@Override
	public void onUserInteraction() {
		// TODO Auto-generated method stub
		if(h!= null)
		{
		h.removeCallbacks(r);
		h.postDelayed(r, 10000);
		}
		super.onUserInteraction();
	}

	private Uri uri;

	// private boolean isStart;
	private String httpLiveUrl;
	private ProgressBar pb;
	private VideoView mVideoView;
	private TextView channel, buffering;
	private ListView list;
	private ArrayAdapter<String> listAdapter;
	private String[] links;
	private String[] names;
	private String[] numbrs;
	private String[] listitems;
	private String[] Channels;
	private String res;
	private int index = 0;
	private boolean wasNext = true;
	private String channel_no = "";
	private boolean is_start = false;
	private Timer timer;
	private Handler h;
	private Runnable r;

	private int get_index(String url) {
		for (int i = 0; i < links.length; i++) {
			Log.i("value", url.trim() + " " + links[i].trim());
			if (url.trim().equals(links[i].trim()))
				return i;

		}
		return 0;

	}

	private void change_channel() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				try {

					// TODO Auto-generated method stub
					if (channel_no != "") {
						int n = Integer.parseInt(channel_no);
						if (Channels[n] != null) {

							buffering.setVisibility(View.VISIBLE);
							pb.setVisibility(View.VISIBLE);
							index = get_index(Channels[n]);
							uri = Uri.parse(Channels[n]);

							mVideoView.setVideoURI(uri);

						} else
							Toast.makeText(getApplicationContext(),
									"Channel Not Found! Please try other",
									Toast.LENGTH_SHORT).show();

					}
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(),
							"Channel Not Found! Please try other",
							Toast.LENGTH_SHORT).show();
					Log.i("MYERR", e.getMessage());
				}
				channel.setVisibility(View.GONE);
				channel_no = "";
				is_start = false;
			}
		});
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		try {
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT:
				prv_channel();
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				next_channel();
				break;
			case KeyEvent.KEYCODE_DPAD_CENTER:
				togglelist();
				break;

			case KeyEvent.KEYCODE_0:
				timer = new Timer();
				channel_no += 0;
				show_channelno();
				if (!is_start) {
					timer.schedule(new TimerTask() {

						@Override
						public void run() {

							change_channel();

						}
					}, 3000);
					is_start = true;
				}
				break;
			case KeyEvent.KEYCODE_1:
				timer = new Timer();
				channel_no += 1;
				show_channelno();
				if (!is_start) {
					timer.schedule(new TimerTask() {

						@Override
						public void run() {

							change_channel();

						}
					}, 3000);
					is_start = true;
				}
				break;
			case KeyEvent.KEYCODE_2:
				timer = new Timer();
				channel_no += 2;
				show_channelno();
				if (!is_start) {
					timer.schedule(new TimerTask() {

						@Override
						public void run() {

							change_channel();

						}
					}, 3000);
					is_start = true;
				}
				break;
			case KeyEvent.KEYCODE_3:
				timer = new Timer();
				channel_no += 3;
				show_channelno();
				if (!is_start) {
					timer.schedule(new TimerTask() {

						@Override
						public void run() {

							change_channel();

						}
					}, 3000);
					is_start = true;
				}
				break;
			case KeyEvent.KEYCODE_4:
				timer = new Timer();
				channel_no += 4;
				show_channelno();
				if (!is_start) {
					timer.schedule(new TimerTask() {

						@Override
						public void run() {

							change_channel();

						}
					}, 3000);
					is_start = true;
				}
				break;
			case KeyEvent.KEYCODE_5:
				timer = new Timer();
				channel_no += 5;
				show_channelno();
				if (!is_start) {
					timer.schedule(new TimerTask() {

						@Override
						public void run() {

							change_channel();

						}
					}, 3000);
					is_start = true;
				}
				break;
			case KeyEvent.KEYCODE_6:
				timer = new Timer();
				channel_no += 6;
				show_channelno();
				if (!is_start) {
					timer.schedule(new TimerTask() {

						@Override
						public void run() {

							change_channel();

						}
					}, 3000);
					is_start = true;
				}
				break;
			case KeyEvent.KEYCODE_7:
				timer = new Timer();
				channel_no += 7;
				show_channelno();
				if (!is_start) {
					timer.schedule(new TimerTask() {

						@Override
						public void run() {

							change_channel();

						}
					}, 3000);
					is_start = true;
				}
				break;
			case KeyEvent.KEYCODE_8:
				timer = new Timer();
				channel_no += 8;
				show_channelno();
				if (!is_start) {
					timer.schedule(new TimerTask() {

						@Override
						public void run() {

							change_channel();

						}
					}, 3000);
					is_start = true;
				}
				break;
			case KeyEvent.KEYCODE_9:
				timer = new Timer();
				channel_no += 9;
				show_channelno();
				if (!is_start) {
					timer.schedule(new TimerTask() {

						@Override
						public void run() {

							change_channel();

						}
					}, 3000);
					is_start = true;
				}
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
		return super.onKeyUp(keyCode, event);
	}

	private void show_channelno() {
		channel.setVisibility(View.VISIBLE);
		channel.setText(channel_no);
	}

	/**
	 * TODO: Set the path variable to a streaming video URL or a local media
	 * file path.
	 */
	// private String path = "rtmp://122.129.75.174:1935/live/colorsF007";

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (!LibsChecker.checkVitamioLibs(this))
			return;

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.player);
		mVideoView = (VideoView) findViewById(R.id.buffer);
		list = (ListView) findViewById(R.id.listView);
		pb = (ProgressBar) findViewById(R.id.probar);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			try {

				httpLiveUrl = extras.getString("key");
				index = Integer.parseInt(extras.getString("indx"));

				res = extras.getString("res");
				String[] arr = res.split("&");

				links = arr[0].split(" ");
				names = arr[1].split("%");
				numbrs = arr[2].split(" ");
				listitems = new String[names.length];
				for (int i = 0; i < names.length; i++) {
					listitems[i] = numbrs[i] + " " + names[i];

				}
				Channels = new String[Integer
						.parseInt(numbrs[numbrs.length - 1]) + 1];
				for (int i = 0; i < numbrs.length; i++) {
					Channels[Integer.parseInt(numbrs[i])] = links[i];

				}

				// Toast.makeText(getApplicationContext(), names.length,
				// Toast.LENGTH_LONG).show();
				listAdapter = new ArrayAdapter<String>(this,
						R.layout.simplerow, listitems);
				list.setAdapter(listAdapter);
				list.setBackgroundColor(Color.argb(200, 53, 206, 195));
				list.setVisibility(View.GONE);
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), e.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		}

		// downloadRateView = (TextView) findViewById(R.id.download_rate);
		// loadRateView = (TextView) findViewById(R.id.load_rate);
		buffering = (TextView) findViewById(R.id.buffering);
		channel = (TextView) findViewById(R.id.channel);
		channel.setBackgroundColor(Color.argb(190, 53, 206, 195));

		uri = Uri.parse(httpLiveUrl);
		mVideoView.setVideoURI(uri);
		// mVideoView.setMediaController(new MediaController(this));
		mVideoView.requestFocus();
		mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
		mVideoView.setOnInfoListener(this);
		mVideoView.setOnBufferingUpdateListener(this);
		h =new Handler();
		r = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				list.setVisibility(View.GONE);
				
			}
		};
		mVideoView.setOnTouchListener(new OnSwipeTouchListener(
				getApplicationContext()) {

			public void onSwipeRight() {
				prv_channel();
			}

			public void onSwipeLeft() {
				next_channel();
			}

			public void onTap() {

				togglelist();

			}

			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}

		});

		// list.setVisibility(View.GONE);
		// mVideoView.setBufferSize(512);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				buffering.setVisibility(View.VISIBLE);
				pb.setVisibility(View.VISIBLE);
				if (index != pos) {
					try {

						index = pos;
						uri = Uri.parse(links[pos]);
					} catch (Exception e2) {
						// TODO: handle exception
						Toast.makeText(getApplicationContext(),
								pos + " " + e2.getMessage(), Toast.LENGTH_LONG)
								.show();
					}
					mVideoView.setVideoURI(uri);

				}
				list.setVisibility(View.GONE);
			}
		});
		mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mediaPlayer) {
				// optional need Vitamio 4.0
				// mediaPlayer.setPlaybackSpeed(1.0f);

			}
		});

		mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			public boolean onError(MediaPlayer mp, int what, int extra) {
				if (wasNext) {
					Toast.makeText(
							getApplicationContext(),
							"Problem in loading Channel. Switching to next channel.",
							Toast.LENGTH_SHORT).show();
					next_channel();
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Problem in loading Channel. Switching to prv channel.",
							Toast.LENGTH_SHORT).show();

					prv_channel();

				}

				return true;
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (list.getVisibility() == View.VISIBLE) {
			list.setVisibility(View.GONE);
			return;

		}
		mVideoView.stopPlayback();
		finish();

	}

	private void next_channel() {
		if (index < (links.length - 1))
			index++;
		else
			index = 0;
		wasNext = true;
		buffering.setVisibility(View.VISIBLE);
		pb.setVisibility(View.VISIBLE);

		uri = Uri.parse(links[index]);

		mVideoView.setVideoURI(uri);

	}

	private void prv_channel() {
		if (index > 0)
			index--;
		else
			index = links.length - 1;
		wasNext = false;
		buffering.setVisibility(View.VISIBLE);
		pb.setVisibility(View.VISIBLE);
		uri = Uri.parse(links[index]);
		mVideoView.setVideoURI(uri);

	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// loadRateView.setText(percent + "%");
		pb.setVisibility(View.GONE);
		buffering.setVisibility(View.GONE);
		mVideoView.start();
	}

	@Override
	public boolean onInfo(MediaPlayer arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	public void togglelist() {
		if (list.getVisibility() == View.VISIBLE)
			list.setVisibility(View.GONE);
		else {

			list.setVisibility(View.VISIBLE);
			list.requestFocus();
			list.setSelection(index);
		}
	}
}
