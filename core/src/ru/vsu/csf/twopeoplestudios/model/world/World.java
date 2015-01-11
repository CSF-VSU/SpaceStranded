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
        for (int i = 0; i < 2 * size; i++)
            for (int j = 0; j < 2 * size; j++)
                edges[i][j] = new MapEdge();
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

        makeFractal(firstMapPart, size / 4, size);
        makeFractal(secondMapPart, size / 4, size);
        makeFractal(middleMapPart, size / 4, size);
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

        for (int i = 0; i < 50; i++) {//кол-во водоемов
            int innerSize = size/16;
            MapTile[][] inner = new MapTile[innerSize][innerSize];
            for (int k = 0; k < innerSize; k++)
                for (int j = 0; j < innerSize; j++)
                    inner[k][j] = new MapTile();
            setCell(inner, 0, 0, TerrainType.WATER);
            setCell(inner, innerSize / 2, 0, TerrainType.WATER);
            setCell(inner, 0, innerSize / 2, TerrainType.WATER);
            setCell(inner, innerSize / 2, innerSize / 2, TerrainType.GROUND);
            makeFractal(inner, innerSize / 4, innerSize);
            for (int k = 0; k < innerSize; k++)
                for (int j = 0; j < innerSize; j++)
                    if (inner[k][j].type == TerrainType.GROUND)
                        inner[k][j].type = TerrainType.INNER_WATER;
                    else
                        inner[k][j].type = TerrainType.GROUND;
            List<Integer> tc = getRandomHighTile(innerSize);
            superimpose(inner, tc);
        }


        for (int i = 0; i < 100; i++) //кол-во рек
        {
            List<Integer> ec = chooseRandomGroundEdge(); //edge coordinates
            int x = ec.get(0);
            int y = ec.get(1);
            edges[x][y].isRiver = true;
            int direction = getDirection(x,y);
            while (farFromSea(x,y, direction)) {
                ec = chooseNextEdge(x,y, direction);
                x = ec.get(0);
                y = ec.get(1);
                edges[x][y].isRiver = true;
                direction = getDirection(x,y);
            }
        }

        countMoisture();
    }

    public Vector2 getRandomPosition() {
        boolean ok = false;
        Vector2 result;
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

    private void makeFractal(MapTile[][] mapPart, int step, int mySize) {
        for (int y = 0; y < mySize; y += step) {
            for (int x = 0; x < mySize; x += step) {
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
                cx = cx % mySize;
                cy = cy % mySize;

                // копировать из (cx,cy) в (x,y)
                setCell(mapPart, x, y, getCell(mapPart, cx, cy));
            }
        }
        if (step > 1) makeFractal(mapPart, step / 2, mySize);
    }

    private List<Integer> getRandomHighTile(int minHeight) {
        List<Integer> res = new ArrayList<Integer>();
        Random rnd = new Random();
        int x = 0, y = 0;
        boolean isFine = false;
        while (!(isFine)) {
            x = rnd.nextInt(2 * size - 3) + 2; //не край карты
            y = rnd.nextInt(size - 2) + 1; //не край карты
            if ((map[x][y].type == TerrainType.GROUND) && (map[x][y].height >= minHeight))
                isFine = true;
        }
        res.add(x);
        res.add(y);
        return res;
    }

    private void superimpose(MapTile[][] inner, List<Integer> tc) {
        for (int i = 0; i < inner.length; i++)
            for (int j = 0; j < inner[0].length; j++)
                if (inner[i][j].type == TerrainType.INNER_WATER)
                    map[tc.get(0) + i][tc.get(1) + j].type = TerrainType.INNER_WATER;
    }

    private List<Integer> getTilesFromEdge(int x, int y) { //возвращает 4 значения - координаты тайлов, между которыми находится ребро
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (y % 2 == 0) { //ребро горизонтальное
            res.add(x);
            res.add(y / 2 - 1);
            res.add(x);
            res.add(y / 2);
        }
        else { //ребро вертикальное
            res.add(x - 1);
            res.add(y / 2);
            res.add(x);
            res.add(y / 2);
        }
        return res;
    }

    private List<Integer> chooseRandomGroundEdge() { //возвращает 2 значения - координаты случайного ребра между тайлами земли
        Random rnd = new Random();
        int x = 0,y = 0;
        boolean isGround = false;
        ArrayList<Integer> res = new ArrayList<Integer>();
        while (!(isGround)) {
            x = rnd.nextInt(2 * size - 3) + 2; //не край карты
            y = rnd.nextInt(2 * size - 3) + 2; //не край карты
            List<Integer> tc = getTilesFromEdge(x, y); //tiles coordinates
            if ((map[tc.get(0)][tc.get(1)].type != TerrainType.WATER) &&
                    (map[tc.get(0)][tc.get(1)].height > 3) &&
                    (map[tc.get(2)][tc.get(3)].type != TerrainType.WATER) &&
                    (map[tc.get(2)][tc.get(3)].height > 3)) //ребро - между тайлами земли
                isGround = true;
        }
        res.add(x);
        res.add(y);
        return res;
    }

    private int getDirection (int x, int y) { //1 - вправо/вверх, -1 - влево/вниз, 0 - неизвестно
        int dir;
        if (y % 2 == 0) {// ребро - горизонтальное
            List<Integer> left = getTilesFromEdge(x - 1, y);
            int leftHeight = map[left.get(0)][left.get(1)].height + map[left.get(2)][left.get(3)].height;
            List<Integer> right = getTilesFromEdge(x + 1, y);
            int rightHeight = map[right.get(0)][right.get(1)].height + map[right.get(2)][right.get(3)].height;
            if (leftHeight > rightHeight)
                dir = 1;
            else if (leftHeight < rightHeight)
                dir = -1;
            else {
                int r = (int)(Math.random()*2);
                if (r == 0)
                    dir = 1;
                else
                    dir = -1;
            }
        }
        else { //ребро - вертикальное
            List<Integer> down = getTilesFromEdge(x, y + 2);
            int downHeight = map[down.get(0)][down.get(1)].height + map[down.get(2)][down.get(3)].height;
            List<Integer> up = getTilesFromEdge(x, y - 2);
            int upHeight = map[up.get(0)][up.get(1)].height + map[up.get(2)][up.get(3)].height;
            if (downHeight > upHeight)
                dir = 1;
            else if (downHeight < upHeight)
                dir = -1;
            else {
                int r = (int)(Math.random()*2);
                if (r == 0)
                    dir = 1;
                else
                    dir = -1;
            }
        }
        return dir;
    }

    private boolean farFromSea(int x, int y, int direction) {
        boolean res = true;
        List<Integer> tiles;
        List<Integer> neighbours = findNeighbours(x,y, direction);
        for (int i = 0; i < neighbours.size(); i += 2) {
            tiles = getTilesFromEdge(neighbours.get(i), neighbours.get(i + 1));
            if ((map[tiles.get(0)][tiles.get(1)].type == TerrainType.WATER) ||
                    (map[tiles.get(2)][tiles.get(3)].type == TerrainType.WATER))
                res = false;
        }

        return res;
    }

    private List<Integer> chooseNextEdge(int x, int y, int direction) { //возвращает 2 значения - координаты следующего участка реки
        List<Integer> neighbours = findNeighbours(x,y, direction);
        return chooseMin(neighbours);
    }

    private List<Integer> findNeighbours(int x, int y, int direction) { //возвращает 6 значений - координаты ребер, соседствующих с исходным с учетом направления
        List<Integer> res = new ArrayList<Integer>();
        if (y % 2 == 0) {  //ребро - горизонтальное
            if (direction == -1) {
                res.add(x);
                res.add(y - 1);
                res.add(x - 1);
                res.add(y);
                res.add(x);
                res.add(y + 1);
            }
            else if (direction == 1) {
                res.add(x + 1);
                res.add(y - 1);
                res.add(x + 1);
                res.add(y);
                res.add(x + 1);
                res.add(y + 1);
            }
        }
        else
        {
            if (direction == 1) {
                res.add(x - 1);
                res.add(y - 1);
                res.add(x);
                res.add(y - 2);
                res.add(x);
                res.add(y - 1);
            }
            else if (direction == -1) {
                res.add(x - 1);
                res.add(y + 1);
                res.add(x);
                res.add(y + 2);
                res.add(x);
                res.add(y + 1);
            }
        }
        return res;
    }

    private List<Integer> chooseMin(List<Integer> neighbours) { //возвращает 2 значения - координаты ребра, минимальноо из соседствующих
        List<Integer> res = new ArrayList<Integer>();
        List<Integer> tiles;
        int current, x = 0, y = 0;
        boolean rnd = false;
        int minHeight = findMin(neighbours);
        for (int i = 0; i < neighbours.size(); i += 2) {
            tiles = getTilesFromEdge(neighbours.get(i), neighbours.get(i + 1));
            current = map[tiles.get(0)][tiles.get(1)].height + map[tiles.get(2)][tiles.get(3)].height;
            if (current == minHeight) {
                if (!(rnd)) {
                    x = neighbours.get(i);
                    y = neighbours.get(i + 1);
                    rnd = true;
                }
                else {
                    int r = (int)(Math.random()*2);
                    if (r == 0) {
                        x = neighbours.get(i);
                        y = neighbours.get(i + 1);
                    }
                }
            }
        }
        res.add(x);
        res.add(y);
        return res;
    }

    private int findMin(List<Integer> neighbours) { //возвращает минимальную высоту для соседних ребер
        int res = 1000, current;
        List<Integer> tiles;
        for (int i = 0; i < neighbours.size(); i += 2) {
            tiles = getTilesFromEdge(neighbours.get(i), neighbours.get(i+1));
            current = map[tiles.get(0)][tiles.get(1)].height + map[tiles.get(2)][tiles.get(3)].height;
            if (current < res)
                res = current;
        }
        return res;
    }

    private void countMoisture() {
        for (int i = 0; i < 2 * size; i++)
            for (int j = 0; j < size; j++) {}
    }
}
