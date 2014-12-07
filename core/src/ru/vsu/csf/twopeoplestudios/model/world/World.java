package ru.vsu.csf.twopeoplestudios.model.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    public int size;

    public MapTile[][] map;
    public MapTile[][] firstMapPart;
    public MapTile[][] secondMapPart;
    public MapTile[][] middleMapPart;
    public MapEdge[][] edges;


    public int height;

    public void create() {

        size = 128;
        map = new MapTile[2 * size][size];
        edges = new MapEdge[2 * size][2 * size];
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

        makeFractal(firstMapPart, size / 4);
        makeFractal(secondMapPart, size / 4);
        makeFractal(middleMapPart, size / 4);

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                map[i][j] = firstMapPart[i][j];
                map[size + i][j] = secondMapPart[i][j];
            }
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                if (map[i + size / 2][j].type.equals(TerrainType.WATER) && middleMapPart[i][j].type.equals(TerrainType.WATER))
                    map[i + size / 2][j].type = TerrainType.WATER;
                else
                    map[i + size / 2][j].type = TerrainType.GROUND;
            }

        for (int i = 0; i < 2 * size; i++)
            for (int j = 0; j < size; j++)
                if (map[i][j].type == TerrainType.WATER)
                    map[i][j].height = -1;
                else
                    map[i][j].height = -2;
        height = -1;
        for (int e = 0; e < 100; e++) {
            height++;
            for (int i = 0; i < 2 * size; i++)
                for (int j = 0; j < size; j++)
                    if (checkHeight(i, j, height)) {
                        setCell(map, i, j, TerrainType.SAND);
                        map[i][j].height = height;
                    }
        }
        for (int i = 0; i < 2 * size; i++)
            for (int j = 0; j < size; j++)
                if (!(map[i][j].type == TerrainType.WATER))
                    if (map[i][j].height == 1)
                        map[i][j].type = TerrainType.SAND;
                    else
                        map[i][j].type = TerrainType.GROUND;
        for (int i = 0; i < 10; i++) //кол-во рек
        {
            List<Integer> ec = chooseRandomGroundEdge(); //edge coordinates
            int x = ec.get(0);
            int y = ec.get(1);
            edges[x][y].isRiver = true;
            int direction = getDirection(x,y);
            while (farFromSea(x,y)) {
                ec = chooseNextEdge(x,y, direction);
                x = ec.get(0);
                y = ec.get(1);
                edges[x][y].isRiver = true;
                //TODO: проверить крайние случаи
            }
        }

    }

    private void setCell(MapTile[][] mapPart, int x, int y, TerrainType value) {
        mapPart[x][y].type = value;
    }

    private TerrainType getCell(MapTile[][] mapPart, int x, int y) {
        return mapPart[x][y].type;
    }


    private boolean checkHeight(int i, int j, int height) {
        return (map[i][j].type == TerrainType.GROUND) &&
                ((i - 1 >= 0) && (map[i - 1][j].height + 1 == height) ||
                        (i + 1 < size*2) && (map[i + 1][j].height + 1 == height) ||
                        (j - 1 >= 0) && (map[i][j - 1].height + 1 == height) ||
                        (j + 1 < size) && (map[i][j + 1].height + 1 == height));
    }

    private void makeFractal(MapTile[][] mapPart, int step) {
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

    private List<Integer> getTilesFromEdge(int x, int y) {
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

    private List<Integer> chooseRandomGroundEdge() {
        Random rnd = new Random();
        int x = 0,y = 0;
        boolean isGround = false;
        ArrayList<Integer> res = new ArrayList<Integer>();
        while (!(isGround)) {
            x = rnd.nextInt(2 * size - 1);
            y = rnd.nextInt(2 * size - 1);
            List<Integer> tc = getTilesFromEdge(x, y);
            if ((map[tc.get(0)][tc.get(1)].type != TerrainType.WATER) &&
                    (map[tc.get(2)][tc.get(3)].type != TerrainType.WATER)) //ребро - между тайлами земли
                isGround = true;
        }
        res.add(x);
        res.add(y);
        return res;
    }

    private int getDirection (int x, int y) {
        int dir;
        if (x % 2 == 0) {// ребро - горизонтальное
            List<Integer> left = getTilesFromEdge(x, y-1);
            int leftHeight = map[left.get(0)][left.get(1)].height + map[left.get(2)][left.get(3)].height;
            List<Integer> right = getTilesFromEdge(x, y+1);
            int rightHeight = map[right.get(0)][right.get(1)].height + map[right.get(2)][right.get(3)].height;
            if (leftHeight > rightHeight)
                dir = 1;
            else if (leftHeight < rightHeight)
                dir = -1;
            else {
                Random rnd = new Random();
                int r = rnd.nextInt(1);
                if (r == 0)
                    dir = 1;
                else
                    dir = -1;
            }
        }
        else { //ребро - вертикальное
            List<Integer> down = getTilesFromEdge(x+2, y);
            int downHeight = map[down.get(0)][down.get(1)].height + map[down.get(2)][down.get(3)].height;
            List<Integer> up = getTilesFromEdge(x-2, y);
            int upHeight = map[up.get(0)][up.get(1)].height + map[up.get(2)][up.get(3)].height;
            if (downHeight > upHeight)
                dir = 1;
            else if (downHeight < upHeight)
                dir = -1;
            else {
                Random rnd = new Random();
                int r = rnd.nextInt(1);
                if (r == 0)
                    dir = 1;
                else
                    dir = -1;
            }
        }
        return dir;
    }

    private boolean farFromSea(int x, int y) {
        boolean res = true;
        List<Integer> tc = getTilesFromEdge(x, y);
        if ((map[tc.get(0)][tc.get(1)].height == 1) &&
                (map[tc.get(2)][tc.get(3)].height == 1))
            res = false;
        return res;
    }

    private List<Integer> chooseNextEdge(int x, int y, int direction) {
        List<Integer> neighbours = findNeighbours(x,y, direction);
        return chooseMin(neighbours);
    }

    private List<Integer> findNeighbours(int x, int y, int direction) {
        List<Integer> res = new ArrayList<Integer>();
        if (x % 2 == 0) {  //ребро - горизонтальное
            if (direction == -1) {
                res.add(x - 1);
                res.add(y);
                res.add(x);
                res.add(y - 1);
                res.add(x + 1);
                res.add(y);
            }
            else if (direction == 1) {
                res.add(x - 1);
                res.add(y + 1);
                res.add(x);
                res.add(y + 1);
                res.add(x + 1);
                res.add(y + 1);
            }
        }
        else
        {
            if (direction == 1) {
                res.add(x - 1);
                res.add(y - 1);
                res.add(x - 2);
                res.add(y);
                res.add(x - 1);
                res.add(y);
            }
            else if (direction == -1) {
                res.add(x + 1);
                res.add(y - 1);
                res.add(x + 2);
                res.add(y);
                res.add(x + 1);
                res.add(y);
            }
        }
        return res;
    }

    private List<Integer> chooseMin(List<Integer> neighbours) {
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
                    Random rn = new Random();
                    int r = rn.nextInt(1);
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

    private int findMin(List<Integer> neighbours) {
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
}
