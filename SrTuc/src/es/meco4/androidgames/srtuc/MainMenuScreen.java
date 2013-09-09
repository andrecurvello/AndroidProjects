package es.meco4.androidgames.srtuc;

import es.meco4.androidgames.framework.Game;
import es.meco4.androidgames.framework.Graphics;
import es.meco4.androidgames.framework.Screen;

public class MainMenuScreen extends Screen {

	public MainMenuScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.background, 0, 0);
		g.drawPixmap(Assets.logo, 32, 20);
		g.drawPixmap(Assets.mainMenu, 64, 220);
		if(Settings.soundEnabled)
			g.drawPixmap(Assets.buttons, 0, 416, 0, 0, 64, 64);
		else
			g.drawPixmap(Assets.buttons, 0, 416, 64, 0, 64, 64);
	}

	@Override
	public void pause() {
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}
