package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import game.Score;

public class IntroClass implements Screen {

    MyGdxGame game;
    Score totalScore ;

    Texture background;


    public IntroClass(MyGdxGame game) {

        this.game = game;
        totalScore = new Score(game.batch);

        background = new Texture("Background\\show.png");

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
