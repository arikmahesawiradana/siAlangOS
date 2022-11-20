package com.sia.alang;

import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidmads.library.qrgenearator.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import com.google.zxing.WriterException;


public class QrgeneratorActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private String data = "";
	QRGEncoder qrgEncoder;
	Bitmap bitmap;
	private HashMap<String, Object> kunya = new HashMap<>();
	private double timer = 0;
	private HashMap<String, Object> update = new HashMap<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private TextView textview1;
	private ImageView qrCodeIV;
	
	private SharedPreferences save;
	private AlertDialog.Builder dialog;
	private TimerTask tim;
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	private Intent i = new Intent();
	private ProgressDialog progDial;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.qrgenerator);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);
		qrCodeIV = findViewById(R.id.qrCodeIV);
		save = getSharedPreferences("save", Activity.MODE_PRIVATE);
		dialog = new AlertDialog.Builder(this);
		net = new RequestNetwork(this);
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				if (_tag.equals("V")) {
					if (_response.equals("SUKSES")) {
						i.setAction(Intent.ACTION_VIEW);
						i.setClass(getApplicationContext(), WaitActivity.class);
						startActivity(i);
					}
					else {
						SketchwareUtil.showMessage(getApplicationContext(), "Error");
					}
				}
				if (_tag.equals("C")) {
					if (_response.equals("SUKSES")) {
						progDial.dismiss();
						finish();
					}
					else {
						progDial.dismiss();
						SketchwareUtil.showMessage(getApplicationContext(), "Error");
					}
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		data = save.getString("dataQR", "");
		kunya = new HashMap<>();
		kunya = new Gson().fromJson(save.getString("dataQR", ""), new TypeToken<HashMap<String, Object>>(){}.getType());
		textview1.setText(kunya.get("nama").toString());
		WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		int width = point.x;
		int height = point.y;
		int dimen = width < height ? width : height;
		dimen = dimen * 3 / 4;
		qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, dimen);
		try {
			    bitmap = qrgEncoder.encodeAsBitmap();
			    qrCodeIV.setImageBitmap(bitmap);
		}
		 catch (WriterException e) {
			     Log.e("Tag", e.toString());
		}
		timer = SketchwareUtil.getRandom((int)(5000), (int)(7000));
		update = new HashMap<>();
		update.put("nama", kunya.get("nama").toString());
		tim = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						net.setParams(update, RequestNetworkController.REQUEST_PARAM);
						net.startRequestNetwork(RequestNetworkController.POST, "https://www.sefeoofficial.com/ariklomba/UpdateSupir.php", "V", _net_request_listener);
					}
				});
			}
		};
		_timer.schedule(tim, (int)(timer));
	}
	
	
	@Override
	public void onBackPressed() {
		dialog.setTitle("Kembali");
		dialog.setMessage("Pesanan akan batal jika anda menekan tombol kembali. Apakah anda yakin?");
		dialog.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				net.setParams(update, RequestNetworkController.REQUEST_PARAM);
				net.startRequestNetwork(RequestNetworkController.POST, "https://www.sefeoofficial.com/ariklomba/DeletePesan.php", "C", _net_request_listener);
				progDial = new ProgressDialog(QrgeneratorActivity.this);
				progDial.setMessage("Membatalkan Pesanan....");
				progDial.setIndeterminate(true);
				progDial.setCancelable(false);
				progDial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progDial.show();
			}
		});
		dialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		dialog.create().show();
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
