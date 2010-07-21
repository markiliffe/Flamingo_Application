package com.android.flamingo;

import android.app.Activity;
import android.os.Bundle;


/**
 * This class displays to the user the 
 * 
 * 
 */
public class ReviewTab extends Activity {

	private ReportDatabase reportDatabase;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		this.reportDatabase = new ReportDatabase(this);
		createReportList(reportDatabase.reportSelect());
    }
    
    public void createReportList(String[][] totalReports){
    	
    }


}
