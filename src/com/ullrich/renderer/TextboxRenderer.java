package com.ullrich.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.opengl.GLSurfaceView;                                        
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.Math;

import com.ullrich.config.TextboxStyle;
import com.ullrich.helper.Camera2D;
import com.ullrich.helper.Vector2D;
import com.ullrich.interaction.SingleTouchHandler;
import com.ullrich.shape.*;

public class TextboxRenderer extends AbstractRenderer
{
    private TexturedRectangle tmRectangle;
    private TextBox textbox;
    private TextboxStyle style;
    private int globalHeightBox;
    private int globalTransparentHeight;
    private int globalWidthBox;
    private String text;
    
    public TextboxRenderer(Context context, String text, TextboxStyle style) {
    	super(context, 0, 0, 0); 
        this.text = text;
        this.style = style;
        textbox = new TextBox(context, this.text, this.style);
        tmRectangle = new TexturedRectangle(textbox.getWidthForTexture(), textbox.getHeightForTexture());
	}
    
    public TextboxRenderer(Context context, String text, int globalX, int globalY, float angle, TextboxStyle style){
        super(context, globalX, globalY, angle); 
        this.text = text;
        this.style = style;
        textbox = new TextBox(context, this.text, this.style);
        tmRectangle = new TexturedRectangle(textbox.getWidthForTexture(), textbox.getHeightForTexture());
    }


	public void onDrawFrame(GL10 gl)                                         
    {	
    	gl.glPushMatrix();

    	gl.glTranslatef(worldX + textbox.getRotationPoint().x, worldY + textbox.getRotationPoint().y, 0f);
    	gl.glRotatef(angle * 1 % 360, 0, 0, 1);
    	//gl.glRotatef(angle, 0, 0, 1);
    	gl.glTranslatef(-worldX - textbox.getRotationPoint().x, -worldY - textbox.getRotationPoint().y, 0f);
    	
    	gl.glTranslatef(worldX, worldY, 0f);
    	
        tmRectangle.draw(gl);  

        gl.glPopMatrix();  
        //angle = angle + 0.5f;
    }

    public void onSurfaceChanged(GL10 gl, int width, int height)             
    {	    	
    	Camera2D camera = Camera2D.getInstance(width, height);
    	
    	this.globalHeightBox = camera.worldHeightToGlobalHeight(textbox.getBoxWorldHeight());
    	this.globalWidthBox = camera.worldWidthToGlobalWidth(textbox.getBoxWorldWidth());
    	this.globalTransparentHeight = camera.worldHeightToGlobalHeight(textbox.getTransparentWorldHeight());
    	
    	
    	Vector2D testVector = new Vector2D(globalX, globalY + globalTransparentHeight);
    	camera.pixelToWorld(testVector);
    	this.worldX = testVector.x;
    	this.worldY = testVector.y;
    	
    	
    	/*
    	 * Debug
    	Log.d("Width",""+ width);
    	Log.d("Height",""+ height);
    	Log.d("TexturedRectangleWorldCoordinate", "X: " + worldX + " Y: " + worldY);	
    	Log.d("TB HeightForTe", "" + textbox.getBoxWorldHeight() + " -> " + camera.worldHeightToGlobalHeight(textbox.getBoxWorldHeight()));
    	Log.d("TB WidthForTe", "" + textbox.getBoxWorldWidth() + " -> " +  camera.worldWidthToGlobalWidth(textbox.getBoxWorldWidth()));
    	Log.d("TB TransparentHeight", "" + textbox.getTransparentWorldHeight() + " -> " +  camera.worldHeightToGlobalHeight(textbox.getTransparentWorldHeight()));
    	*/
    	
    	/* Info just for my brain
    	 * Width: 720 Height: 1038 
    	 * width -> 2, height -> 0
    	 * width -> 2, 0 -> 2
    	 * 
    	 */
    }

    
    public void onSurfaceCreated(GL10 gl, EGLConfig config)                  
    {
        //tmRectangle.createTexture(gl, this.context, "bobargb8888.png");
        //tmRectangle.createTexture(gl, this.context, "test.png");    
    	tmRectangle.createTextureFromBitmap(gl, context, textbox.generateTextBitmap());
    }
    
    public void setText(String text){
        this.text = text;
        textbox = new TextBox(context, text, style);
        tmRectangle = new TexturedRectangle(textbox.getWidthForTexture(), textbox.getHeightForTexture());	
    }
    
    public void setStyle(TextboxStyle style){
        this.style = style;
        textbox = new TextBox(context, text, style);
        tmRectangle = new TexturedRectangle(textbox.getWidthForTexture(), textbox.getHeightForTexture());	
    }
    
    public int getGlobalHeightBox() {
		return globalHeightBox;
	}

	public int getGlobalWidthBox() {
		return globalWidthBox;
	}

	public int getGlobalTransparentHeight() {
		return globalTransparentHeight;
	}
}
