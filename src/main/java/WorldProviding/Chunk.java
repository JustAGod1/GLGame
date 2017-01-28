package WorldProviding;


import Rendering.Teselator;
import Vectors.BlockPos;
import WorldBlocks.Block;
import WorldBlocks.BlockWrapper;
import com.jogamp.opengl.GL2;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static Rendering.WorldRenderer.GL20;

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
        setBlock(pos, block.createWrapper(pos));

    }

    public void setBlock(BlockPos pos, BlockWrapper block) {
        blocks.put(pos, block);
        block.onBlockPlace();
    }

    @Override
    public Iterator<BlockWrapper> iterator() {
        return blocks.values().iterator();
    }

    public void onDraw(GL2 gl) {
        drawBackground();

        try {
            for (Map.Entry<BlockPos, BlockWrapper> entry : blocks.entrySet()) {
                BlockWrapper block = entry.getValue();
                BlockPos pos = entry.getKey();

                GL20.glPushMatrix();
                {
                    //GL20.glTranslated(-1 * (World.CHUNK_SIZE / 2) * 0.1, -1 * (World.CHUNK_SIZE / 2) * 0.1, 0);
                    block.drawAt((float) (pos.getX() / 10.0), (float) (pos.getY() / 10.0), GL20);
                }
                GL20.glPopMatrix();
            }
        } catch (ConcurrentModificationException ignore) {
        }
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

    public BlockWrapper getBlockByPos(BlockPos pos) {
        pos = pos.convertToChunkPos();

        return blocks.get(pos);
    }

    public void destroyBlock(BlockPos pos) {
        blocks.remove(pos.convertToChunkPos());
    }
}
