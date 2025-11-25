package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Aluga;
import model.Cliente;
import model.Manutencao;
import model.Veiculo;

public class RelatorioDAO {

    // =======================================================
    // RELATÓRIO DE LOCAÇÕES
    // =======================================================
    public List<Aluga> getRelatorioLocacoes(LocalDate inicio, LocalDate fim) throws SQLException {
        String SQL =
                "SELECT a.*, c.nome, c.cpf, c.telefone, v.marca, v.modelo, v.placa " +
                        "FROM aluga a " +
                        "JOIN cliente c ON a.idCliente = c.id " +
                        "JOIN veiculo v ON a.idVeiculo = v.id " +
                        "WHERE a.dataAluguel >= ? AND a.dataDevolucao <= ? " +
                        "ORDER BY a.dataAluguel";

        List<Aluga> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setDate(1, java.sql.Date.valueOf(inicio)); // aluguel deve iniciar NO OU DEPOIS do início
            pstmt.setDate(2, java.sql.Date.valueOf(fim));    // devolução deve ocorrer NO OU ANTES do fim

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    Aluga a = new Aluga(
                            rs.getDate("dataAluguel").toLocalDate(),
                            rs.getDate("dataDevolucao").toLocalDate(),
                            rs.getFloat("valor"),
                            rs.getString("formaPagamento")
                    );

                    Cliente c = new Cliente(
                            rs.getString("cpf"),
                            rs.getString("nome"),
                            "",
                            rs.getString("telefone")
                    );

                    Veiculo v = new Veiculo(
                            rs.getString("placa"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            0, 0, null, true
                    );

                    a.setCliente(c);
                    a.setVeiculo(v);

                    lista.add(a);
                }
            }
        }

        return lista;
    }

    // =======================================================
    // RELATÓRIO DE MANUTENÇÃO
    // =======================================================
    public List<Manutencao> getRelatorioManutencoes(LocalDate inicio, LocalDate fim) throws SQLException {

        String SQL =
                "SELECT m.*, v.placa, v.marca, v.modelo, v.ano, v.quilometragemAtual, v.categoria, v.disponibilidade " +
                        "FROM manutencao m " +
                        "JOIN veiculo v ON m.idVeiculo = v.id " +
                        "WHERE m.data >= ? AND m.data <= ? " +
                        "ORDER BY m.data";

        List<Manutencao> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setDate(1, java.sql.Date.valueOf(inicio));
            pstmt.setDate(2, java.sql.Date.valueOf(fim));

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    // Criar veículo completo
                    Veiculo veiculo = new Veiculo(
                            rs.getString("placa"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getInt("ano"),
                            rs.getFloat("quilometragemAtual"),
                            rs.getString("categoria"),
                            rs.getBoolean("disponibilidade")
                    );

                    // Criar manutenção
                    Manutencao manutencao = new Manutencao(
                            rs.getDate("data").toLocalDate(),
                            rs.getFloat("custo"),
                            rs.getString("detalhesManutencao")
                    );

                    // VINCULAR os dois corretamente
                    manutencao.setVeiculo(veiculo);
                    veiculo.setManutencao(manutencao);

                    // adicionar ao relatório
                    lista.add(manutencao);
                }
            }
        }

        return lista;
    }

    // =======================================================
    // RELATÓRIO DE FATURAMENTO
    // =======================================================
    public float getRelatorioFaturamento(LocalDate inicio, LocalDate fim) throws SQLException {

        String SQL =
                "SELECT SUM(valor) AS total " +
                        "FROM aluga " +
                        "WHERE dataAluguel BETWEEN ? AND ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setDate(1, java.sql.Date.valueOf(inicio));
            pstmt.setDate(2, java.sql.Date.valueOf(fim));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("total");
                }
            }
        }

        return 0;
    }
}