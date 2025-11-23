package controller;

import DAO.AlugaDAO;
import DAO.VeiculoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import model.Aluga;
import model.Veiculo;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DevolucaoController {

    @FXML private ComboBox<String> comboFormaPagamento;
    @FXML private Button botaoConfirmarDevolucao;
    @FXML private Button botaoReservar1;
    @FXML private Button botaoSair;
    @FXML private Button botaoVoltar;
    @FXML private ChoiceBox<Veiculo> carroDevolvido;
    @FXML private ChoiceBox<String> danoCausado;
    @FXML private TextField quilometragemDevolucao;
    @FXML private Text valorDano;
    @FXML private ListView<Veiculo> listVeiculosdisponiveis;
    @FXML private ComboBox<String> comboCategoria;

    private ObservableList<Veiculo> veiculosDisponiveis = FXCollections.observableArrayList();

    private final int VALOR_ARRANHAO = 300;
    private final int VALOR_AMASSO = 1000;

    @FXML
    void clickBotaoSair(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/login.fxml", "Login");
    }

    @FXML
    void clickBotaoVoltar(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/menu.fxml",  "Menu");
    }

    @FXML
    public void initialize() {
        carregarVeiculosAlugados();

        danoCausado.getItems().setAll("Nenhum", "Arranhão", "Amasso");
        danoCausado.setValue("Nenhum");

        danoCausado.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            int valor = 0;
            if ("Arranhão".equals(newVal)) valor = VALOR_ARRANHAO;
            else if ("Amasso".equals(newVal)) valor = VALOR_AMASSO;
            valorDano.setText("R$ " + valor + ",00");
        });
    }

    private void carregarVeiculosAlugados() {
        AlugaDAO alugaDAO = new AlugaDAO();
        try {
            List<Veiculo> alugados = alugaDAO.getAlugueis();
            if (alugados != null && !alugados.isEmpty()) {
                carroDevolvido.getItems().setAll(alugados);

                carroDevolvido.setConverter(new StringConverter<Veiculo>() {
                    @Override
                    public String toString(Veiculo v) {
                        return v != null ? v.getPlaca() + " - " + v.getMarca() + " " + v.getModelo() : "";
                    }
                    @Override
                    public Veiculo fromString(String string) {
                        return null;
                    }
                });
            } else {
                System.out.println("Nenhum veículo alugado encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void confirmarDevolucaoDeCarro(ActionEvent event) {
        try {
            Veiculo veiculoSelecionado = carroDevolvido.getValue();
            String dano = danoCausado.getValue();
            String quilometragemStr = quilometragemDevolucao.getText();

            if (veiculoSelecionado == null || dano == null || quilometragemStr.isEmpty()) {
                showAlert(AlertType.ERROR, "Erro", "Preencha todos os campos obrigatórios.");
                return;
            }

            float quilometragemFinal = Float.parseFloat(quilometragemStr.replace(",", "."));

            // Atualizar veículo
            veiculoSelecionado.setQuilometragem(quilometragemFinal);
            veiculoSelecionado.setDisponibilidade(true);

            VeiculoDAO veiculoDAO = new VeiculoDAO();
            veiculoDAO.updateVeiculo(veiculoSelecionado);

            // Atualizar aluguel
            Aluga aluguelAtual = veiculoSelecionado.getAluga();
            if (aluguelAtual == null) {
                showAlert(AlertType.ERROR, "Erro", "Aluguel não encontrado para este veículo.");
                return;
            }

            aluguelAtual.setVeiculo(veiculoSelecionado);
            aluguelAtual.setQuilometragemFinal(quilometragemFinal);
            aluguelAtual.setDanos(dano);
            aluguelAtual.setDataDevolucao(LocalDate.now());

            AlugaDAO alugaDAO = new AlugaDAO();
            alugaDAO.updateAluga(aluguelAtual);

            showAlert(AlertType.INFORMATION, "Sucesso", "Devolução registrada com sucesso!");

            // Limpar campos
            quilometragemDevolucao.clear();
            carroDevolvido.getSelectionModel().clearSelection();
            danoCausado.setValue("Nenhum");
            valorDano.setText("");

            // Recarregar veículos alugados
            carregarVeiculosAlugados();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erro", "Falha ao registrar devolução:\n" + e.getMessage());
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}