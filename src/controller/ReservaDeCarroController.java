package controller;

import DAO.AlugaDAO;
import DAO.ClienteDAO;
import DAO.VeiculoDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.Aluga;
import model.Cliente;
import model.Veiculo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class ReservaDeCarroController {

    @FXML private Button botaoReservar;
    @FXML private Button botaoSair;
    @FXML private Button botaoVoltar;
    @FXML private DatePicker dataDevolucao;
    @FXML private DatePicker dataRetirada;
    @FXML private ComboBox<String> comboCategoria;
    @FXML private ChoiceBox<Cliente> inputCliente;
    @FXML private ListView<Veiculo> listVeiculosdisponiveis;
    @FXML private Text avisoCadastro;
    @FXML private Label valorAluguel;

    private ObservableList<Veiculo> veiculosDisponiveis = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        carregarClientes();
        carregarVeiculos();
        configurarFiltroCategoria();

        dataRetirada.valueProperty().addListener((obs, oldVal, newVal) -> atualizarValor());
        dataDevolucao.valueProperty().addListener((obs, oldVal, newVal) -> atualizarValor());
        listVeiculosdisponiveis.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> atualizarValor());
    }

    /** Carrega clientes na ChoiceBox */
    private void carregarClientes() {
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            List<Cliente> clientes = clienteDAO.getClientes();
            if (clientes != null && !clientes.isEmpty()) {
                inputCliente.getItems().addAll(clientes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Carrega veículos disponíveis no ListView */
    private void carregarVeiculos() {
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        try {
            List<Veiculo> veiculos = veiculoDAO.getVeiculos();
            if (veiculos != null && !veiculos.isEmpty()) {
                veiculosDisponiveis.setAll(veiculos);
                listVeiculosdisponiveis.setItems(veiculosDisponiveis);

                // Preenche categorias automaticamente
                List<String> categorias = veiculos.stream()
                        .map(Veiculo::getCategoria)
                        .distinct()
                        .sorted()
                        .collect(Collectors.toList());
                comboCategoria.getItems().addAll(categorias);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clickBotaoReservar(ActionEvent event) {
        Cliente cliente = inputCliente.getSelectionModel().getSelectedItem();
        Veiculo veiculo = listVeiculosdisponiveis.getSelectionModel().getSelectedItem();
        LocalDate retirada = dataRetirada.getValue();
        LocalDate devolucao = dataDevolucao.getValue();

        if (cliente == null || veiculo == null || retirada == null || devolucao == null) {
            avisoCadastro.setText("Preencha todos os campos e selecione veículo e cliente!");
            return;
        }

        if (devolucao.isBefore(retirada)) {
            avisoCadastro.setText("Data de devolução deve ser após a retirada!");
            return;
        }

        try {
            // Cria Aluga com datas recebidas do front
            Aluga a1 = new Aluga(retirada, devolucao, 0, cliente.getFormaPagamento());
            a1.setCliente(cliente);
            a1.setVeiculo(veiculo);

            AlugaDAO alugaDAO = new AlugaDAO();
            alugaDAO.createAluga(a1); // DAO calcula o valor e atualiza a disponibilidade
            valorAluguel.setText(String.format("R$ %.2f", a1.getValor()));
            avisoCadastro.setStyle("-fx-fill: #009E1A;");
            avisoCadastro.setText("Reserva realizada com sucesso!");
            carregarVeiculos(); // atualiza lista de veículos disponíveis
        } catch (SQLException e) {
            avisoCadastro.setText("Erro ao reservar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void atualizarValor() {
        Veiculo veiculo = listVeiculosdisponiveis.getSelectionModel().getSelectedItem();
        LocalDate retirada = dataRetirada.getValue();
        LocalDate devolucao = dataDevolucao.getValue();

        if (veiculo != null && retirada != null && devolucao != null) {
            // Garantir ordem correta
            LocalDate inicio = retirada.isBefore(devolucao) ? retirada : devolucao;
            LocalDate fim = devolucao.isAfter(retirada) ? devolucao : retirada;

            Aluga a1 = new Aluga(inicio, fim, 0, "");
            a1.setVeiculo(veiculo);

            try {
                float valor = new AlugaDAO().calcularValor(a1);
                valorAluguel.setText(String.format("R$ %.2f", valor));
            } catch (SQLException e) {
                valorAluguel.setText("Erro ao calcular valor");
                e.printStackTrace();
            }
        } else {
            valorAluguel.setText("Selecione veículo e datas");
        }
    }

    /** Configura filtro por categoria */
    private void configurarFiltroCategoria() {
        comboCategoria.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                listVeiculosdisponiveis.setItems(veiculosDisponiveis);
            } else {
                List<Veiculo> filtrados = veiculosDisponiveis.stream()
                        .filter(v -> v.getCategoria().equals(newVal))
                        .collect(Collectors.toList());
                listVeiculosdisponiveis.setItems(FXCollections.observableArrayList(filtrados));
            }
        });
    }

    /** Botão Sair */
    @FXML
    void clickBotaoSair(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/login.fxml", "Login");
    }

    /** Botão Voltar */
    @FXML
    void clickBotaoVoltar(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/menu.fxml", "Menu");
    }
}