package es.meco4.androidgames;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class SurfaceViewTest extends Activity {

	FastRenderView renderView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		renderView = new FastRenderView(this);
		setContentView(renderView);
	}
	@Override
	protected void onResume() {
		super.onResume();
		renderView.resume();
	}
	@Override
	protected void onPause() {
		super.onPause();
		renderView.pause();
	}
	
	class FastRenderView extends SurfaceView implements Runnable {
		Thread renderThread = null;
		SurfaceHolder holder;
		volatile boolean running = false;
		
		public FastRenderView(Context context) {
			super(context);
			holder = getHolder();
		}
		public void resume() {
			running = true;
			renderThread = new Thread(this);
			renderThread.start();
		}
		@Override
		public void run() {
			while(running) {
				if(!holder.getSurface().isValid())
					continue;
				
				Canvas canvas = holder.lockCanvas();
				canvas.drawRGB(255, 0, 0);
				holder.unlockCanvasAndPost(canvas);
			}
		}
		public void pause() {
			running = false;
			while(true) {
				try {
					renderThread.join();
				}
				catch(InterruptedException e) {
					//retry
				}
			}
		}
	}

}