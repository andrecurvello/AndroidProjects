package com.example.conversor;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

public class OptionsActivity extends Activity {

	boolean error = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		
		getPreferencias();
	}

	@Override
	protected void onPause () {
		super.onPause();
		SharedPreferences prefs = getSharedPreferences("ConversorPrefs", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		final EditText txtEquivalencia = (EditText)findViewById(R.id.txtEquivalencia);
		final CheckBox chkValor = (CheckBox)findViewById(R.id.chkValor);
		try {
			float equivalencia = Float.parseFloat(txtEquivalencia.getText().toString());
			editor.putFloat("equivalencia", equivalencia);
		}
		catch(Exception e) {
			error = true;
		}
        //ahora almacenamos el valor de valorCheck
        if (chkValor.isChecked()) {
            editor.putBoolean("valorCheck", true);
        }
        else {
        	editor.putBoolean("valorCheck", false);
        }
		editor.commit();

	}
	
	private void getPreferencias() {
		SharedPreferences prefs = getSharedPreferences("ConversorPrefs", Context.MODE_PRIVATE);
		boolean valorCheck = prefs.getBoolean("valorCheck", false);
		Double equivalencia = (double) prefs.getFloat("equivalencia", (float) 166.386);
		final EditText txtEquivalencia = (EditText)findViewById(R.id.txtEquivalencia);
		final CheckBox chkValor = (CheckBox)findViewById(R.id.chkValor);
		txtEquivalencia.setText(Double.toString(equivalencia));
		if (valorCheck == true) {
			chkValor.setChecked(true);
		}
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_options, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
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
			dialog.setMessage("Esta aplicación ha sido realizada por Oscar Fernández.\n" + 
			                  "Diciembre 2012.\n" + 
					          "Versión: " + version);
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
