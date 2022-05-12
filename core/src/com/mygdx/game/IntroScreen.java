package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import game.AirWaterScreen;

public class IntroScreen implements Screen {

    MyGdxGame game;

    Texture[] background = new Texture[6];
    int state = 0;

    // sound starts
    public boolean soundState;
    // sound ends

    public IntroScreen(MyGdxGame game,boolean soundState) {

        this.game = game;
        this.soundState = soundState;
    }

    @Override
    public void show() {

        background[0] = new Texture("Intro\\1.png");
        background[1] = new Texture("Intro\\2.png");
        background[2] = new Texture("Intro\\3.png");
        background[3] = new Texture("Intro\\4.png");
        background[4] = new Texture("Intro\\5.png");
        background[5] = new Texture("Intro\\6.png");

    }

    @Override
    public void render(float delta) {


        // Any time skip button
        if (Gdx.input.getX() >= 1430 && Gdx.input.getX() <= 1630 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 50
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 117) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new AirWaterScreen(game,soundState));
            }
        }

        game.batch.begin();

        game.batch.draw(background[state],0,0);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            state--;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            state++;
        }


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

        //background1.dispose();

    }
}
