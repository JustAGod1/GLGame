package Entities;

import Vectors.Vector2;

/**
 * Created by Yuri on 10.01.17.
 */
public abstract class TankAI extends AI {

    protected TankEntity tankEntity;

    public Vector2 getPos() {
        return tankEntity.getPos();
    }

    public TankEntity getTankEntity() {
        return tankEntity;
    }

    public void setTankEntity(TankEntity tankEntity) {
        this.tankEntity = tankEntity;
    }
}
