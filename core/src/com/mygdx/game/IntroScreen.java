package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import game.AirWaterScreen;
import game.Score;

public class IntroScreen implements Screen {

    MyGdxGame game;
    Score totalScore ;

    Texture background;


    public IntroScreen(MyGdxGame game) {

        this.game = game;
        totalScore = new Score(game.batch);

        background = new Texture("Intro\\1.png");

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {


        if (Gdx.input.getX() >= 1430 && Gdx.input.getX() <= 1630 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 50
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 117) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new AirWaterScreen(game));
                //game.setScreen(new IntroScreen(game));
            }
        }

        game.batch.begin();

        game.batch.draw(background,0,0);

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

    }
}
