package com.android.flamingo;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FlamingoActivity extends Activity {
	
	//Included for the ANDROID logcat
	private static final String TAG = "FlamingoActivity";
	static int REQUEST_PHOTO = 1;
	static int REQUEST_GEODATA = 2;
	
	boolean rec_gps_valid = false;
	double rec_latitude = 0.0;
	double rec_longitude = 0.0;
	double rec_accuracy = 0.0;
	double rec_altitude = 0.0;
	long rec_fixtime = 0;
	
	String rec_jpegpath = null;

	//Names of the lake to populate the spinner
	private static final String[] lakeNames = {
		"Baringo", "Bogoria", "Elementaita", "Magadi", "Naivasha", "Nakuru", "Oloiden" , "Turkana"
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// We're going to strip it bare, you'll have more freedom than you can handle!  -- ELER 66
		setContentView(R.layout.reportlayout);
		
		Spinner lakeSpinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lakeNames);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		lakeSpinner.setAdapter(adapter);
		
		Button photo = (Button)findViewById(R.id.photo);
		photo.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//				Bundle bundle = new Bundle();
				//Bundle bundle = new Bundle();
				Intent intent = new Intent(FlamingoActivity.this, FlamingoCamera.class);
				startActivityForResult(intent, REQUEST_PHOTO);
			}
		});
		
		Button getgeodata = (Button)findViewById(R.id.getgeodata);
		getgeodata.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//				Bundle bundle = new Bundle();
				//Bundle bundle = new Bundle();
				Intent intent = new Intent(FlamingoActivity.this, GeoPointActivity.class);
				startActivityForResult(intent, REQUEST_GEODATA);
			}
		});
		
		Button save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				//get the lower estimate
				EditText et_lower = (EditText)findViewById(R.id.lower);
				Integer rec_lower = Integer.parseInt(et_lower.getText().toString());
				//get the higher estimate
				EditText et_upper = (EditText)findViewById(R.id.upper);
				Integer rec_upper = Integer.parseInt(et_upper.getText().toString());
				//get the agreed estimate
				EditText et_agreed = (EditText)findViewById(R.id.estimate);
				Integer rec_agreed = Integer.parseInt(et_agreed.getText().toString());

				//Get the spinner value
				Spinner lakeSpinner = (Spinner) findViewById(R.id.spinner);
				String spinnerState = lakeNames[lakeSpinner.getSelectedItemPosition()];

				
				// Insert record
				
				SQLiteDatabase db = ((FlamingoApplication) getApplication()).mOpenHelper.getWritableDatabase();
				
				SQLiteStatement st = db.compileStatement("INSERT INTO report (" +
						"latitude," +
						"longitude," +
						"time," +
						"lake," +
						"lower_estimate," +
						"higher_estimate," +
						"agreed_estimate," +
						"algorithm_count," +
//						"xaxis," +
//						"yaxis," +
//						"zaxis," +
						"altitude," +
						"accuracy," +
						"photo_identifier" +
						") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
				
				st.bindDouble(1, rec_latitude);
				st.bindDouble(2, rec_longitude);
				st.bindLong(3, rec_fixtime);
				st.bindString(4, spinnerState);
				st.bindLong(5, rec_lower);
				st.bindLong(6, rec_upper);
				st.bindLong(7, rec_agreed);
				st.bindLong(8, 0); // 0 for now (will be post-processed)
				st.bindDouble(9, rec_altitude);
				st.bindDouble(10, rec_accuracy);
				st.bindString(11, rec_jpegpath);
				
				long rowid = st.executeInsert();
				
				st.clearBindings();
				
				Toast.makeText(FlamingoActivity.this, "Report saved!", Toast.LENGTH_LONG).show();
				
				resetForm();
				
				db.close();
				
			}
		});

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((resultCode == Activity.RESULT_OK) && (data != null) && (requestCode == REQUEST_PHOTO)) {
			Bundle bundle = data.getExtras();
			if (bundle != null) {
				String jpegpath = bundle.getString("jpegpath");
				rec_jpegpath = jpegpath;
				
				Toast.makeText(FlamingoActivity.this,"Image file path stored", Toast.LENGTH_LONG).show();

			}
		} else if ((resultCode == Activity.RESULT_CANCELED) && (requestCode == REQUEST_PHOTO)) {
			Toast.makeText(FlamingoActivity.this,"Image Capture Cancelled!", Toast.LENGTH_LONG).show();
			
		} else if ((resultCode == Activity.RESULT_OK) && (data != null) && (requestCode == REQUEST_GEODATA)) {
			double[] returned_geodata = data.getDoubleArrayExtra("location-result");
			long returned_fixtime = data.getLongExtra("fix-time", 0L);
			if ((returned_geodata != null) && (returned_geodata.length == 4)) {
				// latitude, longitude, altitude, accuracy, time
				rec_gps_valid = true;
				rec_latitude = returned_geodata[0];
				rec_longitude = returned_geodata[1];
				rec_altitude = returned_geodata[2];
				rec_accuracy = returned_geodata[3];
				rec_fixtime = returned_fixtime;
				
				Toast.makeText(FlamingoActivity.this,"GPS data stored", Toast.LENGTH_LONG).show();
			}
		} else if ((resultCode == Activity.RESULT_CANCELED) && (requestCode == REQUEST_GEODATA)) {
			Toast.makeText(FlamingoActivity.this, "GPS Acquisition Cancelled!", Toast.LENGTH_LONG).show();
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.review:
			Intent intent = new Intent(FlamingoActivity.this, ReviewActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	protected void resetForm() {
		rec_gps_valid = false;
		rec_latitude = 0.0;
		rec_longitude = 0.0;
		rec_accuracy = 0.0;
		rec_altitude = 0.0;
		rec_fixtime = 0;
		
		rec_jpegpath = null;
		
		EditText et_lower = (EditText)findViewById(R.id.lower);
		EditText et_upper = (EditText)findViewById(R.id.upper);
		EditText et_agreed = (EditText)findViewById(R.id.estimate);
		
		et_lower.setText("");
		et_upper.setText("");
		et_agreed.setText("");
		// Don't reset lake
		

	}
}


