package com.jp.controller;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import static com.jp.App.mainStage;
import com.jp.util.Script;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
 

public class MainController {
    private File selectedImage;
    private File generatedImage;
    @FXML
    private ImageView imageExport;

    @FXML
    private ImageView imageImport;

    @FXML
    void converterPNG(ActionEvent event) {
        try {
            generateImage();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void exportImage(ActionEvent event) {
        exportImage();
        clearAll();
    }

    @FXML
    void importImage(ActionEvent event) {
        getImage();
    }


    private void getImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
        .addAll(new FileChooser.ExtensionFilter("Imagenes", "*.jpg", "*.png", "*.jpeg"));
        selectedImage = fileChooser.showOpenDialog(mainStage);
        if(selectedImage != null){
            imageImport.setImage(new Image(selectedImage.toURI().toString()));
            
        }
    }

    private String outPathImage(){
        FileChooser  fileChooser = new FileChooser();
        // File outPuthDir = directoryChooser.showDialog(mainStage);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Imagen PNG (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName(imageImport.getId());
        File file = fileChooser.showSaveDialog(mainStage);
        if(file != null){
            return file.getAbsolutePath()  ;
        }
        throw new RuntimeException("No se selecciono ninguna imagen");


    } 

    private void generateImage() throws Exception{
        String outPath = outPathImage();
        String inputPath = selectedImage.getAbsolutePath();
        new Script(inputPath,outPath, mainStage, imageExport);
        generatedImage = new File(outPath);
        
    }

    private void exportImage(){

        try {
            if(generatedImage != null){
            Desktop.getDesktop().open(generatedImage.getParentFile());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Script.Alertas.alertaError("Error", "No se pudo abrir la carpeta", e.getMessage());
        }
    }

    void clearAll(){
        selectedImage = null;
        generatedImage = null;
        imageExport.setImage(null);
        imageImport.setImage(null);
    }

}
