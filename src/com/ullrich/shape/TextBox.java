package com.ullrich.shape;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import com.ullrich.config.TextboxStyle;
import com.ullrich.helper.Texture;
import com.ullrich.helper.Vector2D;

public class TextBox{
	private Context context;
	private String text;
	private int screenDpi;
	private float widthForTexture;
	private float heightForTexture;
	//private int globalHeightBox;
	//private int globalWidthBox;
	
	//Rotation Point Calculating
	private float transparentWorldHeight;
	private float boxWorldHeight;
	private float boxWorldWidth;
	private Vector2D rotationPoint;

	//Style Attributes
	private int textSize;
	private int backgroundColor;
	private int borderColor;
	private int padding;
	private int borderSize;
	private int pictureHeight;
	private int pictureWidth;
	
	
	public TextBox(Context context, String text, TextboxStyle style){
		this.context = context;
		this.text = text;
		
		
		this.textSize = style.getTextSize();
		this.backgroundColor = style.getBackgroundColor();
		this.borderColor = style.getBorderColor();
		this.borderSize = style.getBorderSize();
		this.padding = style.getPadding();
		this.pictureHeight = style.getPictureHeight();
		this.pictureWidth = style.getPictureWidth();
		
		this.rotationPoint = new Vector2D();
		
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		screenDpi = metrics.densityDpi;
		
		//storeImage(generateTextBitmap());
		generateTextBitmap();
	}
	
	public Bitmap generateTextBitmap(){
		
		Paint textPaint = new Paint();
		int circleSize = dpToPixel(10);
		
		int height = dpToPixel(textSize);
		//int padding = 10;
		
		textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTypeface(Typeface.DEFAULT);
		textPaint.setTextSize(height);
		int width = (int) textPaint.measureText(text);		
		
		Paint textFieldFill = new Paint();
		textFieldFill.setFlags(Paint.ANTI_ALIAS_FLAG);
		textFieldFill.setColor(backgroundColor);
		
		Paint textFieldStroke = new Paint();
		textFieldStroke.setStyle(Paint.Style.FILL_AND_STROKE);
		textFieldStroke.setStrokeWidth(2);
		textFieldStroke.setFlags(Paint.ANTI_ALIAS_FLAG);
		textFieldStroke.setColor(borderColor);
		
		//float borderSize = textFieldStroke.getStrokeWidth();
		//int borderSize = 1;
		
		RectF textField = new RectF(borderSize, borderSize, width + circleSize, height + padding);
		RectF border = new RectF(0, 0, width + circleSize + borderSize, height + borderSize + padding);
		
		
		//int pictureHeight = 128;
		//int pictureWidth = 1024;
		float boxWidth = (width + circleSize + borderSize);
		float boxHeight = (height + borderSize + padding);
		
		//this.globalWidthBox = (int) boxWidth;
		//this.globalHeightBox = (int) boxHeight;
		
		this.widthForTexture = (pictureWidth / 100) * ((boxHeight / pictureHeight) / (pictureHeight / 100));
		this.heightForTexture = (float)(1 * (boxHeight / pictureHeight));

		//------------Rotation Point Calculating------------
		
		transparentWorldHeight = ((pictureHeight - boxHeight) * this.heightForTexture) / pictureHeight;
		boxWorldHeight = (boxHeight * this.heightForTexture) / pictureHeight;

		boxWorldWidth = (boxWidth * this.widthForTexture) / pictureWidth;
		
		rotationPoint.x = (boxWorldWidth / 2);
		rotationPoint.y = (transparentWorldHeight + (boxWorldHeight / 2));
		
		//------------END------------
		
		//--------------------------------DEBUG-----------------------------------------
		/*
		Log.d("RatioHeight", "" + this.heightForTexture);
		Log.d("RatioWidth", "" +  this.widthForTexture);
		Log.d("RatioPicture", "" + (float)(pictureWidth/pictureHeight));
		Log.d("TransparentHeight", "" + transparentWorldHeight);
		Log.d("BoxHeight", "" + boxWorldHeight);
		*/
		
		/* Formel
		 * BN = Breite New
		 * BO = Breite Original
		 * HM = Height Max
		 * HO = Height Original
		 * 
		 * BN = ( BO / 100 ) x ( HM / ( HO / 100 ) )
		 
		Log.d("Formel",""+ (pictureWidth / 100) * ((boxHeight / pictureHeight) / (pictureHeight / 100)));
		*/
		//--------------------------------DEBUG END-------------------------------------
		
		
		// Create an empty, mutable bitmap
		Bitmap bitmap = Bitmap.createBitmap(pictureWidth, pictureHeight, Bitmap.Config.ARGB_8888);
		
		// get a canvas to paint over the bitmap
		Canvas canvas = new Canvas(bitmap);
		bitmap.eraseColor(0);
		canvas.drawColor(Color.TRANSPARENT);	
		//canvas.drawColor(Color.BLUE);
		canvas.drawRoundRect(border, 8, 8, textFieldStroke);
		canvas.drawRoundRect(textField, 8, 8, textFieldFill);
		
		//INFO: ((textPaint.descent() + textPaint.ascent()) / 2) is the distance from the baseline to the center.
		int yPos = (int) (((height + padding) / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)); 
		canvas.drawText(text, (circleSize / 2), yPos, textPaint);
		return bitmap;
	}
	
	public void storeImage(Bitmap image) {
	    File pictureFile = getOutputMediaFile();
	    if (pictureFile == null) {
	        Log.d("Picture","Error creating media file, check storage permissions: ");// e.getMessage());
	        return;
	    } 
	    try {
	        FileOutputStream fos = new FileOutputStream(pictureFile);
	        image.compress(Bitmap.CompressFormat.PNG, 90, fos);
	        fos.close();
	    } catch (FileNotFoundException e) {
	        Log.d("Picute", "File not found: " + e.getMessage());
	    } catch (IOException e) {
	        Log.d("Picture", "Error accessing file: " + e.getMessage());
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
	        String mImageName="MI_"+ timeStamp +".png";
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);  
	    return mediaFile;
	} 
	
	private int dpToPixel(int dp) {
		return (int) (dp * (screenDpi / 160f) + 0.5);
	}
	
	public float getWidthForTexture(){
		return this.widthForTexture;
	}
	
	public float getHeightForTexture(){
		return this.heightForTexture;
	}
	
	public Vector2D getRotationPoint(){
		return this.rotationPoint;
	}

	public int getPictureHeight() {
		return pictureHeight;
	}

	public int getPictureWidth() {
		return pictureWidth;
	}

	public float getBoxWorldHeight() {
		return boxWorldHeight;
	}

	public float getBoxWorldWidth() {
		return boxWorldWidth;
	}

	public float getTransparentWorldHeight() {
		return transparentWorldHeight;
	}
	
	/*
	public int getGlobalHeightBox() {
		return globalHeightBox;
	}

	public int getGlobalWidthBox() {
		return globalWidthBox;
	}
	*/
}
