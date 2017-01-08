package Blocks;


import Rendering.Teselator;
import Vectors.BlockPos;
import com.jogamp.opengl.GL2;

import static com.jogamp.opengl.GL2.*;


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

            te.bindTexture("test_texture.png");
            te.translate(-1f, -1f);

            te.startDrawingQuads();
            {
                /*te.add2DVertexWithUV(0, 0, 0, 1);
                te.add2DVertexWithUV(0, 2f, 1 ,1);
                te.add2DVertexWithUV(2f, 2f, 1, 0);
                te.add2DVertexWithUV(2f, 0, 0, 0);*/




                te.add2DVertexWithUV(0, 0, 0, 1);
                te.add2DVertexWithUV(0, 2f,  1,1);
                te.add2DVertexWithUV(2f, 2f, 1, 0);
                te.add2DVertexWithUV(2f, 0, 0, 0);
            }
            te.draw();
        }
        gl.glPopMatrix();
    }

}
