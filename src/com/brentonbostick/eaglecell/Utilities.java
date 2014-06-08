package com.brentonbostick.eaglecell;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public class Utilities {
	
	public static String readRawResource(Context context, int id) {
		
		StringBuffer s = new StringBuffer();

		try {
			InputStream inputStream = context.getResources().openRawResource(id);
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

			String read = in.readLine();
			while (read != null) {
				s.append(read + "\n");
				read = in.readLine();
			}

			s.deleteCharAt(s.length() - 1);

		} catch (Exception e) {
			Log.d("ERROR-readingShader", "Could not read shader: " + e.getLocalizedMessage());
		}
		
		return s.toString();
	}
	
}
