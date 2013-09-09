package es.meco4.androidgames;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.AssetManager;
import android.view.Menu;
import android.widget.TextView;

public class AssetsTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		setContentView(textView);
		
		AssetManager assetManager = getAssets();
		InputStream inputStream = null;
		try {
			inputStream = assetManager.open("texts/myawesometext.txt");
			String text = loadTextFile(inputStream);
			textView.setText(text);
		}
		catch (IOException e) {
			textView.setText("No se pudo abrir el fichero");
		}
		finally {
			if (inputStream != null)
				try {
					inputStream.close();
				}
				catch(IOException ee) {
					textView.setText("No se pudo abrir el fichero");
				}
		}
	}
	
	public String loadTextFile(InputStream inputStream) throws IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		byte[] bytes = new byte[4096];
		int len = 0;
		while ((len = inputStream.read(bytes)) > 0)
			byteStream.write(bytes, 0, len);
		return new String(byteStream.toByteArray(), "UTF8");
	}

}