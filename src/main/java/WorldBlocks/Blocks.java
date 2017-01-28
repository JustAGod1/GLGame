package WorldBlocks;

import Entities.EffectHolder;
import Entities.Entity;
import Vectors.BlockPos;
import Vectors.Vector2;
import WorldProviding.World;

import static WorldProviding.TankEntity.Effect.SHOOT_SPEED_UP;

/**
 * Создано Юрием в 01.01.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public final class Blocks {
    public static final Block stone = new Block().setHardness(2);
    public static final Block tnt = new Block() {
        @Override
        public void onDestroyByEntity(Entity entity, BlockPos pos) {
            super.onDestroyByEntity(entity, pos);

            World.getInstance().createExplosion(pos, 5);
        }

        @Override
        public void onDestroyByExplosion(BlockPos pos) {
            World.getInstance().createExplosion(pos, 5);
        }


        @Override
        public String getTexture(BlockPos pos) {
            return "tnt.png";
        }

        @Override
        public int getHardness() {
            return 0;
        }
    };
    public static final Block chest = new Block() {
        @Override
        public void onDestroyByEntity(Entity entity, BlockPos pos) {
            super.onDestroyByEntity(entity, pos);
            //if (entity instanceof TankEntity) ((TankEntity) entity).setShootDelayModifier(0.2f, 60);

            EffectHolder holder = new EffectHolder(SHOOT_SPEED_UP, 0.2f, 60 * 20, 60 * 20, new Vector2(pos.getX() * 0.1f, pos.getY() * 0.1f));
            World.getInstance().addEntity(holder);
        }
    }.setTexture("chest.png");
    public static final Block turret = new Block() {

        @Override
        public BlockWrapper createWrapper(BlockPos pos) {
            return new TurretWrapper(pos);
        }

    }.setTexture("turret_base.png").setHardness(30);
}
