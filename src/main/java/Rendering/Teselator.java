package Rendering;

import Vectors.Vector2;
import Vectors.Vector3;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static Rendering.WorldRenderer.GL20;
import static Rendering.WorldRenderer.drawable;
import static com.jogamp.opengl.GL2GL3.GL_QUADS;

/**
 * Created by Yuri on 04.01.17.
 */
public class Teselator {

    public static final Teselator instance = new Teselator();

    private Map<String, Texture> loadedTextures = new HashMap<>();
    private Texture currentTexture = null;
    private Vector3 currentTranslation = new Vector3(0, 0, 0);

    private TextRenderer textRenderer = new TextRenderer(new Font(null, Font.BOLD, 5), false, false);

    private boolean drawing;

    private Teselator() {
    }

    public void bindTexture(String res) {
        bindTexture(createTexture(res));
    }

    public void bindTexture(Texture texture) {

        texture.enable(GL20);
        texture.bind(GL20);

        currentTexture = texture;

    }

    public Texture createTexture(String res) {




        if (loadedTextures.containsKey(res)) {
            currentTexture.disable(GL20);

            Texture texture = loadedTextures.get(res);

            return texture;
        }

        ClassLoader loader = this.getClass().getClassLoader();


        InputStream input = loader.getResourceAsStream(res);
        //System.out.println(input.read());

        TextureData textureData = null;
        Texture texture = null;
        try {

            textureData = TextureIO.newTextureData(drawable.getGLProfile(), input, false, "png");
            texture = TextureIO.newTexture(GL20, textureData);

        } catch (Exception e) {
            e.printStackTrace();
        }


        loadedTextures.put(res, texture);

        return texture;
    }

    public void translate2D(float x, float y) {
        translate3D(x, y, 0);
    }

    public void translate3D(float x, float y, float z) {
        currentTranslation.x += x;
        currentTranslation.y += y;
        currentTranslation.z += z;

        GL20.glTranslatef(x, y, z);

    }

    public void setTranslation(float x, float y, float z) {
        translate3D(-currentTranslation.x, -currentTranslation.y, -currentTranslation.z);

        translate3D(x, y, z);
    }

    public void startDrawingQuads() {
        startDrawing(GL_QUADS);
    }

    public void startDrawing(int mode) {
        if (drawing) {
            throw new RuntimeException("Рисование еще не закончено");
        }
        GL20.glBegin(mode);
        drawing = true;

    }

    public void add2DVertexWithUV(float x, float y, float u, float v) {
        add2DVertex(x, y);
        UV(u, v);
    }

    public void add2DVertex(float x, float y) {
        GL20.glVertex3d(x, y, 0);
    }

    public void UV(double u, double v) {
        double tmpU = u;
        u = 1 - v;
        v = 1 - tmpU;


        GL20.glTexCoord2d(u, v);
    }


    public void draw() {
        if (!drawing) throw new RuntimeException("Рисование еще не начато");
        drawing = false;
        GL20.glEnd();



    }

    public void add3DVertex(double x, double y, double z) {
        GL20.glVertex3d(x, y, z);
    }

    @Override
    public String toString() {
        return String.format("Текущая трансляция - [x %f; y %f; z 0.0]", currentTranslation.x, currentTranslation.y);
    }

    public void add3DVertexWithUV(double x, double y, double z, double u, double v) {
        UV(u, v);
        add3DVertex(x, y, z);

    }

    public void disableTexture() {
        if (currentTexture != null) {
            currentTexture.disable(GL20);
        }
    }

    public void drawText(String text, Vector2 pos) {
        textRenderer.begin3DRendering();
        {
            textRenderer.setColor(0, 0, 0, 1);
            textRenderer.draw3D(text, pos.x, pos.y, 0, 1);
        }
        textRenderer.end3DRendering();
    }
}
