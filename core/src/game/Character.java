package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.SoundManager;


/*
this class is used for,
1. creating and drawing character run, jump and fly animation.
2. controlling character position (how far it can go in water and air)
3. controlling character movement. (how it will move in air and water).
4. doing actions according to user input. (jump, fly).
 */
public class Character implements ForObject {
    Collision collision; // for tracking collision of character with other objects


    // character variables starts
    public float characterX = 50f; // x-axis of character
    public float characterY; // y-axis of character (should be same as waterMinDistance)
    public float characterWaterWidth = 100; // character width when in water
    public float characterAirWidth = 70; // character width when in air
    public float characterWidth = characterWaterWidth; // initially character is in water
    public float characterHeight = 140; // character height is always same
    // character variable ends


    // water, air divider starts
    public boolean inWater = true; // is the character in water or not? (initially in water)
    public boolean inAir = false; // is the character in air or not?
    public float waterMinDistance = 10f; // the minimum distance the character can go when in water
    public float waterMaxDistance = Gdx.graphics.getHeight() / 2f - characterHeight; // the maximum distance the character
        // can go when in water.
    public float airMinDistance = Gdx.graphics.getHeight() / 2f; // the minimum distance the character can go when in water.
    public float characterSafeDistanceInAirFromMaxHeight = 60f;
    public float airMaxDistance = Gdx.graphics.getHeight() - characterHeight - characterSafeDistanceInAirFromMaxHeight; // the
        // maximum distance the character can go when in air.
    public float safeDistanceFromAir = 6f; // to avoid unwanted collision with air objects
    public float safeDistanceFromWater = 6f; // to avoid unwanted collision with water objects
    // water, air divider ends


    // run animation starts
    float runStateTime = 0f; // to calculate state time for running animation
    Animation runAnimation; // to create running animation
    TextureRegion runReg; // for animate every texture
    public int runImgCnt = 10; // number of images for run animation
    public float runFrameDuration = 0.075f; // frame duration of every texture
    // run animation ends


    // fly animation starts
    float flyStateTime = 0f; // to calculate state time for fly animation
    Animation flyAnimation; // for creating fly animation
    TextureRegion flyReg; // used to animate every texture
    public int flyImgCnt = 5; // num of images for fly animation
    public float flyFrameDuration = 0.2f; // frame duration for every fly image
    public float characterFlySpeed = 250; // character up and down speed when flying (fps)
    // fly animation ends


    // jump animation starts
    public float jumpStateTime; // to calculate state time
    public float jumpTime = 2f; // the total time duration for jumping
    Animation jumpAnimation; // to create jump animation
    TextureRegion jumpReg; // to animate every texture
    public int jumpImgCnt = 10; // num of images for jump animation
    public float jumpFrameDuration = jumpTime / jumpImgCnt; // frame duration for every texture
    public float jumpMaxHeight = Gdx.graphics.getHeight() / 2f - characterHeight - safeDistanceFromAir; // the maximum height
        // the character will go while jumping.
    public float characterJumpSpeed = (jumpMaxHeight - waterMinDistance) / (jumpTime / 2); // character jump speed (fps)
    public int jumpDirection = 0; // is he going upward or downward? 0 means upward. 1 means downward.
    public boolean jumpDelay = false; // is jumping executing or not? (other actions will be frozen while its true).
    // jump animation ends


    /*
    default constructor.
     */
    public Character() {
        characterY = waterMinDistance; // initially character is in water

        collision = new Collision(characterX, characterY, characterWidth, characterHeight); // creating object of
            // collision class to check for collision with objects.

        // creating run animation
        createRunAnimation();

        // creating fly animation
        createFlyAnimation();

        // creating jump animation
        createJumpAnimation();
    }


    /*
    this method is used to update character position and different characteristics.
     */
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


    /*
    this method is used to render the character from desired class.
     */
    @Override
    public void render(SpriteBatch batch) {
        /*
        the conditions below check which action to perform.
         */
        if (jumpDelay) {
            // if the jumpDelay is true the condition will be executed. and it will continue jumping until
            // character touches the ground and jumpDelay becomes false.

            renderJumpAnimation(batch); // drawing jump animation
        }
        else {
            if (characterY == waterMinDistance && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                // it will jump now

                jumpDirection = 0; // 0 means going upward. 1 means going downward.
                jumpStateTime = 0f; // used to calculate how long the character is jumping. initially 0
                jumpDelay = true; // true means until it finishes jump other actions will be frozen.
                renderJumpAnimation(batch); // drawing jump animation
                SoundManager.jump.play();
            }
            else if (characterY == waterMinDistance) {
                // if the character y-axis is 0, by default it will start running.

                renderRunAnimation(batch); // drawing run animation.
            }
            else if (characterY >= airMinDistance) {
                // if the character is in air, it will start flying.

                renderFlyAnimation(batch); // drawing fly animation.
            }
        }
    }


    /*
    the method will be called to check if there is any collision of character with any other objects.
     */
    @Override
    public Collision getCollision() {
        return collision; // it will return an object of collision objects. which has information about character.
    }


