package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import game.AirWaterScreen;

public class IntroScreen implements Screen {

    MyGdxGame game;

    Texture[] background = new Texture[7];
    int state = 0;

    // sound starts
    public boolean soundState;
    public boolean showMute;
    Texture mute ;
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
        background[5] = new Texture("Intro\\7.png");
        background[6] = new Texture("Intro\\6.png");

        mute = new Texture("Audio\\mute.png") ;

        SoundManager.create();
        SoundManager.intro.setLooping(true);
        SoundManager.intro.setVolume(0.2f);     // 50% of main volume
        if(soundState)
        {
            SoundManager.intro.play();
            showMute = false;
        }
        else showMute = true;

    }

    @Override
    public void render(float delta) {


        // Any time skip button
        if (Gdx.input.getX() >= 1430 && Gdx.input.getX() <= 1630 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 50
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 117) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                SoundManager.click.play();
                game.setScreen(new AirWaterScreen(game,soundState));
            }
        }

        if(state == 6)
        {
            if (Gdx.input.getX() >= 1544 && Gdx.input.getX() <= 1666 &&
                    MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 423
                    && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 558) {
                if (Gdx.input.justTouched()) {
                    this.dispose();
                    game.setScreen(new AirWaterScreen(game,soundState));

                }
            }
        }

        if (Gdx.input.getX() >= 1544 && Gdx.input.getX() <= 1666 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 423
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 558) {
            if (Gdx.input.justTouched()) {

                state++;
                if(state == 7) state = 6;
                 SoundManager.click.play();

            }
        }

        if (Gdx.input.getX() >= 30 && Gdx.input.getX() <= 155 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 423
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 558) {
            if (Gdx.input.justTouched()) {

                SoundManager.click.play();
                state--;
                if(state < 0) state = 0;

            }
        }

        game.batch.begin();

        game.batch.draw(background[state],0,0);
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

        //background1.dispose();
        SoundManager.click.dispose();
        SoundManager.intro.dispose();
        mute.dispose();

        background[0].dispose();
        background[1].dispose();
        background[2].dispose();
        background[3].dispose();
        background[4].dispose();
        background[5].dispose();
        background[6].dispose();

    }
}
