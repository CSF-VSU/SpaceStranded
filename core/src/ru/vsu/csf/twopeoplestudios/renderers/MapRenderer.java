package ru.vsu.csf.twopeoplestudios.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import ru.vsu.csf.twopeoplestudios.model.collectibles.Items;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herb;
import ru.vsu.csf.twopeoplestudios.model.collectibles.herbs.Herbs;
import ru.vsu.csf.twopeoplestudios.model.contactListener.WorldContactListener;
import ru.vsu.csf.twopeoplestudios.model.map.Map;
import ru.vsu.csf.twopeoplestudios.model.world.MapTile;

public class MapRenderer {

    private static final int CELL_SIZE = 32;

    public Map map;

    Items items;
    Herbs herbs;

    private Box2DDebugRenderer debugRenderer;
    public OrthographicCamera camera;
    public OrthographicCamera bgcamera;
    private World world;

    //region backgroundDrawingVars
    private TiledMap tiledMap;
    private MapLayers layers;
    private TiledMapRenderer tiledMapRenderer;
    private MapTile[][] mapScheme;
    //endregion




    TextureRegion heroTexture;

    public MapRenderer() {
        //region backgroundDrawing

        mapScheme = ru.vsu.csf.twopeoplestudios.model.world.World.getInstance().map;
        tiledMap = new TiledMap();
        int mapWidth = mapScheme.length;
        int mapHeight = mapScheme[0].length;
        TextureRegion water = new TextureRegion(new Texture(new FileHandle("gfx/tiles/wateer.png")));
        TextureRegion land = new TextureRegion(new Texture(new FileHandle("gfx/tiles/grass.png")));
        TextureRegion sand = new TextureRegion(new Texture(new FileHandle("gfx/tiles/sand.png")));

        TiledMapTile waterTile = new StaticTiledMapTile(water);
        TiledMapTile landTile = new StaticTiledMapTile(land);
        TiledMapTile sandTile = new StaticTiledMapTile(sand);

        TiledMapTileLayer layer = new TiledMapTileLayer(128, 64, 32, 32);
        MapLayers layers = tiledMap.getLayers();

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
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        //endregion

        debugRenderer = new Box2DDebugRenderer();


        /*bgcamera = new OrthographicCamera();
        bgcamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bgcamera.update();*/

        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener());

        //herbs = Herbs.getInstance();
        map = new Map(world);
        items = Items.getInstance();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(map.hero.getHeroPosition().x * CELL_SIZE,
                            map.hero.getHeroPosition().y * CELL_SIZE, 0);
        //camera.position.set(0,0,0);
        heroTexture = new TextureRegion(new Texture(Gdx.files.internal("gfx/characters/hero.png")));
    }

    Vector3 lerpTarget = new Vector3();
    public void render(Batch batch, float delta) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        debugRenderer.render(world, camera.combined);
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        map.update(delta);

        for (Herb h : map.herbs) {
            batch.draw(items.getItemTexture(h.getId()),
                    h.getPosition().x * CELL_SIZE,
                    h.getPosition().y * CELL_SIZE,
                    1.5f * CELL_SIZE,
                    1.5f * CELL_SIZE);
        }

        batch.draw(heroTexture,
                (map.hero.getHeroPosition().x - 0.5f) * CELL_SIZE,
                (map.hero.getHeroPosition().y - 0.5f) * CELL_SIZE,
                1.7f * CELL_SIZE,
                (18 * 1.7f / 11f) * CELL_SIZE);
        batch.end();

        camera.position.lerp(lerpTarget.set(
                map.hero.getHeroPosition().x * CELL_SIZE,
                map.hero.getHeroPosition().y * CELL_SIZE,
                0), 2f * delta);
        camera.update();
       //bgcamera.position.lerp(lerpTarget.set(map.hero.getHeroPosition().x, map.hero.getHeroPosition().y, 0), 2f * delta);

      //  bgcamera.update();
    }
}
