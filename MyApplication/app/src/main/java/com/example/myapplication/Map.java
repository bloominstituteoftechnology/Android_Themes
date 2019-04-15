package com.example.myapplication;

import java.util.Random;

public class Map {
     char[][] map;
     private int WIDTH;
     private int HEIGHT;
     public int DENSITY_OF_MAP; //bigger is less dense

    Map(int density, int height, int width){
        DENSITY_OF_MAP = density;
        WIDTH = width;
        HEIGHT = height;
        map = new char[height][width];
    }

     public char getCharYX(int y, int x){
         return map[y][x];
     }

     public void setCharXY(int x, int y, char changeTo){
         map[x][y] = changeTo;
     }

     public void randomize(){

         for(int i = 0; i < WIDTH; i++){
             for(int j = 0; j < HEIGHT; j++){
                 char cha;
                 Random random = new Random();
                 int rand = random.nextInt(DENSITY_OF_MAP);


                 switch(rand){
                     case 1:
                         cha = 'X'; // mountain
                         break;
                     case 2:
                         cha = 'M'; // monster
                         break;
                     case 3:
                         cha = 'C'; // chest
                         break;
                     case 4:
                         cha = 'X'; //extra mountain
                         break;
                     default:
                         cha = ' '; // empty space
                 }
                 if(i < 20 || j < 20)// creates border for an easy way to prevent oob
                 {
                     cha = '#';
                 }
                 map[i][j] = cha;
             }

         }
     }
}
