package Entities.Shells;

import Entities.Entity;
import Vectors.Vector2;
import WorldBlocks.BlockWrapper;
import WorldProviding.World;

/**
 * Created by Yuri on 11.01.17.
 */
public class PowerfulShell extends Shell {

    public PowerfulShell() {

        setPower(5).setShootDelay(20);
    }

    @Override
    public void onBlockCollision(BlockWrapper block, ShellWrapper wrapper) {
        super.onBlockCollision(block, wrapper);

        World.getInstance().createExplosion(World.getInstance().toBlockPos(wrapper.getPosition()), 5);
    }

    @Override
    public ShellWrapper createWrapper(Vector2 position, Vector2 direction, float rotation, Entity tankEntity) {
        return new PowerfulShellWrapper(position, rotation, this, direction, tankEntity);
    }
}
