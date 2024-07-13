package com.example.demo2;
import com.example.demo2.Map.*;
import javafx.scene.image.Image;

import java.io.*;

public class Pacman {
    public int [] pacman_current_loc = {Map.pacman_location[0] , Map.pacman_location[1]}; // {row , col}
    static GhostState pacman_status = GhostState.Alive;
    Direction pacman_last_direction = Direction.Left;
    static int eaten_dots = 0;
    int find_score(int row , int col){
        if(View.grid[row][col].getImage() == View.big_dot)return 5;
        if(View.grid[row][col].getImage() == View.dot)return 1;
        if(View.grid[row][col].getImage() == View.white_ghost)return 6;
        return 0;
    }
    void update_location(int row , int col){
        pacman_current_loc[0] = row;
        pacman_current_loc[1] = col;
    }
    void pacman_reset(){
        pacman_current_loc[0] = Map.pacman_location[0]; // {row , col}
        pacman_current_loc[1] = Map.pacman_location[1];
        pacman_status = GhostState.Alive;
        pacman_last_direction = Direction.Left;
        eaten_dots = 0;
    }
    GhostState pacman_dead_alive(Image new_image ,int row , int col){
        GhostState st = GhostState.None ;
        if(new_image == View.green_ghost || new_image == View.blue_ghost ||
                new_image == View.red_ghost || new_image == View.yellow_ghost){

            if(new_image == View.green_ghost) st = Controller.green_ghost.ghost_Status;
            if(new_image == View.blue_ghost) st = Controller.blue_ghost.ghost_Status;
            if(new_image == View.red_ghost) st = Controller.red_ghost.ghost_Status;
            if(new_image == View.yellow_ghost) st = Controller.yellow_ghost.ghost_Status;
        }
        if(st == GhostState.Alive)return GhostState.Dead;//pacman died
        if(st == GhostState.Sleep){
            if(new_image == View.green_ghost) Controller.green_ghost.ghost_Status = GhostState.Dead;
            if(new_image == View.blue_ghost) Controller.blue_ghost.ghost_Status = GhostState.Dead;
            if(new_image == View.red_ghost) Controller.red_ghost.ghost_Status = GhostState.Dead;
            if(new_image == View.yellow_ghost) Controller.yellow_ghost.ghost_Status = GhostState.Dead;
            return GhostState.Alive;//pacman eats white ghost
        }
        return st;//no ghost

    }
    void move() throws IOException {
        int [] new_loc = new int[2];//{row , col}
        Direction direction = pacman_last_direction;
        if(direction == Direction.Up){
            new_loc[0] = pacman_current_loc[0] - 1;
            new_loc[1] = pacman_current_loc[1];

            int temp_score = find_score(new_loc[0] , new_loc[1]);
            Controller.score += temp_score;
            if(temp_score == 1 )eaten_dots++;
            if(temp_score == 5){
                Pacman.eaten_dots++;
                Controller.white_mode = 1;
            }

            Image new_image = View.grid[new_loc[0]][new_loc[1]].getImage();
            GhostState pc_st = pacman_dead_alive(new_image,new_loc[0] , new_loc[1]);

            if(pc_st == GhostState.Dead){
                View.grid[new_loc[0]][new_loc[1]].setImage(new_image);
                pacman_status = GhostState.Dead;
                View.grid[pacman_current_loc[0]][pacman_current_loc[1]].imageProperty().set(null);

                //return;
            }
            if(pacman_status!= GhostState.Dead && new_image != View.wall){
                View.grid[pacman_current_loc[0]][pacman_current_loc[1]].imageProperty().set(null);
                pacman_current_loc[0]--;
                View.grid[new_loc[0]][new_loc[1]].setImage(View.pacman_up);
            }

        }
        else if(direction == Direction.Down){
            new_loc[0] = pacman_current_loc[0] + 1;
            new_loc[1] = pacman_current_loc[1];

            int temp_score = find_score(new_loc[0] , new_loc[1]);
            Controller.score += temp_score;
            if(temp_score == 1)eaten_dots++;
            if(temp_score == 5){
                Pacman.eaten_dots++;
                Controller.white_mode = 1;
            }
            Image new_image = View.grid[new_loc[0]][new_loc[1]].getImage();
            GhostState pc_st = pacman_dead_alive(new_image,new_loc[0] , new_loc[1]);
            if(pc_st == GhostState.Dead){
                View.grid[new_loc[0]][new_loc[1]].setImage(new_image);
                pacman_status = GhostState.Dead;
                View.grid[pacman_current_loc[0]][pacman_current_loc[1]].imageProperty().set(null);

                //return;
            }
            if(pacman_status!= GhostState.Dead && new_image != View.wall){
                View.grid[pacman_current_loc[0]][pacman_current_loc[1]].imageProperty().set(null);
                pacman_current_loc[0]++;
                View.grid[new_loc[0]][new_loc[1]].setImage(View.pacman_down);
            }

        }
        else if(direction == Direction.Left){
            new_loc[0] = pacman_current_loc[0];
            new_loc[1] = pacman_current_loc[1] - 1 ;

            int temp_score = find_score(new_loc[0] , new_loc[1]);
            Controller.score += temp_score;
            if(temp_score == 1 )eaten_dots++;
            if(temp_score == 5){
                Pacman.eaten_dots++;
                Controller.white_mode = 1;
            }
            Image new_image = View.grid[new_loc[0]][new_loc[1]].getImage();
            GhostState pc_st = pacman_dead_alive(new_image,new_loc[0] , new_loc[1]);
            if(pc_st == GhostState.Dead){
                View.grid[new_loc[0]][new_loc[1]].setImage(new_image);
                pacman_status = GhostState.Dead;
                View.grid[pacman_current_loc[0]][pacman_current_loc[1]].imageProperty().set(null);

                //return;
            }
            if(pacman_status!= GhostState.Dead && new_image != View.wall){
                View.grid[pacman_current_loc[0]][pacman_current_loc[1]].imageProperty().set(null);
                pacman_current_loc[1]--;
                View.grid[new_loc[0]][new_loc[1]].setImage(View.pacman_left);
            }

        }
        else if(direction == Direction.Right){
            new_loc[0] = pacman_current_loc[0];
            new_loc[1] = pacman_current_loc[1] + 1 ;

            int temp_score = find_score(new_loc[0] , new_loc[1]);
            Controller.score += temp_score;
            if(temp_score == 1 )eaten_dots++;
            if(temp_score == 5){
                Pacman.eaten_dots++;
                Controller.white_mode = 1;
            }
            Image new_image = View.grid[new_loc[0]][new_loc[1]].getImage();
            GhostState pc_st = pacman_dead_alive(new_image,new_loc[0] , new_loc[1]);
            if(pc_st == GhostState.Dead){
                View.grid[new_loc[0]][new_loc[1]].setImage(new_image);
                pacman_status = GhostState.Dead;
                View.grid[pacman_current_loc[0]][pacman_current_loc[1]].imageProperty().set(null);

                //return;
            }
            if(pacman_status!= GhostState.Dead && new_image != View.wall){
                View.grid[pacman_current_loc[0]][pacman_current_loc[1]].imageProperty().set(null);
                pacman_current_loc[1]++;
                View.grid[new_loc[0]][new_loc[1]].setImage(View.pacman_right);

            }
        }
    }
}
