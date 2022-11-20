package com.sia.alang;

import android.app.*;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.util.*;
import android.view.View;
import android.widget.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.*;
import java.util.HashMap;

public class AkunActivity extends AppCompatActivity {
	
	private HashMap<String, Object> data = new HashMap<>();
	private HashMap<String, Object> cck = new HashMap<>();
	private String nama = "";
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear7;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private ImageView imageview1;
	private TextView textview1;
	private LinearLayout linear3;
	private LinearLayout linear6;
	private TextView textview2;
	private TextView textview3;
	private TextView textview4;
	private Button button2;
	private TextView textview5;
	
	private SharedPreferences save;
	private Intent i = new Intent();
	private ProgressDialog progDial;
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.akun);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear7 = findViewById(R.id.linear7);
		linear4 = findViewById(R.id.linear4);
		linear5 = findViewById(R.id.linear5);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		linear3 = findViewById(R.id.linear3);
		linear6 = findViewById(R.id.linear6);
		textview2 = findViewById(R.id.textview2);
		textview3 = findViewById(R.id.textview3);
		textview4 = findViewById(R.id.textview4);
		button2 = findViewById(R.id.button2);
		textview5 = findViewById(R.id.textview5);
		save = getSharedPreferences("save", Activity.MODE_PRIVATE);
		net = new RequestNetwork(this);
		
		linear7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				cck = new HashMap<>();
				cck.put("nama", data.get("nama").toString());
				cck.put("passwordnya", data.get("passwordnya").toString());
				net.setParams(cck, RequestNetworkController.REQUEST_PARAM);
				net.startRequestNetwork(RequestNetworkController.GET, "https://www.sefeoofficial.com/ariklomba/cocokkan.php", "A", _net_request_listener);
				progDial = new ProgressDialog(AkunActivity.this);
				progDial.setMessage("Tunggu Sebentar.....");
				progDial.setIndeterminate(true);
				progDial.setCancelable(false);
				progDial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progDial.show();
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setClass(getApplicationContext(), TopupActivity.class);
				startActivity(i);
			}
		});
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				if (_response.equals("FAILED")) {
					progDial.dismiss();
					i.setAction(Intent.ACTION_VIEW);
					i.setClass(getApplicationContext(), MenuutamaActivity.class);
					startActivity(i);
				}
				else {
					progDial.dismiss();
					i.setAction(Intent.ACTION_VIEW);
					i.setClass(getApplicationContext(), WaitActivity.class);
					startActivity(i);
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				progDial.dismiss();
			}
		};
	}
	
	private void initializeLogic() {
		data = new HashMap<>();
		button2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFFFFFFFF));
		data = new Gson().fromJson(save.getString("data", ""), new TypeToken<HashMap<String, Object>>(){}.getType());
		textview1.setText(data.get("nama").toString());
		textview4.setText(data.get("saldo").toString());
		_GradienRadii(linear4, "#FFBED65E", "#FFBED65E", "#FFFFFF", 20, 0, 0, 20, 2, 0);
		_GradienRadii(linear3, "#FFBED65E", "#FFBED65E", "#FFFFFF", 0, 0, 20, 0, 2, 0);
		_GradienRadii(linear6, "#FFBED65E", "#FFBED65E", "#FFFFFF", 0, 20, 0, 0, 2, 0);
		_tombol(linear7, "#FFBED65E", "#FFFFFFFF");
	}
	
	@Override
	public void onBackPressed() {
		finishAffinity();
	}
	public void _GradienRadii(final View _view, final String _color1, final String _color2, final String _strCol, final double _lt, final double _rb, final double _lb, final double _rt, final double _stro, final double _shadow) {
		android.graphics.drawable.GradientDrawable ab = new android.graphics.drawable.GradientDrawable(GradientDrawable.Orientation.BR_TL,new int[] {Color.parseColor(_color1),Color.parseColor(_color2),});
		ab.setCornerRadii(new float[]{(int)_lt,(int)_lt,(int)_rt,(int)_rt,(int)_rb,(int)_rb,(int)_lb,(int)_lb});
		ab.setStroke((int) _stro, Color.parseColor(_strCol));
		_view.setElevation((int) _shadow);
		_view.setBackground(ab);
	}
	
	
	public void _tombol(final View _view, final String _warna, final String _ripple) {
		android.graphics.drawable.GradientDrawable ab = new android.graphics.drawable.GradientDrawable(GradientDrawable.Orientation.BR_TL,new int[] {Color.parseColor(_warna),Color.parseColor(_warna),});
		
		ab.setCornerRadii(new float[]{(int)200,(int)200,(int)200,(int)200,(int)200,(int)200,(int)200,(int)200});
		
		android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(_ripple)}), ab, null); _view.setBackground(ripdr);
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
