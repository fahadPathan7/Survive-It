package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
    public int consecutiveKillsCnt = 0;
    public int consecutiveKillsToGetPoint = 5;
    public int consecutiveKillsPoint = 280;
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

    // health bar start
    Texture[] healthBar = new Texture[4];
    public int state = 0 ;
    public float health = 1  ;  // 0 dead ; 1 full health
    public float healthBarHeight = 10 ;
    public float healthDamage = .19f;
    // health bar ends

    // game time starts
    GameTime gameTime; // declaring GameTime object
    // game time ends

    // sound starts
    Texture mute ;
    public boolean soundState;
    public boolean music;
    public boolean showMute = false;
    // sound ends


    public AirWaterScreen(MyGdxGame game,boolean soundState) {
        this.game = game;

        rand = new Random();

        character = new Character(); // creating character object

        totalScore = new Score(game.batch); // creating score object

        monsters = new ArrayList<>(); // for storing Monster objects

        blasts = new ArrayList<>(); // for storing Blast objects

        spells = new ArrayList<>(); // for storing Spell objects

        gameTime = new GameTime(); // creating GameTime object

        this.soundState = soundState;

    }

    @Override
    public void show() {

        // background starts
        background = new Texture("Background\\water_air.jpeg");
        background2X = background.getWidth();
        // background ends

        // pause screen set starts
        status = false ;
        pause = new Texture("Pause\\pause.png");
        pauseIcon = new Texture("Pause\\icon.png");
        // pause screen ends

        // health bar starts
        healthBar[0] = new Texture("Healthbar\\green.png");
        healthBar[1] = new Texture("Healthbar\\yellow.png");
        healthBar[2] = new Texture("Healthbar\\orange.png");
        healthBar[3] = new Texture("Healthbar\\red.png");
        // health bar ends

        // sound starts
        mute = new Texture("Audio\\mute.png") ;
        soundbar();
        // sound ends
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

        SoundManager.create();
        SoundManager.gameLevel3.setLooping(true);
        SoundManager.gameLevel3.setVolume(0.1f);
        if(soundState)
        {
                 // 30% of main volume
            SoundManager.gameLevel3.play();
            showMute = false;

        }
        else showMute = true;
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
                consecutiveKillsCnt = 0;
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
                consecutiveKillsCnt = 0;
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
            //System.out.println(totalScore.score);
            setScoreAndHighScore();
            this.dispose();
            game.setScreen(new EndScreen(game,soundState));
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
                consecutiveKillsCnt = 0;
                health -= healthDamage;               // health update

                // If health == 0 switch to end screen
                if(health <= 0)
                {
                    setScoreAndHighScore();
                    this.dispose();
                    game.setScreen(new EndScreen(game,soundState));
                }
                // Ends


            }
        }
        monsters.removeAll(monsterToRemove);
    }

    public void setScoreAndHighScore(){

        int score = totalScore.score ;
        String curr_score = Integer.toString(score);

        File file = new File("highestScore.txt");
        try {
            // * save current score in
            new FileWriter("score.txt", false).close();
            FileWriter writeIntoFile = new FileWriter("score.txt");
            writeIntoFile.write(curr_score);
            writeIntoFile.close();

            // * check if file exists or not
            if (file.createNewFile()) {

                //System.out.println("File created: " + file.getName());
                // * if no file push current score ,no need compare

                FileWriter writeInto = new FileWriter("highestScore.txt");
                writeInto.write(curr_score);
                writeInto.close();

            } else {

                //System.out.println("File already exists.");

                Scanner readInto = new Scanner(file);
                
                while (readInto.hasNextLine()) {
                    
                    String pastData = readInto.nextLine();
                    int pastScore = Integer.parseInt(pastData);

                    // comparing past score with current score
                    if(pastScore < score)
                    {
                        //System.out.println("compare");

                        //clear all data in file
                        new FileWriter("highestScore.txt", false).close();

                        //write the new high score
                        FileWriter writeInto = new FileWriter("highestScore.txt");
                        writeInto.write(curr_score);
                        writeInto.close();
                    }

                }
                readInto.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

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
                    consecutiveKillsCnt++;
                    if (consecutiveKillsCnt == consecutiveKillsToGetPoint) {
                        tempScore += consecutiveKillsPoint;
                        consecutiveKillsCnt = 0;
                    }
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

        renderMute();
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
                game.setScreen(new AirWaterScreen(game,soundState));
            }
        }

        if (Gdx.input.getX() >= 538 && Gdx.input.getX() <= 1023 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 478
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 478 + 83) {
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new GameMenuScreen(game,soundState));
            }
        }

        if (Gdx.input.getX() >= 538 && Gdx.input.getX() <= 1023 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 365
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 365 + 83) {
            if (Gdx.input.isTouched()) {
                if(!soundState)
                {
                    soundState = true ;
                    SoundManager.gameLevel3.play();
                    showMute = false;
                }
            }
        }

        if (Gdx.input.getX() >= 538 && Gdx.input.getX() <= 1023 &&
                MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() >= 250
                && MyGdxGame.SCREEN_HEIGHT - Gdx.input.getY() <= 250 + 83) {
            if (Gdx.input.isTouched()) {
                if(soundState)
                {
                    soundState = false;
                    SoundManager.gameLevel3.stop();
                    showMute = true;
                }
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
        if(showMute) game.batch.draw(mute, 1580, 830,100,100);
    }

    public void renderPauseIcon(){

        game.batch.draw(pauseIcon, pauseIconX, pauseIconY ,90, 80);
    }

    public void renderHealthBar(){

        float status = Gdx.graphics.getWidth() * health ;

        if( status < 1200 && status >= 700 ) state = 1;
        else if(status < 690 && status >= 400) state = 2;
        else if(status < 400 ) state = 3 ;

        game.batch.draw(healthBar[state],0,0,Gdx.graphics.getWidth() * health , healthBarHeight);

    }

    public void renderMute(){
        if(showMute) game.batch.draw(mute, 1520, 890,50,50);
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

        background.dispose();
        pause.dispose();
        pauseIcon.dispose();
        healthBar[0].dispose();
        healthBar[1].dispose();
        healthBar[2].dispose();
        healthBar[3].dispose();
    }
}