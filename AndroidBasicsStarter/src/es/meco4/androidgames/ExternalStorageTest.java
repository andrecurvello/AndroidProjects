package es.meco4.androidgames;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ExternalStorageTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		setContentView(textView);
		
		String state = Environment.getExternalStorageState();
		if (!state.equals(Environment.MEDIA_MOUNTED)) {
			textView.setText("No hay almacenamiento externo montado");
		}
		else {
			File externalDir = Environment.getExternalStorageDirectory();
			File textFile = new File(externalDir.getAbsolutePath() + File.separator + "text.txt");
			try {
				writeTextFile(textFile, "Esto es una prueba. Oscar");
				String text = readTextFile(textFile);
				textView.setText(text);
				if (!textFile.delete()) {
					textView.setText("No se pudo eliminar el fichero temporal");
				}
			}
			catch (IOException e) {
				textView.setText("Algo fue mal! " + e.getMessage());
			}
		}
	}

	private void writeTextFile(File file, String text) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(text);
		writer.close();
	}
	
	private String readTextFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder text = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			text.append(line);
			text.append("\n");
		}
		reader.close();
		return text.toString();
	}
}
