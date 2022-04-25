package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class AboutScreen implements Screen {

    MyGdxGame game;

    Texture image ;

    public AboutScreen(MyGdxGame game) {

        this.game = game;

        image =  new Texture("About\\about.png") ;

    }


    @Override
    public void show() {

        SoundManager.create();
        SoundManager.about.setLooping(true);
        SoundManager.about.setVolume(0.3f);       // 50% of main volume
        SoundManager.about.play();

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(1, 1, 1, 1);

        game.batch.begin();

        game.batch.draw(image, 0,0);

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

    }
}
