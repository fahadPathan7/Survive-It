package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {
	public static final int SCREEN_WIDTH = 1700;
	public static final int SCREEN_HEIGHT = 950;

	public SpriteBatch batch;
//	private Music music;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
//		this.setScreen(new MainMenuScreen(this));

//		music = Gdx.audio.newMusic(Gdx.files.internal("home.mp3"));
//		music.setLooping(true);
//		music.setVolume(0.5f);
//		music.play();

		this.setScreen(new HomeScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

		batch.dispose();
	}
}
