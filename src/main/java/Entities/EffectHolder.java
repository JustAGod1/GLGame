package Entities;

import Entities.TankEntity.Effect;
import Rendering.Teselator;
import Vectors.Vector2;
import WorldProviding.World;

import static Rendering.WorldRenderer.GL20;

/**
 * Created by Yuri on 11.01.17.
 */
public class EffectHolder extends Entity {

    protected Effect effect;
    protected float value;
    protected int time;
    protected int liveTime;
    protected Vector2 pos;

    public EffectHolder(Effect effect, float value, int time, int liveTime, Vector2 pos) {
        this.effect = effect;
        this.value = value;
        this.time = time;
        this.liveTime = liveTime;
        this.pos = pos;
        setInvincible(true);
    }

    @Override
    public void updateEntity() {
        liveTime--;
        if (liveTime <= 0) World.getInstance().removeEntity(this);
    }

    @Override
    public boolean isUpdatable() {
        return true;
    }

    @Override
    public void onDraw() {
        Teselator te = Teselator.instance;

        String texture = null;

        switch (effect) {
            case SHOOT_SPEED_UP: {
                texture = "shoot_speed_up.png";
                break;
            }
            case HEALTH: {
                texture = "heart.png";
                break;
            }
            case SPEED_UP: {
                texture = "speed_up.png";
                break;
            }
        }

        GL20.glPushMatrix();
        {
            GL20.glTranslated(pos.x + 0.07, pos.y + 0.07, -0.34);
            GL20.glPushMatrix();
            {
                GL20.glRotated(180, 0, 0, 1);
                GL20.glScaled(0.5, 0.5, 1);
                te.bindTexture(texture);


                te.startDrawingQuads();
                {
                    te.add2DVertexWithUV(0, 0, 0, 1);
                    te.add2DVertexWithUV(0.1f, 0, 1, 1);
                    te.add2DVertexWithUV(0.1f, 0.1f, 1, 0);
                    te.add2DVertexWithUV(0, 0.1f, 0, 0);
                }
                te.draw();
            }
            GL20.glPopMatrix();
        }
        GL20.glPopMatrix();

        GL20.glPushMatrix();
        {

            GL20.glTranslated(pos.x, pos.y, -0.35);
            GL20.glColor3f(1, 1, 1);

            te.bindTexture("bubble.png");

            te.startDrawingQuads();
            {
                te.add2DVertexWithUV(0, 0, 0, 1);
                te.add2DVertexWithUV(0.1f, 0, 1, 1);
                te.add2DVertexWithUV(0.1f, 0.1f, 1, 0);
                te.add2DVertexWithUV(0, 0.1f, 0, 0);
            }
            te.draw();

        }
        GL20.glPopMatrix();





    }

    @Override
    public void onEntityCollision(Entity entity) {
        if (entity instanceof TankEntity) {
            ((TankEntity) entity).addEffect(effect, value, time);
            World.getInstance().removeEntity(this);
        }
    }

    @Override
    public boolean isVectorInBounds(Vector2 pos) {
        float mx = this.pos.x * 10;
        float my = this.pos.y * 10;

        float x = pos.x;
        float y = pos.y;

        return (x <= (mx * 0.1 + 0.1)) && (x >= (mx * 0.1)) && (y <= (my * 0.1 + 0.1)) && (y >= (my * 0.1));
    }

}
