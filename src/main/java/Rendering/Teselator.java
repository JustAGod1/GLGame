package Rendering;

import Vectors.Vector2;
import Vectors.Vector3;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static Rendering.WorldRenderer.GL20;
import static com.jogamp.opengl.GL2GL3.GL_QUADS;

/**
 * Created by Yuri on 04.01.17.
 */
public class Teselator {

    public static final Teselator instance = new Teselator();

    private Map<String, Texture> loadedTextures = new HashMap<>();

    private Texture currentTexture = null;

    private Vector2 currentTranslation = new Vector2(0, 0);

    private Vector2 translationBeforeDraw;

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
        if (currentTexture != null) {
            currentTexture.disable(GL20);
        }

        if (loadedTextures.containsKey(res)) {
            currentTexture.disable(GL20);

            Texture texture = loadedTextures.get(res);

            return texture;
        }

        ClassLoader loader = this.getClass().getClassLoader();


        InputStream input = loader.getResourceAsStream(res);
        //System.out.println(input.read());

        Texture texture = null;
        try {

            texture = TextureIO.newTexture(input, true, "png");

        } catch (IOException e) {
            e.printStackTrace();
        }


        loadedTextures.put(res, texture);

        return texture;
    }

    public void translate(float x, float y) {
        currentTranslation.x += x;
        currentTranslation.y += y;

        GL20.glTranslatef(x, y, 0);
    }

    public void setTranslation(float x, float y) {
        translate(-currentTranslation.x, -currentTranslation.y);

        translate(x, y);
    }

    public void startDrawingQuads() {
        startDrawing(GL_QUADS);
    }

    public void startDrawing(int mode) {
        GL20.glBegin(mode);
        translationBeforeDraw = currentTranslation.clone();
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
        GL20.glEnd();

        setTranslation(translationBeforeDraw.x, translationBeforeDraw.y);
        translationBeforeDraw = null;
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
}
