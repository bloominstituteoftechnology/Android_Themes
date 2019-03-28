package com.example.myapplication;

public class Handler extends Constants{

    private static Map map;
    private static char[][] displayArea;


    public static void createMap() {
        MAP_DISPLAY_HEIGHT = MainActivity.DISPLAY_HEIGHT/70;
        MAP_DISPLAY_WIDTH  = MainActivity.DISPLAY_WIDTH/35;
        PLAYER_POS_Y       = MAP_DISPLAY_HEIGHT/2;
        PLAYER_POS_X       = MAP_DISPLAY_WIDTH/2;

        map                = new Map(MAP_DENSITY, MAP_HEIGHT, MAP_WIDTH);
        map.randomize();
    }

    public static char[][] mapDisplayArea(int y, int x) {
        displayArea = new char[MAP_DISPLAY_HEIGHT][MAP_DISPLAY_WIDTH];
        int counterY = 0;
        for (int i = y; i < y + MAP_DISPLAY_HEIGHT; i++) {
            int counterX = 0;
            for (int j = x; j < x + MAP_DISPLAY_WIDTH; j++) {
                displayArea[counterY][counterX] = map.getCharYX(i, j);
                counterX++;
            }
            counterY++;
        }
        displayArea[PLAYER_POS_Y][PLAYER_POS_X] = 'P';
        return displayArea;
    }


    public static boolean checkCollision(int y, int x){
        y += PLAYER_POS_Y;
        x += PLAYER_POS_X;
        if(map.getCharYX(y,x) == 'X' || map.getCharYX(y,x) == '#'){
            return true;
        }
        else{return false;}
    }

    public static String[] charArr2ToStringArr(char[][] displayedMap) {
        String[] totalMap = new String[displayedMap.length];
        for (int i = 0; i < displayedMap.length; i++) {
            String lineMap = "";
            for (int j = 0; j < displayedMap[i].length; j++) {
                lineMap += displayedMap[i][j];
            }
            totalMap[i] = lineMap;
        }
        return totalMap;
    }
}
