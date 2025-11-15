package controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class RegistrarManutencaoController {

    // --- Variáveis FXML ---
    @FXML
    private TextField inputVeiculo;

    @FXML
    private TextField inputTipoManutencao;

    @FXML
    private DatePicker inputData;

    @FXML
    private TextArea inputDescricao;

    @FXML
    private TextField inputCusto;

    @FXML
    private Button botaoRegistrarManutencao;

    @FXML
    private Button botaoVoltar;

    // (O botão Sair precisa ser referenciado no FXML, ou usaremos o handleSair com o event)


    // --- Métodos de Ação (onAction) ---

    /**
     * Lida com o clique do botão "Registrar Manutenção".
     */
    @FXML
    private void handleRegistrarManutencao(ActionEvent event) {
        // 1. Coleta dos dados
        String veiculo = inputVeiculo.getText();
        String tipo = inputTipoManutencao.getText();
        String data = inputData.getValue() != null ? inputData.getValue().toString() : "";
        String descricao = inputDescricao.getText();
        String custo = inputCusto.getText();

        // 2. Validação simples
        if (veiculo.isEmpty() || tipo.isEmpty() || data.isEmpty() || custo.isEmpty()) {
            showAlert(AlertType.ERROR, "Erro de Registro", "Preencha todos os campos obrigatórios (Veículo, Tipo, Data e Custo).");
            return;
        }

        // 3. Lógica de Negócio
        // Aqui você chamaria um serviço para salvar a manutenção no Banco de Dados
        System.out.println("Manutenção registrada para o Veículo: " + veiculo);
        System.out.println("Tipo: " + tipo + ", Custo: R$" + custo);

        // 4. Feedback e Limpeza
        showAlert(AlertType.INFORMATION, "Sucesso", "Manutenção registrada com sucesso!");
        limparCampos();
    }

    /**
     * Lida com o clique do botão "Voltar", navegando para o menu principal.
     */
    @FXML
    private void handleVoltar(ActionEvent event) throws IOException {
        // Assume que o menu principal é menu.fxml
        navegarPara(event, "/view/menu.fxml", "Menu Principal");
    }

    /**
     * Lida com o clique do botão "Sair", fechando a aplicação.
     * Este é o método que faltava e causava o erro.
     */
    @FXML
    private void handleSair(ActionEvent event) {
        // Fecha o Stage (janela) atual
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    // --- Métodos Auxiliares ---

    private void limparCampos() {
        inputVeiculo.clear();
        inputTipoManutencao.clear();
        inputData.setValue(null);
        inputDescricao.clear();
        inputCusto.clear();
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navegarPara(ActionEvent event, String fxmlPath, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}