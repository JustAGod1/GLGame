package Entities;

import Vectors.Vector2;

/**
 * Created by Yuri on 04.01.17.
 */
public class Building extends Entity {

    @Override
    public void updateEntity() {

    }

    @Override
    public boolean isUpdatable() {
        return false;
    }

    @Override
    public void onDraw() {

    }

    @Override
    public void onEntityCollision(Entity entity) {

    }

    @Override
    public boolean isVectorInBounds(Vector2 vector) {
        return false;
    }
}
