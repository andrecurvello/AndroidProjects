package es.meco4.androidgames.srtuc;

import es.meco4.androidgames.framework.Screen;
import es.meco4.androidgames.framework.impl.AndroidGame;

public class SrTucGame extends AndroidGame {

	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}
}
