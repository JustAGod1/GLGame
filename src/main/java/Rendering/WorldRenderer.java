package Rendering;

import Entities.HUD;
import Vectors.Vector3;
import WorldProviding.World;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import static Game.GameGL.window;
import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

/**
 * Created by Yuri on 03.01.17.
 */
public class WorldRenderer implements GLEventListener {

    public static GL2 GL20;
    public static GLAutoDrawable drawable;
    private static WorldRenderer instance = new WorldRenderer();
    public CameraMan camera = new CameraMan();
    private GLU glu;


    private WorldRenderer() {


    }

    public static WorldRenderer getInstance() {
        return instance;
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL20 = glAutoDrawable.getGL().getGL2();
        FPSAnimator animator = new FPSAnimator(glAutoDrawable, 200, true);
        animator.start();


        //Debug.start();
        glu = GLU.createGLU(GL20);
        GL20.glEnable(GL_BLEND);
        GL20.glEnable(GL_DEPTH_TEST);
        GL20.glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
        GL20.glClearColor(1, 0, 0, 1);
        //System.out.println(glAutoDrawable);
        //camera.setCamera();

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL20 = glAutoDrawable.getGL().getGL2();
        drawable = glAutoDrawable;


        GL20.glClear(GL2.GL_COLOR_BUFFER_BIT |  GL2.GL_DEPTH_BUFFER_BIT | GL2.GL_STENCIL_BUFFER_BIT);



        //GL20.glLoadIdentity();

        GL20.glPushMatrix();
        {
            GL20.glTranslated(0, 0.05, 0);


            GL20.glPushMatrix();
            {
                GL20.glScaled(1, 0.95, 1);
                World.getInstance().onDraw();
            }
            GL20.glPopMatrix();
        }
        GL20.glPopMatrix();
        GL20.glPushMatrix();
        {
            HUD.instance.onDraw();
        }
        GL20.glPopMatrix();




        //camera.setCamera();
    }



    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {


        //glu.gluPerspective(45.0, (float)width / (float)height, 0.2, 200.0);


    }



    public class CameraMan {

        public Vector3 pos;
        public Vector3 dir;
        public Vector3 up;
        private double step = 0.5;

        public CameraMan() {
            pos = new Vector3(0,0,-0.1f);
            dir = new Vector3(0,0,0);
            up = new Vector3(0,1,0);
        }

        private void setCamera() {

            // Change to projection matrix.
            GL20.glMatrixMode(GL_PROJECTION);
            GL20.glLoadIdentity();
            // Perspective.
            float widthHeightRatio = (float) window.getWidth() / (float) window.getHeight();
            glu.gluPerspective(45, widthHeightRatio, 1, 1000);
            glu.gluLookAt(pos.x, pos.y, pos.z, dir.x, dir.y, dir.z,up.x, up.y, up.z);
            // Change back to model view matrix.
            GL20.glMatrixMode(GL_MODELVIEW);
            GL20.glLoadIdentity();
        }



        public void incPosX() {
            pos.x += step;
        }

        public void decPosX() {pos.x -= step;}

        public void incPosY() {pos.y += step;}

        public void decPosY() {pos.y -= step;}

        public void incPosZ() {pos.z += step;}

        public void decPosZ() {pos.z -= step;}

        public void setPos(Vector3 pos) {
            this.pos = pos;
        }

        public void setDir(Vector3 dir) {
            this.dir = dir;
        }

        public void setUp(Vector3 up) {
            this.up = up;
        }
    }
}
