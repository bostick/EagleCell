package com.brentonbostick.eaglecell;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Locale;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

class Renderer implements GLSurfaceView.Renderer {
	
	static final boolean DIAGNOSTICS = true;
	static final boolean RGBA = true;
	
	/*
	 * for Nexus 7:
	 * 
	 * using naive fragment shader, with if statement, able to have grid size of 720 at 60 fps
	 * after finding better evolve function with no if statement, able to have grid size of 853 at 60 fps
	 * 
	 * optimizing onDrawFrame gets it up to 881
	 * 
	 * even better evolve function: 976
	 * 
	 * 
	 * 
	 * for Galaxy Tab 2 7.0:
	 * can get 60 fps with grid size 481 
	 * 
	 * 
	 * for Droid RAZR M
	 * 1004
	 * 
	 * 
	 * Nexus 10
	 * 2560 x 1600
	 * 
	 * Galaxy Tab 4 8"
	 * 800 x 1172
	 */
//	static final int GRID_WIDTH = 800;
//	static final int GRID_HEIGHT = 1172;
	
	static final int FLOAT_SIZE_BYTES = 4;
	static final int STRIDE_BYTES = 6 * FLOAT_SIZE_BYTES;
	static final int POS_OFFSET = 0;
	static final int TEX_COORD_OFFSET = 4;
	
	Shader directShader;
	Shader evolveShader;

	int direct_uTexture;
	int direct_aPosition;
	int direct_aTextureCoord;
	int evolve_uTexture;
	int evolve_aPosition;
	int evolve_aTextureCoord;
	
	int array;
	int elementArray;
	
	int fbA;
	int textureA;
	ByteBuffer texBufferA;
	
	int fbB;
	int textureB;
	ByteBuffer texBufferB;

	Context mContext;
	
	public Renderer(Context context) {
		mContext = context;
	}
	
	int stage = 0;
	
	long time = System.currentTimeMillis();
	int frame = 0;
	
    public native void nativeDrawFrame(int fbB, int evolveShaderProgram, int textureA, int jdirectShaderProgram, int fbA, int textureB);
	
	@Override
	public void onDrawFrame(GL10 glUnused) {
		
//		nativeDrawFrame(fbB, evolveShader.program, textureA, directShader.program, fbA, textureB);
		
		if (stage == 0) {
			
			GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbB);
			
			GLES20.glUseProgram(evolveShader.program);
			
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureA);
			
	        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 3, GLES20.GL_UNSIGNED_BYTE, 0);
			
//			GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbA);
//			
//			GLES20.glUseProgram(evolveShader.program);
//			
//			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureB);
//			
//	        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 3, GLES20.GL_UNSIGNED_BYTE, 0);
//	        
//			GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbB);
//			
//			GLES20.glUseProgram(evolveShader.program);
//			
//			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureA);
//			
//	        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 3, GLES20.GL_UNSIGNED_BYTE, 0);
	        
			GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
			
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
			
			GLES20.glUseProgram(directShader.program);
	        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 3, GLES20.GL_UNSIGNED_BYTE, 0);
	        
			stage = 1;
		} else {
			
			GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbA);
			
			GLES20.glUseProgram(evolveShader.program);
			
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureB);
			
	        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 3, GLES20.GL_UNSIGNED_BYTE, 0);
			
