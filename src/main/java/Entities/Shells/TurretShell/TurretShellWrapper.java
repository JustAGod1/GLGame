package Entities.Shells.TurretShell;

import Entities.Entity;
import Entities.Shells.Shell;
import Entities.Shells.ShellWrapper;
import Vectors.Vector2;

import static Entities.Shells.Shells.TURRET_SHELL;

/**
 * Created by Yuri on 29.01.17.
 */
public class TurretShellWrapper extends ShellWrapper {


    public TurretShellWrapper(Vector2 position, float rotation, Shell shell, Vector2 direction, Entity tankEntity) {
        super(position, rotation, shell, direction, tankEntity);
    }

    @Override
    public void onEntityCollision(Entity entity) {
        if ((entity instanceof ShellWrapper) && (((ShellWrapper) entity).getShell() == TURRET_SHELL))
            return;
        super.onEntityCollision(entity);

    }
}
