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
    public float characterWaterWidth = 108;
    public float characterAirWidth = 70;
    public float characterWidth = characterWaterWidth;
    public float characterHeight = 140;
    public static final float CHARACTER_SPEED = 250;
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
    public final float CHARACTER_JUMP_SPEED = jumpMaxHeight / (jumpTime / 2);
    public int jumpDirection = 0;
    public boolean jumpDelay = false;
    // jump animation ends

    // water, air divider starts
    public boolean inWater = true;
    public boolean inAir = false;
    public float waterMinHeight = 0f;
    public float waterMaxHeight = Gdx.graphics.getHeight() / 2f - characterHeight;
    public float airMinHeight = Gdx.graphics.getHeight() / 2f;
    public float airMaxHeight = Gdx.graphics.getHeight() - characterHeight;
    // water, air divider ends


    public Character() {
        collision = new Collision(characterX, characterY, characterWidth, characterHeight);

        // creating run animation
        createRunAnimation();

        // creating fly animation
        createFlyAnimation();

        // creating jump animation
        createJumpAnimation();
    }

    @Override
    public void update() {
        // setting character position (in air or water)
        setCharacterPosition();

        // setting character movement in air
        setCharacterAirMovement();

        // setting character bound (not exceeding water or air)
        setCharacterBound();

        // updating collision
        collision.update(characterX, characterY, characterWidth, characterHeight);
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

    public void setCharacterPosition() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.D) && !jumpDelay) {
            inWater = true;
            inAir = false;
            characterY = waterMinHeight;
            characterWidth = characterWaterWidth;
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.A) && !jumpDelay && !inAir) {
            inAir = true;
            inWater = false;
            characterY = airMinHeight;
            characterWidth = characterAirWidth;
        }
    }

    public void setCharacterAirMovement() {
        if (!inAir) return;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            characterY += CHARACTER_SPEED * Gdx.graphics.getDeltaTime();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            characterY -= CHARACTER_SPEED * Gdx.graphics.getDeltaTime();
        }
    }

    public void setCharacterBound() {
        if (inWater) {
            if (characterY <= waterMinHeight) characterY = waterMinHeight;
            else if (characterY >= waterMaxHeight) characterY = waterMaxHeight;
        }
        else if (inAir) {
            if (characterY <= airMinHeight + CHARACTER_SPEED * Gdx.graphics.getDeltaTime()) {
                characterY = airMinHeight + CHARACTER_SPEED * Gdx.graphics.getDeltaTime();
            }
            else if (characterY >= airMaxHeight) characterY = airMaxHeight;
        }
    }

    public void createRunAnimation() {
        Array<TextureRegion> textureRegion = new Array<>();
        for (int i = 1; i <= runImgCnt + 1; i++) {
            if (i == 6) continue;
            Texture texture = new Texture("Run\\r" + i + ".png");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureRegion.add(new TextureRegion(texture));
        }
        runAnimation = new Animation<>(runFrameDuration, textureRegion);
        runStateTime = 0f;
    }

    public void renderRunAnimation(SpriteBatch batch) {
        runStateTime += Gdx.graphics.getDeltaTime();
        runStateTime %= (runFrameDuration * runImgCnt);
        runReg = (TextureRegion) runAnimation.getKeyFrame(runStateTime);

        batch.draw(runReg, characterX, characterY, characterWidth / 2, characterHeight / 2,
                characterWidth,characterHeight,1,1,0);
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
    }

    public void renderFlyAnimation(SpriteBatch batch) {
        flyStateTime += Gdx.graphics.getDeltaTime();
        flyStateTime %= (flyFrameDuration * (flyImgCnt));
        flyReg = (TextureRegion) flyAnimation.getKeyFrame(flyStateTime);

        batch.draw(flyReg, characterX, characterY, characterWidth / 2, characterHeight / 2,
                characterWidth,characterHeight,1,1,0);
    }

    public void createJumpAnimation() {
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
        jumpStateTime += Gdx.graphics.getDeltaTime();

        if (jumpDirection == 0) {
            characterY += CHARACTER_JUMP_SPEED * Gdx.graphics.getDeltaTime();
        }
        else {
            characterY -= CHARACTER_JUMP_SPEED * Gdx.graphics.getDeltaTime();
        }

        if (characterY >= jumpMaxHeight - CHARACTER_JUMP_SPEED * Gdx.graphics.getDeltaTime()) {
            jumpDirection = 1;
        }

        if (jumpStateTime >= jumpTime) {
            jumpDelay = false;
            characterY = 0;
            return;
        }

        jumpReg = (TextureRegion) jumpAnimation.getKeyFrame(jumpStateTime);
        batch.draw(jumpReg, characterX, characterY, characterWidth / 2, characterHeight / 2,
                characterWidth,characterHeight,1,1,0);
    }
}
