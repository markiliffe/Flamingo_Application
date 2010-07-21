package com.android.flamingo;

/**
 * This class has the function of saving reports while the CSV writer will iterate through them
 * Used by the CSV writer class and information propagated by the ReportTab. Getter and setter methods are 
 * used throughout due to code cleanliness, therefore all variables are private.
 * 
 * @author mark iliffe
 *
 */

public class ReportInstance {

	private double latitude;
	private double longitude;
	private long time;
	private String lake;
	private int lowerEstimate;
	private int agreedEstimate;
	private int higherEstimate;
	private double xAxis;
	private double yAxis;
	private double zAxis;
	private double altitude;
	private float accuracy;

	/**
	 * Default Constructor, as all of these items are necessary there is no 'stripped down' version. As it is the constructor
	 * I have felt that there is no overiding reason why the setter methods should be used here.
	 * 
	 * @param _idNumber
	 * @param _latitude
	 * @param _longitude
	 * @param _time
	 * @param _lake
	 * @param _lowerEstimate
	 * @param _higherEstimate
	 * @param _agreedEstimate
	 * @param _xAxis
	 * @param _yAxis
	 * @param _zAxis
	 * @param _altitude
	 * @param _accuracy
	 */
	
	public ReportInstance(double latitude, double _longitude, long _time, String _lake, int _lowerEstimate, int _higherEstimate, int _agreedEstimate, double _xAxis, double _yAxis, double _zAxis, double _altitude, float _accuracy){
		this.latitude = latitude;
		longitude = _longitude;
		time = _time;
		lake = _lake;
		lowerEstimate = _lowerEstimate;
		higherEstimate = _higherEstimate;
		agreedEstimate = _agreedEstimate;
		xAxis = _xAxis;
		yAxis = _yAxis;
		zAxis = _zAxis;
		altitude = _altitude;
		accuracy = _accuracy;
	}
	
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}
	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}
	/**
	 * @param lake the lake to set
	 */
	public void setLake(String lake) {
		this.lake = lake;
	}
	/**
	 * @return the lake
	 */
	public String getLake() {
		return lake;
	}
	/**
	 * @param lowerEstimate the lowerEstimate to set
	 */
	public void setLowerEstimate(int lowerEstimate) {
		this.lowerEstimate = lowerEstimate;
	}
	/**
	 * @return the lowerEstimate
	 */
	public int getLowerEstimate() {
		return lowerEstimate;
	}
	/**
	 * @param agreedEstimate the agreedEstimate to set
	 */
	public void setAgreedEstimate(int agreedEstimate) {
		this.agreedEstimate = agreedEstimate;
	}
	/**
	 * @return the agreedEstimate
	 */
	public int getAgreedEstimate() {
		return agreedEstimate;
	}
	/**
	 * @param higherEstimate the higherEstimate to set
	 */
	public void setHigherEstimate(int higherEstimate) {
		this.higherEstimate = higherEstimate;
	}
	/**
	 * @return the higherEstimate
	 */
	public int getHigherEstimate() {
		return higherEstimate;
	}
	/**
	 * @param xAxis the xAxis to set
	 */
	public void setxAxis(double xAxis) {
		this.xAxis = xAxis;
	}
	/**
	 * @return the xAxis
	 */
	public double getxAxis() {
		return xAxis;
	}
	/**
	 * @param yAxis the yAxis to set
	 */
	public void setyAxis(double yAxis) {
		this.yAxis = yAxis;
	}
	/**
	 * @return the yAxis
	 */
	public double getyAxis() {
		return yAxis;
	}
	/**
	 * @param zAxis the zAxis to set
	 */
	public void setzAxis(double zAxis) {
		this.zAxis = zAxis;
	}
	/**
	 * @return the zAxis
	 */
	public double getzAxis() {
		return zAxis;
	}
	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	/**
	 * @return the altitude
	 */
	public double getAltitude() {
		return altitude;
	}
	/**
	 * @param accuracy the accuracy to set
	 */
	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}
	/**
	 * @return the accuracy
	 */
	public float getAccuracy() {
		return accuracy;
	}
}
