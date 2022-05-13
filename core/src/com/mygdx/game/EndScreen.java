package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import game.AirWaterScreen;
import game.Score;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EndScreen implements Screen {

    MyGdxGame game;
    Score totalScore ;

    Texture endscreen1 ;
    Texture endscreen2 ;
    Texture endscreen3 ;
    Texture endscreen4 ;

    public boolean higestScore ;
    public int currentScore ;

    // sound starts
    public boolean soundState;
    public boolean showMute;
    Texture mute ;
    // sound ends

    public int status;

    public EndScreen(MyGdxGame game,boolean soundState,int status) {

        this.game = game;
        totalScore = new Score();
        this.soundState = soundState;
        this.status = status;

        SoundManager.create();
        SoundManager.end.setLooping(true);
        SoundManager.end.setVolume(0.4f);     // 50% of main volume
        if(soundState)
        {
            SoundManager.end.play();
            showMute = false;
        }
        else showMute = true;

    }


    @Override
    public void show() {

        endscreen1 = new Texture("EndScreen\\2.png");
        endscreen2 = new Texture("EndScreen\\4.png");
        endscreen3 = new Texture("EndScreen\\1.png");
        endscreen4 = new Texture("EndScreen\\3.png");
        mute = new Texture("Audio\\mute.png") ;

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(1, 1, 1, 1);

        // * set position
        //totalScore.score = 0 ;
        totalScore.extraYAxis = 470 ;

        button();
        getScore();
        compareScore();

        //System.out.println(status);

        game.batch.begin();

        if(!higestScore)
        {
            if(status==1) game.batch.draw(endscreen1,0,0);
            else game.batch.draw(endscreen3,0,0);
        }
        else
        {
            if(status==1) game.batch.draw(endscreen2,0,0);
            else game.batch.draw(endscreen4,0,0);
        }

        if(showMute) game.batch.draw(mute, 1580, 830,100,100);
        totalScore.digitWidth = 50f;
        totalScore.digitHeight = 70f;
        totalScore.negativeSignWidth = 50f;
        totalScore.negativeSignHeight = 20f;
        totalScore.render(game.batch);

        game.batch.end();

    }

    public void getScore() {
        File file1 = new File("score.txt");

        try {

                Scanner readInto = new Scanner(file1);
                while (readInto.hasNextLine()) {

                    String pastData = readInto.nextLine();

                    totalScore.score = Integer.parseInt(pastData) ;
                    //System.out.println(totalScore);
                    currentScore = Integer.parseInt(pastData) ;
                    //System.out.println(currentScore);

                }
                readInto.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compareScore(){
        File file = new File("highestScore.txt");

        try {
            // * check if file exists or not
            if (file.exists())
            {
                Scanner readInto = new Scanner(file);
                int pastScore = 0;
                while (readInto.hasNextLine()) {

                    String pastData = readInto.nextLine();

                    pastScore = Integer.parseInt(pastData) ;
                    //System.out.println(pastScore);

                }
                if(currentScore >= pastScore) higestScore = true ;
                else higestScore = false;

                readInto.close();


            } else {

                higestScore = true ;
                // file not exists

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void button()
    {

        if (Gdx.input.getX() >= 644 && Gdx.input.getX() <= 1080 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 303
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 303+78) {
            if (Gdx.input.justTouched()) {
                this.dispose();
                SoundManager.click.play();
                game.setScreen(new StatsScreens(game,soundState,status));
            }
        }
        if (Gdx.input.getX() >= 644 && Gdx.input.getX() <= 1080 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 215
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 215+78) {
            if (Gdx.input.justTouched()) {
                this.dispose();
                SoundManager.click.play();
                game.setScreen(new AirWaterScreen(game,soundState));
            }
        }
        if (Gdx.input.getX() >= 644 && Gdx.input.getX() <= 1080 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 128
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 128+78) {
            if (Gdx.input.justTouched()) {
                this.dispose();
                SoundManager.click.play();
                game.setScreen(new GameMenuScreen(game,soundState));
            }
        }
        if (Gdx.input.getX() >= 644 && Gdx.input.getX() <= 1080 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 36
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 36+78) {
            if (Gdx.input.isTouched()) {
                System.exit(0);
            }
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

        SoundManager.end.dispose();
        SoundManager.click.dispose();

        endscreen3.dispose();
        endscreen2.dispose();
        endscreen1.dispose();
        endscreen4.dispose();

    }
}
