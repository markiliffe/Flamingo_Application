package com.android.flamingo;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReportDatabase extends SQLiteOpenHelper {

	private static Context context;
	private SQLiteDatabase database;

	static ReportDatabase reportDatabase;

	private static final String DATABASE_NAME = "flamingo_reports";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "reports";

	/**
	 * Default (and only) constructor....
	 * 
	 * @param context
	 */

	public ReportDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		ReportDatabase.context = context;
		OpenHelper openHelper = new OpenHelper(ReportDatabase.context);
		this.database = openHelper.getWritableDatabase();
	}

	static ReportDatabase open(Context c){
		if(reportDatabase == null){
			reportDatabase = new ReportDatabase(ReportDatabase.context);
			return reportDatabase;
		}
		return reportDatabase;
	}

	/**
	 * 
	 * @param name
	 */

	public void insert(String name){
		this.database.execSQL("INSERT INTO " + TABLE_NAME + "(latitude, longitude, time, lake, lower_estimate, higher_estimate, agreed_estimate, algorithm_count, xaxis, yaxis, zaxis, altitude, accuracy, photo_identifier) VALUES " + name);
	}

	/**
	 * This method returns a double array and probably shouldn't be this hacky...
	 * 
	 * 
	 * @return
	 */

	public Vector<ReportInstanceQuery> reportSelect(){
		Vector<ReportInstanceQuery> tempReports = new Vector<ReportInstanceQuery>();

		Cursor c = database.rawQuery("SELECT id,time,lake,lower_estimate,higher_estimate,agreed_estimate,algorithm_count FROM" + TABLE_NAME + ";",null);
		int indexTime = c.getColumnIndex("time");
		int indexLake = c.getColumnIndex("lake");
		int indexLowerEstimate = c.getColumnIndex("lower_estimate");
		int indexHigherEstimate = c.getColumnIndex("higher_estimate");
		int indexAgreedEstimate = c.getColumnIndex("agreed_estimate");
		int indexAlgorithmCount = c.getColumnIndex("algorithm_count");


		if (c != null){
			int i = 0;
			do {
				i++;
				int columnTime = c.getInt(indexTime);
				String columnLake = c.getString(indexLake);
				int columnLowerEstimate = c.getInt(indexLowerEstimate);
				int columnHigherEstimate = c.getInt(indexHigherEstimate);
				int columnAgreedEstimate = c.getInt(indexAgreedEstimate);
				int columnAlgorithmCount = c.getInt(indexAlgorithmCount);

				tempReports.add(new ReportInstanceQuery(columnTime, columnLake, columnLowerEstimate, columnHigherEstimate, columnAgreedEstimate, columnAlgorithmCount));
			} while (c.moveToNext());
		}
		reportDatabase.close();
		return tempReports;
	}

	/**
	 * This method connects to the database  
	 * 
	 */

	public void CSVReportSelect(){

	}

	/**
	 * The nuke method for the database, used extensively in testing, used in when 'resetting' the application
	 * It should probably invoke the CSVWRITE commands as a form of backup before deleting the database...
	 */

	public void delete(){
		this.database.delete(TABLE_NAME, null, null);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {        
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {		
	}


	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, latitude REAL, longitude REAL, time INTEGER, lake TEXT, lower_estimate INTEGER, higher_estimate INTEGER, agreed_estimate INTEGER, algorithm_count INTEGER, xaxis REAL, yaxis REAL, zaxis REAL, altitude REAL, accuracy REAL, photo_identifier TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Example", "Upgrading database, this will drop tables and recreate.");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}

}