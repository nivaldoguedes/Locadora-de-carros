package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CadastroDeClienteController {

    @FXML
    private Button botaoCadastrarCliente;

    @FXML
    private Button botaoSair;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TextField inputMarca;

    @FXML
    private TextField inputModelo;

    @FXML
    void clickBotaoSair(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/login.fxml", "Login");
    }

    @FXML
    void clickBotaoVoltar(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/menu.fxml",  "Menu");
    }

    void clickBotaoCadastrar() {

    }

}
