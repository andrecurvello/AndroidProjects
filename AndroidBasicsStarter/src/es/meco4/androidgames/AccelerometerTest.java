package es.meco4.androidgames;

import android.app.*;
import android.content.Context;
import android.hardware.*;
import android.os.Bundle;
import android.widget.*;

public class AccelerometerTest extends Activity implements SensorEventListener {

	StringBuilder builder = new StringBuilder();
	TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		textView = new TextView(this);
		setContentView(textView);
		
		SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() == 0) {
			textView.setText("No hay aceler�metro instalado");
		}
		else {
			Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			if (!manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)) {
				textView.setText("No se pudo registrar el listener del sensor");
			}
		}
	}

	@Override 
    public void onSensorChanged(SensorEvent event) { 
        builder.setLength(0); 
        builder.append("x: "); 
        builder.append(event.values[0] + "\n"); 
        builder.append("y: "); 
        builder.append(event.values[1] + "\n"); 
        builder.append("z: "); 
        builder.append(event.values[2]); 
        textView.setText(builder.toString()); 
    } 

    @Override 
    public void onAccuracyChanged(Sensor sensor, int accuracy) { 
        // nothing to do here 
    } 

}
