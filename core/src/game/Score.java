package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/*
this class is used for,
1. calculating and showing score on screens.
 */
public class Score{
    public int score = 0; // to store score of a match


    // digit starts
    Texture[] digitTexture; // to store the textures of digits
    public int digitCnt = 10; // total digit count
    public float digitExtraDistanceYAxis = 10f; // distance from top
    public float digitWidth = 30f; // width of every digit
    public float digitHeight = 40f; // height of every digit
    public float digitX; // x-axis of digit
    public float digitY = Gdx.graphics.getHeight() - digitHeight - digitExtraDistanceYAxis; // y-axis of digit
    // digit ends


    // negative sign starts
    public float negativeSignWidth = 30f; // width of negative sign
    public float negativeSignHeight = 10f; // height of negative sign
    public float negativeSignX; // x-axis of negative sign
    public float negativeSignExtraDistanceYAxis = digitExtraDistanceYAxis + digitHeight / 2f; // it is used to make the
        // placement of negative sign in the middle of digits. (like -100)
    public float negativeSignY = Gdx.graphics.getHeight() - negativeSignExtraDistanceYAxis - negativeSignHeight / 2f; // y-axis of
        // negative sign
    public float negativeSignAndDigitGap = 5f; // distance between negative sign and digits.
    Texture negativeSignTexture; // to store the texture of negative sign.
    // negative sign ends


    // * for highScore Screen
    public float extraYAxis = 0f;
    public float extraXAxis = 0f;


    /*
    default constructor.
     */
    public Score() {
        setDigitTexture(); // setting the textures of digits and negative sign.
    }


    /*
    to update score.
     */
    public void update(int newPoint) {
        score += newPoint; // updating score.
    }


    /*
    to render the textures.
     */
    public void render(SpriteBatch batch) {
        int temp = score; // assigning score in a temp variable for storing digits into a stack.
        if (temp < 0) {
            temp *= -1; // working with absolute value.
        }

        Stack<Integer> stack = new Stack<>(); // to store digits.
        while (temp > 0) {
            // storing the digits into a stack

            stack.push(temp % 10);
            temp /= 10;
        }

        if (stack.empty()) stack.push(0); // if the score is 0, the value will not store into the stack. so pushing it manually.

        float tempWidth = stack.size() * digitWidth; // calculating total width of digits.
        if (score < 0) tempWidth += negativeSignWidth + negativeSignAndDigitGap; // if score is negative adding the
            // width of the negative sign too.

        digitX = Gdx.graphics.getWidth() / 2f - tempWidth / 2f; // calculating x-axis. x-axis will always change according to
            // the width of digits. (score will be shown in the center)

        if (score < 0) {
            // if score is negative, drawing negative sign first.

            negativeSignX = digitX;
            batch.draw(negativeSignTexture, negativeSignX + extraXAxis, negativeSignY - extraYAxis,
                    negativeSignWidth, negativeSignHeight); // drawing negative sign
            digitX += negativeSignWidth + negativeSignAndDigitGap; // changing x-axis for next digit
        }

        while (!stack.empty()) {
            batch.draw(digitTexture[stack.pop()], digitX + extraXAxis, digitY - extraYAxis,
                    digitWidth, digitHeight); // drawing digits
            digitX += digitWidth; // changing x-axis for next digit
        }
    }


    /*
    to set the textures of digits and negative sign.
     */
    public void setDigitTexture() {
        digitTexture = new Texture[digitCnt]; // creating texture array for storing textures.
        for (int i = 0; i < digitCnt; i++) {
            digitTexture[i] = new Texture("Digit\\" + i + ".png"); // creating digit textures.
        }

        negativeSignTexture = new Texture("Digit\\negative.png"); // creating negative sign texture.
    }
}