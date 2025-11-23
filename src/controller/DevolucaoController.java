package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class DevolucaoController {

    @FXML
    private Button botaoConfirmarDevolucao;

    @FXML
    private Button botaoReservar1;

    @FXML
    private Button botaoSair;

    @FXML
    private Button botaoVoltar;

    @FXML
    private ChoiceBox<?> carroDevolvido;

    @FXML
    private ChoiceBox<?> danoCausado;

    @FXML
    private TextField quilometragemDevolucao;

    @FXML
    private Text valorDano;

    @FXML
    void clickBotaoSair(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/login.fxml", "Login");
    }

    @FXML
    void clickBotaoVoltar(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/menu.fxml",  "Menu");
    }

    @FXML
    void confirmarDevolucaoDeCarro(ActionEvent event) {

    }

}