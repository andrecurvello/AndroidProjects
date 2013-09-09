package es.meco4.androidgames;

import java.io.IOException;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class SoundPoolTest extends Activity implements OnTouchListener {
	
	SoundPool soundPool;
	int explosionId = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		textView.setOnTouchListener(this);
		setContentView(textView);
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
		
		try {
			AssetManager assetManager = getAssets();
			AssetFileDescriptor descriptor = assetManager.openFd("explosion.ogg");
			explosionId = soundPool.load(descriptor, 1);
		}
		catch(IOException e) {
			textView.setText("No se pudo cargar el efecto de sonido del asset, "
							 + e.getMessage());
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (explosionId != -1) {
				soundPool.play(explosionId, 1, 1, 0, 0, 1);
			}
		}
		return true;
	}
}
