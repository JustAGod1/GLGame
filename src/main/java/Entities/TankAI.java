package Entities;

import WorldProviding.TankEntity;

/**
 * Created by Yuri on 10.01.17.
 */
public abstract class TankAI extends AI {

    protected TankEntity tankEntity;

    public void setTankEntity(TankEntity tankEntity) {
        this.tankEntity = tankEntity;
    }
}
