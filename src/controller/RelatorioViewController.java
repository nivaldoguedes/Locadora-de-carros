package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Aluga;
import model.Manutencao;
import model.Veiculo;

import java.util.List;

public class RelatorioViewController {

    @FXML private Label tituloRelatorio;
    @FXML private Label aviso;

    // Tabela para LOCAÇÕES
    @FXML private TableView<Aluga> tabelaAluguel;
    @FXML private VBox container;


    @FXML private TableColumn<Aluga, String> colCliente;
    @FXML private TableColumn<Aluga, String> colVeiculo;
    @FXML private TableColumn<Aluga, String> colDataAluguel;
    @FXML private TableColumn<Aluga, String> colDataDevolucao;
    @FXML private TableColumn<Aluga, Float> colValor;

    @FXML
    public void initialize() {

        if (colCliente != null) {

            colCliente.setCellValueFactory(cell ->
                    new javafx.beans.property.SimpleStringProperty(
                            cell.getValue().getCliente() != null
                                    ? cell.getValue().getCliente().getNome()
                                    : ""
                    )
            );

            colVeiculo.setCellValueFactory(cell ->
                    new javafx.beans.property.SimpleStringProperty(
                            cell.getValue().getVeiculo() != null
                                    ? cell.getValue().getVeiculo().getMarca() + " "
                                    + cell.getValue().getVeiculo().getModelo() + " - " +
                                    cell.getValue().getVeiculo().getPlaca()
                                    : ""
                    )
            );

            colDataAluguel.setCellValueFactory(cell ->
                    new javafx.beans.property.SimpleStringProperty(
                            cell.getValue().getDataAluguel().toString()
                    )
            );

            colDataDevolucao.setCellValueFactory(cell ->
                    new javafx.beans.property.SimpleStringProperty(
                            cell.getValue().getDataDevolucao().toString()
                    )
            );

            colValor.setCellValueFactory(cell ->
                    new javafx.beans.property.SimpleObjectProperty<>(
                            cell.getValue().getValor()
                    )
            );
        }
    }


    // ============================================================
    //   RELATÓRIO DE LOCAÇÕES
    // ============================================================
    public void exibirRelatorioAluguel(java.util.List<Aluga> lista, String titulo) {

        tituloRelatorio.setText(titulo);

        // limpa tudo
        container.getChildren().clear();

        // coloca a tabela de aluguel na tela
        container.getChildren().add(tabelaAluguel);

        tabelaAluguel.setItems(FXCollections.observableArrayList(lista));

        aviso.setText("Total de registros: " + lista.size());

        ajustarLarguraJanela();
    }

    // ============================================================
    //   RELATÓRIO DE MANUTENÇÕES
    // ============================================================
    public void exibirRelatorioManutencoes(List <Manutencao> lista, String titulo) {
        tituloRelatorio.setText(titulo);
        container.getChildren().clear();

        TableView<Manutencao> tabela = new TableView<>();
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Manutencao, String> colVeiculo = new TableColumn<>("Veículo");
        colVeiculo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getVeiculo().getMarca() + " " +
                        data.getValue().getVeiculo().getModelo() + " - " + data.getValue().getVeiculo().getPlaca())
        );

        TableColumn<Manutencao, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getData().toString())
        );

        TableColumn<Manutencao, Float> colCusto = new TableColumn<>("Custo");
        colCusto.setCellValueFactory(new PropertyValueFactory<>("custo"));

        TableColumn<Manutencao, String> colDetalhes = new TableColumn<>("Detalhes");
        colDetalhes.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getDetalhesManutencao())
        );

        tabela.getColumns().addAll(colVeiculo, colData, colCusto, colDetalhes);

        tabela.getItems().addAll(lista);

        container.getChildren().add(tabela);
        ajustarLarguraJanela();
    }


    // ============================================================
    //   RELATÓRIO DE FATURAMENTO
    // ============================================================
    public void exibirRelatorioFaturamento(float total, String titulo) {

        tituloRelatorio.setText(titulo);
        aviso.setText("Faturamento total no período: R$ " + total);

        tabelaAluguel.setVisible(false);
    }

    // ============================================================
    //   AJUSTE AUTOMÁTICO DA JANELA
    // ============================================================
    private void ajustarLarguraJanela() {
        Platform.runLater(() -> {
            // tenta achar a tabela visível: primeiro tenta o container (tabela dinâmica),
            // depois fallback para tabelaAluguel caso esteja sendo usada
            TableView<?> table = null;

            if (container != null && !container.getChildren().isEmpty()) {
                if (container.getChildren().get(0) instanceof TableView) {
                    table = (TableView<?>) container.getChildren().get(0);
                }
            }

            if (table == null && tabelaAluguel != null && tabelaAluguel.getScene() != null) {
                table = tabelaAluguel;
            }

            if (table == null) return; // nada para ajustar

            // força CSS/layout para garantir tamanhos válidos
            table.applyCss();
            table.layout();

            double larguraTotal = 0;

            for (TableColumn<?, ?> col : table.getColumns()) {
                // tenta usar largura atual (se já calculada), senão calcula por texto como fallback
                double pref = col.getWidth();
                if (pref <= 0) {
                    pref = Math.max(100, col.getText().length() * 12 + 80);
                    col.setPrefWidth(pref);
                }
                larguraTotal += pref;
            }

            // adicionar margem para bordas e possíveis scrollbars
            larguraTotal += 140;

            Stage stage = (Stage) table.getScene().getWindow();
            if (stage != null) {
                // guarda altura atual
                double altura = stage.getHeight();
                stage.setWidth(larguraTotal);
                // manter altura
                stage.setHeight(altura);
            }
        });
    }
}