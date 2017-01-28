package Entities.Shells;

import Entities.Entity;
import Rendering.Teselator;
import Vectors.Vector2;

import static Rendering.WorldRenderer.GL20;

/**
 * Created by Yuri on 11.01.17.
 */
public class PowerfulShellWrapper extends ShellWrapper {

    public PowerfulShellWrapper(Vector2 position, float rotation, Shell shell, Vector2 direction, Entity entity) {
        super(position, rotation, shell, direction, entity);
    }

    @Override
    public void onDraw() {
        Teselator te = Teselator.instance;
        te.bindTexture(shell.getTexture());

        GL20.glPushMatrix();
        {
            //GL20.glRotated(rotation, 0, 0, 1);

            GL20.glTranslated(position.x, position.y, -0.1f);
            GL20.glRotated(rotation + 90, 0, 0, 1);
            GL20.glColor3f(1, 1, 1);
            GL20.glPushMatrix();
            {
                GL20.glScaled(1.5, 1.5, 1);
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
        GL20.glPopMatrix();
    }


}
