package com.example.demo2;

import javafx.scene.image.Image;

public class Ghost {
    int[] ghost_location = new int[2];
    int[] ghost_last_location = new int[2];
    Direction last_direction = null;
    int direction_counter = 0;

    GhostState ghost_Status = GhostState.Alive;

    public void update_ghost_loc(int row , int col){
        ghost_location[0] = row;
        ghost_location[1] = col;
        ghost_last_location[0] = row;
        ghost_last_location[1] = col;


    }
    void update_ghost_status(GhostState state){
        ghost_Status = state;
    }
    void ghost_reset(){
        ghost_location = new int[2];
        ghost_last_location = new int[2];
        last_direction = null;
        direction_counter = 0;

        //Image last_image ;
        ghost_Status = GhostState.Alive;

    }
    Image move(int mode , Image last_image ) {

        //mode == 0 blue
        //mode == 1 red
        //mode == 2 green
        //mode == 3 yellow
        //get a random location - up - down - right - left
        int[][] available_moves = new int[4][2];
        int counter = 0;
        Image temp = null;
        int flag = 0;
            if (ghost_location[0] + 1 < 21 - 1 &&
                    View.grid[ghost_location[0] + 1][ghost_location[1]].getImage() != View.wall &&
                    View.grid[ghost_location[0] + 1][ghost_location[1]].getImage() != View.red_ghost &&
                    View.grid[ghost_location[0] + 1][ghost_location[1]].getImage() != View.blue_ghost &&
                    View.grid[ghost_location[0] + 1][ghost_location[1]].getImage() != View.green_ghost &&
                    View.grid[ghost_location[0] + 1][ghost_location[1]].getImage() != View.yellow_ghost) {
                //up

                if(last_direction == Direction.Up)flag = 1;
                available_moves[counter][0] = ghost_location[0] + 1;
                available_moves[counter][1] = ghost_location[1];
                counter++;
            }
            if ( ghost_location[0] - 1 > 0 &&
                    View.grid[ghost_location[0] - 1][ghost_location[1]].getImage() != View.wall &&
                    View.grid[ghost_location[0] - 1][ghost_location[1]].getImage() != View.red_ghost &&
                    View.grid[ghost_location[0] - 1][ghost_location[1]].getImage() != View.blue_ghost &&
                    View.grid[ghost_location[0] - 1][ghost_location[1]].getImage() != View.green_ghost &&
                    View.grid[ghost_location[0] - 1][ghost_location[1]].getImage() != View.yellow_ghost) {
                //down

                if(last_direction == Direction.Down)flag = 2;
                available_moves[counter][0] = ghost_location[0] - 1;
                available_moves[counter][1] = ghost_location[1];
                counter++;

            }
            if (ghost_location[1] - 1 > 0 &&
                    View.grid[ghost_location[0]][ghost_location[1] - 1].getImage() != View.wall &&
                    View.grid[ghost_location[0]][ghost_location[1] - 1].getImage() != View.red_ghost &&
                    View.grid[ghost_location[0]][ghost_location[1] - 1].getImage() != View.blue_ghost &&
                    View.grid[ghost_location[0]][ghost_location[1] - 1].getImage() != View.green_ghost &&
                    View.grid[ghost_location[0]][ghost_location[1] - 1].getImage() != View.yellow_ghost) {
                //left

                if(last_direction == Direction.Right)flag = 3;
                available_moves[counter][0] = ghost_location[0];
                available_moves[counter][1] = ghost_location[1] - 1;
                counter++;
            }
            if ( ghost_location[1] + 1 < 37 - 1 &&
                    View.grid[ghost_location[0]][ghost_location[1] + 1].getImage() != View.wall &&
                    View.grid[ghost_location[0]][ghost_location[1] + 1].getImage() != View.red_ghost &&
                    View.grid[ghost_location[0]][ghost_location[1] + 1].getImage() != View.blue_ghost &&
                    View.grid[ghost_location[0]][ghost_location[1] + 1].getImage() != View.green_ghost &&
                    View.grid[ghost_location[0]][ghost_location[1] + 1].getImage() != View.yellow_ghost) {
                //right

                if(last_direction == Direction.Right)flag = 4;
                available_moves[counter][0] = ghost_location[0];
                available_moves[counter][1] = ghost_location[1] + 1;
                counter++;
            }
            //counter--;
            int spot = Map.randomNumberGenerator(0, counter);

            temp = View.grid[available_moves[spot][0]][available_moves[spot][1]].getImage();
            if(flag != 0){
                switch (flag){
                    case 1:
                        available_moves[spot][0] = ghost_location[0] + 1;
                        available_moves[spot][1] = ghost_location[1];
                        break;
                    case 2:
                        available_moves[spot][0] = ghost_location[0] - 1;
                        available_moves[spot][1] = ghost_location[1];
                        break;
                    case 3:
                        available_moves[spot][0] = ghost_location[0];
                        available_moves[spot][1] = ghost_location[1] - 1;
                        break;
                    case 4:
                        available_moves[spot][0] = ghost_location[0];
                        available_moves[spot][1] = ghost_location[1] + 1;
                        break;

                }
            }
            flag = 0;
            if( available_moves[spot][0] - ghost_location[0]  == 1)last_direction = Direction.Up;
            else if( available_moves[spot][0] - ghost_location[0]  == -1)last_direction = Direction.Down;
            else if(available_moves[spot][1] - ghost_location[1] == 1)last_direction = Direction.Right;
            else if(available_moves[spot][1] - ghost_location[1] == -1)last_direction = Direction.Left;




            if ((last_image == View.dot) ||
                    (ghost_location[0] == ghost_last_location[0] && ghost_location[1] == ghost_last_location[1])) {
                View.grid[ghost_location[0]][ghost_location[1]].setImage(View.dot);
            } else if (last_image == View.big_dot) {
                View.grid[ghost_location[0]][ghost_location[1]].setImage(View.big_dot);
            } else if (last_image == View.pacman_down || last_image == View.pacman_up ||
                    last_image == View.pacman_left || last_image == View.pacman_right ) {
                if(ghost_Status == GhostState.Alive){
                    View.grid[ghost_location[0]][ghost_location[1]].imageProperty().set(null);
                    Pacman.pacman_status = GhostState.Dead;
                }
                else {
                    ghost_Status = GhostState.Dead;
                }

            } else View.grid[ghost_location[0]][ghost_location[1]].imageProperty().set(null);
            ghost_last_location[0] = ghost_location[0];
            ghost_last_location[1] = ghost_location[1];
            ghost_location[0] = available_moves[spot][0];
            ghost_location[1] = available_moves[spot][1];
            if(ghost_Status != GhostState.Dead) {
                switch (mode) {
                    case 0:
                        View.grid[ghost_location[0]][ghost_location[1]].setImage(View.blue_ghost);
                        break;
                    case 1:
                        View.grid[ghost_location[0]][ghost_location[1]].setImage(View.red_ghost);
                        break;
                    case 2:
                        View.grid[ghost_location[0]][ghost_location[1]].setImage(View.green_ghost);
                        break;
                    case 3:
                        View.grid[ghost_location[0]][ghost_location[1]].setImage(View.yellow_ghost);
                        break;
                }
            }
            return temp;

    }



}
