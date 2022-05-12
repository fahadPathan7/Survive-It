package com.mygdx.game;

import Intro.IntroScreen1;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class SettingScreen implements Screen {

    MyGdxGame game;

    Texture background;

    // sound starts
    public boolean soundState;
    public boolean showMute;
    Texture mute ;
    // sound ends

    public SettingScreen(MyGdxGame game, boolean soundState) {

        this.game = game;
        this.soundState = soundState;

    }

    @Override
    public void show() {

        background = new Texture("Audio\\setting.png") ;
        mute = new Texture("Audio\\mute.png") ;

        SoundManager.create();
        SoundManager.setting.setLooping(true);
        SoundManager.setting.setVolume(0.2f);     // 50% of main volume
        if(soundState)
        {
            SoundManager.setting.play();
            showMute = false;
        }
        else showMute = true;

    }

    @Override
    public void render(float delta) {

        if (Gdx.input.getX() >= 187 && Gdx.input.getX() <= 800 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 544
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 658) {
            if (Gdx.input.justTouched()) {
                SoundManager.click.play();
                if(!soundState)
                {
                    soundState = true ;
                    SoundManager.setting.play();
                    showMute = false;
                }
            }
        }

        if (Gdx.input.getX() >= 187 && Gdx.input.getX() <= 800 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 367
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 480) {
            if (Gdx.input.justTouched()) {
                SoundManager.click.play();
                if(soundState)
                {
                    soundState = false;
                    SoundManager.setting.stop();
                    showMute = true;
                }
            }
        }

        if (Gdx.input.getX() >= 187 && Gdx.input.getX() <= 800 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 100
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 210) {
            if (Gdx.input.justTouched()) {

                this.dispose();
                SoundManager.click.play();
                game.setScreen(new GameMenuScreen(game,soundState));
            }
        }

        game.batch.begin();

        game.batch.draw(background, 0, 0);

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

        SoundManager.setting.dispose();
        SoundManager.click.dispose();

    }
}
