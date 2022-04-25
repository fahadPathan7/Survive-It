package game;

import com.badlogic.gdx.Gdx;
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

    // bullet starts
    ArrayList<Monster> monsters;
    private final float MONSTER_MIN_TIME = 1f, MONSTER_MAX_TIME = 4f;
    public float monsterLaunchTime = 0f;
    // bullet ends

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

        // creating character object
        character = new Character();

        monsters = new ArrayList<>();

        // background starts
        background1 = new Texture("Background\\water_air.jpeg");
        background2 = new Texture("Background\\water_air.jpeg");
        background1X = 0;
        background2X = 5000; // initial huge value to avoid colliding
        // background ends

        // poison animation starts
        //poisons = new ArrayList<>();
        // poison animation ends
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

        // bullet starts
        monsterLaunchTime -= delta;
        if (monsterLaunchTime <= 0) {
            monsterLaunchTime = rand.nextFloat() * (MONSTER_MAX_TIME - MONSTER_MIN_TIME) + MONSTER_MIN_TIME;
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
                monsterToRemove.add(monster);
            }
        }
        monsters.removeAll(monsterToRemove);

//        // checking if bullet is colliding with character
//        monsterToRemove.clear();
//        for (Monster bullet : monsters) {
//            if (character.getCollision().isCollide(bullet.getCollision())) {
//                monsterToRemove.add(bullet);
//            }
//        }
//        monsters.removeAll(monsterToRemove);
        // bullet ends

        // background starts
        background1X -= BACKGROUND_HORIZONTAL_SPEED * delta;
        background2X -= BACKGROUND_HORIZONTAL_SPEED * delta;
        if (background1X + background1.getWidth() < Gdx.graphics.getWidth())
            background2X = background1X + background1.getWidth();
        if (background2X + background2.getWidth() < Gdx.graphics.getWidth())
            background1X = background2X + background2.getWidth();
        // background ends

        // poison animation starts
//        poisonLaunchTime -= delta;
//        if (poisonLaunchTime <= 0) {
//            poisons.add(new Poison());
//            poisonLaunchTime = rand.nextFloat() * (POISON_MAX_TIME - POISON_MIN_TIME) + POISON_MIN_TIME;
//        }
//
//        ArrayList<Poison> poisonToRemove = new ArrayList<>();
//        for (Poison poison : poisons) {
//            poison.update();
//            if (poison.poisonRemove) poisonToRemove.add(poison);
//        }
//        poisons.removeAll(poisonToRemove);
        // poison animation ends

        game.batch.begin();

        // background starts
        game.batch.draw(background1, background1X, 0, background1.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(background2, background2X, 0, background2.getWidth(), Gdx.graphics.getHeight());
        // background ends

        // rendering character
        character.render(game.batch);

        // bullet starts
        for (Monster monster : monsters) {
            monster.render(game.batch);
        }
        // bullet ends

        // for poison animation
//        for (Poison poison : poisons) {
//            poison.render(game.batch);
//        }

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
