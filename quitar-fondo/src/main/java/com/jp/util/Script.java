package com.jp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.jp.util.Script.Alertas;

import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Script {
    private final String python = "python";
    private final String script = ScriptHelper.extractScript("/xd.py");

    public Script(String input, String output, Stage ownerStage, ImageView imageExpor) throws Exception{

        // ProcessBuilder pb = new ProcessBuilder(
        //     python, script, input, output
        // );
        // pb.redirectErrorStream(true);
        // Process process = pb.start();

        // BufferedReader reader = new BufferedReader(
        //     new InputStreamReader(process.getInputStream())
        // );
        // String line;
        // while((line = reader.readLine())!= null){
        //     Alertas.alertaInformacion("Guardado", "Se ha guardado con éxito", line);
        // }

        // int exitCode = process.waitFor();
        // System.out.println("Python termino con codigo: " + exitCode);

         // Componente de carga
    ProgressIndicator progressIndicator = new ProgressIndicator();
    progressIndicator.setPrefSize(100, 100);

    // Crear un diálogo modal con el indicador
    Stage dialog = new Stage();
    dialog.initOwner(ownerStage);
    dialog.initModality(Modality.APPLICATION_MODAL);

    StackPane pane = new StackPane(progressIndicator);
    pane.setPadding(new Insets(20));

    Scene scene = new Scene(pane, 150, 150);
    dialog.setScene(scene);
    dialog.setTitle("Procesando...");

    // Definir tarea en segundo plano
    Task<Void> task = new Task<>() {
        @Override
        protected Void call() throws Exception {
            ProcessBuilder pb = new ProcessBuilder(python, script, input, output);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); // puedes mostrar logs en consola
                }
            }

            int exitCode = process.waitFor();
            System.out.println("Python terminó con código: " + exitCode);
            return null;
        }
    };

    // Cuando la tarea termine, cerrar el diálogo y mostrar alerta
    task.setOnSucceeded(e -> {
        dialog.close();
        Alertas.alertaInformacion("Guardado", "Se ha guardado con éxito", output);
        imageExpor.setImage(new javafx.scene.image.Image(new File(output).toURI().toString()));
    });

    task.setOnFailed(e -> {
        dialog.close();
        Alertas.alertaError("Error", "El proceso falló", task.getException().getMessage());
    });

    // Ejecutar la tarea en un hilo separado
    new Thread(task).start();

    // Mostrar el diálogo con el indicador de carga
    dialog.show();
    }

    public class ScriptHelper {
        public static String extractScript(String resource) throws IOException {
            InputStream in = ScriptHelper.class.getResourceAsStream(resource);
            if (in == null) throw new FileNotFoundException("No se encontró " + resource);
    
            File tempFile = File.createTempFile("script", ".py");
            tempFile.deleteOnExit();
    
            try (OutputStream out = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }
            return tempFile.getAbsolutePath();
        }
    }
public static class Alertas{

    public static void alertaInformacion(String titulo, String header, String cuerpo){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle(titulo);
                alert.setHeaderText(header);
                alert.setContentText(cuerpo);
                alert.showAndWait();
    }
    public static void alertaError(String titulo, String header, String cuerpo){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setTitle(titulo);
                alert.setHeaderText(header);
                alert.setContentText(cuerpo);
                alert.showAndWait();
    }
}

}

