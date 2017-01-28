package WorldProviding;


import Entities.Entity;
import Entities.HUDElement;
import Game.GameGL;
import Vectors.BlockPos;
import Vectors.Vector2;
import WorldBlocks.Block;
import WorldBlocks.BlockWrapper;

import java.util.*;
import java.util.function.Predicate;

import static Rendering.WorldRenderer.GL20;
import static WorldBlocks.Blocks.*;

/**
 * Создано Юрием в 31.12.16.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class World implements Iterable<BlockWrapper> {
    public static final int CHUNK_SIZE = 20;
    private static World instance;
    public ArrayList<HUDElement> hudElements = new ArrayList<>();
    private HashMap<BlockPos, Chunk> chunks = new HashMap<>();
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Entity> updatableEntities = new ArrayList<>();
    private Thread thread;

    public World() {


        thread = new Thread(this::loop);
        thread.start();
    }

    public static void generateNewWorld() {
        Random random = new Random();
        instance = new World();
        for (int i = 0; i < 555; i++) {
            Block block;
            switch (i % 60) {
                case 5:
                case 3:
                case 10: {
                    block = tnt;
                    break;
                }
                case 2: {
                    block = turret;
                    break;
                }
                case 0:
                case 8:
                case 34: {
                    block = chest;
                    break;
                }
                default: {
                    block = stone;
                }
            }
            BlockPos pos = new BlockPos(random.nextInt(20) - 10, random.nextInt(20) - 10);
            instance.setBlock(pos, block);
        }
    }

    public static void load(String worldName) {

    }

    public static World getInstance() {
        return instance;
    }

    public void setBlock(BlockPos pos, Block block) {
        Chunk chunk = getChunkByBlockPos(pos);

        chunk.setBlock(pos.convertToChunkPos(), block);
    }

    public Chunk getChunkByBlockPos(BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();

        x = x / (CHUNK_SIZE);
        y = y / (CHUNK_SIZE);

        pos = new BlockPos(x, y);

        return getChunk(pos);
    }

    public Chunk getChunk(BlockPos pos) {
        Chunk res = chunks.get(pos);
        if (res == null) {
            res = new Chunk();
            res.generate();
            chunks.put(pos, res);
        }
        return res;
    }

    public Collection<Chunk> getChuncks() {
        return chunks.values();
    }

    public Set<Map.Entry<BlockPos, Chunk>> getChunksSet() {
        return chunks.entrySet();
    }


    @Override
    public Iterator<BlockWrapper> iterator() {
        return new WorldIterator(chunks);
    }

    private Vector2 translateMouseVector(float x, float y) {
        x -= GameGL.window.getWidth() / 2.0;
        y -= GameGL.window.getHeight() / 2.0;
        x /= GameGL.window.getWidth() / 2;
        y /= GameGL.window.getHeight() / 2;
        y *= -1;

        return new Vector2(x, y);
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
        if (entity.isUpdatable()) updatableEntities.add(entity);
    }

    public boolean hasBlockAtPos(BlockPos pos) {
        return getBlockByPos(pos) != null;
    }

    public boolean hasBlockAtPos(Vector2 pos) {


        for (BlockWrapper block : this) {
            if (block.isVectorInBounds(pos)) return true;
        }
        return false;
    }

    public BlockWrapper getBlockByPos(BlockPos pos) {
        Chunk chunk = getChunkByBlockPos(pos);

        return chunk.getBlockByPos(pos);
    }

    public BlockWrapper getBlockByPos(Vector2 pos) {

        for (BlockWrapper block : this) {
            if (block.isVectorInBounds(pos)) return block;
        }
        return null;
    }

    private void loop() {
        while (true) {
            long before = new Date().getTime();
            try {

                for (int i = 0; i < updatableEntities.size(); i++) {
                    updatableEntities.get(i).updateEntity();
                }


                Thread.sleep(1000 / 20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ConcurrentModificationException ignored) {

            } finally {
                long after = new Date().getTime();
                long sum = after - before - 1000 / 20;

                if (sum > 10) System.out.printf("Entities update took %dms\n", sum);
            }
        }
    }

    public void onDraw() {
        try {
            for (Map.Entry<BlockPos, Chunk> entry : chunks.entrySet()) {
                GL20.glPushMatrix();
                {
                    float x = entry.getKey().getX();
                    float y = entry.getKey().getY();
                    GL20.glTranslated((x / 2.0) * CHUNK_SIZE * 0.1, (y / 2.0) * CHUNK_SIZE * 0.1, 0);
                    entry.getValue().onDraw(GL20);
                    GL20.glTranslated((x / 2.0) * CHUNK_SIZE * -0.1, (y / 2.0) * CHUNK_SIZE * -0.1, 0);
                }
                GL20.glPopMatrix();
            }
            for (Entity entity : entities) {
                GL20.glPushMatrix();
                {
                    entity.onDraw();
                }
                GL20.glPopMatrix();
            }
        } catch (ConcurrentModificationException ignore) {
        }
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
        updatableEntities.remove(entity);
    }

    public void destroyBlock(BlockPos pos) {
        getChunkByBlockPos(pos).destroyBlock(pos);
    }

    public void createExplosion(BlockPos pos, int power) {
        World world = World.getInstance();
        if (world.hasBlockAtPos(pos.upperBlock()))
            World.getInstance().getBlockByPos(pos.upperBlock()).explosion(power);
        if (world.hasBlockAtPos(pos.downBlock()))
            World.getInstance().getBlockByPos(pos.downBlock()).explosion(power);
        if (world.hasBlockAtPos(pos.leftBlock()))
            world.getBlockByPos(pos.leftBlock()).explosion(power);
        if (world.hasBlockAtPos(pos.rightBlock()))
            world.getBlockByPos(pos.rightBlock()).explosion(power);
    }

    public BlockPos toBlockPos(Vector2 vector) {
        return new BlockPos(Math.round(vector.x / 0.1f) - 1, Math.round(vector.y / 0.1f));
    }

    public Entity findEntity(Vector2 pos, Entity... except) {
        ArrayList<Entity> exceptions = new ArrayList<>();
        Collections.addAll(exceptions, except);
        for (Entity entity : entities) {
            if (exceptions.contains(entity)) continue;
            if (entity.isVectorInBounds(pos)) return entity;
        }

        return null;
    }

    public Entity findEntity(Predicate<Entity> filter) {
        for (Entity entity : entities) {
            if (filter.test(entity)) return entity;
        }
        return null;
    }

    private class WorldIterator implements Iterator<BlockWrapper> {

        private HashMap<BlockPos, Chunk> map;
        private Iterator<Chunk> mapIterator;
        private Iterator<BlockWrapper> currentIterator;

        public WorldIterator(HashMap<BlockPos, Chunk> map) {
            this.map = map;
            mapIterator = map.values().iterator();
        }

        @Override
        public boolean hasNext() {
            if (currentIterator == null) {
                if (!mapIterator.hasNext()) return false;
                currentIterator = mapIterator.next().iterator();
            }
            if (!currentIterator.hasNext()) {
                if (!mapIterator.hasNext()) {
                    return false;
                }
                currentIterator = mapIterator.next().iterator();
            }
            return currentIterator.hasNext();
        }

        @Override
        public BlockWrapper next() {
            if (!hasNext()) return null;
            return currentIterator.next();
        }

        @Override
        public void remove() {

        }

    }
}
