package Vectors;

/**
 * Created by Yuri on 29.11.16.
 */
public class Vector2 implements Cloneable{
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Vector2 clone() {
        try {
            Vector2 res = (Vector2) super.clone();
            res.x = x;
            res.y = y;
            return res;
        } catch (CloneNotSupportedException e) {e.printStackTrace();}
        return null;
    }

    public void add(Vector2 direction) {
        x += direction.x;
        y += direction.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Vector2) {
            Vector2 v = (Vector2) obj;

            return v.x == x && v.y == y;
        } else return false;
    }

    @Override
    public String toString() {
        return String.format("x: %f; y: %f", x, y);
    }
}
