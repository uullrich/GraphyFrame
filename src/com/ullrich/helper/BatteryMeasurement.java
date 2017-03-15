package com.ullrich.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import android.content.Context;
import android.database.CursorJoiner.Result;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class BatteryMeasurement {
	private BufferedWriter f;
	
	//Second counter
	private Context context;
	long startTime = System.nanoTime();
	private int totalTime = 0;
	int batteryDelta = 0;
	int lastStatus;
	private int maxDelta = 0;
	private boolean reachedMaxDelta;
	
	public BatteryMeasurement(Context context, int maxDelta){
		this.context = context;
		this.maxDelta = maxDelta;
		this.reachedMaxDelta = false;
	}
	
	public void logBattery(int lastStatus){
		
		if(!reachedMaxDelta){
			
			if(this.lastStatus != 0){
				this.batteryDelta += (this.lastStatus - lastStatus);
			}
			this.lastStatus = lastStatus;
			
			
			//Time Measurement
			BigInteger currentTime = new BigInteger("0");
			BigInteger startTime = new BigInteger("0");
			BigInteger result = new BigInteger("0");
			
	
			currentTime = new BigInteger(""+System.nanoTime());
			startTime = new BigInteger(""+this.startTime);
			result = currentTime.subtract(startTime).divide(new BigInteger("1000000000"));
			totalTime += result.intValue();
			this.startTime = System.nanoTime();
			
			if(batteryDelta >= maxDelta){
				Log.d("TotalTime", "" + totalTime);
				store("TotalTime: " + totalTime);
				reachedMaxDelta = true;
			}else{
				//Debug: 
				Log.d("Battery"," new lastStatus: " + lastStatus + " batteryDelta: " + batteryDelta + " deltatime: " + result.toString() + " elappsedTime: " + totalTime);
				store("Status: " + lastStatus + " batteryDelta: " + batteryDelta + " time: " + result.toString()  + " elappsedTime: " + totalTime + "\n");
			}
		}
	}
	
	
	public void store(String text) {
	    File textFile = getOutputMediaFile();
	    if (textFile == null) {
	        Log.d("Textfile","Error creating text file, check storage permissions: ");
	        return;
	    } 
	    
	    
	    try {		
			if(f==null){ 
				f = new BufferedWriter(new FileWriter(textFile, true));
			}
			
			f.append(text);
		    f.newLine();
			f.flush();
			//f.close();
	    } catch (FileNotFoundException e) {
	        Log.d("Textfile", "File not found: " + e.getMessage());
	    } catch (IOException e) {
	        Log.d("Textfile", "Error accessing file: " + e.getMessage());
	    }  
	}
	
	/** Create a File for saving an image or video */
	private File getOutputMediaFile(){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this. 
	    File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
	            + "/Android/data/"
	            + context.getPackageName()
	            + "/Files"); 

	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            return null;
	        }
	    } 
	    
	    // Create a media file name
	    String timeStamp = new java.text.SimpleDateFormat("ddMMyyyy_HHmm").format(new java.util.Date());
	    File mediaFile;
	        String mImageName="MI_"+ timeStamp +".txt";
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);  
	    return mediaFile;
	} 
}
