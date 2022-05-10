package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import game.Score;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HighScoreScreen implements Screen {

    MyGdxGame game;
    Score totalScore ;

    Texture background;


    public HighScoreScreen(MyGdxGame game) {

        this.game = game;
        totalScore = new Score(game.batch);

        background = new Texture("Background\\show.png");

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);

        // * set position
        totalScore.score = 0 ;
        totalScore.minusHeight = 470 ;

        buttonWork();
        getScore();

        game.batch.begin();

        game.batch.draw(background,0,0);
        totalScore.render(game.batch);

        game.batch.end();

    }

    public void buttonWork() {
        if (Gdx.input.getX() >= 615 && Gdx.input.getX() <= 1095 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 225
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 310) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new GameMenuScreen(game));
            }
        }

        if (Gdx.input.getX() >= 615 && Gdx.input.getX() <= 1095 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 120
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 120+85) {
            if (Gdx.input.isTouched()) {

                File file = new File("highestScore.txt");
                file.delete();

            }
        }

        if (Gdx.input.getX() >= 615 && Gdx.input.getX() <= 1095 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 20
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 20+85) {
            if (Gdx.input.isTouched()) {
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

    }
}
