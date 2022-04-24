package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Character implements ForObject {
    float characterX = 50, characterY = 0;
    public float characterWidth = 100, characterHeight = 147; // change
    private final float CHARACTER_SPEED = 200;
    Collision collision;

    // run animation starts
    float runStateTime = 0f;
    Animation runAnimation;
    TextureRegion runReg;
    public int runRows = 2, runCols = 5;
    public float runFrameDuration = 0.1f;
    // run animation ends

    // fly animation starts
    float flyStateTime = 0f;
    Animation flyAnimation;
    TextureRegion flyReg;
    public int flyRows = 1, flyCols = 5;
    public float flyFrameDuration = 0.1f;
    // fly animation ends

    // jump animation starts
    TextureRegion jumpReg;
    public float jumpTime = 2f;
    public float jumpStateTime;
    Animation jumpAnimation;
    public int jumpRows = 2, jumpCols = 5;
    public float jumpFrameDuration = jumpTime / (jumpRows * jumpCols);
    public float jumpMaxHeight = (float)Gdx.graphics.getHeight() / 2f - characterHeight;
    public float jumpSpeed = jumpMaxHeight / (jumpTime / 2);
    public int jumpDirection = 0;
    public boolean jumpDelay = false;
    // jump animation ends


    public Character() {
        collision = new Collision(characterX, characterY, characterWidth, characterHeight);

        // creating run animation
        createRunAnimation();

        // creating fly animation
        createFlyAnimation();

        // creating jump animation
        System.out.println(jumpMaxHeight + characterHeight); //! test
        jumpMaxHeight -= jumpSpeed * Gdx.graphics.getDeltaTime(); // correcting to avoid unnecessary collusion
        createJumpAnimation();
    }

    @Override
    public void update() {
        // changing character position
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            characterY += CHARACTER_SPEED * Gdx.graphics.getDeltaTime();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            characterY -= CHARACTER_SPEED * Gdx.graphics.getDeltaTime();
        }

        // making boundary of character
        if (characterX <= 0) characterX = 0;
        if (characterX + characterWidth >= Gdx.graphics.getWidth())
            characterX = Gdx.graphics.getWidth() - characterWidth;
        if (characterY <= 0) characterY = 0;
        if (characterY + characterHeight >= Gdx.graphics.getHeight())
            characterY = Gdx.graphics.getHeight() - characterHeight;

        // updating collision
        collision.update(characterX, characterY, characterWidth, characterHeight);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (jumpDelay == true) {
            renderJumpAnimation(batch);
        }

        if (characterY == 0 && Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && jumpDelay == false) {
            jumpDirection = 0;
            jumpStateTime = 0f;
            jumpDelay = true;
            renderJumpAnimation(batch);
//            System.out.println(jumpFrameDuration);
//            System.out.println(jumpSpeed);
//            System.out.println(jumpMaxHeight);
//            System.out.println(jumpSpeed * Gdx.graphics.getDeltaTime());
        }
        else if (characterY == 0 && jumpDelay == false) {
            renderRunAnimation(batch);
        }
        else if (characterY > 0 && jumpDelay == false) {
            renderFlyAnimation(batch);
        }
    }

    @Override
    public Collision getCollision() {
        return collision;
    }

    public void createRunAnimation() {
        // run animation starts
        Texture runSheet = new Texture("Run\\run1.png");

        TextureRegion[][] runningTmp = TextureRegion.split(runSheet,
                runSheet.getWidth() / runCols,
                runSheet.getHeight() / runRows);

        TextureRegion[] runFrames = new TextureRegion[runRows * runCols];
        int index = 0;
        for (int i = 0; i < runRows; i++) {
            for (int j = 0; j < runCols; j++) {
                runFrames[index++] = runningTmp[i][j];
            }
        }
        runAnimation = new Animation<TextureRegion>(runFrameDuration, runFrames);
        runStateTime = 0f;
        // run animation ends
    }

    public void renderRunAnimation(SpriteBatch batch) {
        // run animation starts
        runStateTime += Gdx.graphics.getDeltaTime();
        runStateTime %= (runFrameDuration * (runRows * runCols));
        runReg = (TextureRegion) runAnimation.getKeyFrame(runStateTime);

        batch.draw(runReg, characterX, characterY, characterWidth / 2, characterHeight / 2,
                characterWidth,characterHeight,1,1,0);
        // run animation ends
    }

    public void createFlyAnimation() {
        Texture flySheet = new Texture("Fly\\fly.png");

        TextureRegion[][] flyingTmp = TextureRegion.split(flySheet,
                flySheet.getWidth() / flyCols,
                flySheet.getHeight() / flyRows);

        TextureRegion[] flyFrames = new TextureRegion[1];
//        int index = 0;
//        for (int i = 0; i < flyRows; i++) {
//            for (int j = 0; j < flyCols; j++) {
//                flyFrames[index++] = flyingTmp[i][j];
//            }
//        }

        flyFrames[0] = flyingTmp[0][0];

        flyAnimation = new Animation<TextureRegion>(flyFrameDuration, flyFrames);
        flyStateTime = 0f;
    }

    public void renderFlyAnimation(SpriteBatch batch) {
        // fly animation starts
        flyStateTime += Gdx.graphics.getDeltaTime();
        flyStateTime %= (flyFrameDuration * (flyRows * flyCols));
        flyReg = (TextureRegion) flyAnimation.getKeyFrame(flyStateTime);

        batch.draw(flyReg, characterX, characterY, characterWidth / 2, characterHeight / 2,
                characterWidth,characterHeight,1,1,0);
        // fly animation ends
    }

    public void createJumpAnimation() {
        Texture jumpSheet = new Texture("Jump\\jump.png");

        TextureRegion[][] jumpingTmp = TextureRegion.split(jumpSheet,
                jumpSheet.getWidth() / jumpCols,
                jumpSheet.getHeight() / jumpRows);

        TextureRegion[] jumpFrames = new TextureRegion[jumpRows * jumpCols];
        int index = 0;
        for (int i = 0; i < jumpRows; i++) {
            for (int j = 0; j < jumpCols; j++) {
                jumpFrames[index++] = jumpingTmp[i][j];
            }
        }
        jumpAnimation = new Animation<TextureRegion>(jumpFrameDuration, jumpFrames);
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
        batch.draw(jumpReg, characterX, characterY, characterWidth / 2, characterHeight / 2,
                characterWidth,characterHeight,1,1,0);
    }
}
