package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.vsu.csf.twopeoplestudios.Values;
import ru.vsu.csf.twopeoplestudios.model.world.MapTile;
import ru.vsu.csf.twopeoplestudios.model.world.World;

import java.util.ArrayList;

public class MapTestScreen extends AbstractScreen {

    private static final int TILE_SIZE = 4;
    private static final int MARGIN_LEFT = 128;
    private static final int MARGIN_BOTTOM = 100;

    private TextureRegion water;
    private TextureRegion sand;
    private ArrayList<TextureRegion> lands;

    private final World world = new World();
    private boolean isCreatingWorld = false;
    private final Runnable mapGen = new Runnable() {
        @Override
        public void run() {
            isCreatingWorld = true;
            world.create();
            isCreatingWorld = false;
        }
    };

    Vector2 mousePos = new Vector2(0, 0);

    /*private volatile boolean isGenerating = false;
    private final Thread mapGenerator = new Thread(new Runnable() {
        @Override
        public void run() {
            isGenerating = true;
            Map.create();
            isGenerating = false;
        }
    });*/
    public MapTestScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        Gdx.input.setInputProcessor(
                new InputAdapter() {
                    @Override
                    public boolean mouseMoved(int screenX, int screenY) {
                        screenY = Values.SCREEN_HEIGHT - screenY;
                        mousePos = new Vector2(screenX, screenY);
                        return true;
                    }

                    @Override
                    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                        if (!isCreatingWorld) {
                            Thread fromMouseThread = new Thread(mapGen);
                            fromMouseThread.start();
                        }
                        return true;
                    }
                }
        );

        water = new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/water.png")));
        sand = new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/beachbitch.png")));

        lands = new ArrayList<TextureRegion>() {{
            for (int i = 0; i < 13; i++)
                add(new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/Land" + (i + 1) + ".png"))));
        }};

        Thread t = new Thread(mapGen);
        t.start();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (!isCreatingWorld) {
            uiBatch.begin();
            MapTile[][] map = world.map;
            int width = world.map.length;
            int height = world.map[0].length;

            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    switch (map[i][j].type) {
                        case SAND:
                            uiBatch.draw(sand,
                                    i * TILE_SIZE + MARGIN_LEFT,
                                    j * TILE_SIZE + MARGIN_BOTTOM,
                                    TILE_SIZE, TILE_SIZE);
                            break;
                        case WATER:
                            uiBatch.draw(water,
                                    i * TILE_SIZE + MARGIN_LEFT,
                                    j * TILE_SIZE + MARGIN_BOTTOM,
                                    TILE_SIZE, TILE_SIZE);
                            break;
                        default:
                            if (map[i][j].height >= lands.size())
                                uiBatch.draw(lands.get(12),
                                        i * TILE_SIZE + MARGIN_LEFT,
                                        j * TILE_SIZE + MARGIN_BOTTOM,
                                        TILE_SIZE, TILE_SIZE);
                            else {
                                int index = map[i][j].height;
                                if (index >= lands.size()) {
                                    uiBatch.draw(lands.get(12),
                                            i * TILE_SIZE + MARGIN_LEFT,
                                            j * TILE_SIZE + MARGIN_BOTTOM,
                                            TILE_SIZE, TILE_SIZE);
                                }
                                else
                                    uiBatch.draw(lands.get(index),
                                            i * TILE_SIZE + MARGIN_LEFT,
                                            j * TILE_SIZE + MARGIN_BOTTOM,
                                            TILE_SIZE, TILE_SIZE);
                            }
                            break;
                    }
                }
            }
            uiBatch.end();
        }
    }
}
