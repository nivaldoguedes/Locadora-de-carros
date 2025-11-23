package controller;

import DAO.ManutencaoDAO;
import DAO.VeiculoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import model.Manutencao;
import model.Veiculo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RegistrarManutencaoController {

    @FXML private DatePicker inputData;
    @FXML private TextArea inputDescricao;
    @FXML private TextField inputCusto;
    @FXML private ComboBox<Veiculo> comboVeiculo;  // ⬅ Agora é ComboBox<Veiculo>
    @FXML private Button botaoRegistrarManutencao;
    @FXML private Button botaoVoltar;

    private ObservableList<Veiculo> veiculosDisponiveis = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        carregarVeiculos();
    }

    private void carregarVeiculos() {
        try {
            VeiculoDAO dao = new VeiculoDAO();
            List<Veiculo> veiculos = dao.getVeiculos();

            comboVeiculo.getItems().clear();
            comboVeiculo.getItems().addAll(veiculos);

            // Exibir Marca Modelo (Placa)
            comboVeiculo.setCellFactory(param -> new ListCell<Veiculo>() {
                @Override
                protected void updateItem(Veiculo v, boolean empty) {
                    super.updateItem(v, empty);
                    setText(empty || v == null ? null : v.getMarca() + " " + v.getModelo() + " (" + v.getPlaca() + ")");
                }
            });

            comboVeiculo.setButtonCell(new ListCell<Veiculo>() {
                @Override
                protected void updateItem(Veiculo v, boolean empty) {
                    super.updateItem(v, empty);
                    setText(empty || v == null ? null : v.getMarca() + " " + v.getModelo() + " (" + v.getPlaca() + ")");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegistrarManutencao(ActionEvent event) {
        try {
            Veiculo veiculo = comboVeiculo.getValue();
            LocalDate data = inputData.getValue();
            String descricao = inputDescricao.getText();
            String custoStr = inputCusto.getText();

            if (veiculo == null || data == null || descricao.isEmpty() || custoStr.isEmpty()) {
                showAlert(AlertType.ERROR, "Erro de Registro",
                        "Preencha todos os campos obrigatórios (Veículo, Data, Descrição e Custo).");
                return;
            }

            float custo = Float.parseFloat(custoStr.replace(",", "."));

            Manutencao m1 = new Manutencao(data, custo, descricao);
            m1.setVeiculo(veiculo);

            ManutencaoDAO manutencaoDAO = new ManutencaoDAO();
            manutencaoDAO.createManutencao(m1);

            showAlert(AlertType.INFORMATION, "Sucesso", "Manutenção registrada com sucesso!");
            limparCampos();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erro", "Falha ao registrar manutenção:\n" + e.getMessage());
        }
    }

    @FXML
    private void handleVoltar(ActionEvent event) throws IOException {
        navegarPara(event, "/view/menu.fxml", "Menu Principal");
    }

    @FXML
    private void handleSair(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void limparCampos() {
        inputData.setValue(null);
        inputDescricao.clear();
        inputCusto.clear();
        comboVeiculo.getSelectionModel().clearSelection();
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