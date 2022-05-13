package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Stack;


/*
this class is used for,
1. creating and showing spell animation.
2. calculating spell count. (the number of spells user have)
3. showing spell count
4. shooting spell according to user input
 */
public class Spell implements ForObject{
    Collision collision; // to check if there is a collision with monsters


    // spell variables starts
    public float spellX; // spell x-axis
    public float spellY; // spell y-axis
    public float spellWidth; // spell width
    public float spellHeight; // spell height
    public float spellHorizontalSpeed = 180f; // speed of spells. (horizontally)
    public boolean spellRemove = false; // to check if the spell should remove or not
    public int spellType; // is the spell fire or freeze? (1 means fire. 0 means freeze)
    // spell variables ends


    // fire variables starts
    public float fireWidth = 100; // width of fire spell
    public float fireHeight = 40; // height of fire spell
    public float fireFrameDuration = 0.1f; // frame duration of every texture
    public float fireStateTime = 0f; // to show the animation it will store time. (will be used make loop)
    public int fireImgCnt = 6; // no of textures for fire animation.
    Animation fireAnimation; // to create animation
    TextureRegion fireReg; // to store textures
    // fire variable ends


    // freeze variables starts
    public float freezeWidth = 100; // freeze spell width
    public float freezeHeight = 40; // freeze spell height
    public float freezeFrameDuration = 0.1f; // frame duration for every texture
    public float freezeStateTime = 0f; // to show the animation it will store time. (will be used make loop)
    public int freezeImgCnt = 7; // image count of freeze animation
    Animation freezeAnimation; // to create animation
    TextureRegion freezeReg; // to store textures
    // freeze variable ends


    // digit starts
    static Texture[] digitTexture; // to store digit textures.
    public static int totalSpellRemaining = 5; // number of initial spells
    public static float newSpellCreateDelayTime = 2f; // after how long the character will get a new spell.
    public static float newSpellCreateStateTime = 0f; // to calculate time
    public static int digitCnt = 10; // total digit counts.
    public static float digitExtraDistanceYAxis = 10f; // extra distance from top
    public static float digitWidth = 30f; // width of digits
    public static float digitHeight = 40f; // height of digits
    public static float digitX; // x-axis of digits
    public static float digitY = Gdx.graphics.getHeight() - digitHeight - digitExtraDistanceYAxis; // y-axis of digits.
    // digit ends


    /*
    default constructor
     */
    public Spell(float characterX, float characterY, float characterWidth, float characterHeight, boolean inWater) {
        if (inWater) {
            // if character is in water

            createFreezeAnimation(); // creating spell animation
            spellType = 0; // 0 means freeze spell
            spellWidth = freezeWidth; // setting width
            spellHeight = freezeHeight; // setting height
        }
        else {
            // the character is in air

            createFireAnimation(); // creating spell animation
            spellType = 1; // 1 means fire animation
            spellWidth = fireWidth; // setting width
            spellHeight = fireHeight; // setting height
        }

        spellX = characterX + characterWidth; // setting x-axis of character.
        spellY = characterY + characterHeight / 2f - spellHeight / 2f; // modifying y-axis

        collision = new Collision(spellX, spellY, spellWidth, spellHeight); // creating collision object
    }


    /*
    updating spell. (changing y-axis, updating collision object)
     */
    @Override
    public void update() {
        spellX += spellHorizontalSpeed * Gdx.graphics.getDeltaTime(); // changing x-axis
        collision.update(spellX, spellY, spellWidth, spellHeight); // updating collision object

        if (spellX >= Gdx.graphics.getWidth()) spellRemove = true; // if the spell is out of screen, it should be removed.
    }


    /*
    to draw spell animation
     */
    @Override
    public void render(SpriteBatch batch) {
        if (spellType == 0) {
            // spell is freeze

            renderFreezeAnimation(batch); // drawing freeze animation
        }
        else {
            // spell is fire

            renderFireAnimation(batch); // drawing fire animation
        }
    }


