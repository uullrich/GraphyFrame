package com.ullrich.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import com.ullrich.renderer.WorldRenderer;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FPSCounter {
	//Average FPS
	private ArrayList<Integer> fps;
	private long startTimeAverage = System.nanoTime();
	private int averageFPS = 0;
	
	//Log
	private BufferedWriter f;
	private Context context;
	
	//Second counter
	long startTime = System.nanoTime();
	int frames = 0;
	
	//Reference for the world renderer
	private WorldRenderer world;
	
	public void logFrame(){
		frames++;
		if(System.nanoTime() - startTime >= 1000000000){
			Log.d("FPSCounter", "fps: " + frames);
			fps.add(frames);
			if(world != null){
				world.setFPSCounter("" + frames);
			}
			frames = 0;
			startTime = System.nanoTime();
		}
	
		
		BigInteger delta = new BigInteger("60000000000");
		BigInteger currentTime = new BigInteger(""+System.nanoTime());
		BigInteger startTime = new BigInteger(""+startTimeAverage);
		
		
		if(currentTime.subtract(startTime).compareTo(delta) == 1){
			int temp = 0;
			for(Integer i : fps){
				temp += i;
			}
			averageFPS = temp / fps.size();
			Log.d("AverageFPS", "AverageFPS: " + averageFPS + " NumberOfPOIs: " + world.getNumberOfPOI());
			store("NumberOfPOIs: " + world.getNumberOfPOI() + " AverageFPS: " + averageFPS);
			startTimeAverage = System.nanoTime();
			fps.clear();
		}
		
	}
	
	public FPSCounter(WorldRenderer world, Context context){
		this.world = world;
		this.fps = new ArrayList<Integer>();
		this.context = context;
	}
	
	public FPSCounter(Context context){
		this.fps = new ArrayList<Integer>();
		this.context = context;
	}

	public int getAverageFPS() {
		return averageFPS;
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
	        String mImageName="FPS_"+ timeStamp +".txt";
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);  
	    return mediaFile;
	} 
}
