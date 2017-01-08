package WorldProviding;

import Rendering.Teselator;
import Vectors.Vector2;

import static Rendering.WorldRenderer.GL20;
import static java.lang.Math.*;

/**
 * Created by Yuri on 05.01.17.
 */
public class PlayerEntity {

    private static PlayerEntity instance = new PlayerEntity();
    private Vector2 position = new Vector2(0, 0);
    private float rotation = 0.0f;



    public void moveForward(float distance) {
        float radians = (float) toRadians(rotation);
        float x = (float) cos(radians);
        float y = (float) sin(radians);
        x *= distance;
        y *= distance;

        position.x += x;
        position.y += y;
    }

    public void moveBackwards(float distance) {

        moveForward(-distance);
    }

    public void rotateToRight(float value) {

        rotation -= value;
        if (rotation < 0) rotation = 360 + rotation;
        System.out.println(rotation);
        float radians = (float) toRadians(rotation);
        System.out.println("sin - " + sin(radians));
        System.out.println("cos - " + cos(radians));

    }

    public void rotateToLeft(float value) {
        rotateToRight(-value);
    }

    public void onDraw() {

        Teselator te = Teselator.instance;


        GL20.glColor3f(0, 1, 0);


        float radians = (float) toRadians(rotation);
        float x = (float) cos(radians);
        float y = (float) sin(radians);
        //x *= 0.1;
        //y *= 0.1;

        GL20.glLineWidth(5);
        GL20.glPushMatrix();
        {
            te.translate(position.x, position.y);


            GL20.glRotated(Math.abs(rotation % 360) + 180, 0, 0, 4);
            GL20.glColor3f(1, 1, 1);
            te.bindTexture("tank.png");
            te.startDrawingQuads();
            {
                te.add3DVertexWithUV(-0.05f, -0.05f, -0.1f, 0, 1);
                te.add3DVertexWithUV(0.05f, -0.05f, -0.1f, 1, 1);
                te.add3DVertexWithUV(0.05f, 0.05f, -0.1f, 1, 0);
                te.add3DVertexWithUV(-0.05f, 0.05f, -0.1f, 0, 0);
            }
            te.draw();

            GL20.glColor3f(1, 0, 0);


        }
        GL20.glPopMatrix();
    }
}
