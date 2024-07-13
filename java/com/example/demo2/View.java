package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class View extends Group {
    @FXML private int row_num = 21;
    @FXML private int col_num = 37;

    public static ImageView[][] grid;
    static int big_dot_counter = 0;

     public Map board_map = new Map();
    //public static Image pacman;
    public static Image pacman_up;
    public static Image pacman_down;
    public static Image pacman_left;
    public static Image pacman_right;
    public static Image wall;
    public static Image dot;
    public static Image big_dot;
    public static Image blue_ghost;
    public static Image green_ghost;
    public static Image red_ghost ;
    public static Image yellow_ghost;
    public static Image white_ghost;
    public static Image last_blue_ghost = null;
    public static Image last_green_ghost = null;
    public static Image last_red_ghost = null;
    public static Image last_yellow_ghost = null;

    public View() throws IOException {
        // Initialize your images
        pacman_up = new Image(getClass().getResourceAsStream("/Picture/PacMan_Up.png"));
        pacman_down = new Image(getClass().getResourceAsStream("/Picture/PacMan_Down.png"));
        pacman_right = new Image(getClass().getResourceAsStream("/Picture/PacMan_Right.png"));
        pacman_left = new Image(getClass().getResourceAsStream("/Picture/PacMan_Left.png"));
        wall = new Image(getClass().getResourceAsStream("/Picture/wall2.png"));
        dot = new Image(getClass().getResourceAsStream("/Picture/Small_Dot.png"));
        big_dot = new Image(getClass().getResourceAsStream("/Picture/Big_Dot.png"));
        blue_ghost = new Image(getClass().getResourceAsStream("/Picture/Blue_Ghost.png"));
        red_ghost = new Image(getClass().getResourceAsStream("/Picture/Red_Ghost.png"));
        yellow_ghost = new Image(getClass().getResourceAsStream("/Picture/Yellow_Ghost.png"));
        green_ghost = new Image(getClass().getResourceAsStream("/Picture/Green_Ghost.png"));
        white_ghost = new Image(getClass().getResourceAsStream("/Picture/White_Ghost.png"));
        this.initialize();



    }
    void reset() throws IOException {
        for (int row = 0 ; row < row_num ; row++) {
            for (int col = 0; col < col_num; col++) {
                grid[row][col].imageProperty().set(null);
            }
        }
       // new View();
        big_dot_counter = 0;
        last_blue_ghost = null;
        last_green_ghost = null;
        last_red_ghost = null;
        last_yellow_ghost = null;
    }
    void initialize() throws FileNotFoundException {
        grid = new ImageView[row_num + 1][col_num ];
        for (int row = 0; row < row_num; row++) {
            for (int column = 0; column < col_num; column++) {
                ImageView imageView = new ImageView();
                imageView.setX((double)column * 20);
                imageView.setY((double)row * 20);
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                grid[row][column] = imageView;
                this.getChildren().add(imageView);
            }
        }
        int p = Map.randomNumberGenerator(0,3);
        try {
            board_map.set_random(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File file = new File(Map.map_location[p]);
        Scanner map = new Scanner(file);
        String line = "";
        int row = 0;

        int ghost_counter = 0;
        while (map.hasNextLine()){
            line = map.nextLine();
            if(row == row_num )break;

            for (int col = 0 ; col < col_num ; col++){

                if(line.charAt(col) == 'w'){
                    grid[row][col].setImage(wall);
                }
                else if(line.charAt(col) == ' '){
                    grid[row][col].setImage(dot);

                }
                else if(line.charAt(col) == 'd'){
                    if(row == Map.pacman_location[0] && col == Map.pacman_location[1])grid[row][col].setImage(pacman_left);
                    else if(big_dot_counter <= (Map.big_dot_len - 1) && row == Map.big_dot_location[big_dot_counter][0] && col == Map.big_dot_location[big_dot_counter][1]){
                        grid[row][col].setImage(big_dot);
                        big_dot_counter++;


                    }

                    else if(board_map.is_ghost(row,col) >= 0 ){
                        switch (ghost_counter){
                            case 0:
                                grid[row][col].setImage(red_ghost);
                                Controller.red_ghost.update_ghost_loc(row,col);
                                Controller.red_ghost.update_ghost_status(GhostState.Alive);
                                break;
                            case 1:
                                grid[row][col].setImage(blue_ghost);
                                Controller.blue_ghost.update_ghost_loc(row,col);
                                Controller.blue_ghost.update_ghost_status(GhostState.Alive);
                                break;
                            case 2:
                                grid[row][col].setImage(green_ghost);
                                Controller.green_ghost.update_ghost_loc(row,col);
                                Controller.green_ghost.update_ghost_status(GhostState.Alive);
                                break;
                            case 3:
                                grid[row][col].setImage(yellow_ghost);
                                Controller.yellow_ghost.update_ghost_loc(row,col);
                                Controller.yellow_ghost.update_ghost_status(GhostState.Alive);
                                break;

                        }
                        ghost_counter++;

                    }
                    else {
                        grid[row][col].setImage(dot);

                    }
                }
            }
            row++;
        }
    }
}
