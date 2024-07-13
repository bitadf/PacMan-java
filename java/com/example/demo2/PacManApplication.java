package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PacManApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo2/board_map.fxml"));
        Parent root = loader.load();
        //Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Pac-Man Game");
        Controller controller = loader.getController();
        root.setOnKeyPressed(controller);
        //controller.SetRowAndCol(21 , 37);
        double sceneWidth = (20 * 37) + 20.0;
        double sceneHeight = (20 * 21)+ 100.0;
        primaryStage.setScene(new Scene(root, sceneWidth , sceneHeight));
        primaryStage.show();
        root.requestFocus();
        //primaryStage.setScene(scene);
        //primaryStage.show();

    }




    public static void main(String[] args) throws IOException {
        launch();

    }
}