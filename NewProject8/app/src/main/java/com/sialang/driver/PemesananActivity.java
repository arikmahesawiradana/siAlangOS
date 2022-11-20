package com.sialang.driver;

import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class PemesananActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private String title = "";
	private String pesan = "";
	private String plat = "";
	private double panjang = 0;
	private double pos = 0;
	private String plat2 = "";
	private HashMap<String, Object> nama = new HashMap<>();
	private HashMap<String, Object> platNomor = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> data = new ArrayList<>();
	
	private LinearLayout linear2;
	private ScrollView vscroll1;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear1;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear11;
	private ImageView imageview1;
	private TextView textview3;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private TextView textview4;
	private TextView textview5;
	private TextView textview6;
	private TextView textview7;
	private ListView listview1;
	
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	private Intent timer = new Intent();
	private TimerTask tim;
	private AlertDialog.Builder dialog;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.pemesanan);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear2 = findViewById(R.id.linear2);
		vscroll1 = findViewById(R.id.vscroll1);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		linear1 = findViewById(R.id.linear1);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		linear5 = findViewById(R.id.linear5);
		linear11 = findViewById(R.id.linear11);
		imageview1 = findViewById(R.id.imageview1);
		textview3 = findViewById(R.id.textview3);
		linear6 = findViewById(R.id.linear6);
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		linear9 = findViewById(R.id.linear9);
		textview4 = findViewById(R.id.textview4);
		textview5 = findViewById(R.id.textview5);
		textview6 = findViewById(R.id.textview6);
		textview7 = findViewById(R.id.textview7);
		listview1 = findViewById(R.id.listview1);
		net = new RequestNetwork(this);
		dialog = new AlertDialog.Builder(this);
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				if (_tag.equals("A")) {
					if (_response.equals("[]")) {
						
					}
					else {
						data = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						listview1.setAdapter(new Listview1Adapter(data));
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
						pos = data.size();
						if (panjang < pos) {
							panjang = pos;
							title = "Pelanggan Baru";
							pesan = "Anda mendapatkan pelanggan baru";
							_notifikasi(title, pesan);
						}
					}
				}
				if (_tag.equals("B")) {
					if (_response.contains("SUKSES")) {
						SketchwareUtil.showMessage(getApplicationContext(), "Pesanan sudah di hapus");
					}
					else {
						textview3.setText(_response);
					}
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				if (_tag.equals("B")) {
					SketchwareUtil.showMessage(getApplicationContext(), "No internet");
				}
			}
		};
	}
	
	private void initializeLogic() {
		panjang = 0;
		plat = "N 8373 LB";
		_notifBackgroud();
	}
	
	public void _notifikasi(final String _title, final String _message) {
		final Context context = getApplicationContext();
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(this, PemesananActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		androidx.core.app.NotificationCompat.Builder builder;
		int notificationId = 1;
		String channelId = "channel-01";
		String channelName = "Channel Name";
		int importance = NotificationManager.IMPORTANCE_HIGH;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			    NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
			    notificationManager.createNotificationChannel(mChannel);
			    }
		androidx.core.app.NotificationCompat.Builder mBuilder = new androidx.core.app.NotificationCompat.Builder(context, channelId)
		.setSmallIcon(R.drawable.logo)
		.setContentTitle(_title)
		.setContentText(_message)
		.setAutoCancel(false)
		.setOngoing(false)
		.setContentIntent(pendingIntent);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addNextIntent(intent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		notificationManager.notify(notificationId, mBuilder.build());
	}
	
	
	public void _notifBackgroud() {
		class task extends AsyncTask<Void, Void, Void> { 
			@Override
			protected void onPreExecute() {
				 
				return ;
			}

			protected Void doInBackground(Void... arg0) {
				
				tim = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								platNomor = new HashMap<>();
								platNomor.put("plat", plat);
								net.setParams(platNomor, RequestNetworkController.REQUEST_PARAM);
								net.startRequestNetwork(RequestNetworkController.POST, "https://www.sefeoofficial.com/ariklomba/GetSupir.php", "A", _net_request_listener);
							}
						});
					}
				};
				_timer.scheduleAtFixedRate(tim, (int)(5), (int)(5000));
				
				return null;
				 }

			protected void onPostExecute(Void result) {
				 
								return ;
								    }
		}
		new task().execute();
	}
	
	public class Listview1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.listview, null);
			}
			
			final LinearLayout linear10 = _view.findViewById(R.id.linear10);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final LinearLayout linear6 = _view.findViewById(R.id.linear6);
			final LinearLayout linear7 = _view.findViewById(R.id.linear7);
			final LinearLayout linear8 = _view.findViewById(R.id.linear8);
			final LinearLayout linear9 = _view.findViewById(R.id.linear9);
			final TextView textview4 = _view.findViewById(R.id.textview4);
			final TextView textview5 = _view.findViewById(R.id.textview5);
			final TextView textview6 = _view.findViewById(R.id.textview6);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			
			linear6.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c) { this.setStroke(a, b); this.setColor(c); return this; } }.getIns((int)2, 0xFFBED65E, 0xFFFFFFFF));
			linear7.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c) { this.setStroke(a, b); this.setColor(c); return this; } }.getIns((int)2, 0xFFBED65E, 0xFFFFFFFF));
			linear8.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c) { this.setStroke(a, b); this.setColor(c); return this; } }.getIns((int)2, 0xFFBED65E, 0xFFFFFFFF));
			linear9.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c) { this.setStroke(a, b); this.setColor(c); return this; } }.getIns((int)2, 0xFFBED65E, 0xFFFFFFFF));
			textview4.setText(_data.get((int)_position).get("id").toString());
			textview5.setText(_data.get((int)_position).get("tujuan").toString());
			textview6.setText(_data.get((int)_position).get("statusa").toString());
			imageview1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					dialog.setTitle("Hapus!");
					dialog.setMessage("Apakah yakin ingin di hapus?");
					dialog.setPositiveButton("ya", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							nama = new HashMap<>();
							nama.put("nama", data.get((int)_position).get("nama").toString());
							net.setParams(nama, RequestNetworkController.REQUEST_PARAM);
							net.startRequestNetwork(RequestNetworkController.POST, "https://www.sefeoofficial.com/ariklomba/DeletePesan.php", "B", _net_request_listener);
						}
					});
					dialog.setNegativeButton("tidak", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.create().show();
				}
			});
			
			return _view;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}
