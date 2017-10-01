package Entities.Shells;

import Entities.Entity;
import Vectors.Vector2;
import WorldBlocks.BlockWrapper;
import WorldProviding.World;

/**
 * Created by Yuri on 08.01.17.
 */
public class Shell {

    protected String texture = "Shells/default_shell.png";
    protected int power = 1;
    private int shootDelay = 10;

    public ShellWrapper createWrapper(Vector2 position, Vector2 direction, float rotation, Entity entity) {
        return new ShellWrapper(position, rotation, this, direction, entity);
    }

    public void onEntityCollision(Entity entity) {

    }

    public void onBlockCollision(BlockWrapper block, ShellWrapper wrapper) {
        block.addDestroy(getPower(), wrapper.getEntity());
        World.getInstance().removeEntity(wrapper);
    }

    public void onOutOfBorders(ShellWrapper wrapper) {
        World.getInstance().removeEntity(wrapper);
    }

    public int getPower() {
        return power;
    }

    public Shell setPower(int power) {
        this.power = power;
        return this;
    }

    public String getTexture() {
        return texture;
    }

    protected Shell setTexture(String texturePath) {
        this.texture = texturePath;
        return this;
    }

    public int getShootDelay() {
        return shootDelay;
    }

    public Shell setShootDelay(int delay) {
        this.shootDelay = delay;
        return this;
    }
}
