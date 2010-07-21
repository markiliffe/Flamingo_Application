/**
 * 
 * 
 * 
 */

package com.android.flamingo;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.content.Intent;
import android.content.res.Resources;

public class HelloFlamingos extends TabActivity {
	
	/**
	 * The onCreate method acts as a main method (for java programmers) or a run method (for java applet programmers)
	 * As any ANDROID application is inherently visual this methods needs to instantiate other classes that will display
	 * to the user or show something to the user itself. Here the class instantiates and creates the tabbed system, also contains
	 * the critically important reports Vector, through which the 
	 * 
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Resources res = getResources();
		final TabHost tabHost = getTabHost();

		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("New Report", res.getDrawable(R.drawable.ic_menu_camera_grey))
				.setContent(new Intent(this, ReportTab.class)));

		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Reports", res.getDrawable(R.drawable.ic_menu_info_details_grey))
				.setContent(new Intent(this, ReviewTab.class)));

		// This tab sets the intent flag so that it is recreated each time
		// the tab is clicked.
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Map", res.getDrawable(R.drawable.ic_menu_mapmode_grey))
				.setContent(new Intent(this, MapTab.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
	
	}
}
