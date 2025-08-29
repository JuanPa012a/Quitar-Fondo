package com.jp;

import java.io.IOException;

import com.jp.util.Path;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application{

    public static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        mainStage = primaryStage;
        mainStage.setTitle("Quitar fondo");
        mainStage.setMaximized(true);
        mainStage.getIcons().add( new Image(
            getClass().getResourceAsStream(Path.ICON))
        );
        setScene(Path.MAIN);
      
    }

    public static void main(String[] args) {
        launch(args);
    }
    void setScene(String path){
        Pane load;
        try {
            load = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(load);
            scene.getStylesheets().add(getClass().getResource(Path.STYLE).toExternalForm());
            // AccionesGenerales.agregarTitulos(load);
            mainStage.setScene(scene);
            mainStage.show();   
        } catch (IOException e) {
           
        }
        
    }
    
}
