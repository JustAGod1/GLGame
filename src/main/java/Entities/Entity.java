package Entities;

import Vectors.Vector2;

/**
 * Created by Yuri on 08.01.17.
 */
public abstract class Entity {

    private boolean invincible;

    public abstract void updateEntity();

    public abstract boolean isUpdatable();

    public abstract void onDraw();

    public abstract void onEntityCollision(Entity entity);

    public abstract boolean isVectorInBounds(Vector2 vector);

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }
}
