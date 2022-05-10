package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import game.AirWaterScreen;

public class EndScreen implements Screen {

    MyGdxGame game;

    Texture endscreen ;

    public EndScreen(MyGdxGame game) {

        this.game = game;

        endscreen = new Texture("EndScreen\\2.png");

    }


    @Override
    public void show() {



    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        if (Gdx.input.getX() >= 621 && Gdx.input.getX() <= 1105 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 300
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 385) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new AirWaterScreen(game));
            }
        }
        if (Gdx.input.getX() >= 621 && Gdx.input.getX() <= 1105 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 180
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 265) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new GameMenuScreen(game));
            }
        }
        if (Gdx.input.getX() >= 621 && Gdx.input.getX() <= 1105 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 65
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 150) {
            if (Gdx.input.isTouched()) {
                System.exit(0);
            }
        }

        game.batch.begin();
        game.batch.draw(endscreen,0,0);
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
