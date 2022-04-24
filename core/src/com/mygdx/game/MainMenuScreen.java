package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import game.AirScreen;
import game.AirWaterScreen;
import game.WaterScreen;

public class MainMenuScreen implements Screen {
    MyGdxGame game;

    private final float IMAGE_GAP = 200;

    // for air and water starts
    private final float AIR_WATER_IMAGE_WIDTH = 100;
    private final float AIR_WATER_IMAGE_HEIGHT = 100;
    float airWaterImageX = (float)Gdx.graphics.getWidth() / 2 - AIR_WATER_IMAGE_WIDTH / 2,
            airWaterImageY = (float)Gdx.graphics.getHeight() / 2 - AIR_WATER_IMAGE_HEIGHT / 2;
    Texture airWaterImg;
    // for air and water ends

    // for air starts
    private final float AIR_IMAGE_WIDTH = 100;
    private final float AIR_IMAGE_HEIGHT = 100;
    float airImageX = (float)Gdx.graphics.getWidth() / 2 - AIR_IMAGE_WIDTH / 2 - IMAGE_GAP,
            airImageY = (float)Gdx.graphics.getHeight() / 2 - AIR_IMAGE_HEIGHT / 2;
    Texture airImg;
    // for air ends

    // for water starts
    private final float WATER_IMAGE_WIDTH = 100;
    private final float WATER_IMAGE_HEIGHT = 100;
    float waterImageX = (float)Gdx.graphics.getWidth() / 2 - WATER_IMAGE_WIDTH / 2 + IMAGE_GAP,
            waterImageY = (float)Gdx.graphics.getHeight() / 2 - WATER_IMAGE_HEIGHT / 2;
    Texture waterImg;
    // for water ends

    public MainMenuScreen(MyGdxGame game) {
        this.game = game;
        airWaterImg = new Texture("RandomButtons\\air_water_screen.png");
        airImg = new Texture("RandomButtons\\air_screen.png");
        waterImg = new Texture("RandomButtons\\water_screen.png");
    }
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 0, 1);

        if (Gdx.input.getX() >= airWaterImageX && Gdx.input.getX() <= airWaterImageX + AIR_WATER_IMAGE_WIDTH &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= airWaterImageY
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= airWaterImageY + AIR_WATER_IMAGE_HEIGHT) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new AirWaterScreen(game));
            }
        }

        if (Gdx.input.getX() >= airImageX && Gdx.input.getX() <= airImageX + AIR_IMAGE_WIDTH &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= airImageY
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= airImageY + AIR_IMAGE_HEIGHT) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new AirScreen(game));
            }
        }

        if (Gdx.input.getX() >= waterImageX && Gdx.input.getX() <= waterImageX + WATER_IMAGE_WIDTH &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= waterImageY
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= waterImageY + WATER_IMAGE_HEIGHT) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new WaterScreen(game));
            }
        }

        game.batch.begin();
        game.batch.draw(airImg, airImageX, airImageY, AIR_IMAGE_WIDTH, AIR_IMAGE_HEIGHT);
        game.batch.draw(airWaterImg, airWaterImageX, airWaterImageY, AIR_WATER_IMAGE_WIDTH, AIR_WATER_IMAGE_HEIGHT);
        game.batch.draw(waterImg, waterImageX, waterImageY, WATER_IMAGE_WIDTH, WATER_IMAGE_HEIGHT);
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
        //game.batch.dispose();
        //airWaterImg.dispose();
    }
}
