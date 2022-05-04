package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class Score{
    public int score = 0;

    Texture[] digitTexture;

    public int digitCnt = 10;
    public float digitExtraDistance = 10f;
    public float digitWidth = 30f;
    public float digitHeight = 40f;
    public float digitX;
    public float digitY = Gdx.graphics.getHeight() - digitHeight - digitExtraDistance;

    public float negativeSignWidth = 20;
    public float negativeSignHeight = 10;
    public float negativeSignX;
    public float negativeSignExtraDistance = 10f;
    public float negativeSignY = Gdx.graphics.getHeight() - negativeSignExtraDistance - digitHeight / 2f - negativeSignHeight / 2f;
    public float negativeSignAndDigitGap = 5f;

    public Score(SpriteBatch batch) {
        setDigitTexture();
    }

    public void update(int newPoint) {
        score += newPoint;
    }

    public void render(SpriteBatch batch) {
        int temp = score;
        if (temp < 0) {
            temp *= -1;
        }

        Stack<Integer> stack = new Stack<>();
        while (temp > 0) {
            stack.push(temp % 10);
            temp /= 10;
        }

        if (stack.empty()) stack.push(0);

        float tempWidth = stack.size() * digitWidth;
        if (score < 0) tempWidth += negativeSignWidth + negativeSignAndDigitGap;

        digitX = Gdx.graphics.getWidth() / 2f - tempWidth / 2f;

        if (score < 0) {
            Texture texture = new Texture("Score\\negative.png");
            negativeSignX = digitX;
            batch.draw(texture, negativeSignX, negativeSignY, negativeSignWidth, negativeSignHeight);
            digitX += negativeSignWidth + negativeSignAndDigitGap;
        }

        while (!stack.empty()) {
            batch.draw(digitTexture[stack.pop()], digitX, digitY, digitWidth, digitHeight);
            digitX += digitWidth;
        }
    }

    public void setDigitTexture() {
        digitTexture = new Texture[digitCnt];
        for (int i = 0; i < digitCnt; i++) {
            digitTexture[i] = new Texture("Score\\" + i + ".png");
        }
    }
}
