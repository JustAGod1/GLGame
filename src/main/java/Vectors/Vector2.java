package Vectors;

import static com.jogamp.opengl.math.FloatUtil.acos;
import static com.jogamp.opengl.math.FloatUtil.asin;
import static java.lang.Math.*;

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

    public Vector2(double direction) {
        this.x = (float) cos(toRadians(direction));
        this.y = (float) sin(toRadians(direction));
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2 vector2 = (Vector2) o;

        return Float.compare(vector2.x, x) == 0 && Float.compare(vector2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("x: %f; y: %f", x, y);
    }

    public Vector2 decrease(Vector2 decrement) {
        x -= decrement.x;
        y -= decrement.y;

        return this;
    }

    public float length() {
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Vector2 normalize() {
        float length = length();
        if (length == 0) {
            x = 0;
            y = 0;
            return this;
        }

        float invertedLength = 1 / length;

        x *= invertedLength;
        y *= invertedLength;

        return this;
    }

    public float direction() {
        Vector2 normalizedVector = this.clone().normalize();
        float sin = normalizedVector.y;
        float cos = normalizedVector.x;
        float res = 0;

        if (sin > 0 && cos >= 0) {
            res = asin(sin);
        }

        if (sin > 0 && cos < 0) {
            res = acos(cos);
        }

        if (sin < 0 && cos < 0) {
            res = asin(sin);
            res = (float) toDegrees(res);
            res = res - (90 + res) * 2;
            res = (float) toRadians(res);
        }

        if (sin < 0 && cos >= 0) {
            res = asin(sin);
        }

        return (float) toDegrees(res);
    }
}
