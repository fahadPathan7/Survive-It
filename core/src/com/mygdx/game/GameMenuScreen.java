package com.mygdx.game;

import Intro.IntroScreen1;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class GameMenuScreen implements Screen {

    MyGdxGame game;

    // backgrounds start
    Texture menuBackground;
    int menuBackgroundX = 0 ;
    int menuBackgroundY = 0;
    // backgrounds ends

    // sound starts
    public boolean soundState;
    public boolean showMute;
    Texture mute ;
    // sound ends

    public GameMenuScreen(MyGdxGame game, boolean soundState) {

        this.game = game;
        this.soundState = soundState;
    }

    @Override
    public void show() {

        menuBackground = new Texture("Background\\menu.png") ;
        mute = new Texture("Audio\\mute.png") ;

        SoundManager.create();
        SoundManager.menu.setLooping(true);
        SoundManager.menu.setVolume(0.3f);

        if(soundState)
        {
            SoundManager.menu.play();
            showMute = false;
        }
        else showMute = true;

    }

    @Override
    public void render(float delta) {

        // start
        if (Gdx.input.getX() >= 176 && Gdx.input.getX() <= 520 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 790
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 890) {
            if (Gdx.input.justTouched()) {
                this.dispose();
                //game.setScreen(new AirWaterScreen(game));
                SoundManager.click.play();
                game.setScreen(new IntroScreen(game,soundState));
            }
        }

        // tutorial
        if (Gdx.input.getX() >= 176 && Gdx.input.getX() <= 520 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 625
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 725) {
            if (Gdx.input.justTouched()) {
                this.dispose();
                SoundManager.click.play();
                game.setScreen(new SettingScreen(game,soundState));
            }
        }

        // high score
        if (Gdx.input.getX() >= 176 && Gdx.input.getX() <= 520 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 454
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 554) {
            if (Gdx.input.justTouched()) {
                this.dispose();
                SoundManager.click.play();
                game.setScreen(new HighScoreScreen(game,soundState));
            }
        }

        // about us
        if (Gdx.input.getX() >= 176 && Gdx.input.getX() <= 520 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 285
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 385) {
            if (Gdx.input.justTouched()) {
                this.dispose();
                SoundManager.click.play();
                game.setScreen(new AboutScreen(game,soundState));
            }
        }

        // exit
        if (Gdx.input.getX() >= 176 && Gdx.input.getX() <= 520 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 115
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 225) {
            if (Gdx.input.justTouched()) {

                SoundManager.click.play();
                System.exit(0);
            }
        }

        game.batch.begin();

        game.batch.draw(menuBackground, menuBackgroundX, menuBackgroundY);
        if(showMute) game.batch.draw(mute, 1580, 830,100,100);

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        SoundManager.menu.dispose();
        SoundManager.click.dispose();
        menuBackground.dispose();

    }
}
