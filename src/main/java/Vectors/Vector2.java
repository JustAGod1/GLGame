package Vectors;

/**
 * Created by Yuri on 29.11.16.
 */
public class Vector2 {
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Vector2 clone() {
        return new Vector2(x, y);
    }

    public void add(Vector2 direction) {
        x += direction.x;
        y += direction.y;
    }
}
