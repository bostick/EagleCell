package com.brentonbostick.eaglecell;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity {// implements SurfaceHolder.Callback {
	
	private GLSurfaceView surfaceView;
	
	MyRenderer renderer;
	
//	static {
//        System.loadLibrary("eaglecell");
//    }
//	
//    public static native void nativeOnStart();
//    public static native void nativeOnResume();
//    public static native void nativeOnPause();
//    public static native void nativeOnStop();
//    public static native void nativeSetSurface(Surface surface);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_main);
//        SurfaceView surfaceView = (SurfaceView)findViewById(R.id.surfaceview);
//        surfaceView.getHolder().addCallback(this);
		
		surfaceView = new GLSurfaceView(this);
			
		surfaceView.setEGLContextClientVersion(2);
			
		renderer = new MyRenderer(this);
			
		surfaceView.setRenderer(renderer);

		setContentView(surfaceView);
	}
	
//	@Override
//    protected void onStart() {
//        super.onStart();
//        nativeOnStart();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        nativeOnResume();
//    }
//    
//    @Override
//    protected void onPause() {
//        super.onPause();
//        nativeOnPause();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        nativeOnStop();
//    }
//	
//	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//        nativeSetSurface(holder.getSurface());
//    }
//
//    public void surfaceCreated(SurfaceHolder holder) {
//    }
//
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        nativeSetSurface(null);
//    }
    
}
