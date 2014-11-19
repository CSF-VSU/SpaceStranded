package ru.vsu.csf.twopeoplestudios.model.world;

public class World {

    public static int size;
    public static MapTile[][] map;
    public static int height;

    public static void create() {
        height = 0;
        size = 64;
        map = new MapTile[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                map[i][j] = new MapTile();
        setCell(0, 0, TerrainType.WATER);
        setCell(size / 2, 0, TerrainType.WATER);
        setCell(0, size / 2, TerrainType.WATER);
        setCell(size / 2, size / 2, TerrainType.GROUND);
        makeFractal(size / 4); //суша и моря сгенерированы
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (map[i][j].type == TerrainType.WATER)
                    map[i][j].height = 0;
                else
                    map[i][j].height = -1;
        for (int e = 0; e < 20; e++) {
            height++;
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    if (checkHeight(i, j, height)) {
                        setCell(i, j, TerrainType.SAND); //песок сгенерирован
                        map[i][j].height = height;
                    }
        }
    }


    public static void setCell(int x, int y, TerrainType value) {
        map[x][y].type = value;
    }

    public static TerrainType getCell(int x, int y) {
        return map[x][y].type;
    }


    public static boolean checkHeight(int i, int j, int height) {
        return (map[i][j].type == TerrainType.GROUND) &&
                ((i - 1 >= 0) && (map[i - 1][j].height + 1 == height) ||
                        (i + 1 < size) && (map[i + 1][j].height + 1 == height) ||
                        (j - 1 >= 0) && (map[i][j - 1].height + 1 == height) ||
                        (j + 1 < size) && (map[i][j + 1].height + 1 == height));
    }

    public static void makeFractal(int step) {
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
                setCell(x, y, getCell(cx, cy));
            }
        }
        if (step > 1) makeFractal(step / 2);
    }


    /*public static boolean isNearWater (int i, int j) {
        return (map[i][j].type == TerrainType.GROUND) &&
               ((i - 1 >= 0) && (map[i-1][j].type == TerrainType.WATER) ||
               (i + 1 < size) && (map[i+1][j].type == TerrainType.WATER) ||
               (j - 1 >= 0) && (map[i][j-1].type == TerrainType.WATER) ||
               (j + 1 < size) && (map[i][j+1].type == TerrainType.WATER));
    }*/
}
