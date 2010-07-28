package com.android.flamingo;

//Import Packages
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.location.LocationManager; 
import android.content.Context; 
import android.widget.Button;

//import android.widget.FrameLayout;

import com.android.flamingo.R;

/**
 * This class acts as a launcher for the camera, and as soon as the camera takes a picture
 * snapshots the status of the GPS, Accelerometers and Time of the device, passing information 
 * up to the reports Vector situated HelloFlamingos.java file.
 */

public class ReportTab extends Activity{

	double latitude = 0.0;
	double longitude = 0.0;
	long time = 0;
	double accuracy = 0.0;
	double altitude = 0.0;
	double xAxis = 10.0;
	double yAxis = 10.0;
	double zAxis = 10.0;

	int lower;
	int upper;
	int agreed;
	EditText _lower; 
	EditText _upper;
	EditText _agreed;
	
	Spinner lakeSpinner; 
	Button save, photo, calibrate;

	String spinnerState;

	static ReportDatabase reportDatabase;

	//Names of the lake to populate the spinner
	private static final String[] lakeNames = {
		"Baringo", "Bogoria", "Elementaita", "Magadi", "Naivasha", "Nakuru", "Oloiden" , "Turkana"
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reportlayout);
		
		lakeSpinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lakeNames);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		lakeSpinner.setAdapter(adapter);

		Button photo = (Button)findViewById(R.id.photo);
		photo.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				//				Bundle bundle = new Bundle();
				//				float tempX = Float.parseFloat((String)bundle.get("xAxis"));
				//				Toast.makeText(ReportTab.this,"Photo " + Float.toString(tempX), Toast.LENGTH_LONG).show();
				Toast.makeText(ReportTab.this,"Photo Taken", Toast.LENGTH_LONG).show();			
				
				//Trig for the photo attributes
			}
		});

		//The save method
		Button save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				//get the lower estimate
				_lower = (EditText)findViewById(R.id.lower);
				lower = Integer.parseInt(_lower.getText().toString());
				//get the higher estimate
				_upper = (EditText)findViewById(R.id.upper);
				upper = Integer.parseInt(_upper.getText().toString());
				//get the agreed estimate
				_agreed = (EditText)findViewById(R.id.estimate);
				agreed = Integer.parseInt(_agreed.getText().toString());
				//Run the save method
				onSave();
				
			}
		});
	}
	
	/**
	 * This function when called snapshots the current state of the GPS and the accelerometers.  
	 * 
	 */

	public void trigonometry(){
		LocationManager location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		latitude = location.getLastKnownLocation("gps").getLatitude();
		longitude = location.getLastKnownLocation("gps").getLatitude();

		time = location.getLastKnownLocation("gps").getTime();

		//While it is not envisaged that accuracy of the GPS will play a great part in this iteration of the
		//the project, it would be assumed that further iterations would look for greater accuracy to within 
		//certain tolerance, even though just an arbitrary value.
		accuracy = location.getLastKnownLocation("gps").getAccuracy();

	}

	/**
	 * This function gets the item currently selected by the spinner. While it would be possible to geocode
	 * from the users GPS location for the rapid development and time scale it seems best to simply ask; 
	 * clearly this would be rectified in later iterations.
	 * 
	 * @return A lake area selected by the spinner
	 */

	public String getLakeSpinnerState(){		
		int spinnerLocation = lakeSpinner.getSelectedItemPosition();
		return lakeNames[spinnerLocation];
	}

	/**
	 * This method resets and clears all information from class, invoked after data has been saved.
	 * 
	 */

	private void clean(){
		latitude = 0.0;
		longitude = 0.0;
		time = 0;
		accuracy = 0;
		altitude = 0;
		xAxis = 0.0;
		yAxis = 0.0;
		zAxis = 0.0;

	}

	/**
	 * This method detects (informs, shirley?) whether the upload of the report into the database was successful or not
	 * by way of a toast,
	 * 
	 * 
	 * @param isSucessful
	 */

	public void successfulOrNot(boolean isSucessful, String message){

		if (isSucessful) {
			Toast.makeText(ReportTab.this,message, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(ReportTab.this,"ARRRRRRRRGH!!!", Toast.LENGTH_LONG).show();

		}
	}

	/**
	 * This is where the image detection algorithm would be launched from...
	 * 
	 * 
	 * 
	 * @return Census Count
	 */

	public int getAlgorithmCount(){
		int censusCount = 0;
		return censusCount;
	}

	public void onPhoto(){
	}


	/**
	 * The onSave() method writes to the database the resets the class variables by calling the clean() method.
	 * It would be nice 
	 * 
	 */

	public void onSave(){
		//Get the spinner value
		spinnerState = getLakeSpinnerState();

		//Open a connection to the database then insert this information into it.
		ReportTab.reportDatabase = ReportDatabase.open(this);
		//ReportTab.reportDatabase = ReportDatabase.open(this);
		//reportDatabase = ReportDatabase.open(this);
		//		float[] acceleromerValues = SettingsTab.getAccelerometer();
		//		xAxis = acceleromerValues[0];
		//		yAxis = acceleromerValues[1];
		//		zAxis = acceleromerValues[2];
		reportDatabase.insert("(" + latitude + ", " + longitude + ", " + time + ", '" + spinnerState + "', " + lower + ", " + upper + ", " + agreed + ", " + getAlgorithmCount() + ", " + xAxis + ", " + yAxis + ", " + zAxis + ", " + altitude + ", "+ accuracy + ", 'photo');");
		successfulOrNot(true,"Report Added");
		//reportDatabase.close();
		//Reset so all data taken in will be new
		clean();
	}
}