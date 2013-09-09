package es.meco4.androidgames.framework.impl;

import android.graphics.Bitmap;
import es.meco4.androidgames.framework.Pixmap;
import es.meco4.androidgames.framework.Graphics.PixmapFormat;

public class AndroidPixmap implements Pixmap {
	
	Bitmap bitmap;
	PixmapFormat format;
	
	public AndroidPixmap(Bitmap bitmap, PixmapFormat format) {
		this.bitmap = bitmap;
		this.format = format;
	}

	@Override
	public int getWidth() {
		return bitmap.getWidth();
	}

	@Override
	public int getHeigth() {
		return bitmap.getHeight();
	}

	@Override
	public PixmapFormat getFormat() {
		return format;
	}

	@Override
	public void dispose() {
		bitmap.recycle();

	}

}
