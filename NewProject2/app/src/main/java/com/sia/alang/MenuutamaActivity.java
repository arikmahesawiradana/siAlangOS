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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuutamaActivity extends AppCompatActivity {
	
	private String titikJemput = "";
	private String titikTujuan = "";
	private String waktu = "";
	private String platNomor = "";
	private String dataQR = "";
	private HashMap<String, Object> data = new HashMap<>();
	private double datacost1 = 0;
	private double datacost2 = 0;
	private double harga = 0;
	private HashMap<String, Object> dataku = new HashMap<>();
	
	private ArrayList<String> titik = new ArrayList<>();
	private ArrayList<String> jadwal = new ArrayList<>();
	private ArrayList<String> plat = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private ImageView imageview1;
	private LinearLayout linear2;
	private TextView textview1;
	private LinearLayout linear3;
	private TextView textview2;
	private LinearLayout linear4;
	private TextView textview3;
	private LinearLayout linear5;
	private TextView textview4;
	private LinearLayout linear6;
	private Button button1;
	private Spinner spinner1;
	private Spinner spinner2;
	private Spinner spinner3;
	private Spinner spinner4;
	
	private SharedPreferences save;
	private Intent i = new Intent();
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	private ProgressDialog progDial;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.menuutama);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		imageview1 = findViewById(R.id.imageview1);
		linear2 = findViewById(R.id.linear2);
		textview1 = findViewById(R.id.textview1);
		linear3 = findViewById(R.id.linear3);
		textview2 = findViewById(R.id.textview2);
		linear4 = findViewById(R.id.linear4);
		textview3 = findViewById(R.id.textview3);
		linear5 = findViewById(R.id.linear5);
		textview4 = findViewById(R.id.textview4);
		linear6 = findViewById(R.id.linear6);
		button1 = findViewById(R.id.button1);
		spinner1 = findViewById(R.id.spinner1);
		spinner2 = findViewById(R.id.spinner2);
		spinner3 = findViewById(R.id.spinner3);
		spinner4 = findViewById(R.id.spinner4);
		save = getSharedPreferences("save", Activity.MODE_PRIVATE);
		net = new RequestNetwork(this);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				harga = Math.abs(datacost1 - datacost2) * 1000;
				if (titikJemput.equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Tolong pilih semua data");
				}
				else
				if (titikTujuan.equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Tolong pilih semua data");
				}
				else
				if (waktu.equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Tolong pilih semua data");
				}
				else
				if (platNomor.equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Tolong pilih semua data");
				}
				else {
					data = new HashMap<>();
					data.put("nama", dataku.get("nama").toString());
					data.put("titik", titikJemput);
					data.put("tujuan", titikTujuan);
					data.put("waktu", waktu);
					data.put("plat", platNomor);
					data.put("harga", (int)(harga));
					dataQR = new Gson().toJson(data);
					save.edit().putString("dataQR", dataQR).commit();
					net.setParams(data, RequestNetworkController.REQUEST_PARAM);
					net.startRequestNetwork(RequestNetworkController.POST, "https://www.sefeoofficial.com/ariklomba/AddPesan.php", "A", _net_request_listener);
					progDial = new ProgressDialog(MenuutamaActivity.this);
					progDial.setMessage("Memesan.....");
					progDial.setIndeterminate(true);
					progDial.setCancelable(false);
					progDial.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					progDial.show();
				}
			}
		});
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				titikJemput = titik.get((int)(_position));
				datacost1 = _position;
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				titikTujuan = titik.get((int)(_position));
				datacost2 = _position;
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				waktu = jadwal.get((int)(_position));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				platNomor = plat.get((int)(_position));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				if (_response.equals("SUKSES")) {
					progDial.dismiss();
					i.putExtra("nama", data.get("nama").toString());
					i.setAction(Intent.ACTION_VIEW);
					i.setClass(getApplicationContext(), QrgeneratorActivity.class);
					startActivity(i);
				}
				else {
					progDial.dismiss();
					textview4.setText(_response);
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				progDial.dismiss();
				SketchwareUtil.showMessage(getApplicationContext(), "Tidak ada koneksi internet");
			}
		};
	}
	
	private void initializeLogic() {
		linear2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)100, 0xFFBED65E));
		linear3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFFFFFFFF));
		linear4.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFFFFFFFF));
		linear5.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFFFFFFFF));
		linear6.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFFFFFFFF));
		titik.add("01 - Klojen");
		titik.add("02 - Blimbing");
		titik.add("03 - Kedungkandang");
		titik.add("04 - Lowokwaru");
		titik.add("05 - Sukun");
		jadwal.add("08.00 - 09.00");
		jadwal.add("12.00 - 13.00");
		jadwal.add("15.00 - 16.00");
		jadwal.add("18.00 - 19.00");
		jadwal.add("20.00 - 21.00");
		plat.add("N 8373 LB");
		plat.add("N 6282 LB");
		plat.add("N 1539 LA");
		plat.add("N 5792 LT");
		spinner1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, titik));
		((ArrayAdapter)spinner1.getAdapter()).notifyDataSetChanged();
		spinner2.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, titik));
		((ArrayAdapter)spinner2.getAdapter()).notifyDataSetChanged();
		spinner3.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, jadwal));
		((ArrayAdapter)spinner3.getAdapter()).notifyDataSetChanged();
		spinner4.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, plat));
		((ArrayAdapter)spinner4.getAdapter()).notifyDataSetChanged();
		_tombol(button1, "#FFFFFFFF", "#FFBED65E");
		dataku = new HashMap<>();
		dataku = new Gson().fromJson(save.getString("data", ""), new TypeToken<HashMap<String, Object>>(){}.getType());
	}
	
	public void _tombol(final View _view, final String _warna, final String _ripple) {
		android.graphics.drawable.GradientDrawable ab = new android.graphics.drawable.GradientDrawable(GradientDrawable.Orientation.BR_TL,new int[] {Color.parseColor(_warna),Color.parseColor(_warna),});
		ab.setCornerRadii(new float[]{(int)50,(int)50,(int)50,(int)50,(int)50,(int)50,(int)50,(int)50});
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
