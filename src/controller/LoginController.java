package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController {
    @FXML
    private AnchorPane login;

    @FXML
    private TextField inputUsuario;

    @FXML
    private PasswordField inputSenha;

    public TextField getInputUsuario() {
        return inputUsuario;
    }

    public PasswordField getInputSenha() {
        return inputSenha;
    }
}
