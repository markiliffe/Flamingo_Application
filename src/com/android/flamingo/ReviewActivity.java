package com.android.flamingo;


import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ReviewActivity extends ListActivity {

	class ReviewViewBinder implements SimpleCursorAdapter.ViewBinder {
		
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			if (columnIndex == 1) {
				// Column index 1 is the time field
				TextView tview = (TextView) view;
				String convtime = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date(cursor.getLong(1)));
				tview.setText("GPS Time: " + convtime);
				return true;
			} else if (columnIndex == 2) {
				TextView tview = (TextView) view;
				tview.setText("Lake: " + cursor.getString(2));
				return true;
			} else if (columnIndex == 3) {
				TextView tview = (TextView) view;
				tview.setText("Lower: " + cursor.getLong(3));
				return true;
			} else if (columnIndex == 4) {
				TextView tview = (TextView) view;
				tview.setText("Upper: " + cursor.getLong(4));
				return true;
			} else if (columnIndex == 5) {
				TextView tview = (TextView) view;
				tview.setText("Agreed: " + cursor.getLong(5));
				return true;
			} else {
				return false;
			}
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Open Database
		SQLiteDatabase db = ((FlamingoApplication) getApplication()).mOpenHelper.getWritableDatabase();

		Cursor c = db.rawQuery("SELECT id AS _id, " +
				"time, " +
				"lake, " +
				"lower_estimate, " +
				"higher_estimate, " +
				"agreed_estimate, " +
				"algorithm_count " +
			"FROM report;", null);
		
		SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.review_example_listentry, c,
				new String[] {
					"time",
					"lake",
					"lower_estimate",
					"higher_estimate",
					"agreed_estimate"
				},
				new int[] {
					R.id.time_entry,
					R.id.lake_entry,
					R.id.lower_entry,
					R.id.higher_entry,
					R.id.agreed_entry
		});
		
		sca.setViewBinder(new ReviewViewBinder());
		setListAdapter(sca);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.back_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.report:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
