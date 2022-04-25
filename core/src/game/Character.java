package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Character implements ForObject {
    float characterX = 50, characterY = 0;
    public float characterWidth = 108, characterHeight = 140; // change
    private final float CHARACTER_SPEED = 250;
    Collision collision;

    // run animation starts
    float runStateTime = 0f;
    Animation runAnimation;
    TextureRegion runReg;
    public int runImgCnt = 9;
    public float runFrameDuration = 0.075f;
    // run animation ends

    // fly animation starts
    float flyStateTime = 0f;
    Animation flyAnimation;
    TextureRegion flyReg;
    public int flyImgCnt = 5;
    public float flyFrameDuration = 0.2f;
    // fly animation ends

    // jump animation starts
    TextureRegion jumpReg;
    public float jumpTime = 2.5f;
    public float jumpStateTime;
    Animation jumpAnimation;
    public int jumpRows = 2, jumpCols = 5;
    public int jumpImgCnt = 10;
    public float jumpFrameDuration = jumpTime / (jumpRows * jumpCols);
    public float jumpMaxHeight = (float)Gdx.graphics.getHeight() / 2f - characterHeight;
    public float jumpSpeed = jumpMaxHeight / (jumpTime / 2);
    public int jumpDirection = 0;
    public boolean jumpDelay = false;
    // jump animation ends

    // water, air divider starts
    public boolean inWater = true;
    public boolean inAir = false;
    public float waterMinHeight = 0f;
    public float waterMaxHeight = Gdx.graphics.getHeight() / 2f - characterHeight;
    public float airMinHeight = Gdx.graphics.getHeight() / 2f + CHARACTER_SPEED * Gdx.graphics.getDeltaTime();
    public float airMaxHeight = Gdx.graphics.getHeight() - characterHeight;
    // water, air divider ends


    public Character() {
        collision = new Collision(characterX, characterY, characterWidth, characterHeight);

        // creating run animation
        createRunAnimation();

        // creating fly animation
        createFlyAnimation();

        // creating jump animation
        //System.out.println(jumpMaxHeight + characterHeight); //! test
        jumpMaxHeight -= jumpSpeed * Gdx.graphics.getDeltaTime(); // correcting to avoid unnecessary collusion
        createJumpAnimation();
    }

    @Override
    public void update() {
        // setting character position (in air or water)
        setCharacterPosition();

        // setting character bound (not exceeding water or air)
        setCharacterBound();

        // updating collision
        collision.update(characterX, characterY, characterWidth, characterHeight);
    }

    public void setCharacterPosition() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && !jumpDelay) {
            characterY = waterMinHeight;
            inWater = true;
            inAir = false;
            characterWidth = 108;
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.S) && !jumpDelay && !inAir) {
            characterY = airMinHeight;
            inAir = true;
            inWater = false;
            characterWidth = 70;
        }
    }

    public void setCharacterBound() {
        if (inWater) {
            if (characterY <= waterMinHeight) characterY = waterMinHeight;
            else if (characterY >= waterMaxHeight) characterY = waterMaxHeight;
        }
        else if (inAir) {
            if (characterY <= airMinHeight) characterY = airMinHeight;
            else if (characterY >= airMaxHeight) characterY = airMaxHeight;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (jumpDelay) {
            renderJumpAnimation(batch);
        }

        if (characterY == 0 && Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !jumpDelay) {
            jumpDirection = 0;
            jumpStateTime = 0f;
            jumpDelay = true;
            renderJumpAnimation(batch);
//            System.out.println(jumpFrameDuration);
//            System.out.println(jumpSpeed);
//            System.out.println(jumpMaxHeight);
//            System.out.println(jumpSpeed * Gdx.graphics.getDeltaTime());
        }
        else if (characterY == 0 && !jumpDelay) {
            renderRunAnimation(batch);
        }
        else if (characterY >= airMinHeight && !jumpDelay) {
            renderFlyAnimation(batch);
        }
    }

    @Override
    public Collision getCollision() {
        return collision;
    }

    public void createRunAnimation() {
        // run animation starts
        Array<TextureRegion> textureRegion = new Array<>();
        for (int i = 1; i <= runImgCnt + 1; i++) {
            if (i == 6) continue;
            Texture texture = new Texture("Run\\r" + i + ".png");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureRegion.add(new TextureRegion(texture));
        }
        runAnimation = new Animation<>(runFrameDuration, textureRegion);
        runStateTime = 0f;
        // run animation ends
    }

    public void renderRunAnimation(SpriteBatch batch) {
        // run animation starts
        runStateTime += Gdx.graphics.getDeltaTime();
        runStateTime %= (runFrameDuration * runImgCnt);
        runReg = (TextureRegion) runAnimation.getKeyFrame(runStateTime);

        batch.draw(runReg, characterX, characterY, characterWidth / 2, characterHeight / 2,
                characterWidth,characterHeight,1,1,0);
        // run animation ends
    }

    public void createFlyAnimation() {
        Array<TextureRegion> textureRegion = new Array<>();
        for (int i = 1; i <= flyImgCnt; i++) {
            if (i == 6) continue;
            Texture texture = new Texture("Fly\\fly" + i + ".png");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureRegion.add(new TextureRegion(texture));
        }
        flyAnimation = new Animation<>(flyFrameDuration, textureRegion);
        flyStateTime = 0f;

        flyAnimation = new Animation<TextureRegion>(flyFrameDuration, textureRegion);
        flyStateTime = 0f;
    }

    public void renderFlyAnimation(SpriteBatch batch) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            characterY += CHARACTER_SPEED * Gdx.graphics.getDeltaTime();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            characterY -= CHARACTER_SPEED * Gdx.graphics.getDeltaTime();
        }
        // fly animation starts
        flyStateTime += Gdx.graphics.getDeltaTime();
        flyStateTime %= (flyFrameDuration * (flyImgCnt));
        flyReg = (TextureRegion) flyAnimation.getKeyFrame(flyStateTime);

        batch.draw(flyReg, characterX, characterY, characterWidth / 2, characterHeight / 2,
                characterWidth,characterHeight,1,1,0);
        // fly animation ends
