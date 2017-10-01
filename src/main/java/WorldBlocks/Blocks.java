package WorldBlocks;

import Entities.EffectHolder;
import Entities.Entity;
import Entities.Shells.Shells;
import Vectors.BlockPos;
import Vectors.Vector2;
import WorldBlocks.Turret.Turret;
import WorldProviding.World;

import java.util.Random;

import static Entities.TankEntity.Effect.*;

/**
 * Создано Юрием в 01.01.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public final class Blocks {
    public static final Block STONE = new Block().setHardness(2);
    public static final Block TNT = new Block() {
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
    public static final Block CHEST = new Block() {
        @Override
        public void onDestroyByEntity(Entity entity, BlockPos pos) {
            super.onDestroyByEntity(entity, pos);
            //if (entity instanceof TankEntity) ((TankEntity) entity).setShootDelayModifier(0.2f, 60);

            Random random = new Random();

            int i = random.nextInt(3);
            EffectHolder holder = null;
            switch (i) {
                case 0: {
                    holder = new EffectHolder(SHOOT_SPEED_UP, 0.2f, 25 * 20, 60 * 20, new Vector2(pos.getX() * 0.1f, pos.getY() * 0.1f));
                    break;
                }
                case 1: {
                    holder = new EffectHolder(HEALTH, 10, 0, 60 * 20, new Vector2(pos.getX() * 0.1f, pos.getY() * 0.1f));
                    break;
                }
                case 2: {
                    holder = new EffectHolder(SPEED_UP, 3f, 20 * 20, 60 * 20, new Vector2(pos.getX() * 0.1f, pos.getY() * 0.1f));
                    break;
                }
            }
            World.getInstance().addEntity(holder);
        }
    }.setTexture("chest.png");
    public static final Turret TURRET = (Turret) new Turret();
    public static final Turret RAPID_TURRET = new Turret().setTexture("rapid_turret_base.png").setShell(Shells.RAPID_TURRET_SHELL);
    public static final Block BEDROCK = new Block().setInvincible(true).setTexture("bedrock.png");
}
