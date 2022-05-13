package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import game.Score;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class HighScoreScreen implements Screen {

    MyGdxGame game;
    Score totalScore ;

    Texture background;

    // sound starts
    public boolean soundState;
    public boolean showMute;
    Texture mute ;
    // sound ends


    public HighScoreScreen(MyGdxGame game,boolean soundState) {

        this.game = game;
        totalScore = new Score();
        this.soundState = soundState;


    }
    @Override
    public void show() {

        background = new Texture("Background\\show.png");
        mute = new Texture("Audio\\mute.png") ;


        SoundManager.create();
        SoundManager.highscore.setLooping(true);
        SoundManager.highscore.setVolume(0.2f);     // 50% of main volume
        if(soundState)
        {
            SoundManager.highscore.play();
            showMute = false;
        }
        else showMute = true;

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);

        // * set position
        totalScore.score = 0 ;
        totalScore.extraYAxis = 470 ;

        buttonWork();
        getScore();

        game.batch.begin();

        game.batch.draw(background,0,0);
        if(showMute) game.batch.draw(mute, 1580, 830,100,100);

        totalScore.digitWidth = 70f;
        totalScore.digitHeight = 90f;
        totalScore.render(game.batch);

        game.batch.end();

    }

    public void buttonWork() {
        if (Gdx.input.getX() >= 615 && Gdx.input.getX() <= 1095 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 225
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 310) {
            if (Gdx.input.justTouched()) {
                this.dispose();
                SoundManager.click.play();
                game.setScreen(new GameMenuScreen(game,soundState));
            }
        }

        if (Gdx.input.getX() >= 615 && Gdx.input.getX() <= 1095 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 120
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 120+85) {
            if (Gdx.input.justTouched()) {

                SoundManager.click.play();
                File file = new File("highestScore.txt");
                file.delete();

            }
        }

        if (Gdx.input.getX() >= 615 && Gdx.input.getX() <= 1095 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 20
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 20+85) {
            if (Gdx.input.justTouched()) {
                System.exit(0);
            }
        }
    }

    public void getScore() {
        File file = new File("highestScore.txt");

        try {
            // * check if file exists or not
            if (file.exists())
            {
                Scanner readInto = new Scanner(file);

                while (readInto.hasNextLine()) {

                    String pastData = readInto.nextLine();

                    totalScore.score = Integer.parseInt(pastData) ;

                }
                readInto.close();


            } else {

                totalScore.score = 0 ;
                // file not exists

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
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
        SoundManager.highscore.dispose();
        SoundManager.click.dispose();

    }
}
