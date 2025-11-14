package controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class LoginController {

    @FXML
    private PasswordField inputSenha;

    @FXML
    private TextField inputUsuario;

    @FXML
    private Text avisoLogin;

    @FXML
    void botaoEntrarClick(ActionEvent event) {
        String usuario = inputUsuario.getText();
        String senha = inputSenha.getText();

        if (usuario.trim().isEmpty() || senha.trim().isEmpty()) {
            avisoLogin.setStyle("-fx-fill: #ff0000;");
            avisoLogin.setText("Preencha todos os campos corretamente!");
        } else if (usuario.contains(" ") || senha.contains(" ")) {
            avisoLogin.setStyle("-fx-fill: #ff0000;");
            avisoLogin.setText("Usuário ou senha não podem conter espaços!");
        } else {
            //aqui tem que verificar se usuario e senha existe no banco de dados
            // para aí sim mostrar mensagem de sucesso e trocar a tela!
            avisoLogin.setStyle("-fx-fill: #009E1A;");
            avisoLogin.setText("Login realizado com sucesso!");

            PauseTransition pause = new PauseTransition(Duration.millis(1500));

            pause.setOnFinished(e -> {
                try{

                    Stage telaLogin = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    telaLogin.close();

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/menu.fxml")));
                    Stage telaMenu = new Stage();
                    telaMenu.setScene(new Scene(root));
                    telaMenu.setTitle("Menu");
                    telaMenu.setResizable(false);
                    telaMenu.show();

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

            pause.play();
        }
    }

}
