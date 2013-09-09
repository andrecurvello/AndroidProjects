package es.meco.geo;

import java.util.Iterator;
import java.util.List;

import es.meco.geo.R;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PrincipalActivity extends Activity {

	private LocationManager locManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(Aplicacion.LOGGEO,"Inicio aplicación");
		setContentView(R.layout.activity_principal);
		inicioProviders();
		iniciarLocalizacion();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_principal, menu);
		return true;
	}

	private void inicioProviders() {
		//Vamos a crear el objeto de localización
		locManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		List<String> listaProviders = locManager.getAllProviders();
		
		Iterator<String> iter = listaProviders.iterator();
        for (int i = 0; i < listaProviders.size(); i++) { 
          Log.i(Aplicacion.LOGGEO, "Nombre proveedor: " + listaProviders.get(i));
		  LocationProvider provider = locManager.getProvider(listaProviders.get(i));
		  int precision = provider.getAccuracy();
		  boolean obtieneAltitud = provider.supportsAltitude();
		  int consumoRecursos = provider.getPowerRequirement();
          Log.i(Aplicacion.LOGGEO,"Precision: " + ((Integer)precision).toString());
          Log.i(Aplicacion.LOGGEO,"Altitud: " + obtieneAltitud);
          Log.i(Aplicacion.LOGGEO,"Recursos: " + ((Integer)consumoRecursos).toString());
		}
        
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			AlertDialog dialog = new AlertDialog.Builder(this).create();
			String mensaje = "No está activado el GPS. Actívelo antes de continuar.";
			dialog.setTitle("Geo");
			dialog.setMessage(mensaje);
			dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar",
			new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					  // GPS not enabled
					  Log.d(Aplicacion.LOGGEO, "Proveedor GPS no se encuentra habilitado");
					    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					    startActivity(intent);
					                // Consider the case when user does not enable GPS and come out. 
				}
			});		
			dialog.show();      
        }
        
	}

	private void iniciarLocalizacion() {

		//OBtenemos referencia al Location Manager
		locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		//Obtenemos la última posición conocida
		Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		//Mostramos la última posición conocida
		mostrarPosicion(loc);

		final Button btnActivar = (Button)findViewById(R.id.btnActivar);
		final Button btnDesactivar = (Button)findViewById(R.id.btnDesactivar);
		
		btnActivar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				final TextView lblEstado = (TextView)findViewById(R.id.lblEstado);

				final LocationListener locListener = new LocationListener() {
					
					public void onLocationChanged(Location location) {
						mostrarPosicion(location);
					}
					
					public void onProviderDisabled(String provider) {
						lblEstado.setText("Provider OFF");
					}
					
					public void onProviderEnabled(String provider) {
						lblEstado.setText("Provider ON");
					}
					
					public void onStatusChanged(String provider, int status, Bundle extras) {
						lblEstado.setText("Provider Status: " + status);
					}
				};
				locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locListener);	

				btnActivar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Log.d(Aplicacion.LOGGEO, "Activando muestreo...");
						locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locListener);	
						Log.d(Aplicacion.LOGGEO, "Muestreo activado...");
				    }    	
				});
				btnDesactivar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Log.d(Aplicacion.LOGGEO, "Desactivando muestreo...");
						locManager.removeUpdates(locListener);	
						Log.d(Aplicacion.LOGGEO, "Muestreo desactivado...");
				    }    	
				});
			}    	
		});
	}
	
	private void mostrarPosicion(Location loc) {
		
		final TextView lblEstado = (TextView)findViewById(R.id.lblEstado);
		final TextView lblAltitud = (TextView)findViewById(R.id.lblAltitud);
		final TextView lblLatitud = (TextView)findViewById(R.id.lblLatitud);
		final TextView lblLongitud = (TextView)findViewById(R.id.lblLongitud);
		final TextView lblPrecision = (TextView)findViewById(R.id.lblPrecision);
		
		if (loc != null) {
          lblLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
          lblLongitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
          lblAltitud.setText("Altitud: " + String.valueOf(loc.getAltitude()));
          lblPrecision.setText("Precisión: " + String.valueOf(loc.getAccuracy()));
          Log.d(Aplicacion.LOGGEO,"Posición: " + String.valueOf(loc.getLatitude()) + " / " +
        		  String.valueOf(loc.getLongitude()) + " / " + String.valueOf(loc.getAltitude()) + " / " +
        		  String.valueOf(loc.getAccuracy()));
		}
		else {
          lblLatitud.setText("Latitud: (sin datos)");
          lblLongitud.setText("Longitud: (sin datos)");
          lblAltitud.setText("Altitud: (sin datos)");
          lblPrecision.setText("Precisión: (sin datos)");
          Log.d(Aplicacion.LOGGEO,"Posición desconocida...");
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		    startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
