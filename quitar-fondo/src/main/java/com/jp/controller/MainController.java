package com.jp.controller;
import java.io.File;

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

        if (generatedImage != null) {
            // INSERT_YOUR_CODE
            // Seleccionar el archivo generado en el explorador (Windows)
            try {
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("win")) {
                    // En Windows, se puede usar explorer.exe /select, pero para traerlo al frente usamos cmd /c start
                    String cmd = String.format("cmd /c start \"\" explorer.exe /select,\"%s\"", generatedImage.getAbsolutePath());
                    Runtime.getRuntime().exec(cmd);
                } else if (os.contains("mac")) {
                    // En MacOS, usar open -R
                    String[] cmd = {"open", "-R", generatedImage.getAbsolutePath()};
                    Runtime.getRuntime().exec(cmd);
                } else if (os.contains("nux") || os.contains("nix")) {
                    // En Linux, intentar con nautilus o xdg-open (no siempre selecciona el archivo)
                    String parent = generatedImage.getParent();
                    String[] cmd = {"xdg-open", parent};
                    Runtime.getRuntime().exec(cmd);
                }
            } catch (Exception ex) {
                // Si falla, solo abrir la carpeta como fallback
            }
            //     Desktop.getDesktop().open(generatedImage.getParentFile());
        } // TODO Auto-generated catch block
    }

    void clearAll(){
        selectedImage = null;
        generatedImage = null;
        imageExport.setImage(null);
        imageImport.setImage(null);
    }

}
