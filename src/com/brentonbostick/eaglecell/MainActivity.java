package com.brentonbostick.eaglecell;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	
	private GLSurfaceView mGLSurfaceView;
	
	Renderer renderer;
	
	static {
        System.loadLibrary("eaglecell");
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mGLSurfaceView = new GLSurfaceView(this);
		
		if (detectOpenGLES20()) {
			
			mGLSurfaceView.setEGLContextClientVersion(2);
			
			renderer = new Renderer(this);
			
			mGLSurfaceView.setRenderer(renderer);
			
//			mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
			
//			Thread rendererThread = new Thread() {
//				
//				@Override
//				public void run() {
//					
//					try {
//						
//						while (true) {
//							if (paused) {
//								break;
//							}
//							synchronized (renderer.obj) {
//								renderer.obj.wait();
//							}
//							
//							Log.d("request", "requestRender");
//							mGLSurfaceView.requestRender();
//						}
//						
//					} catch (InterruptedException e) {
//						
//					}
//					
//				}
//				
//			};
//			
//			rendererThread.start();
			
		} else {
			this.finish();
		}
		
		// set the content view
		setContentView(mGLSurfaceView);
	}
	
	boolean paused = false;
	
	@Override
	public void onPause() {
		super.onPause();
		paused = true;
	}
	
	private boolean detectOpenGLES20() {
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo info = am.getDeviceConfigurationInfo();
		Log.d("OpenGL Ver:", info.getGlEsVersion());
		return (info.reqGlEsVersion >= 0x20000);
	}
}
