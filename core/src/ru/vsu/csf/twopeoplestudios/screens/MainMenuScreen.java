package ru.vsu.csf.twopeoplestudios.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.vsu.csf.twopeoplestudios.model.map.Map;
import ru.vsu.csf.twopeoplestudios.model.map.MapTile;

import java.util.ArrayList;

public class MainMenuScreen extends AbstractScreen {

    private static final int TILE_SIZE = 16;
    private static final int MARGIN_LEFT = 128;
    private static final int MARGIN_BOTTOM = 0;

    private TextureRegion water;

    private ArrayList<TextureRegion> lands;

    Vector2 mousePos = new Vector2(0, 0);

    public MainMenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        Gdx.input.setInputProcessor(
                new InputAdapter() {
                    @Override
                    public boolean mouseMoved(int screenX, int screenY) {
                        screenY = AbstractScreen.SCREEN_HEIGHT - screenY;
                        mousePos = new Vector2(screenX, screenY);
                        return true;
                    }

                    @Override
                    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                        Map.create();
                        return true;
                    }
                }
        );

        water = new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/water.png")));

        lands = new ArrayList<TextureRegion>() {{
            add(new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/Land1.png"))));
            add(new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/Land2.png"))));
            add(new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/Land3.png"))));
            add(new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/Land4.png"))));
            add(new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/Land5.png"))));
            add(new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/Land6.png"))));
            add(new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/Land7.png"))));
            add(new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/Land8.png"))));
            add(new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/Land9.png"))));
            add(new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/Land10.png"))));
            add(new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/Land11.png"))));
            add(new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/Land12.png"))));
            add(new TextureRegion(new Texture(Gdx.files.internal("gfx/tiles/Land13.png"))));
        }};

        Map.create();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        MapTile[][] map = Map.map;
        int width = Map.map.length;
        int height = Map.map[0].length;

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                switch (map[i][j].type) {
                    case WATER:
                        batch.draw(water, i * TILE_SIZE + MARGIN_LEFT, j * TILE_SIZE + MARGIN_BOTTOM, TILE_SIZE, TILE_SIZE);
                        break;
                    default:
                        if (map[i][j].height >= lands.size())
                            batch.draw(water, i * TILE_SIZE + MARGIN_LEFT, j * TILE_SIZE + MARGIN_BOTTOM, TILE_SIZE, TILE_SIZE);
                        else {
                            Gdx.app.log("", String.valueOf(map[i][j].height - 1));
                            batch.draw(lands.get(map[i][j].height - 1), i * TILE_SIZE + MARGIN_LEFT, j * TILE_SIZE + MARGIN_BOTTOM, TILE_SIZE, TILE_SIZE);
                        }
                        break;
                }
            }
        }
        batch.end();
    }
}
