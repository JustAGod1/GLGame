package Entities.Shells.TurretShell;

import Entities.Entity;
import Entities.Shells.Shell;
import Entities.Shells.ShellWrapper;
import Vectors.Vector2;
import WorldBlocks.BlockWrapper;
import WorldBlocks.Blocks;

/**
 * Created by Yuri on 29.01.17.
 */
public class TurretShell extends Shell {

    public TurretShell() {
        setTexture("tnt.png").setPower(2).setShootDelay(30);
    }

    @Override
    public void onBlockCollision(BlockWrapper block, ShellWrapper wrapper) {
        if (!Blocks.TURRET.getClass().isAssignableFrom(block.getBlock().getClass()))
            super.onBlockCollision(block, wrapper);
    }

    @Override
    public ShellWrapper createWrapper(Vector2 position, Vector2 direction, float rotation, Entity entity) {
        return new TurretShellWrapper(position, rotation, this, direction, entity);
    }
}
