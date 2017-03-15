package com.ullrich.renderer;

import javax.microedition.khronos.egl.EGL10;                                 //1
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;                                         //2
import android.util.Log;
import android.content.Context;

import java.lang.Math;

import com.ullrich.config.CircleStyle;
import com.ullrich.graphyframe.R;
import com.ullrich.shape.*;

public class SquareRenderer implements GLSurfaceView.Renderer
{
    private boolean mTranslucentBackground;
   // private Circle mSquare;
    //private Circle mmSquare;
    private BorderedCircle circle;
    private float mTransY;
    private float mAngle;
    private Context context;
    
    public SquareRenderer(boolean useTranslucentBackground)
    {
        mTranslucentBackground = useTranslucentBackground;
      //  mSquare = new Circle(1f, 1f, 0f, 0f, 1f);                                              //3
        circle = new BorderedCircle(0.1f, 0.02f, CircleStyle.POI);
        //mmSquare = new Circle(1.1f, 1f, 1f, 1f, 1f);
    }

	public void onDrawFrame(GL10 gl)                                         //4
    {

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);     //5

        gl.glMatrixMode(GL10.GL_MODELVIEW);                                  //6
        gl.glLoadIdentity();                                                 //7
        gl.glTranslatef(0.0f,(float)Math.sin(mTransY), -3.0f);               //8

        //gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);                        //9
        //gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

     //   mSquare.draw(gl);                                                    //10
     //   mmSquare.draw(gl);
        circle.draw(gl);
        
        mTransY += .075f;
    }

    public void onSurfaceChanged(GL10 gl, int width, int height)             //11
    {
         gl.glViewport(0, 0, width, height);                                 //12

         float ratio = (float) width / height;
         gl.glMatrixMode(GL10.GL_PROJECTION);                                //13
         gl.glLoadIdentity();
         gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);                         //14
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config)                  //15
    {                              

    	//Schoene Farben auf kosten von Performace, spaeter deaktivieren
    	gl.glDisable(GL10.GL_DITHER);                                        

    	//Kosmetik
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,                       //17
                 GL10.GL_FASTEST);

         if (mTranslucentBackground)                                     //18
         {
             gl.glClearColor(0,0,0,0);                       
         }
             else
         {
             gl.glClearColor(1,1,1,1);
         }
         gl.glEnable(GL10.GL_CULL_FACE);                                     //Abgewandte Flaechen (hier Dreiecke) entfernen
         gl.glShadeModel(GL10.GL_SMOOTH);                                    //20
         gl.glEnable(GL10.GL_DEPTH_TEST);                                    //21
    }
}
