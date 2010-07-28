//package com.android.flamingo;
//
//import java.util.Vector;
//import android.app.ListActivity;
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//
//
///**
// * This class displays to the user the 
// * 
// * 
// */
//public class ReviewTab extends ListActivity {
//
//	private static ReportDatabase reportDatabase;
//
//	//private ReportDatabase reportDatabase;
//	//	ListView listView;
//
//	@SuppressWarnings("null")
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		//Open Database
//		ReviewTab.reportDatabase = ReportDatabase.open(this);
//		//Vector<ReportInstanceQuery> reportReview = reportDatabase.reportSelect();
//		String[] reports = null;
//		//for(int i=0;i<=reportReview.size();i++){
//			
//		//	ReportInstanceQuery tempReport = (ReportInstanceQuery) reportReview.elementAt(i);
//			String tempLake = tempReport.getLake();
//			//String tempAgreedEstimate = Integer.toString(tempReport.getAgreedEstimate());
//			//String time = Long.toString(tempReport.getTime());
//			reports[i] = tempLake;// + " " + tempAgreedEstimate + " " + time;
//		}
//		
//		setListAdapter(new ArrayAdapter<String>(this, R.layout.reviewlayout,reports));
//	}
//}