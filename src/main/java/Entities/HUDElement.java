package Entities;

import Vectors.Vector2;
import com.jogamp.opengl.GL2;

/**
 * Created by Yuri on 03.01.17.
 */
public abstract class HUDElement {

    public boolean isVectorInBounds(Vector2 vector) {
        return false;
    }

    public abstract void drawAt(GL2 gl);

    public boolean onElementClicked() {
        return false;
    }

    public boolean isClickable() {
        return false;
    }
}
