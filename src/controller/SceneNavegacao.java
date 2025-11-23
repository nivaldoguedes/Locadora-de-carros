package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneNavegacao {
    public static void navegar(ActionEvent event, String fxmlPath, String title) throws IOException {
        try{
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneNavegacao.class.getResource(fxmlPath)));

            Stage newStage = new Stage();
            Scene scene = new Scene(root,1180,750);

            newStage.setTitle(title);
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
