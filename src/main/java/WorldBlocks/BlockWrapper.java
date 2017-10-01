package WorldBlocks;


import Entities.Entity;
import Rendering.Teselator;
import Vectors.BlockPos;
import Vectors.Vector2;
import WorldProviding.World;
import com.jogamp.opengl.GL2;

import static Rendering.WorldRenderer.GL20;



/**
 * Создано Юрием в 01.01.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class BlockWrapper {

    private int meta;
    private Block block;
    private BlockPos pos;
    private int destroyStage = 0;

    public BlockWrapper(Block block, BlockPos pos) {
        this(1, block, pos);
    }

    public BlockWrapper(int meta, Block block, BlockPos pos) {
        this.meta = meta;
        this.block = block;
        this.pos = pos;
    }

    public int getMeta() {
        return meta;
    }

    public void setMeta(int meta) {
        this.meta = meta;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public Block getBlock() {
        return block;
    }

    public synchronized void drawAt(float x, float y, GL2 gl) {
        gl.glPushMatrix();
        {
            Teselator te = Teselator.instance;

            te.bindTexture(block.getTexture(pos));

            GL20.glTranslated(0, 0, -0.001);

            te.startDrawingQuads();
            {

                te.add2DVertexWithUV(x + 0, y + 0, 0, 1);
                te.add2DVertexWithUV(x + 0, y + 0.1f, 0,0);
                te.add2DVertexWithUV(x + 0.1f, y + 0.1f, 1, 0);
                te.add2DVertexWithUV(x + 0.1f, y + 0, 1, 1);
            }
            te.draw();

            if (destroyStage != 0) {
                int stage = (int) (9 / ((block.getHardness() * 1.0) / (destroyStage * 1.0)));

                if (stage > 9) stage = 9;

                GL20.glTranslated(0, 0, -0.001);

                te.bindTexture("destroy_stages/destroy_stage_" + stage + ".png");
                te.startDrawingQuads();
                {

                    te.add3DVertexWithUV(x + 0, y + 0, -0.00f, 0, 1);
                    te.add3DVertexWithUV(x + 0, y + 0.1f, -0.00f, 0,0);
                    te.add3DVertexWithUV(x + 0.1f, y + 0.1f, -0.00f, 1, 0);
                    te.add3DVertexWithUV(x + 0.1f, y + 0, -0.00f, 1, 1);
                }
                te.draw();

                GL20.glTranslated(0, 0, 0.001);


            }

        }
        gl.glPopMatrix();
    }

    public boolean isVectorInBounds(Vector2 pos) {
        float mx = getPos().getX();
        float my = getPos().getY();

        float x = pos.x;
        float y = pos.y;

        return (x <= (mx * 0.1 + 0.1)) && (x >= (mx * 0.1)) && (y <= (my * 0.1 + 0.1)) && (y >= (my * 0.1));
    }

    public synchronized void addDestroy(int power, Entity entity) {
        if (block.isInvincible()) return;
        destroyStage += power;
        if (destroyStage > block.getHardness()) {
            onDestroyByEntity(entity);
        }
    }

    public void explosion(int power) {
        destroyStage += power;
        if (destroyStage > block.getHardness()) {
            onDestroyByExplosion();
        }
    }

    public void onDestroyByEntity(Entity entity) {
        World.getInstance().destroyBlock(pos);
        block.onDestroyByEntity(entity, pos);
    }

    public void onDestroyByExplosion() {
        World.getInstance().destroyBlock(pos);
        block.onDestroyByExplosion(pos);
    }

    public void onBlockPlace() {
        block.onBlockPlace(this);
    }


    public void onRemove() {

    }
}
