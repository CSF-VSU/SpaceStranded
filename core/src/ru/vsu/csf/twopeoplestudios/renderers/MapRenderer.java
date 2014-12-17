package ru.vsu.csf.twopeoplestudios.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import ru.vsu.csf.twopeoplestudios.Settings;
import ru.vsu.csf.twopeoplestudios.Values;
import ru.vsu.csf.twopeoplestudios.model.characters.monsters.Monster;
import ru.vsu.csf.twopeoplestudios.model.collectibles.Items;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herb;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herbs;
import ru.vsu.csf.twopeoplestudios.model.contactListener.WorldContactListener;
import ru.vsu.csf.twopeoplestudios.model.map.Map;
import ru.vsu.csf.twopeoplestudios.model.weapons.FlyingProjectile;
import ru.vsu.csf.twopeoplestudios.model.weapons.HeroAttacks;
import ru.vsu.csf.twopeoplestudios.model.weapons.Weapons;
import ru.vsu.csf.twopeoplestudios.model.world.MapTile;
import ru.vsu.csf.twopeoplestudios.model.world.TerrainType;

import java.util.Random;

public class MapRenderer {

    public static final int CELL_SIZE = 64;

    public Map map;

    Items items;
    Herbs herbs;

    private Box2DDebugRenderer debugRenderer;
    public OrthographicCamera camera;
    private World world;

    private ExtendedOrthogonalTiledMapRenderer tiledMapRenderer;

    Random random = new Random();

    public MapRenderer() {
        //region backgroundDrawing

        MapTile[][] mapScheme = ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().map;
        TiledMap tiledMap = new TiledMap();
        int mapWidth = mapScheme.length;
        int mapHeight = mapScheme[0].length;
        TextureRegion water = new TextureRegion(new Texture(new FileHandle("gfx/tiles/water.png")));
        TextureRegion land = new TextureRegion(new Texture(new FileHandle("gfx/tiles/grass.png")));
        TextureRegion sand = new TextureRegion(new Texture(new FileHandle("gfx/tiles/sand.png")));
        TextureRegion tree = new TextureRegion(new Texture(new FileHandle("gfx/tiles/tree-icon.png")));

        TiledMapTile waterTile = new StaticTiledMapTile(water);
        TiledMapTile landTile = new StaticTiledMapTile(land);
        TiledMapTile sandTile = new StaticTiledMapTile(sand);
        TiledMapTile treeTile = new StaticTiledMapTile(tree);
        MapLayers layers = tiledMap.getLayers();

        TiledMapTileLayer layer = new TiledMapTileLayer(128, 64, 64, 64);

        for (int i = 0; i < mapWidth; i++)
            for (int j = 0; j < mapHeight; j++)
            {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                switch (mapScheme[i][j].type) {
                    case GROUND:
                        cell.setTile(landTile);
                        break;
                    case WATER:
                        cell.setTile(waterTile);
                        break;
                    case SAND:
                        cell.setTile(sandTile);
                        break;
                }
                layer.setCell(i, j, cell);
            }
        layers.add(layer);

        TiledMapTileLayer treeLayer = new TiledMapTileLayer(128, 64, 64, 64);
        for (int i = 0; i < mapWidth; i++)
            for (int j = 0; j < mapHeight; j++)
            {

                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

                if (Settings.SPAWN_BADASS_TREES && mapScheme[i][j].type == TerrainType.GROUND && random.nextInt(4) == 2)
                    cell.setTile(treeTile);

                treeLayer.setCell(i, j, cell);
            }
        layers.add(treeLayer);


        //endregion

        debugRenderer = new Box2DDebugRenderer(true, false, false, false, true, true);

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener());

        HeroAttacks.init(world);

        herbs = Herbs.getInstance();
        map = new Map(world);
        items = Items.getInstance();

        if (Settings.SHOW_GRAPHICS) {
            camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            camera.position.set(map.hero.getHeroPosition().x * CELL_SIZE, map.hero.getHeroPosition().y * CELL_SIZE, 0);
        }
        else {
            camera = new OrthographicCamera(20, 16);
            camera.position.set(map.hero.getHeroPosition().x, map.hero.getHeroPosition().y, 0);
        }

        tiledMapRenderer = new ExtendedOrthogonalTiledMapRenderer(tiledMap, map);
    }

    Vector3 lerpTarget = new Vector3();
    public void render(Batch batch, float delta) {
        map.update(delta);
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        if (Settings.SHOW_GRAPHICS) {
            camera.position.lerp(lerpTarget.set(
                    map.hero.getHeroPosition().x * CELL_SIZE,
                    map.hero.getHeroPosition().y * CELL_SIZE,
                    0), 2f * delta);
        }
        else {
            camera.position.lerp(lerpTarget.set(
                    map.hero.getHeroPosition().x,
                    map.hero.getHeroPosition().y,
                    0), 2f * delta);
        }
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        tiledMapRenderer.setView(camera);

        if (Settings.SHOW_GRAPHICS)
            tiledMapRenderer.render(batch);
        else
            debugRenderer.render(world, camera.combined);
    }

    public void updateMousePos(int screenX, int screenY) {
        Values.mousePos = camera.unproject(new Vector3(screenX, screenY, 0));
    }
}
