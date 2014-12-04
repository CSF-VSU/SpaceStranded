package ru.vsu.csf.twopeoplestudios.model.world;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {


   private static World instance;
  //TODO: set initial player pos;
  //TODO: transfer map rendering to MapRenderer.java
  //TODO: edit gamescreen

   private World()
   {
       create();
   }

   public static synchronized World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
   }



    private static Random random;

    //public int size;

    public MapTile[][] map;
    public MapTile[][] firstMapPart;
    public MapTile[][] secondMapPart;
    public MapTile[][] middleMapPart;
    public MapEdge[][] edges;
    private int size = 64;
    //public MapTile[][] borderMapPart;

    public int height;

    public void create() {
        random = new Random();
        //size = 64;
        map = new MapTile[2*size][size];
        edges = new MapEdge[2*size][2*size];
        /*for (int i = 0; i < 2*size; i++)
            for (int j = 0; j < size; j++)
                map[i][j] = new MapTile();*/
        firstMapPart = new MapTile[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                firstMapPart[i][j] = new MapTile();
        secondMapPart = new MapTile[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                secondMapPart[i][j] = new MapTile();
        middleMapPart = new MapTile[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                middleMapPart[i][j] = new MapTile();
        /*borderMapPart = new MapTile[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                borderMapPart[i][j] = new MapTile();*/

        setCell(firstMapPart, 0, 0, TerrainType.WATER);
        setCell(firstMapPart, size / 2, 0, TerrainType.WATER);
        setCell(firstMapPart, 0, size / 2, TerrainType.WATER);
        setCell(firstMapPart, size / 2, size / 2, TerrainType.GROUND);

        setCell(secondMapPart, 0, 0, TerrainType.WATER);
        setCell(secondMapPart, size / 2, 0, TerrainType.WATER);
        setCell(secondMapPart, 0, size / 2, TerrainType.WATER);
        setCell(secondMapPart, size / 2, size / 2, TerrainType.GROUND);

        setCell(middleMapPart, 0, 0, TerrainType.WATER);
        setCell(middleMapPart, size / 2, 0, TerrainType.WATER);
        setCell(middleMapPart, 0, size / 2, TerrainType.WATER);
        setCell(middleMapPart, size / 2, size / 2, TerrainType.GROUND);

        /*setCell(borderMapPart, 0, 0, TerrainType.WATER);
        setCell(borderMapPart, size / 2, 0, TerrainType.WATER);
        setCell(borderMapPart, 0, size / 2, TerrainType.WATER);
        setCell(borderMapPart, size / 2, size / 2, TerrainType.GROUND);*/

        makeFractal(firstMapPart, size / 4);
        makeFractal(secondMapPart, size / 4);
        makeFractal(middleMapPart, size / 4);
        //makeFractal(borderMapPart, size / 4);


        for (int i  = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                map[i][j] = firstMapPart[i][j];
                map[size + i][j] = secondMapPart[i][j];
            }
        for (int i  = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                if (map[i+size/2][j].type.equals(TerrainType.WATER) && middleMapPart[i][j].type.equals(TerrainType.WATER))
                    map[i+size/2][j].type = TerrainType.WATER;
                else
                    map[i+size/2][j].type = TerrainType.GROUND;
            }

        /*for (int i  = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                if (i < size/2) {
                    if (map[i + 3*size/2][j].type.equals(TerrainType.WATER) && borderMapPart[i][j].type.equals(TerrainType.WATER))
                        map[i + 3*size/2][j].type = TerrainType.WATER;
                    else
                        map[i + 3*size/2][j].type = TerrainType.GROUND;
                }
                else {
                    if (map[i][j].type.equals(TerrainType.WATER) && borderMapPart[i][j].type.equals(TerrainType.WATER))
                        map[i][j].type = TerrainType.WATER;
                    else
                        map[i][j].type = TerrainType.GROUND;
                }
            }*/

        for (int i = 0; i < 2*size; i++)
            for (int j = 0; j < size; j++)
                if (map[i][j].type == TerrainType.WATER)
                    map[i][j].height = -1;
                else
                    map[i][j].height = -2;
        height = -1;
        for (int e = 0; e < 100; e++) {
            height++;
            for (int i = 0; i < 2*size; i++)
                for (int j = 0; j < size; j++)
                    if (checkHeight(i, j, height)) {
                        setCell(map, i, j, TerrainType.SAND);
                        map[i][j].height = height;
                    }
        }
        for (int i = 0; i < 2*size; i++)
            for (int j = 0; j < size; j++)
                if (!(map[i][j].type == TerrainType.WATER))
                    if (map[i][j].height == 1 || map[i][j].height == 0)
                        map[i][j].type = TerrainType.SAND;
                    else if (map[i][j].height == 2) {
                        if (random.nextInt(2) == 1)
                            map[i][j].type = TerrainType.SAND;
                        else
                            map[i][j].type = TerrainType.GROUND;
                    }
                    else
                        map[i][j].type = TerrainType.GROUND;
    }

    public Vector2 getRandomPosition() {
        boolean ok = false;
        Vector2 result = new Vector2();
        do {
            result = new Vector2(random.nextInt(2*size), random.nextInt(size));
            if (map[(int)result.x][(int)result.y].type != TerrainType.WATER) {
                ok = true;
            }
        } while (!ok);
        return result;
    }

    public void setCell(MapTile[][] mapPart, int x, int y, TerrainType value) {
        mapPart[x][y].type = value;
    }

    public TerrainType getCell(MapTile[][] mapPart, int x, int y) {
        return mapPart[x][y].type;
    }


    public boolean checkHeight(int i, int j, int height) {
        return (map[i][j].type == TerrainType.GROUND) &&
                ((i - 1 >= 0) && (map[i - 1][j].height + 1 == height) ||
                        (i + 1 < size*2) && (map[i + 1][j].height + 1 == height) ||
                        (j - 1 >= 0) && (map[i][j - 1].height + 1 == height) ||
                        (j + 1 < size) && (map[i][j + 1].height + 1 == height));
    }

    public void makeFractal(MapTile[][] mapPart, int step) {
        for (int y = 0; y < size; y += step) {
            for (int x = 0; x < size; x += step) {
                // Внутренний цикл вычисляет (cx,cy)
                // это точка от которой копируется цвет карты

                // добавить случайное смещение
                int cx = x + ((Math.random() < 0.5) ? 0 : step);
                int cy = y + ((Math.random() < 0.5) ? 0 : step);

                // округлить до ближайшего произведения от step*2
                // где step*2 предыдущий уровень даталирования
                cx = (cx / (step * 2)) * step * 2;
                cy = (cy / (step * 2)) * step * 2;

                // вращать мир как тор
                // для гарантии что getCell() и setCell() в пределах границ
                cx = cx % size;
                cy = cy % size;

                // копировать из (cx,cy) в (x,y)
                setCell(mapPart, x, y, getCell(mapPart, cx, cy));
            }
        }
        if (step > 1) makeFractal(mapPart, step / 2);
    }

    public List<Integer> getTilesFromEdge(int x, int y) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (x % 2 == 0) { //ребро горизонтальное
            res.add(x / 2 - 1);
            res.add(y);
            res.add(x / 2);
            res.add(y);
        }
        else { //ребро вертикальное
            res.add(x / 2);
            res.add(y - 1);
            res.add(x / 2);
            res.add(y);
        }
        return res;
    }
}
