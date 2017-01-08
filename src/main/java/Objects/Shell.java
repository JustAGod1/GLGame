package Objects;

import Rendering.Teselator;
import Vectors.Vector2;
import com.jogamp.opengl.util.texture.Texture;

import static Rendering.WorldRenderer.GL20;

/**
 * Created by Yuri on 08.01.17.
 */
public class Shell extends Entity {

    protected Texture texture = Teselator.instance.createTexture("Shells/default_shell.png");
    protected Vector2 position;
    protected Vector2 direction;
    protected float rotation;

    public Shell(Vector2 position, Vector2 direction) {
        this.position = position;
        this.direction = direction;

        rotation = (float) Math.toDegrees(Math.asin(direction.y));
    }

    @Override
    public void onDraw() {
        Teselator te = Teselator.instance;
        te.bindTexture(texture);

        GL20.glPushMatrix();
        {
            GL20.glRotated(rotation, 0, 0, 1);
            GL20.glTranslatef(position.x, position.y, -0.1f);
            te.startDrawingQuads();
            {
                te.add2DVertexWithUV(-0.025f, -0.025f, 0, 1);
                te.add2DVertexWithUV(0.025f, -0.025f, 1, 1);
                te.add2DVertexWithUV(0.025f, 0.025f, 1, 0);
                te.add2DVertexWithUV(-0.025f, 0.025f, 0, 0);
            }
            te.draw();
        }
        GL20.glPopMatrix();
    }

    @Override
    public void updateEntity() {
        position.add(direction);
    }

    @Override
    public boolean isUpdatable() {
        return true;
    }

    public void onCollision(Entity entity) {

    }
}