//        Texture flyPic = new Texture("Fly\\f11.png");
//        batch.draw(flyPic, characterX, characterY, characterWidth, characterHeight);
    }

    public void createJumpAnimation() {
//        Texture jumpSheet = new Texture("Jump\\jump.png");
//
//        TextureRegion[][] jumpingTmp = TextureRegion.split(jumpSheet,
//                jumpSheet.getWidth() / jumpCols,
//                jumpSheet.getHeight() / jumpRows);
//
//        TextureRegion[] jumpFrames = new TextureRegion[jumpRows * jumpCols];
//        int index = 0;
//        for (int i = 0; i < jumpRows; i++) {
//            for (int j = 0; j < jumpCols; j++) {
//                jumpFrames[index++] = jumpingTmp[i][j];
//            }
//        }
//        jumpAnimation = new Animation<TextureRegion>(jumpFrameDuration, jumpFrames);

        Array<TextureRegion> textureRegion = new Array<>();
        for (int i = 1; i <= jumpImgCnt; i++) {
            Texture texture = new Texture("Jump\\jump" + i + ".png");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureRegion.add(new TextureRegion(texture));
        }
        jumpAnimation = new Animation<>(jumpFrameDuration, textureRegion);
        jumpStateTime = 0f;
    }

    public void renderJumpAnimation(SpriteBatch batch) {
        if (jumpDirection == 0) {
            characterY += jumpSpeed * Gdx.graphics.getDeltaTime();
        }
        else {
            characterY -= jumpSpeed * Gdx.graphics.getDeltaTime();
        }
        if (characterY >= jumpMaxHeight) {
            jumpDirection = 1;
            //System.out.println(characterY + characterHeight); //! test
        }
        jumpStateTime += Gdx.graphics.getDeltaTime();

        if (jumpStateTime >= jumpTime) {
            jumpDelay = false;
            characterY = 0;
        }

        jumpReg = (TextureRegion) jumpAnimation.getKeyFrame(jumpStateTime);
        //characterWidth = (jumpReg.getRegionWidth() / jumpReg.getRegionHeight()) * characterHeight;
        batch.draw(jumpReg, characterX, characterY, characterWidth / 2, characterHeight / 2,
                characterWidth,characterHeight,1,1,0);
    }
}
