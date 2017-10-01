package Entities;

import Vectors.Vector2;
import WorldProviding.World;

import static Game.GameGL.TANK_SPEED;
import static Game.GameGL.player;

/**
 * Created by Yuri on 12.01.17.
 */
public class NPCTankAI extends TankAI {


    @Override
    public void update() {
        tankEntity.shoot();
        tankEntity.setInvincible(true);
        //tankEntity.addEffect(TankEntity.Effect.SHOOT_SPEED_UP, 0.1f, 2);
        //tankEntity.setShell(Shells.UNUSUAL_SHELL);

        Side dir = getNextDirection();

        if (dir == Side.NONE) {
            tankEntity.shoot();
            return;
        }

        if (hasBlocksAtFront(dir)) {
            moveToSide(dir, 0);
            getTankEntity().shoot();
        } else moveToSide(dir, TANK_SPEED);
    }

    private void moveToSide(Side side, float distance) {
        switch (side) {
            case RIGHT: {
                getTankEntity().moveRight(distance);
                break;
            }
            case DOWN: {
                getTankEntity().moveBackwards(distance);
                break;
            }
            case LEFT: {
                getTankEntity().moveLeft(distance);
                break;
            }
            case UP: {
                getTankEntity().moveForward(distance);
            }
        }
    }

    private Side getNextDirection() {
        Vector2 mPos = getPos();
        Vector2 pPos = player.getPos();

        Vector2 vec = pPos.clone().decrease(mPos);

        if (vec.length() == 0) return Side.NONE;

        if (Math.abs(vec.y) >= Math.abs(vec.x)) {
            if (vec.y > 0) return Side.UP;
            else return Side.DOWN;
        } else {
            if (vec.x > 0) return Side.RIGHT;
            else return Side.LEFT;
        }
    }

    private boolean hasBlocksAtFront(Side side) {
        Vector2 vec = getPos().clone();

        if (side == Side.NONE) {
            throw new RuntimeException("Ну и что мне делать с NONE?");
        }

        switch (side) {
            case DOWN: {
                while (vec.y >= -1) {
                    vec.y -= 0.1f;
                    if (World.getInstance().hasBlockAtPos(vec)) return true;
                }
                break;
            }
            case UP: {
                while (vec.y <= 1) {
                    vec.y += 0.1f;
                    if (World.getInstance().hasBlockAtPos(vec)) return true;
                }
                break;
            }
            case LEFT: {
                while (vec.x >= -1) {
                    vec.x -= 0.1f;
                    if (World.getInstance().hasBlockAtPos(vec)) return true;
                }
                break;
            }
            case RIGHT: {
                while (vec.x >= 1) {
                    vec.x += 0.1f;
                    if (World.getInstance().hasBlockAtPos(vec)) return true;
                }
                break;
            }
        }

        return false;

    }

    private enum Side {UP, DOWN, LEFT, RIGHT, NONE}
}
