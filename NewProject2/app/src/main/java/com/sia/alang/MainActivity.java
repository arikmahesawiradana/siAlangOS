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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
	
	private HashMap<String, Object> login = new HashMap<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private ImageView imageview1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private Button button1;
	private Button button2;
	private ImageView imageview2;
	private EditText edittext1;
	private ImageView imageview3;
	private EditText edittext2;
	
	private Intent i = new Intent();
	private SharedPreferences save;
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	private ProgressDialog progDial;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		imageview1 = findViewById(R.id.imageview1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		button1 = findViewById(R.id.button1);
		button2 = findViewById(R.id.button2);
		imageview2 = findViewById(R.id.imageview2);
		edittext1 = findViewById(R.id.edittext1);
		imageview3 = findViewById(R.id.imageview3);
		edittext2 = findViewById(R.id.edittext2);
		save = getSharedPreferences("save", Activity.MODE_PRIVATE);
		net = new RequestNetwork(this);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				/*
if (edittext2.getText().toString().equals("admin") && edittext1.getText().toString().equals("admin")) {
save.edit().putString("name", edittext2.getText().toString()).commit();
i.setAction(Intent.ACTION_VIEW);
i.setClass(getApplicationContext(), AkunActivity.class);
startActivity(i);
}
else {
SketchwareUtil.showMessage(getApplicationContext(), "kata sandi atau username anda salah");
}
*/
				login = new HashMap<>();
				login.put("nama", edittext1.getText().toString());
				login.put("passwordnya", edittext2.getText().toString());
				net.setParams(login, RequestNetworkController.REQUEST_PARAM);
				net.startRequestNetwork(RequestNetworkController.GET, "https://www.sefeoofficial.com/ariklomba/Login.php", "A", _net_request_listener);
				progDial = new ProgressDialog(MainActivity.this);
				progDial.setMessage("Mencoba masuk......");
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
				i.setClass(getApplicationContext(), SigninActivity.class);
				startActivity(i);
			}
		});
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				if (_response.contains("{")) {
					progDial.dismiss();
					save.edit().putString("data", _response).commit();
					i.setAction(Intent.ACTION_VIEW);
					i.setClass(getApplicationContext(), AkunActivity.class);
					startActivity(i);
				}
				else {
					progDial.dismiss();
					edittext1.setText(_response);
					SketchwareUtil.showMessage(getApplicationContext(), "GAGAL");
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				progDial.dismiss();
				finish();
			}
		};
	}
	
	private void initializeLogic() {
		linear2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFFBED65E));
		linear3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFFBED65E));
		_tombol(button1, "#FFBED65E", "#FFFFFFFF");
		_tombol(button2, "#FFBED65E", "#FFFFFFFF");
	}
	
	public void _tombol(final View _view, final String _warna, final String _ripple) {
		android.graphics.drawable.GradientDrawable ab = new android.graphics.drawable.GradientDrawable(GradientDrawable.Orientation.BR_TL,new int[] {Color.parseColor(_warna),Color.parseColor(_warna),});
		
		ab.setCornerRadii(new float[]{(int)30,(int)30,(int)30,(int)30,(int)30,(int)30,(int)30,(int)30});
		
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
