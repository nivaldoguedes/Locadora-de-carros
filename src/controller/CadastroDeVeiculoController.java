package controller;

import DAO.VeiculoDAO;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Veiculo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CadastroDeVeiculoController implements Initializable {

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
    private ComboBox<String> comboCategoria;

    @FXML
    private Text avisoCadastro;

    private VeiculoDAO veiculoDAO = new VeiculoDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> inputMarca.requestFocus());
    }

    @FXML
    void clickBotaoVoltar(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/menu.fxml", "Menu");
    }

    @FXML
    void clickBotaoSair(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/login.fxml", "Login");
    }

    @FXML
    void clickBotaoCadastrarVeiculo(ActionEvent event) {

        String marca = inputMarca.getText();
        String modelo = inputModelo.getText();
        String placa = inputPlaca.getText();
        String ano = inputAno.getText();
        String km = inputQuilometragem.getText();
        String categoria = comboCategoria.getValue();

        // 1. Validações básicas
        if (marca.trim().isEmpty() || modelo.trim().isEmpty() ||
                placa.trim().isEmpty() || ano.trim().isEmpty() ||
                km.trim().isEmpty() || categoria == null) {

            avisoCadastro.setStyle("-fx-fill: #ff0000;");
            avisoCadastro.setText("Preencha todos os campos!");
            return;
        }

        // 2. Validação da placa (7 dígitos padrão)
        if (placa.length() != 7) {
            avisoCadastro.setStyle("-fx-fill: #ff0000;");
            avisoCadastro.setText("Placa deve conter 7 caracteres!");
            return;
        }

        // 3. Ano deve ser número
        int anoInt;
        try {
            anoInt = Integer.parseInt(ano);
            if (anoInt < 1900 || anoInt > 2100) {
                avisoCadastro.setStyle("-fx-fill: #ff0000;");
                avisoCadastro.setText("Ano inválido!");
                return;
            }
        } catch (NumberFormatException e) {
            avisoCadastro.setStyle("-fx-fill: #ff0000;");
            avisoCadastro.setText("Ano deve ser um número inteiro!");
            return;
        }

        // 4. Quilometragem deve ser número
        float kmFloat;
        try {
            kmFloat = Float.parseFloat(km);
            if (kmFloat < 0) {
                avisoCadastro.setStyle("-fx-fill: #ff0000;");
                avisoCadastro.setText("Quilometragem não pode ser negativa!");
                return;
            }
        } catch (NumberFormatException e) {
            avisoCadastro.setStyle("-fx-fill: #ff0000;");
            avisoCadastro.setText("Quilometragem deve ser numérica!");
            return;
        }

        try {
            // 5. Criar objeto Veiculo
            Veiculo novoVeiculo = new Veiculo(
                    placa,
                    marca,
                    modelo,
                    anoInt,
                    kmFloat,
                    categoria,
                    true
            );

            // 6. Inserir no banco
            VeiculoDAO dao = new VeiculoDAO();
            dao.createVeiculo(novoVeiculo);

            // 7. Mensagem de sucesso
            avisoCadastro.setStyle("-fx-fill: #009E1A;");
            avisoCadastro.setText("Veículo cadastrado com sucesso!");

            // 8. Limpar campos após 1.5s
            PauseTransition pause = new PauseTransition(Duration.millis(1500));
            pause.setOnFinished(e -> {
                inputMarca.clear();
                inputModelo.clear();
                inputPlaca.clear();
                inputAno.clear();
                inputQuilometragem.clear();
                comboCategoria.setValue(null);
            });
            pause.play();

        } catch (SQLException e) {
            avisoCadastro.setStyle("-fx-fill: #ff0000;");
            avisoCadastro.setText("Erro ao cadastrar no banco!");
            e.printStackTrace();
        }
    }
}