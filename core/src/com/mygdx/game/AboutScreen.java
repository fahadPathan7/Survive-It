package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class AboutScreen implements Screen {

    MyGdxGame game;

    Texture image ;

    // sound starts
    public boolean soundState;
    public boolean showMute;
    Texture mute ;
    // sound ends

    public AboutScreen(MyGdxGame game,boolean soundState) {

        this.game = game;
        this.soundState = soundState;


    }

    @Override
    public void show() {

        image =  new Texture("About\\about.png") ;
        mute = new Texture("Audio\\mute.png") ;

        SoundManager.create();
        SoundManager.about.setLooping(true);
        SoundManager.about.setVolume(0.2f);       // 50% of main volume

        if(soundState)
        {
            SoundManager.about.play();
            showMute = false;
        }
        else showMute = true;

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(1, 1, 1, 1);

        if (Gdx.input.getX() >= 305 && Gdx.input.getX() <= 7855 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 25
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 95) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                SoundManager.click.play();
                game.setScreen(new GameMenuScreen(game,soundState));
            }
        }

        game.batch.begin();

        game.batch.draw(image, 0,0);

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

        SoundManager.about.dispose();
        SoundManager.click.dispose();

    }
}
