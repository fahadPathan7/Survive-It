package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.SoundManager;

import java.util.Stack;

public class GameTime {
    public float time = 0f;
    public float totalGameDuration = 105f;
    public boolean gameEnd = false;


    Texture[] digitTexture;
    public int digitTextureCnt = 10;
    public float extraDistanceXAxis = 20f;
    public float extraDistanceYAxis = 10f;
    public float digitWidth = 30f;
    public float digitHeight = 40f;
    public float digitX;
    public float digitY = Gdx.graphics.getHeight() - digitHeight - extraDistanceYAxis;


    Texture[] transparentDigitTexture;
    public int transparentDigitTextureCnt = 4;
    public float transparentDigitWidth = 300f;
    public float transparentDigitHeight = 400f;
    public float transparentDigitX = Gdx.graphics.getWidth() / 2f - transparentDigitWidth / 2f;
    public float transparentDigitY = Gdx.graphics.getHeight() / 2f - transparentDigitHeight / 2f;


    public float sCharacterX;
    public float sCharacterY = digitY;
    public float sCharacterWidth = 30f;
    public float sCharacterHeight = 30f;
    Texture sTexture;


    public GameTime() {
        setDigitTexture();
    }

    public void update() {
        time += Gdx.graphics.getDeltaTime();
        if (time >= totalGameDuration + 0.5f) gameEnd = true;
    }

    public void render(SpriteBatch batch) {
        int temp = (int)totalGameDuration - (int)time;
        int temp1 = (int)time;

        Stack<Integer> stack = new Stack<>();
        while (temp > 0) {
            stack.push(temp % 10);
            temp /= 10;
        }

        if (stack.empty()) stack.push(0);

        float tempWidth = stack.size() * digitWidth + sCharacterWidth;

        digitX = Gdx.graphics.getWidth() - tempWidth - extraDistanceXAxis;

        while (!stack.empty()) {
            batch.draw(digitTexture[stack.pop()], digitX, digitY, digitWidth, digitHeight);
            digitX += digitWidth;
        }

        sCharacterX = digitX;
        batch.draw(sTexture, sCharacterX, sCharacterY, sCharacterWidth, sCharacterHeight);

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

    public void setDigitTexture() {
        digitTexture = new Texture[digitTextureCnt];
        for (int i = 0; i < digitTextureCnt; i++) {
            digitTexture[i] = new Texture("Digit\\" + i + ".png");
        }

        sTexture = new Texture("Digit\\s.png");

        transparentDigitTexture = new Texture[transparentDigitTextureCnt];
        for (int i = 0; i < transparentDigitTextureCnt; i++) {
            transparentDigitTexture[i] = new Texture("Digit\\transparent" + i + ".png");
        }
    }
}
