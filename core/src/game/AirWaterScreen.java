package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;

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
    Score totalScore;
    public int tempScore;
    public int monsterHitPoint = 100;
    public int monsterMissPoint = -50;
    public int spellMissPoint = -20;
    public int collisionPoint = -300;
    // score ends

    // background starts
    Texture background;
    public float backgroundHorizontalSpeed = 160;
    public float background1X = 0;
    public float background2X;
    // background ends


    // pause screen start
    Texture pause;
    Texture pauseIcon;
    public int pauseScreenX = 0;
    public int pauseScreenY = 0;
    public float iconHeight = 77f;
    public float pauseIconX = 0 ;
    public float pauseIconY = Gdx.graphics.getHeight() - iconHeight;
    public boolean status  ;
    // pause screen ends

    // sound start
    public boolean music = true;
    // sound off

    // health bar start
    Texture healthBar;
    public float health = 1  ;  // 0 dead ; 1 full health
    public float healthBarHeight = 10 ;
    public float healthDamage = .05f;
    // health bar ends

    // game time starts
    GameTime gameTime; // declaring GameTime object
    // game time ends


    public AirWaterScreen(MyGdxGame game) {
        this.game = game;
        rand = new Random();

        character = new Character(); // creating character object

        totalScore = new Score(game.batch); // creating score object

        monsters = new ArrayList<>(); // for storing Monster objects

        blasts = new ArrayList<>(); // for storing Blast objects

        spells = new ArrayList<>(); // for storing Spell objects

        gameTime = new GameTime(); // creating GameTime object

        // background starts
        background = new Texture("Background\\water_air.jpeg");
        background2X = background.getWidth();
        // background ends

        // pause screen set starts
        status = false ;
        pause = new Texture("Pause\\pause.png");
        // pause screen ends

        // health bar starts
        healthBar = new Texture("Healthbar\\blank.png");
        // health bar ends
    }

    @Override
    public void show() {
        soundbar();
    }

    @Override
    public void render(float delta) {


        updatePauseScreen();
        if(!status)
        {
            updateObjects();
            detectCollision();

        }

        else{
            // * working in pause screen
            showPauseMenu();

        }

        game.batch.begin();

        if(!status)renderObjects();
        else renderPause();

        game.batch.end();
    }

    public void updateObjects() {
        updateBackground();

        updateCharacter();

        updateMonster();

        updateSpell();

        updateBlast();

        updateScore();

        showPauseIcon();

        updateGameTime();
    }

    public void soundbar(){
        if(music)
        {
            SoundManager.create();
            SoundManager.gameLevel3.setLooping(true);
            SoundManager.gameLevel3.setVolume(0.3f);     // 30% of main volume
            SoundManager.gameLevel3.play();

        }
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
        totalScore.update(tempScore);
        tempScore = 0;
    }

    public void showPauseIcon(){
        pauseIcon = new Texture("Pause\\icon.png");

        if (Gdx.input.getX() >= 0 && Gdx.input.getX() <= 90 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= pauseIconY
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= pauseIconY+90) {
            if (Gdx.input.isTouched()) {
                status = true ;
            }
        }
    }

    public void updatePauseScreen() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {

            if(!status)status = true;
            else status = false;
            //System.out.println(status);
        }

    }

    public void updateGameTime() {
        gameTime.update();
        if (gameTime.gameEnd) {
            this.dispose();
            game.setScreen(new EndScreen(game));
        }
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

                tempScore += collisionPoint;           // updating score
                health -= healthDamage;               // health update

                // If health == 0 switch to end screen
                if(health <= 0)
                {
                    this.dispose();
                    game.setScreen(new EndScreen(game));
                }
                // Ends


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

        renderPauseIcon();

        renderHealthBar();

        renderGameTime();
    }

    public void showPauseMenu() {
        if (Gdx.input.getX() >= 538 && Gdx.input.getX() <= 1023 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 705
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 788) {
            if (Gdx.input.isTouched()) {
                status = false ;
            }
        }

        if (Gdx.input.getX() >= 538 && Gdx.input.getX() <= 1023 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 586
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 586 + 83) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new AirWaterScreen(game));
            }
        }

        if (Gdx.input.getX() >= 538 && Gdx.input.getX() <= 1023 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 478
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 478 + 83) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new GameMenuScreen(game));
            }
        }

        if (Gdx.input.getX() >= 538 && Gdx.input.getX() <= 1023 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 388
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 388 + 83) {
            if (Gdx.input.isTouched()) {
                music = true;
            }
        }

        if (Gdx.input.getX() >= 538 && Gdx.input.getX() <= 1023 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 365
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 365 + 83) {
            if (Gdx.input.isTouched()) {
                SoundManager.gameLevel3.stop();
                SoundManager.gameLevel3.play();
            }
        }

        if (Gdx.input.getX() >= 538 && Gdx.input.getX() <= 1023 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 250
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 250 + 83) {
            if (Gdx.input.isTouched()) {
                SoundManager.gameLevel3.stop();
            }
        }

        if (Gdx.input.getX() >= 538 && Gdx.input.getX() <= 1023 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 140
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 140 + 83) {
            if (Gdx.input.isTouched()) {
                System.exit(0);
            }
        }
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
        totalScore.render(game.batch);
    }

    public void renderPause() {
        game.batch.draw(pause, pauseScreenX, pauseScreenY,pause.getWidth(), Gdx.graphics.getHeight());
    }

    public void renderPauseIcon(){
        game.batch.draw(pauseIcon, pauseIconX, pauseIconY ,90, 80);
    }

    public void renderHealthBar(){
        game.batch.draw(healthBar,0,0,Gdx.graphics.getWidth() * health , healthBarHeight);
    }

    public void renderGameTime() {
        gameTime.render(game.batch);
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