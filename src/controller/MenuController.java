package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import java.io.IOException;

public class MenuController {

    @FXML
    private Button botaoMenuSair;

    @FXML
    private Button btnMenuCadastrarCliente;

    @FXML
    private Button btnMenuCadastrarVeiculo;

    @FXML
    private Button btnMenuDevolucao;

    @FXML
    private Button btnMenuGerarRelatorios;

    @FXML
    private Button btnMenuLocarVeiculo;

    @FXML
    private Button btnMenuManutencoes;

    @FXML
    void clickBotaoCadastrarVeiculo(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/cadastroDeVeiculo.fxml", "Cadastro de Veiculo");
    }

    @FXML
    void clickBotaoSair(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/login.fxml", "Login");
    }

    @FXML
    void clickBotaoManutencoes(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/registrarManutencao.fxml", "Registrar Manutenção");
    }

}
