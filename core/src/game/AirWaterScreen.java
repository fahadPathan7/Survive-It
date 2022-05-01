package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.SoundManager;

import java.util.ArrayList;
import java.util.Random;

public class AirWaterScreen implements Screen {
    MyGdxGame game; // reference of MyGdxGame
    Character character; // reference of Character
    Random rand; // for taking random value

    // monster starts
    ArrayList<Monster> monsters;
    public float monsterMinLaunchTime = 1f;
    public float monsterMaxLaunchTime = 4f;
    public float monsterLaunchTime = 0f;
    // monster ends

    // blast animation starts
    ArrayList<Blast> blasts;
    // blast animation ends

    // fire animation starts
    ArrayList<Fire> fires;
    // fire animation ends

    // background starts
    Texture background1, background2;
    public final int BACKGROUND_HORIZONTAL_SPEED = 120;
    int background1X, background2X;
    // background ends

    // poison animation starts
    float poisonLaunchTime = 5f;
    ArrayList<Poison> poisons;
    public final float POISON_MIN_TIME = 40f, POISON_MAX_TIME = 60f;
    // poison animation ends

    public AirWaterScreen(MyGdxGame game) {
        this.game = game;
        rand = new Random();

        character = new Character(); // creating character object

        monsters = new ArrayList<>(); // for storing Monster objects

        blasts = new ArrayList<>(); // for storing Blast objects

        fires = new ArrayList<>(); // for storing Fire objects

        // background starts
        background1 = new Texture("Background\\water_air.jpeg");
        background2 = new Texture("Background\\water_air.jpeg");
        background1X = 0;
        background2X = 5000; // initial huge value to avoid colliding
        // background ends
    }
    @Override
    public void show() {
        SoundManager.create();
        SoundManager.gameLevel3.setLooping(true);
        SoundManager.gameLevel3.setVolume(0.3f);     // 30% of main volume
        SoundManager.gameLevel3.play();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        // updating character
        character.update();

        // monster starts
        monsterLaunchTime -= delta;
        if (monsterLaunchTime <= 0) {
            monsterLaunchTime = rand.nextFloat() * (monsterMaxLaunchTime - monsterMinLaunchTime) + monsterMinLaunchTime;
            monsters.add(new Monster());
        }

        ArrayList<Monster> monsterToRemove = new ArrayList<>();
        for (Monster monster : monsters) {
            monster.update();
            if (monster.remove) {
                monsterToRemove.add(monster);
            }
            // checking if monster is colliding with character
            if (character.getCollision().isCollide(monster.getCollision())) {
                blasts.add(new Blast(monster.monsterX, monster.monsterY)); // adding to animate blast where collision occurs.

                monsterToRemove.add(monster);
            }
        }
        monsters.removeAll(monsterToRemove);
        // monster ends

        // fire starts
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            fires.add(new Fire(character.characterX, character.characterY, character.characterWidth, character.characterHeight));
        }

        ArrayList<Fire> fireToRemove = new ArrayList<>();
        for (Fire fire : fires) {
            fire.update();
            if (fire.fireRemove) fireToRemove.add(fire);
        }
        fires.removeAll(fireToRemove);

        monsterToRemove.clear();
        fireToRemove.clear();
        for (Fire fire : fires) {
            if (fire.fireRemove) fireToRemove.add(fire);
            for (Monster monster : monsters) {
                if (fire.getCollision().isCollide(monster.getCollision())) {
                    fireToRemove.add(fire);
                    blasts.add(new Blast(monster.monsterX, monster.monsterY));
                    monsterToRemove.add(monster);
                }
            }
        }
        fires.removeAll(fireToRemove);
        monsters.removeAll(monsterToRemove);
        // fire ends

        // blast update starts
        ArrayList<Blast> blastToRemove = new ArrayList<>();
        for (Blast blast : blasts) {
            if (blast.blastRemove) blastToRemove.add(blast);
        }
        blasts.removeAll(blastToRemove);
        // blast update ends

        // background starts
        background1X -= BACKGROUND_HORIZONTAL_SPEED * delta;
        background2X -= BACKGROUND_HORIZONTAL_SPEED * delta;
        if (background1X + background1.getWidth() < Gdx.graphics.getWidth())
            background2X = background1X + background1.getWidth();
        if (background2X + background2.getWidth() < Gdx.graphics.getWidth())
            background1X = background2X + background2.getWidth();
        // background ends


        game.batch.begin();

        // background starts
        game.batch.draw(background1, background1X, 0, background1.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(background2, background2X, 0, background2.getWidth(), Gdx.graphics.getHeight());
        // background ends

        // rendering character
        character.render(game.batch);

        // monster starts
        for (Monster monster : monsters) {
            monster.render(game.batch);
        }
        // monster ends

        // fire starts
        for (Fire fire : fires) {
            fire.render(game.batch);
        }
        // fire ends

        // blast starts
        for (Blast blast : blasts) {
            blast.renderBlastAnimation(game.batch);
        }
        // blast ends

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
        SoundManager.gameLevel3.dispose();
    }
}
