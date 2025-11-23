package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import java.io.IOException;

public class ReservaDeCarroController {

    @FXML
    private Button botaoReservar;

    @FXML
    private Button botaoSair;

    @FXML
    private Button botaoVoltar;

    @FXML
    private DatePicker dataDevolucao;

    @FXML
    private DatePicker dataRetirada;

    @FXML
    private ChoiceBox<?> inputCategoria;

    @FXML
    private ChoiceBox<?> inputCliente;

    @FXML
    private ListView<?> listVeiculosdisponiveis;

    @FXML
    void clickBotaoSair(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/login.fxml", "Login");
    }

    @FXML
    void clickBotaoVoltar(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/menu.fxml",  "Menu");
    }

}
