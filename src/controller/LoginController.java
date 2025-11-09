package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {

    @FXML
    private PasswordField inputSenha;

    @FXML
    private TextField inputUsuario;

    @FXML
    private Text avisoLogin;

    @FXML
    void botaoEntrarClick(ActionEvent event) {
        if(inputSenha.getText().equals("") || inputUsuario.getText().equals("")) {
            avisoLogin.setText("Preencha todos os campos!");
        }else{
            avisoLogin.setStyle("-fx-fill: #009E1A;");
            avisoLogin.setText("Login realizado com sucesso!");
        }
    }

}
