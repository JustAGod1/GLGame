package WorldProviding;


import Blocks.Block;
import Blocks.Blocks;
import Blocks.BlockWrapper;
import Game.GameGL;
import Gui.MouseHandler;
import Objects.HUDElement;
import Vectors.BlockPos;
import Vectors.Vector2;
import java.util.*;
import Objects.Entity;

import static Rendering.WorldRenderer.GL20;

/**
 * Создано Юрием в 31.12.16.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class World implements Iterable<BlockWrapper> {
    public static final int CHUNK_SIZE = 20;
    private HashMap<BlockPos, Chunk> chunks = new HashMap<>();
    public ArrayList<HUDElement> hudElements = new ArrayList<>();
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Entity> updatableEntities = new ArrayList<>();
    private Thread thread;

    private static World instance;

    public static void generateNewWorld() {
        Random random = new Random();
        instance = new World();
        for (int i = 0; i < 555; i++) {
            BlockPos pos = new BlockPos(random.nextInt(20) - 10, random.nextInt(20) - 10);
            instance.setBlock(pos, Blocks.stone);
        }
    }

    public World() {


        thread = new Thread(this::loop);
        thread.start();
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
            try {
            for (Entity entity : updatableEntities) {
                entity.updateEntity();
            }


                Thread.sleep(1000 / 20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ConcurrentModificationException ignored) {

            }
        }
    }

    public void onDraw() {

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
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
        updatableEntities.remove(entity);
    }

    public void destroyBlock(BlockPos pos) {
        getChunkByBlockPos(pos).destroyBlock(pos);
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
