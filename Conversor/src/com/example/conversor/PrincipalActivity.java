package com.example.conversor;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PrincipalActivity extends Activity {

	private Conversor cnv;
	//Creamos los bontones y cajas de texto que vamos a utilizar

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);

		cnv = new Conversor();
		getPreferencias();	
		
		//Creamos referencias a los controles
		final EditText txtImporte = (EditText)findViewById(R.id.txtImporte);
		final Button btnConvertir = (Button)findViewById(R.id.btnConvertir);
		final TextView lblResultado = (TextView)findViewById(R.id.lblResultado);
		final EditText txtResultado = (EditText)findViewById(R.id.txtResultado);
		lblResultado.setText("");
		
		btnConvertir.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					//Lineas para ocultar el teclado virtual (Hide keyboard)
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(txtImporte.getWindowToken(), 0);
					try {
						float importe = Float.parseFloat(txtImporte.getText().toString());
				    	cnv.setImporte(importe);
				    	float res = cnv.getImporteConvertido();
				    	if (res==-1) {
				    		lblResultado.setText(cnv.getError());
				    		txtResultado.setText(cnv.getError());
				    	}
				    	else {
				    		lblResultado.setText(String.valueOf(res));
				    		txtResultado.setText(String.valueOf(res));
				    	}
					}
					catch(Exception e) {
						lblResultado.setText("Se ha producido un error al obtener el importe.");
					}
				}
				catch(Exception e) {
					lblResultado.setText("Se ha producido un error al ocultar el teclado.");
				}
		    }    	
		});

	}

	@Override
	protected void onResume() {
		getPreferencias();
		super.onResume();
	}
	
	private void getPreferencias() {
		SharedPreferences prefs = getSharedPreferences("ConversorPrefs", Context.MODE_PRIVATE);
		float equivalencia = prefs.getFloat("equivalencia", (float) 166.386);
		cnv.setEquivalencia(equivalencia);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_principal, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent intent = new Intent(PrincipalActivity.this, OptionsActivity.class);
			startActivity(intent);
			return true;
		case R.id.menu_about:
			AlertDialog dialog = new AlertDialog.Builder(this).create();
			String version;
			try {
				PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
				version = pInfo.versionName;
			}
			catch(Exception e) {
				version = "(desconocida)";
			}
			dialog.setMessage("Esta aplicaci�n ha sido realizada por Oscar Fern�ndez.\n" + 
			                  "Diciembre 2012.\n" + 
					          "Versi�n: " + version);
			dialog.setTitle("Conversor");
			dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Aceptar",
			new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
                      //no hacemos nada.
				}
			});		
			dialog.show();
		default:
			return super.onContextItemSelected(item);
		}
		
	}
	
}
