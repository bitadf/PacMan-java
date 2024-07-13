package com.example.demo2;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.example.demo2.View.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.security.Key;
import java.security.KeyException;
import java.util.concurrent.TimeUnit;

public class Controller implements EventHandler<KeyEvent> {
    final private static double FRAMES_PER_SECOND = 4.0;
    static int Row_number;
    static int Col_number;
    @FXML
    private View view = new View();
    @FXML
    private Label scoreLabel;
    @FXML
    private Label gameLabel;
    @FXML
    private Label gide1;
    @FXML
    private Label gide2;
    private Pacman pacman = new Pacman();
    static Ghost red_ghost = new Ghost();

    static Ghost blue_ghost = new Ghost();

    static Ghost green_ghost = new Ghost();

    static Ghost yellow_ghost = new Ghost();

    static int score = 0;
//    static GhostState pacman_status = GhostState.Alive;
    private Timer timer;
    int white_ghost_counter = 0;
    int temp_white_ghost ;
    Image temp_white_ghost_image;

    static int white_mode = 0;
    private boolean paused = false;

    public Controller() throws IOException {
    }
    void SetRowAndCol(int row , int col){
        Row_number = row;
        Col_number = col;
    }
    void stop(){
        this.timer.cancel();
        this.paused = true;

    }

    public void initialize() throws FileNotFoundException {
        pacman.update_location(Map.pacman_location[0] , Map.pacman_location[1]);
        paused = false;
        gameLabel.setText(String.format("PacMan"));
        gide1.setText("restart(key r)");
        gide2.setText("exit(key esc)");
        this.startTimer();

    }
    private void handle_ghosts(){
        //place the white ghost
        if(white_mode == 1){
            //a random ghost
            temp_white_ghost = Map.randomNumberGenerator(0 , 3);
            switch (temp_white_ghost){
                case 0:
                    temp_white_ghost_image = View.blue_ghost;
                    View.blue_ghost = View.white_ghost;
                    blue_ghost.ghost_Status = GhostState.Sleep;

                    break;
                case 1:
                    temp_white_ghost_image = View.red_ghost;
                    View.red_ghost = View.white_ghost;
                    red_ghost.ghost_Status = GhostState.Sleep;
                    break;
                case 2:
                    temp_white_ghost_image = View.green_ghost;
                    View.green_ghost = View.white_ghost;
                    green_ghost.ghost_Status = GhostState.Sleep;
                    break;
                case 3:
                    temp_white_ghost_image = View.yellow_ghost;
                    View.yellow_ghost = View.white_ghost;
                    yellow_ghost.ghost_Status = GhostState.Sleep;
                    break;
            }
            white_mode++;

        }
        if(white_mode == 2)white_ghost_counter++;
        //replace the prev ghost
        if(white_ghost_counter == 15){
            switch (temp_white_ghost){
                case 0:
                    View.blue_ghost = temp_white_ghost_image;
                    if(blue_ghost.ghost_Status != GhostState.Dead)blue_ghost.ghost_Status = GhostState.Alive;
                    break;
                case 1:
                    View.red_ghost = temp_white_ghost_image;
                    if(red_ghost.ghost_Status != GhostState.Dead)red_ghost.ghost_Status = GhostState.Alive;
                    break;
                case 2:
                    View.green_ghost = temp_white_ghost_image;
                    if(green_ghost.ghost_Status != GhostState.Dead)green_ghost.ghost_Status = GhostState.Alive;
                    break;
                case 3:
                    View.yellow_ghost = temp_white_ghost_image;
                    if(yellow_ghost.ghost_Status != GhostState.Dead)yellow_ghost.ghost_Status = GhostState.Alive;
                    break;
            }
            white_ghost_counter = 0;
            white_mode = 0;
        }

        View.last_blue_ghost = blue_ghost.move(0 , View.last_blue_ghost);
        View.last_red_ghost = red_ghost.move(1,View.last_red_ghost);
        View.last_green_ghost = green_ghost.move(2 ,View.last_green_ghost);
        View.last_yellow_ghost = yellow_ghost.move(3 , View.last_yellow_ghost);



    }
    private void restart() throws IOException {

        score = 0;
        Map.MaxScore = 0;
        Map.Max = 0;
        white_ghost_counter = 0;
        //view =  new View();
        blue_ghost.ghost_reset();
        red_ghost.ghost_reset();
        green_ghost.ghost_reset();
        yellow_ghost.ghost_reset();
        view.reset();
        view.initialize();
        pacman.pacman_reset();

        //paused = false;
        this.initialize();

    }

    private void update() throws IOException, InterruptedException {
        if(!paused){
        handle_ghosts();
        pacman.move();
        scoreLabel.setText("Score : " + score);

        if(Pacman.pacman_status == GhostState.Dead){
            gameLabel.setText(String.format("Game Over"));
            //gameLabel.setText("Game Over");
            paused = true;
            timer.cancel();

            //Thread.sleep(1000);
            //this.restart();
        }
        if(Pacman.eaten_dots == Map.MaxScore){
            gameLabel.setText(String.format("You Won"));
            //gameLabel.setText("You Won");

            //timer.cancel();
            paused = true;
            //this.restart();
        }
    }
        if(paused )timer.cancel();
    }


    private void startTimer(){
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //update
                        try {
                            update();
                        } catch (IOException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        };
        long frameTimeInMill = (long) (1000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask , 0 , frameTimeInMill);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode direction = keyEvent.getCode();
        try {
        if(direction == KeyCode.A){
            //left
            pacman.pacman_last_direction = Direction.Left;
        }
        else if(direction == KeyCode.D){
            //right
            pacman.pacman_last_direction = Direction.Right;
        }
        else if(direction == KeyCode.W){
            //up
            pacman.pacman_last_direction = Direction.Up;

        }
        else if(direction == KeyCode.S)
        {

            pacman.pacman_last_direction = Direction.Down;
            //down
        }else if (direction == KeyCode.R){
            this.stop();
            restart();
        }
        else if(direction == KeyCode.ESCAPE)
            System.exit(0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
