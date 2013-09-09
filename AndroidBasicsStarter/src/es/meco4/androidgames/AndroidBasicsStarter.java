package es.meco4.androidgames;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AndroidBasicsStarter extends ListActivity {

	String tests[] = {"LifeCycleTest", "SingleTouchTest", "MultiTouchTest", "KeyTest", 
	                  "AccelerometerTest", "AssetsTest", "ExternalStorageTest", 
	                  "SoundPoolTest", "MediaPlayerTest", "FullScreenTest", 
	                  "RenderViewTest", "ShapeTest", "BitmapTest",
	                  "FontTest", "SurfaceViewTest", "MoveTest"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, tests));
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position,
			long id) {
		super.onListItemClick(list, view, position, id);
		String testName = tests[position];
		try{
			Class clazz = Class.forName("es.meco4.androidgames." + testName);
			Intent intent = new Intent(this, clazz);
			startActivity(intent);
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(getBaseContext(),
					"La clase " + testName + " no existe", Toast.LENGTH_SHORT).show();
		}
	}
}
