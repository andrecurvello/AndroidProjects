package es.meco4.androidgames.framework;

import es.meco4.androidgames.framework.Graphics.PixmapFormat;

public interface Pixmap {
	
	public int getWidth();
	
	public int getHeigth();
	
	public PixmapFormat getFormat();
	
	public void dispose();
	
}