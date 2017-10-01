package WorldBlocks.Turret;

import Entities.Entity;
import Entities.Shells.TurretShell.TurretShell;
import Vectors.BlockPos;
import Vectors.Vector2;
import WorldBlocks.EnemyBlock;
import WorldProviding.World;


/**
 * Created by Yuri on 28.01.17.
 */
public class TurretWrapper extends EnemyBlock {

    private TurretGunEntity entity;

    public TurretWrapper(Turret turret, BlockPos pos, TurretShell shell) {
        super(turret, pos);
        entity = new TurretGunEntity(new Vector2(pos.getX() * 0.1f + 0.05f, pos.getY() * 0.1f + 0.05f), shell);
        World.getInstance().addEntity(entity);
    }

    public void destroy() {
        World.getInstance().removeEntity(entity);
    }

    @Override
    public void onDestroyByEntity(Entity entity) {
        destroy();
        super.onDestroyByEntity(entity);
    }

    @Override
    public void onDestroyByExplosion() {
        destroy();
        super.onDestroyByExplosion();
    }

    @Override
    public void onRemove() {
        destroy();
    }


    @Override
    public int getScore() {
        return 10;
    }
}
