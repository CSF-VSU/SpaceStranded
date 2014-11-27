package ru.vsu.csf.twopeoplestudios.model.world;

public class World {

    public int size;

    public MapTile[][] map;
    public MapTile[][] firstMapPart;
    public MapTile[][] secondMapPart;
    public MapTile[][] middleMapPart;
    public MapTile[][] borderMapPart;

    public int height;

    public void create() {

        size = 128;
        map = new MapTile[2*size][size];
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
        borderMapPart = new MapTile[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                borderMapPart[i][j] = new MapTile();

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

        setCell(borderMapPart, 0, 0, TerrainType.WATER);
        setCell(borderMapPart, size / 2, 0, TerrainType.WATER);
        setCell(borderMapPart, 0, size / 2, TerrainType.WATER);
        setCell(borderMapPart, size / 2, size / 2, TerrainType.GROUND);

        makeFractal(firstMapPart, size / 4);
        makeFractal(secondMapPart, size / 4);
        makeFractal(middleMapPart, size / 4);
        makeFractal(borderMapPart, size / 4);


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
        /*for (int i = 0; i < size/2; i++)
            for (int j = 0; j < size; j++) {
                if ((firstMapPart[i][j].type == TerrainType.WATER) &&
                    (borderMapPart[size / 2 + i][j].type == TerrainType.WATER))
                    map[i][j].type = TerrainType.WATER;
                else
                    map[i][j].type = TerrainType.GROUND;

                if ((firstMapPart[size/2 + i][j].type == TerrainType.WATER) &&
                    (middleMapPart[i][j].type == TerrainType.WATER))
                    map[size/2 + i][j].type = TerrainType.WATER;
                else
                    map[size/2 + i][j].type = TerrainType.GROUND;

                if ((secondMapPart[i][j].type == TerrainType.WATER) &&
                   (middleMapPart[size/2 + i][j].type == TerrainType.WATER))
                    map[size+i][j].type = TerrainType.WATER;
                else
                    map[size+i][j].type = TerrainType.GROUND;

                if ((secondMapPart[size/2+i][j].type == TerrainType.WATER) &&
                    (borderMapPart[i][j].type == TerrainType.WATER))
                    map[3/2 * size + i][j].type = TerrainType.WATER;
                else
                    map[3/2 * size + i][j].type = TerrainType.GROUND;
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
                        setCell(map, i, j, TerrainType.SAND); //песок сгенерирован
                        map[i][j].height = height;
                    }
        }
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


    /*public static boolean isNearWater (int i, int j) {
        return (map[i][j].type == TerrainType.GROUND) &&
               ((i - 1 >= 0) && (map[i-1][j].type == TerrainType.WATER) ||
               (i + 1 < size) && (map[i+1][j].type == TerrainType.WATER) ||
               (j - 1 >= 0) && (map[i][j-1].type == TerrainType.WATER) ||
               (j + 1 < size) && (map[i][j+1].type == TerrainType.WATER));
    }*/
}
