package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
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

    // spell animation starts
    ArrayList<Spell> spells;
    public float spellPauseTime = 0.7f;
    public float spellStateTime = spellPauseTime;
    // spell animation ends

    // score starts
    Score score;
    public int tempScore;
    public int monsterHitPoint = 100;
    public int monsterMissPoint = -50;
    public int spellMissPoint = -20;
    public int collisionPoint = -300;
    // score ends

    // background starts
    Texture background;
    public float backgroundHorizontalSpeed = 120;
    public float background1X = 0;
    public float background2X;
    // background ends


    public AirWaterScreen(MyGdxGame game) {
        this.game = game;
        rand = new Random();

        character = new Character(); // creating character object

        score = new Score(game.batch); // creating score object

        monsters = new ArrayList<>(); // for storing Monster objects

        blasts = new ArrayList<>(); // for storing Blast objects

        spells = new ArrayList<>(); // for storing Spell objects

        // background starts
        background = new Texture("Background\\water_air.jpeg");
        background2X = background.getWidth();
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
        updateObjects();

        detectCollision();


        game.batch.begin();

        renderObjects();

        game.batch.end();
    }

    public void updateObjects() {
        updateBackground();

        updateCharacter();

        updateMonster();

        updateSpell();

        updateBlast();

        updateScore();
    }

    public void updateCharacter() {
        character.update();
    }

    public void updateMonster() {
        monsterLaunchTime -= Gdx.graphics.getDeltaTime();
        if (monsterLaunchTime <= 0) {
            monsterLaunchTime = rand.nextFloat() * (monsterMaxLaunchTime - monsterMinLaunchTime) + monsterMinLaunchTime;
            monsters.add(new Monster());
        }

        ArrayList<Monster> monsterToRemove = new ArrayList<>();
        for (Monster monster : monsters) {
            monster.update();
            if (monster.remove) {
                monsterToRemove.add(monster);

                tempScore += monsterMissPoint; // updating score
            }
        }
        monsters.removeAll(monsterToRemove);
    }

    public void updateSpell() {
        spellStateTime += Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyJustPressed(Input.Keys.M) && spellStateTime >= spellPauseTime) {
            spells.add(new Spell(character.characterX, character.characterY, character.characterWidth,
                    character.characterHeight, character.inWater));
            spellStateTime = 0f;
        }

        ArrayList<Spell> spellToRemove = new ArrayList<>();
        for (Spell spell : spells) {
            spell.update();
            if (spell.spellRemove) {
                spellToRemove.add(spell);

                tempScore += spellMissPoint; // updating score
            }
        }
        spells.removeAll(spellToRemove);
    }

    public void updateBlast() {
        ArrayList<Blast> blastToRemove = new ArrayList<>();
        for (Blast blast : blasts) {
            if (blast.blastRemove) blastToRemove.add(blast);
        }
        blasts.removeAll(blastToRemove);
    }

    public void updateBackground() {
        background1X -= backgroundHorizontalSpeed * Gdx.graphics.getDeltaTime();
        background2X -= backgroundHorizontalSpeed * Gdx.graphics.getDeltaTime();

        if (background1X + background.getWidth() <= 0)
            background1X = background2X + background.getWidth();
        if (background2X + background.getWidth() <= 0)
            background2X = background1X + background.getWidth();
    }

    public void updateScore() {
        score.update(tempScore);
        tempScore = 0;
    }

    public void detectCollision() {
        characterWithMonsterCollision();

        spellWithMonsterCollision();
    }

    public void characterWithMonsterCollision() {
        ArrayList<Monster> monsterToRemove = new ArrayList<>();
        for (Monster monster : monsters) {
            if (character.getCollision().isCollide(monster.getCollision())) {
                monsterToRemove.add(monster);

                blasts.add(new Blast(monster.monsterX, monster.monsterY));

                tempScore += collisionPoint; // updating score
            }
        }
        monsters.removeAll(monsterToRemove);
    }

    public void spellWithMonsterCollision() {
        ArrayList<Monster> monsterToRemove = new ArrayList<>();
        ArrayList<Spell> spellToRemove = new ArrayList<>();
        for (Spell spell : spells) {
            for (Monster monster : monsters) {
                if (spell.getCollision().isCollide(monster.getCollision())) {
                    spellToRemove.add(spell);
                    monsterToRemove.add(monster);

                    blasts.add(new Blast(monster.monsterX, monster.monsterY));

                    tempScore += monsterHitPoint; // updating score
                }
            }
        }
        monsters.removeAll(monsterToRemove);
        spells.removeAll(spellToRemove);
    }

    public void renderObjects() {
        renderBackground();

        renderCharacter();

        renderMonster();

        renderSpell();

        renderBlast();

        renderScore();
    }

    public void renderBackground() {
        game.batch.draw(background, background1X, 0, background.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(background, background2X, 0, background.getWidth(), Gdx.graphics.getHeight());
    }

    public void renderCharacter() {
        character.render(game.batch);
    }

    public void renderMonster() {
        for (Monster monster : monsters) {
            monster.render(game.batch);
        }
    }

    public void renderSpell() {
        for (Spell spell : spells) {
            spell.render(game.batch);
        }
    }

    public void renderBlast() {
        for (Blast blast : blasts) {
            blast.renderBlastAnimation(game.batch);
        }
    }

    public void renderScore() {
        score.render(game.batch);
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