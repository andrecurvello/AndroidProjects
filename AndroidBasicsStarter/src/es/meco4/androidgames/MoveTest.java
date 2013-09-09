package es.meco4.androidgames;

import es.meco4.androidgames.ShapeTest.RenderView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

/**
 * <p>Esta clase muestra varias figuras en pantalla.</p>
 * <ul>
 * <li>Una línea diagonal que permanece inmutable en toda la vida de la actividad.</li>
 * <li>Un cuadrado relleno que se puede mover con el dedo.</li>
 * <li>Un círculo relleno que se puede mover con el dedo.<<li>
 * </ul>
 * <p>Implementa la interfaz OnTouchListener para recoger los eventos que se puedan
 * producir de toques de pantalla. De esta manera recogemos las coordenadas correspondientes
 * al toque realizado y posicionamos la figura que esté más cerca del toque.</p>
 * @author Oscar
 *
 */
public class MoveTest extends Activity implements OnTouchListener {

	/** Este objeto es el que recoge la visualización de las formas pintadas en pantalla */
	RenderView renderView;
	/** Coordenada X de posicionamiento del círculo */
	float x = 100;
	/** Coordenada Y de posicionamiento del círculo */
	float y = 100;
	/** Coordenada X de posicionamiento del cuadrado */
	float xc = 100;
	/** Coordenada Y de posicionamiento del cuadrado */
	float yc = 100;
	/** Enumeración de las figuras móviles de la pantalla */
	enum obj_movement {
		/** Figura que corresponde con el círculo */
		circle,
		/** Figura que corresponde con el cuadrado */
		rect,
		/** No existe ninguna figura que esté en movimiento */
		nothing
	}

	/** Estado en el que se encuentra el toque. Inicialmente cancelado. */
	int status = MotionEvent.ACTION_CANCEL;
	/** Figura que se está moviendo en este momento */
	obj_movement moving = obj_movement.nothing; 

	/**
	 * Clase anidada que representa el contenedor de la pantalla donde se dibujarán
	 * todas las figuras
	 * @author Oscar
	 *
	 */
	class RenderView extends View {
		/** Objeto que establece la forma en que se representa una figura */
		Paint paint;

		/**
		 * Constructor de la clase. Inicializa el objeto paint.
		 * @param context contexto de la vista
		 */
		public RenderView(Context context) {
			super(context);
			paint = new Paint();
		}
		
		/**
		 * Método sobreescrito que se ejecuta cada vez que se repinta la pantalla
		 * @param canvas contenedor de los dibujos
		 */
		protected void onDraw(Canvas canvas) {
			
			canvas.drawRGB(255, 255, 255);
			paint.setColor(Color.RED);
			canvas.drawLine(0, 0, canvas.getWidth()-1, canvas.getHeight()-1, paint);

			if (moving == obj_movement.circle) {
				paint.setStyle(Style.STROKE);
			}
			else {
				paint.setStyle(Style.FILL);
			}
			paint.setColor(0xff00ff00);
			canvas.drawCircle(x, y, 40, paint);
			
			if (moving == obj_movement.rect) {
				paint.setStyle(Style.STROKE);
			}
			else {
				paint.setStyle(Style.FILL);
			}
			paint.setColor(0x770000ff);
			canvas.drawRect(xc-50, yc-50, xc+50, yc+50, paint);
			invalidate();
		}
	}
	
	/**
	 * Evento sobreescrito que inicia la actividad.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		renderView = new RenderView(this);
		renderView.setOnTouchListener(this);
		setContentView(renderView);
	}

	/**
	 * Evento de interacción con la pantalla, ya sea tocarla, mover el dedo
	 * o levantar el dedo. Si se levanta el dedo, entonces se establece que no
	 * hay ningún objeto en movimiento.
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			moving = obj_movement.nothing;
			break;
		}
		status = event.getAction();
		float xp = event.getX();
		float yp = event.getY();
		moverFigura(xp, yp);
		return true;
	}

	/**
	 * <p>Método que establece qué figura es la que se tiene que mover. Recibe las coordenadas
	 * que le informan dónde se encuentra el puntero del usuario.</p>
	 * <p>El funcionamiento es muy simple:</p>
	 * <ul>
	 * <li>Si el círculo se encuentra en movimiento, las nuevas coordenadas son del círculo.</li>
	 * <li>Si el cuadrado se encuentra en movimiento, las nuevas coordenadas son del cuadrado.</li>
	 * <li>Si no sucede nada de lo anterior, miramos si estamos cerca del círculo. Si es así, el
	 * círculo se mueve a las coordenadas indicadas. Se establece entonces que el círculo se
	 * encuentra en movimiento.</li>
	 * <li>Si no sucede nada de lo anterior, miramos si estamos cerca del cuadrado. Si es así, el
	 * cuadrado se mueve a las coordenadas indicadas. Se establece entonces que el cuadrado se
	 * encuentra en movimiento.</li>
	 * <li>Si no sucede nada de lo anterior, entonces se establece que no hay ningún objeto en
	 * movimiento.</li>
	 * </ul> 
	 * @param xp coordenada x donde apunta el usuario
	 * @param yp coordenada y donde apunta el usuario
	 */
	private void moverFigura(float xp, float yp) {
		if (moving == obj_movement.circle) {
			x = xp;
			y = yp;
		}
		else if (moving == obj_movement.rect) {
			xc = xp;
			yc = yp;
		}
		else {
			if (isCercaCirculo(xp, yp)) {
				x = xp;
				y = yp;
				if (status == MotionEvent.ACTION_DOWN) {
					moving = obj_movement.circle;
				}
			}
			else if (isCercaCuadrado(xp, yp)) {
				xc = xp;
				yc = yp;
				if (status == MotionEvent.ACTION_DOWN) {
					moving = obj_movement.rect;
				}
			}
			else {
				moving = obj_movement.nothing;
			}
		}
	}
	
	/**
	 * Función que comprueba si las coordenadas que ha tocado el usuario se encuentran dentro del
	 * radio de acción del cuadrado. Hay que notar que las coordenadas se miran a partir del centro
	 * justo del cuadrado.
	 * @param xp coordenada x donde apunta el usuario
	 * @param yp coordenada y donde apunta el usuario
	 * @return devuelve <code>true</code> en caso de que el toque se encuentre dentro del radio de acción
	 * del cuadrado y <code>false</code> en caso contrario 
	 */
	private boolean isCercaCuadrado(float xp, float yp) {
		boolean res = false;
		if (xp > xc -50 && xp < xc + 50 &&
				yp > yc - 50 && yp < yc + 50) {
			res = true;
		}
		return res;
	}
	
	/**
	 * Función que comprueba si las coordenadas que ha tocado el usuario se encuentran dentro del
	 * radio de acción del círculo. Hay que notar que las coordenadas se miran a partir del centro
	 * justo del círculo.
	 * @param xp coordenada x donde apunta el usuario
	 * @param yp coordenada y donde apunta el usuario
	 * @return devuelve <code>true</code> en caso de que el toque se encuentre dentro del radio de acción
	 * del círculo y <code>false</code> en caso contrario 
	 */
	private boolean isCercaCirculo(float xp, float yp) {
		boolean res = false;
		if (xp > x-20 && xp < x+20 &&
				yp > y-20 && yp < y+20) {
			res = true;
		}
		return res;
	}
}
