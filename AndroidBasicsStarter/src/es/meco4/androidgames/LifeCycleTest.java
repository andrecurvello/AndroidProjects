package es.meco4.androidgames;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class LifeCycleTest extends Activity {

	StringBuilder builder = new StringBuilder();
	TextView textView;
	
	private void log(String text) {
		Log.d("LifeCycleTest", text);
		builder.append(text);
		builder.append("\n");
		textView.setText(builder.toString());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		textView = new TextView(this);
		textView.setText(builder.toString());
		setContentView(textView);
		log("creado");
	}

	@Override
	protected void onResume() {
		super.onResume();
		log("reiniciado");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		log("pausado");
		
		if (isFinishing()) {
			log("finalizado");
		}
	}

}
