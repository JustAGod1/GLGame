package Entities;

/**
 * Created by Yuri on 12.01.17.
 */
public class NPCTankAI extends TankAI {


    @Override
    public void update() {
        tankEntity.shoot();
    }
}