    /*
    the method will get user input and will teleport character in water or air according to command.
     */
    public void setCharacterPosition() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.D) && !jumpDelay) {
            // if this condition is true the character will be teleported into water. if the character is already
            // in water or performing jump, it will not do anything.

            inWater = true; // it is in water.
            inAir = false; // leaving air
            characterY = waterMinDistance; // setting character y-axis at the lowest point of water.
            characterWidth = characterWaterWidth; // setting character width as the width should be in water.

            SoundManager.surfaceChange.dispose();
            SoundManager.surfaceChange.play();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.A) && !jumpDelay && !inAir) {
            // if this condition is true the character will be teleported into air. if the character is already
            // in air or performing jump, it will not do anything.

            inAir = true; // it is in air.
            inWater = false; // leaving water.
            characterY = airMinDistance; // // setting character y-axis at the lowest point of air.
            characterWidth = characterAirWidth; // setting character width as the width should be in air.

            SoundManager.surfaceChange.dispose();
            SoundManager.surfaceChange.play();

        }
    }


    /*
    this method is used get user input. and do action according to command. it will shift the character up or down
    according to command.
     */
    public void setCharacterAirMovement() {
        if (!inAir) return; // if the character is not in the air it will not do action. so it returned.

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            // will shift the character upward.

            characterY += characterFlySpeed * Gdx.graphics.getDeltaTime(); // changing y-axis
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            // will shift the character downward.

            characterY -= characterFlySpeed * Gdx.graphics.getDeltaTime(); // changing y-axis
        }
    }


    /*
    this method is used to make sure the character is within its range both in air and water.
     */
    public void setCharacterBound() {
        if (inWater) {
            // checking if the character is exceeded its limit in water.

            if (characterY <= waterMinDistance) characterY = waterMinDistance;
            else if (characterY >= waterMaxDistance - safeDistanceFromAir) characterY = waterMaxDistance - safeDistanceFromAir;
        }
        else if (inAir) {
            // checking if the character is exceeding its limit in air.

            if (characterY <= airMinDistance + safeDistanceFromWater) {
                characterY = airMinDistance + safeDistanceFromWater;
            }
            else if (characterY >= airMaxDistance) characterY = airMaxDistance;
        }
    }


    /*
    this method is used to create run animation.
     */
    public void createRunAnimation() {
        Array<TextureRegion> textureRegion = new Array<>(); // to store all the textures.
        for (int i = 1; i <= runImgCnt; i++) {
            Texture texture = new Texture("Run\\r" + i + ".png"); // creating new texture
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // filtering texture
            textureRegion.add(new TextureRegion(texture)); // adding texture to texture region
        }
        runAnimation = new Animation<>(runFrameDuration, textureRegion); // creating animation.
        runStateTime = 0f; // setting state time 0
    }


    /*
    to draw run animation
     */
    public void renderRunAnimation(SpriteBatch batch) {
        runStateTime += Gdx.graphics.getDeltaTime(); // to calculate how long the character is running.
        runStateTime %= (runFrameDuration * runImgCnt); // modded for looping the animation
        runReg = (TextureRegion) runAnimation.getKeyFrame(runStateTime); // setting a texture for specific time period.`

        batch.draw(runReg, characterX, characterY, characterWidth / 2, characterHeight / 2,
                characterWidth,characterHeight,1,1,0); // drawing run animation
    }


    /*
    to create fly animation
     */
    public void createFlyAnimation() {
        Array<TextureRegion> textureRegion = new Array<>(); // to store all the textures.
        for (int i = 1; i <= flyImgCnt; i++) {
            Texture texture = new Texture("Fly\\fly" + i + ".png"); // creating new texture
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // filtering texture
            textureRegion.add(new TextureRegion(texture));// adding texture to texture region
        }
        flyAnimation = new Animation<>(flyFrameDuration, textureRegion); // creating animation
        flyStateTime = 0f; // setting state time 0
    }


    /*
    to draw fly animation
     */
    public void renderFlyAnimation(SpriteBatch batch) {
        flyStateTime += Gdx.graphics.getDeltaTime(); // to calculate how long character is flying
        flyStateTime %= (flyFrameDuration * (flyImgCnt)); // modded for looping fly animation
        flyReg = (TextureRegion) flyAnimation.getKeyFrame(flyStateTime); // setting a texture for a specific time period

        batch.draw(flyReg, characterX, characterY, characterWidth / 2, characterHeight / 2,
                characterWidth,characterHeight,1,1,0); // drawing fly animation
    }


    /*
    to create jump animation
     */
    public void createJumpAnimation() {
        Array<TextureRegion> textureRegion = new Array<>(); // to store all the textures
        for (int i = 1; i <= jumpImgCnt; i++) {
            Texture texture = new Texture("Jump\\jump" + i + ".png"); // creating new texture
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // filtering texture
            textureRegion.add(new TextureRegion(texture)); // adding texture to texture region
        }
        jumpAnimation = new Animation<>(jumpFrameDuration, textureRegion); // creating animation
        jumpStateTime = 0f; // setting state time 0
    }


    /*
    to draw jump animation
     */
    public void renderJumpAnimation(SpriteBatch batch) {
        jumpStateTime += Gdx.graphics.getDeltaTime(); // to calculate how long the character is jumping

        if (jumpDirection == 0) {
            // if jumpDirection is 0, the character will go upward.

            characterY += characterJumpSpeed * Gdx.graphics.getDeltaTime();
        }
        else {
            // the character will go downward. (jumpDirection is 1)

            characterY -= characterJumpSpeed * Gdx.graphics.getDeltaTime();
        }

        if (characterY >= jumpMaxHeight) {
            // if the character reached higher position the direction will be changed.

            jumpDirection = 1; // direction is set to 1.
        }

        if (jumpStateTime >= jumpTime) {
            // if jumpTime is crossed for one jump, the character will be moved to initial position, and
            // it will be ready for new action

            jumpDelay = false; // jump is finished so it is false now
            characterY = 0; // setting to initial position
        }

        jumpReg = (TextureRegion) jumpAnimation.getKeyFrame(jumpStateTime); // setting a texture for specific time period
        batch.draw(jumpReg, characterX, characterY, characterWidth / 2, characterHeight / 2,
                characterWidth,characterHeight,1,1,0); // drawing animation.
    }
}