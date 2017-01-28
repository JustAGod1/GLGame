package Entities.Shells;

import Entities.Entity;
import Vectors.Vector2;
import WorldBlocks.BlockWrapper;
import WorldBlocks.Blocks;

/**
 * Created by Yuri on 27.01.17.
 */
public class Shells {
    public static final PowerfulShell POWERFUL_SHELL = new PowerfulShell();
    public static final Shell SHELL = new Shell();
    public static final UnusualShell UNUSUAL_SHELL = new UnusualShell();
    public static final Shell TURRET_SHELL = new Shell() {

        @Override
        public void onBlockCollision(BlockWrapper block, ShellWrapper wrapper) {
            if (!(block.getBlock() == Blocks.turret)) super.onBlockCollision(block, wrapper);
        }

        @Override
        public ShellWrapper createWrapper(Vector2 position, Vector2 direction, float rotation, Entity entity) {
            return new ShellWrapper(position, rotation, TURRET_SHELL, direction, entity) {
                @Override
                public void onEntityCollision(Entity entity) {
                    if ((entity instanceof ShellWrapper) && (((ShellWrapper) entity).getShell() == TURRET_SHELL))
                        return;
                    super.onEntityCollision(entity);
                }
            };
        }
    }.setTexture("tnt.png").setPower(2).setShootDelay(30);
}
