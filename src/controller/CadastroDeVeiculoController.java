package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CadastroDeVeiculoController {

    @FXML
    private Button botaoCadastrarVeiculo;

    @FXML
    private Button botaoSair;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TextField inputAno;

    @FXML
    private TextField inputMarca;

    @FXML
    private TextField inputModelo;

    @FXML
    private TextField inputPlaca;

    @FXML
    private TextField inputQuilometragem;

    @FXML
    void clickBotaoVoltar(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/menu.fxml",  "Menu");
    }

    @FXML
    void clickBotaoSair(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/login.fxml", "Login");
    }

}