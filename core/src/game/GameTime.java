package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.SoundManager;

import java.util.Stack;


/*
this class is used for,
1. controlling the game duration.
 */
public class GameTime {
    public float time = 0f; // to store how long the game is running.
    public float totalGameDuration = 100f; // total game time
    public boolean gameEnd = false; // is the game running or ended.


    // digit starts
    Texture[] digitTexture; // for storing textures of digits.
    public int digitTextureCnt = 10; // no of digit textures.
    public float extraDistanceXAxis = 20f; // distance of last digit from the end of screen.
    public float extraDistanceYAxis = 10f; // distance from the top of screen
    public float digitWidth = 30f; // width of every digit
    public float digitHeight = 40f; // height of every digit
    public float digitX; // x-axis of digit.
    public float digitY = Gdx.graphics.getHeight() - digitHeight - extraDistanceYAxis; // y-axis of digit.
    // digit ends.


    // transparent digit starts
    Texture[] transparentDigitTexture; // to store textures.
    public int transparentDigitTextureCnt = 4; // no of textures
    public float transparentDigitWidth = 300f; // width
    public float transparentDigitHeight = 400f; // height
    public float transparentDigitX = Gdx.graphics.getWidth() / 2f - transparentDigitWidth / 2f; // x-axis
    public float transparentDigitY = Gdx.graphics.getHeight() / 2f - transparentDigitHeight / 2f; // y-axis
    // transparent digit ends.


    // 's' character starts. (used to show second)
    public float sCharacterX; // x-axis
    public float sCharacterY = digitY; // y-axis
    public float sCharacterWidth = 30f; // width
    public float sCharacterHeight = 30f; // height
    Texture sTexture; // to store texture
    // 's' character ends.


    /*
    default constructor.
     */
    public GameTime() {
        setDigitTexture(); // setting digit textures.
    }


    /*
    to update game time
     */
    public void update() {
        time += Gdx.graphics.getDeltaTime(); // updating game time
        if (time >= totalGameDuration + 0.5f) gameEnd = true; // if time is over game should be ended.
    }


    /*
    to show remaining time on screen
     */
    public void render(SpriteBatch batch) {
        int temp = (int)totalGameDuration - (int)time; // storing remaining time in a temp variable
        int temp1 = (int)time; // storing current time in a temp variable

        Stack<Integer> stack = new Stack<>(); // for storing digits.
        while (temp > 0) {
            // storing digits into the stack

            stack.push(temp % 10);
            temp /= 10;
        }

        if (stack.empty()) stack.push(0); // if the time is 0, the value will not store into the stack. so pushing it manually.

        float tempWidth = stack.size() * digitWidth + sCharacterWidth; // total width needed to show the time on screen

        digitX = Gdx.graphics.getWidth() - tempWidth - extraDistanceXAxis; // setting x-axis

        while (!stack.empty()) {
            // drawing remaining time on screen

            batch.draw(digitTexture[stack.pop()], digitX, digitY, digitWidth, digitHeight); // drawing textures
            digitX += digitWidth; // changing x-axis for next digit
        }

        sCharacterX = digitX;
        batch.draw(sTexture, sCharacterX, sCharacterY, sCharacterWidth, sCharacterHeight); // drawing 's' character

        // drawing transparent textures when the game is about to end.
        if (time >= totalGameDuration) {
            batch.draw(transparentDigitTexture[0], transparentDigitX, transparentDigitY, transparentDigitWidth, transparentDigitHeight);
        }
        else if (temp1 >= (int)totalGameDuration - 1) {
            batch.draw(transparentDigitTexture[1], transparentDigitX, transparentDigitY, transparentDigitWidth, transparentDigitHeight);
        }
        else if (temp1 >= (int)totalGameDuration - 2) {
            batch.draw(transparentDigitTexture[2], transparentDigitX, transparentDigitY, transparentDigitWidth, transparentDigitHeight);
        }
        else if (temp1 >= (int)totalGameDuration - 3) {
            batch.draw(transparentDigitTexture[3], transparentDigitX, transparentDigitY, transparentDigitWidth, transparentDigitHeight);
            SoundManager.countdown.play();
        }
    }


    /*
    to create digit textures.
     */
    public void setDigitTexture() {
        digitTexture = new Texture[digitTextureCnt]; // texture array to store textures
        for (int i = 0; i < digitTextureCnt; i++) {
            digitTexture[i] = new Texture("Digit\\" + i + ".png"); // creating and storing new texture
        }

        sTexture = new Texture("Digit\\s.png"); // 's' digit texture

        transparentDigitTexture = new Texture[transparentDigitTextureCnt]; // texture array to store textures
        for (int i = 0; i < transparentDigitTextureCnt; i++) {
            transparentDigitTexture[i] = new Texture("Digit\\transparent" + i + ".png"); // creating and storing new texture
        }
    }
}