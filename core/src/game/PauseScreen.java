package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

public class PauseScreen implements Screen {

    MyGdxGame game;

    Texture mainBackground;
    int mainBackgroundX ,mainBackgroundY;

    // sound starts
    public boolean soundState;
    // sound ends

    public PauseScreen(MyGdxGame game,boolean soundState) {

        this.game = game;
        this.soundState = soundState;

        mainBackgroundX = 0;
        mainBackgroundY = 0;
        mainBackground = new Texture("Background\\21.jpeg") ;


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            this.dispose();
            game.setScreen(new AirWaterScreen(game,soundState));

        }

        game.batch.begin();

        game.batch.draw(mainBackground, mainBackgroundX, mainBackgroundY,mainBackground.getWidth(), Gdx.graphics.getHeight());

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