//			GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbB);
//			
//			GLES20.glUseProgram(evolveShader.program);
//			
//			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureA);
//			
//	        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 3, GLES20.GL_UNSIGNED_BYTE, 0);
//	        
//			GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbA);
//			
//			GLES20.glUseProgram(evolveShader.program);
//			
//			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureB);
//			
//	        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 3, GLES20.GL_UNSIGNED_BYTE, 0);
	        
			GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
			
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
			
			GLES20.glUseProgram(directShader.program);
	        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 3, GLES20.GL_UNSIGNED_BYTE, 0);
			
			stage = 0;
		}
		
		try {
			Thread.sleep(000);
		} catch (InterruptedException e) {
			
		}
		
		frame++;
		
		long cur = System.currentTimeMillis();
		if (cur - time > 5000) {
			Log.d("fps", "fps: " + frame / ((cur - time) / 1000f));
			
			time = cur;
			frame = 0;
		}
		
		if (DIAGNOSTICS) checkGlError("onDrawFrame");
	}
	
	public void onSurfaceChanged(GL10 glUnused, int width, int height) {
		
		
		try {
			
//			String exts = GLES20.glGetString(GLES20.GL_EXTENSIONS).replace(' ', '\n');
			
			String vertexShaderString = Utilities.readRawResource(mContext, R.raw.vertex);
			String directFragmentShaderString;
			String evolveFragmentShaderString;
			if (RGBA) {
				directFragmentShaderString = Utilities.readRawResource(mContext, R.raw.direct_rgba_fragment);
				evolveFragmentShaderString = Utilities.readRawResource(mContext, R.raw.evolve_rgba_fragment);
			} else {
				directFragmentShaderString = Utilities.readRawResource(mContext, R.raw.direct_fragment);
				evolveFragmentShaderString = Utilities.readRawResource(mContext, R.raw.evolve_fragment);
			}
			
			String vertexShader = String.format(Locale.US, vertexShaderString, width, height);
			
			String evolveFragmentShader = String.format(Locale.US, evolveFragmentShaderString, width, height);
			
			String directFragmentShader = String.format(Locale.US, directFragmentShaderString, width, height);
			
			directShader = new Shader(vertexShader, directFragmentShader);
			evolveShader = new Shader(vertexShader, evolveFragmentShader);
			
			GLES20.glUseProgram(directShader.program);
			
			direct_uTexture = GLES20.glGetUniformLocation(directShader.program, "uTexture");
			direct_aPosition = GLES20.glGetAttribLocation(directShader.program, "aPosition");
			direct_aTextureCoord = GLES20.glGetAttribLocation(directShader.program, "aTextureCoord");
			
			GLES20.glUseProgram(evolveShader.program);
			
			evolve_uTexture = GLES20.glGetUniformLocation(evolveShader.program, "uTexture");
			evolve_aPosition = GLES20.glGetAttribLocation(evolveShader.program, "aPosition");
			evolve_aTextureCoord = GLES20.glGetAttribLocation(evolveShader.program, "aTextureCoord");
			
		} catch (Exception e) {
			Log.d("Shader Setup", e.getLocalizedMessage());
		}
        
		setupFBOsAndTextures(width, height);
		
		
		GLES20.glUseProgram(directShader.program);
		
		GLES20.glVertexAttribPointer(direct_aPosition, 4, GLES20.GL_FLOAT, false, STRIDE_BYTES, POS_OFFSET * FLOAT_SIZE_BYTES);
		GLES20.glEnableVertexAttribArray(direct_aPosition);
		
		GLES20.glVertexAttribPointer(direct_aTextureCoord, 2, GLES20.GL_FLOAT, false, STRIDE_BYTES, TEX_COORD_OFFSET * FLOAT_SIZE_BYTES);
		GLES20.glEnableVertexAttribArray(direct_aTextureCoord);
		
		GLES20.glUseProgram(evolveShader.program);
		
		GLES20.glVertexAttribPointer(evolve_aPosition, 4, GLES20.GL_FLOAT, false, STRIDE_BYTES, POS_OFFSET * FLOAT_SIZE_BYTES);
		GLES20.glEnableVertexAttribArray(evolve_aPosition);
		
		GLES20.glVertexAttribPointer(evolve_aTextureCoord, 2, GLES20.GL_FLOAT, false, STRIDE_BYTES, TEX_COORD_OFFSET * FLOAT_SIZE_BYTES);
		GLES20.glEnableVertexAttribArray(evolve_aTextureCoord);
		
		if (DIAGNOSTICS) checkGlError("onSurfaceCreated");
		
		
		
		GLES20.glViewport(0, 0, width, height);
	}
	
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		
	}
	
	private void setupFBOsAndTextures(int width, int height) {
		
		int[] out = new int[1];
		
		GLES20.glGenFramebuffers(1, out, 0);
		fbA = out[0];
		
		GLES20.glGenTextures(1, out, 0);
		textureA = out[0];
		
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureA);
		
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);

		texBufferA = ByteBuffer.allocateDirect(2 * width * height).order(ByteOrder.nativeOrder());
		
		Random rand = new Random();
		for (int i = 0; i < width * height / 2; i++) {
			int index = rand.nextInt(width * height);
			if (RGBA) {
				int b = rand.nextInt(2);
				int a = rand.nextInt(2);
				int r = rand.nextInt(2);
				int g = rand.nextInt(2);
				texBufferA.put(2*index, (byte)(0xf0*b | 0x0f*a));
				texBufferA.put(2*index+1, (byte)(0xf0*r | 0x0f*g));
			} else {
				/*
				 * only set R bits
				 */
				texBufferA.put(2*index+1, (byte)0xf0);
			}
		}
		
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_SHORT_4_4_4_4, texBufferA);
		
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbA);
		
		GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, textureA, 0);		
		
		if (DIAGNOSTICS) checkFramebuffer();
		
		
		GLES20.glGenFramebuffers(1, out, 0);
		fbB = out[0];
		
		GLES20.glGenTextures(1, out, 0);
		textureB = out[0];
		
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureB);
		
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		
		texBufferB = ByteBuffer.allocateDirect(2 * width * height).order(ByteOrder.nativeOrder());
		
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_SHORT_4_4_4_4, texBufferB);
		
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbB);
		
		GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, textureB, 0);
		
		if (DIAGNOSTICS) checkFramebuffer();
		
		
		float verts[] = 
			{ -1, -1, 0, 1,     0, 0,
			  -1,  3, 0, 1,     0, 2,
			   3, -1, 0, 1,     2, 0 };
		
		FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(verts.length * FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexBuffer.put(verts);
		vertexBuffer.position(0);
		
		GLES20.glGenBuffers(1, out, 0);
        array = out[0];
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, array);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, verts.length * FLOAT_SIZE_BYTES, vertexBuffer, GLES20.GL_STATIC_DRAW);
        
        byte indices[] = { 0, 1, 2 };
		
		ByteBuffer indexBuffer = ByteBuffer.allocateDirect(indices.length * 1).order(ByteOrder.nativeOrder());
		indexBuffer.put(indices);
		indexBuffer.position(0);
        
		GLES20.glGenBuffers(1, out, 0);
        elementArray = out[0];
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, elementArray);
        GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, indices.length * 1, indexBuffer, GLES20.GL_STATIC_DRAW);
        
        if (DIAGNOSTICS) checkGlError("setupFBOsAndTextures");
	}
	
	private void checkGlError(String op) {
		int errorCode;
		while ((errorCode = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			String error;
			switch(errorCode) {
			case GLES20.GL_INVALID_OPERATION:      error="INVALID_OPERATION";      break;
			case GLES20.GL_INVALID_ENUM:           error="INVALID_ENUM";           break;
			case GLES20.GL_INVALID_VALUE:          error="INVALID_VALUE";          break;
			case GLES20.GL_OUT_OF_MEMORY:          error="OUT_OF_MEMORY";          break;
			case GLES20.GL_INVALID_FRAMEBUFFER_OPERATION:  error="INVALID_FRAMEBUFFER_OPERATION";  break;
			default: error="Unknown error code";
			}
			Log.e("checkGlError",  "glError " + error);
			throw new RuntimeException("glError " + error);
        }
	}
	
	private void checkFramebuffer() {
		int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
		if (status != GLES20.GL_FRAMEBUFFER_COMPLETE) {
			String error;
			switch (status) {
			case GLES20.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
				error="INCOMPLETE ATTACHMENT";
				break;
			case GLES20.GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS:
				error="INCOMPLETE DIMENSIONS";
				break;
			case GLES20.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
				error="INCOMPLETE MISSING ATTACHMENT";
				break;
			case GLES20.GL_FRAMEBUFFER_UNSUPPORTED:
				error="FRAMEBUFFER UNSUPPORTED";
				break;
			default:
				error="";
				break;
			}
			Log.e("checkFramebuffer",  "glError " + error);
			throw new RuntimeException("check Framebuffer glError " + error);
		}
	}

}
