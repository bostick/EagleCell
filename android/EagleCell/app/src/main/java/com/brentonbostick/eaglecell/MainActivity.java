package com.brentonbostick.eaglecell;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private GLSurfaceView surfaceView;
	
	MyRenderer renderer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		surfaceView = new GLSurfaceView(this);
			
		surfaceView.setEGLContextClientVersion(2);
			
		renderer = new MyRenderer(this);
			
		surfaceView.setRenderer(renderer);

		setContentView(surfaceView);
	}
    
}
