package game;

public class Collision {
    float objectX, objectY;
    float objectWidth, objectHeight;

    public Collision(float x, float y, float width, float height) {
        objectX = x;
        objectY = y;
        objectWidth = width;
        objectHeight = height;
    }

    public void update(float x, float y, float width, float height) {
        objectX = x;
        objectY = y;
        objectWidth = width;
        objectHeight = height;
    }

    public boolean isCollide (Collision col) {
        return objectX < col.objectX + col.objectWidth && objectY < col.objectY + col.objectHeight && col.objectX < objectX + objectWidth && col.objectY < objectY + objectHeight;
    }
}
