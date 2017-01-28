package WorldBlocks;


import Entities.Entity;
import Vectors.BlockPos;

/**
 * Создано Юрием в 01.01.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class Block {
    private String texture = "block.png";
    private int hardness = 0;

    public int getHardness() {
        return hardness;
    }

    public Block setHardness(int hardness) {
        this.hardness = hardness;
        return this;
    }

    public void onDestroyByEntity(Entity entity, BlockPos pos) {

    }

    public void onDestroyByExplosion(BlockPos pos) {

    }

    public String getTexture(BlockPos pos) {
        return texture;
    }

    protected Block setTexture(String texture) {
        this.texture = texture;
        return this;
    }

    public BlockWrapper createWrapper(BlockPos pos) {
        return new BlockWrapper(this, pos);
    }

    public void onBlockPlace(BlockWrapper wrapper) {

    }
}
