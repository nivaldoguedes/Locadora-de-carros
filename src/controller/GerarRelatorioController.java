package controller;

import DAO.RelatorioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Aluga;
import model.Manutencao;
import model.Veiculo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class GerarRelatorioController {

    @FXML private ComboBox<String> inputOpcaoDeRelatorio;
    @FXML private DatePicker dataInicio;
    @FXML private DatePicker dataFim;
    @FXML private Button botaoGerar;
    @FXML private Button botaoSair;
    @FXML private Button botaoVoltar;
    @FXML private Label aviso;

    private final RelatorioDAO relatorioDAO = new RelatorioDAO();

    @FXML
    public void initialize() {
        inputOpcaoDeRelatorio.getItems().addAll(
                "Locações",
                "Manutenções",
                "Faturamento"
        );
    }


    // ====================================================================
    //  GERAR RELATÓRIO
    // ====================================================================
    @FXML
    void clickBotaoGerarRelatorio(ActionEvent event) {

        aviso.setText(""); // limpar aviso

        // ---------------------------------------------
        // 1. VALIDAR ESCOLHA DE RELATÓRIO
        // ---------------------------------------------
        if (inputOpcaoDeRelatorio.getValue() == null) {
            aviso.setText("Selecione um tipo de relatório.");
            return;
        }

        // ---------------------------------------------
        // 2. VALIDAR DATAS
        // ---------------------------------------------
        LocalDate inicio = dataInicio.getValue();
        LocalDate fim = dataFim.getValue();

        if (inicio == null || fim == null) {
            aviso.setText("Selecione um intervalo de datas.");
            return;
        }

        if (fim.isBefore(inicio)) {
            aviso.setText("Data final não pode ser antes da inicial.");
            return;
        }

        String opcao = inputOpcaoDeRelatorio.getValue();

        try {

            switch (opcao) {

                // =============================================================
                //  RELATÓRIO DE LOCAÇÕES
                // =============================================================
                case "Locações" -> {
                    List<Aluga> locacoes =
                            relatorioDAO.getRelatorioLocacoes(inicio, fim);
                    abrirRelatorioLista(locacoes, "Relatório de Locações");
                }

                // =============================================================
                //  RELATÓRIO DE MANUTENÇÃO
                // =============================================================
                case "Manutenções" -> {
                    List<Manutencao> manutencoes =
                            relatorioDAO.getRelatorioManutencoes(inicio, fim);
                    abrirRelatorioManutencoes(manutencoes, "Relatório de Manutenções");

                }

                // =============================================================
                //  RELATÓRIO DE FATURAMENTO
                // =============================================================
                case "Faturamento" -> {
                    float total =
                            relatorioDAO.getRelatorioFaturamento(inicio, fim);
                    abrirRelatorioFaturamento(total, "Relatório de Faturamento");
                }

            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            aviso.setText("Erro ao gerar relatório.");
        }
    }


    // ====================================================================
    //  ABRIR RELATÓRIO DE LISTAGEM (Aluguéis, Locações)
    // ====================================================================
    private void abrirRelatorioLista(List<Aluga> lista, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/relatorioView.fxml"));
        Parent root = loader.load();

        RelatorioViewController controller = loader.getController();
        controller.exibirRelatorioAluguel(lista, titulo);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(titulo);
        stage.show();
    }


    // ====================================================================
    //  ABRIR RELATÓRIO DE MANUTENÇÕES
    // ====================================================================
    private void abrirRelatorioManutencoes(List<Manutencao> lista, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/relatorioView.fxml"));
        Parent root = loader.load();

        RelatorioViewController controller = loader.getController();
        controller.exibirRelatorioManutencoes(lista, titulo);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(titulo);
        stage.show();
    }


    // ====================================================================
    //  ABRIR RELATÓRIO DE FATURAMENTO
    // ====================================================================
    private void abrirRelatorioFaturamento(float total, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/relatorioView.fxml"));
        Parent root = loader.load();

        RelatorioViewController controller = loader.getController();
        controller.exibirRelatorioFaturamento(total, titulo);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(titulo);
        stage.show();
    }


    // ====================================================================
    //  NAVEGAÇÃO
    // ====================================================================
    @FXML
    void clickBotaoSair(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/login.fxml", "Login");
    }

    @FXML
    void clickBotaoVoltar(ActionEvent event) throws IOException {
        SceneNavegacao.navegar(event, "/view/menu.fxml", "Menu");
    }

}