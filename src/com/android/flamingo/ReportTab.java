package com.android.flamingo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.location.LocationManager; 
import android.content.Context; 
import android.widget.Button;

import com.android.flamingo.R;

/**
 * This class acts as a launcher for the camera, and as soon as the camera takes a picture
 * snapshots the status of the GPS, Accelerometers and Time of the device, passing information 
 * upto the reports Vector situated HelloFlamingos.java file.
 */

public class ReportTab extends Activity {

	double latitude = 0.0;
	double longitude = 0.0;
	long time = 0;
	double accuracy = 0.0;
	double altitude = 0.0;
	double xAxis = 0.0;
	double yAxis = 0.0;
	double zAxis = 0.0;

	Spinner s1;
	Button save;

	String spinnerState;
	
	private ReportDatabase reportDatabase;
	
	//Names of the lake to populate the spinner
	private static final String[] lakeNames = {
		"Baringo", "Bogoria", "Elementaita", "Magadi", "Naivasha", "Nakuru", "Oloiden" , "Turkana"
	};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reportlayout);
		
		s1 = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lakeNames);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s1.setAdapter(adapter);

		@SuppressWarnings("unused")
		Button save = (Button) findViewById(R.id.save);
		spinnerState = getLakeSpinnerState();
		onSave();
		
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
		//certain tolerance, even though just an arbitary value.
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
		int spinnerLocation = s1.getSelectedItemPosition();
		return lakeNames[spinnerLocation];
	}

	/**
	 * This is the get method that captures what the user/group of users has decided is the lower estimate is.
	 * 
	 * @return
	 */
	public int getLowerEstimate(){
		// CAPTURE THE VALUE OF THE LOWER ESTIMATE

		int lowerEstimate = 0;

		return lowerEstimate;
	}

	public int getHigherEstimate(){
		// CAPTURE THE VALUE OF THE HIGHER ESTIMATE

		int higherEstimate = 0;
		return higherEstimate;
	}

	public int getAgreedEstimate(){
		// CAPTURE THE VALUE OF THE AGREED ESTIMATE

		int agreedEstimate = 0;

		return agreedEstimate;
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
	 * This method detects whether the upload of the report into the database was successful or not
	 * by way of a toast,
	 * 
	 * 
	 * @param isSucessful
	 */
	
	public void successfulOrNot(boolean isSucessful){
		
		if (isSucessful) {
			Toast.makeText(ReportTab.this,"Report Sucessfully Added!", Toast.LENGTH_LONG).show();
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

	/**
	 * The onSave() method writes to the database the resets the class variables by calling the clean() method.
	 * It would be nice 
	 * 
	 */

	public void onSave(){
		
		//Open a connection to the database then insert this information into it.
		this.reportDatabase = new ReportDatabase(this);
		this.reportDatabase.insert("(" + latitude + ", " + longitude + ", " + time + ", '" + getLakeSpinnerState() + "', " + getLowerEstimate() + ", " + getHigherEstimate() + ", " + getAgreedEstimate() + ", " + getAlgorithmCount() + ", " + xAxis + ", " + yAxis + ", " + zAxis + ", " + altitude + ", "+ accuracy + ", 'photo');");
		
		/*Currently there is no way to make this false. It is included if I get time to fix it. It exists to give a user feedback that their report was
		successfully added - especially to those unfamiliar with the platform - is very important.*/
		successfulOrNot(true);
		
		//Reset so all data taken in will be new
		clean();
	}
}