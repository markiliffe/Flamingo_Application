package com.android.flamingo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MapTab extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textview = new TextView(this);
        textview.setText("Actually this is the Review Tab, Eventually a map showing the progress of the censusing will take place, while" +
        		" strict spatial analysis will not be possible. Though with time it is assumed natural clustering will occur.");
        setContentView(textview);
	}
}
