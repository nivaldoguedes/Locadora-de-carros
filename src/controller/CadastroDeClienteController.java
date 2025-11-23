package controller;

import DAO.ClienteDAO;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Cliente;

import java.io.IOException;
import java.sql.SQLException;

public class CadastroDeClienteController {

    @FXML
    private ComboBox<String> comboFormaPagamento;

    @FXML
    private Button botaoCadastrarCliente;

    @FXML
    private Button botaoSair;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TextField inputCPF;

    @FXML
    private TextField inputNome;

    @FXML
    private TextField inputTelefone;

    @FXML
    private Text avisoCadastro;

    @FXML
    void clickBotaoSair(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/login.fxml", "Login");
    }

    @FXML
    void clickBotaoVoltar(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/menu.fxml", "Menu");
    }

    @FXML
    void clickBotaoCadastrar() {
        String cpf = inputCPF.getText();
        String nome = inputNome.getText();
        String formaPagamento = comboFormaPagamento.getValue(); // CORRETO
        String telefone = inputTelefone.getText();

        // 1. Validações básicas
        if (cpf.trim().isEmpty() || nome.trim().isEmpty() ||
                formaPagamento == null || telefone.trim().isEmpty()) {

            avisoCadastro.setStyle("-fx-fill: #ff0000;");
            avisoCadastro.setText("Preencha todos os campos!");
            return;
        }

        // 2. Validação simples do CPF (tamanho)
        if (cpf.length() != 11) {
            avisoCadastro.setStyle("-fx-fill: #ff0000;");
            avisoCadastro.setText("CPF deve conter 11 dígitos!");
            return;
        }

        // 3. Telefone básico
        if (telefone.length() < 8) {
            avisoCadastro.setStyle("-fx-fill: #ff0000;");
            avisoCadastro.setText("Telefone inválido!");
            return;
        }

        try {
            // 4. Criar objeto Cliente
            Cliente novoCliente = new Cliente(
                    cpf,
                    nome,
                    formaPagamento,
                    telefone
            );

            // 5. Inserir no banco
            ClienteDAO dao = new ClienteDAO();
            dao.createCliente(novoCliente);

            // 6. Feedback de sucesso
            avisoCadastro.setStyle("-fx-fill: #009E1A;");
            avisoCadastro.setText("Cliente cadastrado com sucesso!");

            // 7. Limpar os campos após 1.5s
            PauseTransition pause = new PauseTransition(Duration.millis(1500));
            pause.setOnFinished(e -> {
                inputCPF.clear();
                inputNome.clear();
                inputTelefone.clear();
                comboFormaPagamento.setValue(null);
            });
            pause.play();

        } catch (SQLException e) {
            avisoCadastro.setStyle("-fx-fill: #ff0000;");
            avisoCadastro.setText("Erro ao cadastrar no banco!");
            e.printStackTrace();
        }
    }
}