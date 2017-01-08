package WorldProviding;


import Blocks.Block;
import Blocks.BlockWrapper;
import Rendering.Teselator;
import Vectors.BlockPos;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

import java.util.HashMap;
import java.util.Iterator;

import static Rendering.WorldRenderer.GL20;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW_MATRIX;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION_MATRIX;

/**
 * Создано Юрием в 31.12.16.
 *
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class Chunk implements Iterable<BlockWrapper> {

    private HashMap<BlockPos, BlockWrapper> blocks = new HashMap<>();

    public void generate() {

    }

    public void setBlock(BlockPos pos, Block block) {
        blocks.put(pos, new BlockWrapper(block, pos));
    }

    public void setBlock(BlockPos pos, BlockWrapper block) {
        blocks.put(pos, block);
    }

    @Override
    public Iterator<BlockWrapper> iterator() {
        return blocks.values().iterator();
    }

    public void onDraw(GL2 gl) {
        drawBackground();
    }

    private void drawBackground() {
        float x = -1;
        float y = -1f;


        for (int i = 0; i < World.CHUNK_SIZE; i++) {
            for (int j = 0; j < World.CHUNK_SIZE; j++) {
                GL20.glPushMatrix();



                drawBackgroundTile(x, y);

                GL20.glPopMatrix();



                x += 0.1f;
            }
            y += 0.1f;
            x = -1;
        }

    }

    private void drawBackgroundTile(float x, float y) {
        //GL20.glLoadIdentity();

        GL20.glColor3f(1, 1, 1);
        {
            Teselator te = Teselator.instance;
            te.bindTexture("background.png");

            te.startDrawingQuads();
            {
                te.add2DVertexWithUV(x + 0, y + 0, 0, 1);
                te.add2DVertexWithUV(x + 0, y + 0.1f,0, 0);
                te.add2DVertexWithUV(x + 0.1f, y + 0.1f, 1, 0);
                te.add2DVertexWithUV(x + 0.1f, y + 0, 1, 1);

            }
            te.draw();


            //GL20.glRotated(315, 0, 0, 1);
        }



    }

}
