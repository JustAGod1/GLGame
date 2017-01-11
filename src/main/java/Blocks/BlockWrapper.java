package Blocks;


import Rendering.Teselator;
import Vectors.BlockPos;
import Vectors.Vector2;
import WorldProviding.World;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import static Rendering.WorldRenderer.GL20;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;


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

    public void drawAt(float x, float y, GL2 gl) {
        gl.glPushMatrix();
        {
            Teselator te = Teselator.instance;

            te.bindTexture("block.png");

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
                int stage = (9 / (block.getHardness() / destroyStage));


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

        return (x < (mx * 0.1 + 0.1)) && (x > (mx * 0.1)) && (y < (my * 0.1 + 0.1)) && (y > (my * 0.1));
    }

    public void addDestroy(int power) {
        destroyStage += power;
        if (destroyStage > block.getHardness()) World.getInstance().destroyBlock(pos);
    }
}
