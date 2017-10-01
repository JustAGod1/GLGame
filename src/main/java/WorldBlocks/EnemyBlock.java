package WorldBlocks;

import Entities.Entity;
import Entities.PlayerAI;
import Entities.TankEntity;
import Vectors.BlockPos;

/**
 * Created by Yuri on 03.05.17.
 */
public abstract class EnemyBlock extends BlockWrapper {
    public EnemyBlock(Block block, BlockPos pos) {
        super(block, pos);
    }

    public EnemyBlock(int meta, Block block, BlockPos pos) {
        super(meta, block, pos);
    }

    public abstract int getScore();

    @Override
    public void onDestroyByEntity(Entity entity) {
        super.onDestroyByEntity(entity);

        if (entity instanceof TankEntity) {
            if (((TankEntity) entity).getAI() instanceof PlayerAI) {
                ((PlayerAI) ((TankEntity) entity).getAI()).addScore(getScore());
            }
        }

    }
}
