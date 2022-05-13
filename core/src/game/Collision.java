package game;


/*
this class is used for,
1. checking collision of two objects.
 */
public class Collision {
    public float objectX; // x-axis
    public float objectY; // y-axis
    public float objectWidth; // width
    public float objectHeight; // height


    /*
    default constructor
     */
    public Collision(float x, float y, float width, float height) {
        // setting axis and dimensions.
        objectX = x;
        objectY = y;
        objectWidth = width;
        objectHeight = height;
    }


    /*
    used to update object's axis and dimensions.
     */
    public void update(float x, float y, float width, float height) {
        // updating axis and dimensions.
        objectX = x;
        objectY = y;
        objectWidth = width;
        objectHeight = height;
    }


    /*
    to check if there is a collision.
     */
    public boolean isCollide (Collision col) {
        return objectX < col.objectX + col.objectWidth && objectY < col.objectY + col.objectHeight &&
                col.objectX < objectX + objectWidth && col.objectY < objectY + objectHeight;
    }
}
