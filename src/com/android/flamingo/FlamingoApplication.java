package com.android.flamingo;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FlamingoApplication extends Application {
	
	private static final String DATABASE_NAME = "flamingo.db";
	private static final int DATABASE_VERSION = 2;
	public static final String TAG = "FlamingoApplication";
	
	// package-private
	DatabaseHelper mOpenHelper = null;
	
	static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.w(TAG, "Creating " + DATABASE_NAME + " with version " + DATABASE_VERSION);
			db.execSQL("CREATE TABLE report (" +
					"id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"latitude REAL," +
					"longitude REAL," +
					"time INTEGER," +
					"lake TEXT," +
					"lower_estimate INTEGER," +
					"higher_estimate INTEGER," +
					"agreed_estimate INTEGER," +
					"algorithm_count INTEGER," +
//					"xaxis REAL," +
//					"yaxis REAL," +
//					"zaxis REAL," +
					"altitude REAL," +
					"accuracy REAL," +
					"photo_identifier TEXT" +
			");");
		}
		
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Received unexpected database upgrade request, bailing out");
			assert false; // No upgrade functionality, schema has never changed
		}
		
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mOpenHelper = new DatabaseHelper(getApplicationContext());
		
	}
}
