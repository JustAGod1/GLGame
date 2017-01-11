package Objects;

import Blocks.Block;
import Blocks.BlockWrapper;
import Rendering.Teselator;
import Vectors.Vector2;
import WorldProviding.World;

import static Rendering.WorldRenderer.GL20;

/**
 * Created by Yuri on 08.01.17.
 */
public class Shell extends Entity {

    protected String texture = "Shells/default_shell.png";
    protected Vector2 position;
    protected Vector2 direction;
    protected float rotation;
    protected int power = 1;

    public Shell(Vector2 position, Vector2 direction, float rotation) {
        this.position = position;
        this.direction = new Vector2(direction.x / 50, direction.y / 50);
        this.rotation = rotation;
    }

    @Override
    public void onDraw() {
        Teselator te = Teselator.instance;
        te.bindTexture(texture);

        GL20.glPushMatrix();
        {
            //GL20.glRotated(rotation, 0, 0, 1);
            GL20.glTranslated(position.x, position.y, -0.1f);
            GL20.glRotated(rotation + 90, 0, 0, 1);
            GL20.glColor3f(1, 1, 1);
            te.startDrawingQuads();
            {


                te.add2DVertexWithUV(-0.0125f, -0.0125f, 0, 1);
                te.add2DVertexWithUV(0.0125f, -0.0125f, 1, 1);
                te.add2DVertexWithUV(0.0125f, 0.0125f, 1, 0);
                te.add2DVertexWithUV(-0.0125f, 0.0125f, 0, 0);
            }
            te.draw();
        }
        GL20.glPopMatrix();
    }

    @Override
    public void updateEntity() {
        position.add(direction);

        if (position.x > 1 || position.y > 1 || position.x < -1 || position.y < -1) onOutOfBorders();
        if (World.getInstance().hasBlockAtPos(position)) onBlockCollision(World.getInstance().getBlockByPos(position));
    }

    @Override
    public boolean isUpdatable() {
        return true;
    }

    public void onEntityCollision(Entity entity) {

    }

    public void onBlockCollision(BlockWrapper block) {
        block.addDestroy(power);
        World.getInstance().removeEntity(this);
    }

    public void onOutOfBorders() {
        World.getInstance().removeEntity(this);
    }
}
