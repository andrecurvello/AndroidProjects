package es.meco4.androidgames;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class FontTest extends Activity {

	class RenderView extends View {
		Paint paint;
		Typeface font;
		Rect bounds = new Rect();
		
		public RenderView(Context context) {
			super(context);
			paint = new Paint();
			font = Typeface.createFromAsset(context.getAssets(), "calibri.ttf");
		}
		
		protected void onDraw(Canvas canvas) {
			paint.setColor(Color.YELLOW);
			paint.setTypeface(font);
			paint.setTextSize(28);
			paint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText("Esto es un texto!", canvas.getWidth() / 2, 100, 
					paint);
			String text = "Esto es otro texto o_O";
			paint.setColor(Color.WHITE);
			paint.setTextSize(18);
			paint.setTextAlign(Paint.Align.LEFT);
			paint.getTextBounds(text, 0, text.length(), bounds);
			canvas.drawText(text, canvas.getWidth() - bounds.width(), 140, 
					paint);
			invalidate();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new RenderView(this));
	}

}
