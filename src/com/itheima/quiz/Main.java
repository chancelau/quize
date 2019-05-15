package com.itheima.quiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = getClass().getResource("/uiXml/main_ui.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.<Controller>getController();
        //Parent root = FXMLLoader.load(getClass().getResource("main_ui.fxml"));
        primaryStage.setTitle("知识竞赛");
        primaryStage.setScene(new Scene(root, 700, 460));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