    /*
    checks for collision with other objects.
     */
    @Override
    public Collision getCollision() {
        return collision;
    }


    /*
    to create fire animation.
     */
    public void createFireAnimation() {
        Array<TextureRegion> textureRegion = new Array<>(); // to store all the textures.
        for (int i = 1; i <= fireImgCnt; i++) {
            Texture texture = new Texture("Fire\\fire" + i + ".png"); // creating new texture
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // filtering texture
            textureRegion.add(new TextureRegion(texture));// adding texture to texture region
        }
        fireAnimation = new Animation<>(fireFrameDuration, textureRegion); // creating animation
        fireStateTime = 0f; // setting state time 0
    }


    /*
    to draw fire animation
     */
    public void renderFireAnimation(SpriteBatch batch) {
        fireStateTime += Gdx.graphics.getDeltaTime(); // to calculate how long the animation is showing
        fireStateTime %= (fireImgCnt * fireFrameDuration); // to make loop
        fireReg = (TextureRegion) fireAnimation.getKeyFrame(fireStateTime); // setting a texture for a specific time period

        batch.draw(fireReg, spellX, spellY, spellWidth / 2, spellHeight / 2, spellWidth,
                spellHeight,1,1,0); // drawing fire animation
    }


    /*
    creating freeze animation
     */
    public void createFreezeAnimation() {
        Array<TextureRegion> textureRegion = new Array<>(); // to store all the textures.
        for (int i = 1; i <= freezeImgCnt; i++) {
            Texture texture = new Texture("Freeze\\freeze" + i + ".png"); // creating new texture
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); // filtering texture
            textureRegion.add(new TextureRegion(texture));// adding texture to texture region
        }
        freezeAnimation = new Animation<>(freezeFrameDuration, textureRegion); // creating animation
        freezeStateTime = 0f; // setting state time 0
    }


    /*
    to draw freeze animation
     */
    public void renderFreezeAnimation(SpriteBatch batch) {
        freezeStateTime += Gdx.graphics.getDeltaTime(); // to calculate how long the animation is showing
        freezeStateTime %= (freezeImgCnt * freezeFrameDuration); // to make loop
        freezeReg = (TextureRegion) freezeAnimation.getKeyFrame(freezeStateTime); // setting a texture for a specific time period

        batch.draw(freezeReg, spellX, spellY, spellWidth / 2, spellHeight / 2, spellWidth,
                spellHeight,1,1,0); // drawing freeze animation
    }


    /*
    updating the number of spells the user have to use.
     */
    public static void updateSpellCnt() {
        newSpellCreateStateTime += Gdx.graphics.getDeltaTime(); // updating time
        if (newSpellCreateStateTime >= newSpellCreateDelayTime) {
            // it is time to add new spell

            totalSpellRemaining++; // adding a new spell
            newSpellCreateStateTime = 0f; // setting state time to 0
        }
    }


    /*
    to show the user how many spell he has to use.
     */
    public static void renderSpellCnt(SpriteBatch batch) {
        int temp = totalSpellRemaining; // assigning value to temp to store digits into a stack.

        Stack<Integer> stack = new Stack<>(); // creating a stack
        while (temp > 0) {
            // pushing digits into stack

            stack.push(temp % 10);
            temp /= 10;
        }

        if (stack.empty()) stack.push(0); // if the spell cnt is 0, the value will not store into the stack.
            // so pushing it manually.

        digitX = 100f; // setting digit x (initial x-axis of digits)

        while (!stack.empty()) {
            batch.draw(digitTexture[stack.pop()], digitX, digitY, digitWidth, digitHeight); // drawing digits.
            digitX += digitWidth; // changing x-axis for next digit.
        }
    }


    /*
    creating digit textures.
     */
    public static void setDigitTexture() {
        digitTexture = new Texture[digitCnt]; // to store textures
        for (int i = 0; i < digitCnt; i++) {
            digitTexture[i] = new Texture("Digit\\" + i + ".png"); // creating and storing textures.
        }
    }
}