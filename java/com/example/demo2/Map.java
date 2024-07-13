package com.example.demo2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Map {
    static int [] pacman_location = new int[2];
    static int [][] big_dot_location= new int[5][2];
    static int [][] ghosts_location = new int [4][2];
    static int big_dot_len;
    static String  [] map_location = {"src/main/resources/Picture/map1.txt" ,"src/main/resources/Picture/map2.txt" , "src/main/resources/Picture/map3.txt" };
    static int Max;
    static int MaxScore = 0;
    int [][] available_spot_map_one = new int[400][2];
    void find_av_spot(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner map = new Scanner(file);
        String line = "";
        int row_count = 0;
        int counter = 0;
        while (map.hasNextLine()) {
            line = map.nextLine();
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == 'd') {
                    available_spot_map_one[counter][0] = row_count;
                    available_spot_map_one[counter][1] = i;
                    counter++;
                    MaxScore++;
                }
                if(line.charAt(i) == ' ')MaxScore++;

            }
            row_count++;
        }
        this.Max = counter--;
    }
    public static int randomNumberGenerator(int min, int max)
    {
        double r = Math.random();
        int randomNum = (int)(r * (max - min)) + min;
        return randomNum;
    }
    void set_random(int p) throws IOException {
        find_av_spot(map_location[p]);
        int [] spots = new int[6];
        int pac_spot = randomNumberGenerator(0,this.Max+1);
        pacman_location[0] = available_spot_map_one[pac_spot][0];
        pacman_location[1] = available_spot_map_one[pac_spot][1];
        int spot_count = 0;
        for (int i = 0 ; i < 5 ; i++) {
           int spot =  randomNumberGenerator(0, this.Max+1);
           if(spot != pac_spot){
               spots[spot_count] = spot;
               spot_count++;
           }
        }
        for (int i = 0 ; i < spot_count ; i++){
            big_dot_location[i][0] = available_spot_map_one[spots[i]][0];
            big_dot_location[i][1] = available_spot_map_one[spots[i]][1];
        }
        big_dot_len = spot_count;
        int ghost_counter = 0 ;
        int flag = 1;
        int [] temp_spots = new int[4];
        while (ghost_counter < 4){
            int spot = randomNumberGenerator(0,this.Max+1);
            if(spot == pac_spot )flag = 0;
            else {
                for(int i = 0 ; i < big_dot_len ; i++){
                    if(spot == spots[i]){
                        flag = 0;
                        break;
                    }
                }
                for (int i = 0 ; i < ghost_counter ; i++){
                    if(spot == temp_spots[i]){
                        flag = 0 ;
                         break;
                    }
                }
            }
            if(flag == 1){
                ghosts_location[ghost_counter][0] = available_spot_map_one[spot][0];
                ghosts_location[ghost_counter][1] = available_spot_map_one[spot][1];
                temp_spots[ghost_counter] = spot;
                ghost_counter++;
                flag = 1;
            }
        }


    }
    int is_ghost(int row , int col){
        for(int i = 0 ; i < 4 ; i++){
            if(ghosts_location[i][0] == row && ghosts_location[i][1] == col){
                return i;
            }
        }
        return -1;
    }


}
