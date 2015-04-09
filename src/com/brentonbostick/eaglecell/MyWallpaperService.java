package com.brentonbostick.eaglecell;

import net.rbgrn.android.glwallpaperservice.OpenGLES2WallpaperService;

//Original code provided by Robert Green
//http://www.rbgrn.net/content/354-glsurfaceview-adapted-3d-live-wallpapers
public class MyWallpaperService extends OpenGLES2WallpaperService {
	@Override
    public Renderer getNewRenderer() {
        return new MyRenderer(this);
    }
}
