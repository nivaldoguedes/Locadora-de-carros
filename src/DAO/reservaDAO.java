package DAO;

import model.Aluga;
import model.Cliente;
import model.Veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class reservaDAO {
    private static final String TABLE = "reserva";

    public void createReserva(Aluga a1) throws SQLException {
        String getClienteSQL = "SELECT id FROM cliente WHERE cpf = ?";
        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";
        String checkAlugaSQL =
                "SELECT COUNT(*) AS total FROM aluga " +
                        "WHERE idVeiculo = ? " +
                        "AND dataAluguel <= ? " +
                        "AND dataDevolucao >= ?";

        String checkReservaSQL =
                "SELECT COUNT(*) AS total FROM reserva " +
                        "WHERE idVeiculo = ? " +
                        "AND dataReserva <= ? " +
                        "AND dataDevolucao >= ?";

        String getCustoSQL =
                "SELECT c.custo " +
                        "FROM veiculo v " +
                        "JOIN categoria c ON v.categoria = c.modelo " +
                        "WHERE v.id = ?";

        String SQL = "INSERT INTO " + TABLE +
                " (idCliente, idVeiculo, dataReserva, dataDevolucao, valor, formaPagamento) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection()) {
            int idCliente = 0;
            int idVeiculo = 0;

            // Buscar ID do cliente
            try (PreparedStatement pstmt = connection.prepareStatement(getClienteSQL)) {
                pstmt.setString(1, a1.getCliente().getCPF());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        idCliente = rs.getInt("id");
                    } else {
                        throw new SQLException("Cliente não encontrado: " + a1.getCliente().getCPF());
                    }
                }
            }

            // Buscar ID do veículo
            try (PreparedStatement pstmt = connection.prepareStatement(getVeiculoSQL)) {
                pstmt.setString(1, a1.getVeiculo().getPlaca());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        idVeiculo = rs.getInt("id");
                    } else {
                        throw new SQLException("Veículo não encontrado: " + a1.getVeiculo().getPlaca());
                    }
                }
            }

            // Buscar custo da categoria do veículo
            float custoCategoria = 0;

            try (PreparedStatement pstmt = connection.prepareStatement(getCustoSQL)) {
                pstmt.setInt(1, idVeiculo);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        custoCategoria = rs.getFloat("custo");
                    } else {
                        throw new SQLException("Categoria do veículo não encontrada.");
                    }
                }
            }

            // Calcular valor total do aluguel
            long dias = ChronoUnit.DAYS.between(a1.getDataAluguel(), a1.getDataDevolucao());
            if (dias <= 0) dias = 1;

            float valorFinal = dias * custoCategoria;
            a1.setValor(valorFinal); // substituir o valor enviado pelo cálculo

            // Verificar se o carro já está alugado no período solicitado
            try (PreparedStatement pstmt = connection.prepareStatement(checkAlugaSQL)) {
                pstmt.setInt(1, idVeiculo);
                pstmt.setDate(2, java.sql.Date.valueOf(a1.getDataDevolucao()));
                pstmt.setDate(3, java.sql.Date.valueOf(a1.getDataAluguel()));

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next() && rs.getInt("total") > 0) {
                        throw new SQLException("O veículo está alugado na data informada.");
                    }
                }
            }
            // Verificar se o carro já está reservado no período solicitado
            try (PreparedStatement pstmt = connection.prepareStatement(checkReservaSQL)) {
                pstmt.setInt(1, idVeiculo);
                pstmt.setDate(2, java.sql.Date.valueOf(a1.getDataDevolucao()));
                pstmt.setDate(3, java.sql.Date.valueOf(a1.getDataAluguel()));

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next() && rs.getInt("total") > 0) {
                        throw new SQLException("O veículo está reservado na data informada.");
                    }
                }
            }

            // Inserir reserva
            try (PreparedStatement pstmt = connection.prepareStatement(SQL)) {
                pstmt.setInt(1, idCliente);
                pstmt.setInt(2, idVeiculo);
                pstmt.setDate(3, java.sql.Date.valueOf(a1.getDataAluguel()));
                pstmt.setDate(4, java.sql.Date.valueOf(a1.getDataDevolucao()));
                pstmt.setFloat(5, valorFinal);
                pstmt.setString(6, a1.getFormaPagamento());
                pstmt.executeUpdate();
            }
        }
    }

    public List<Veiculo> getReservas() throws SQLException {
        String SQL = "SELECT a1.*, v1.*, c1.* FROM reserva a1 " +
                "JOIN veiculo v1 ON a1.idVeiculo = v1.id " +
                "JOIN cliente c1 ON a1.idCliente = c1.id";

        List<Veiculo> alugueis = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                // Criar Cliente
                Cliente c1 = new Cliente(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("formaPagamento"),
                        rs.getString("telefone")
                );

                // Criar Veículo
                Veiculo v1 = new Veiculo(
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getFloat("quilometragemAtual"),
                        rs.getString("categoria"),
                        rs.getBoolean("disponibilidade")
                );

                // Criar Reserva
                Aluga a1 = new Aluga(
                        rs.getDate("dataReserva").toLocalDate(),
                        rs.getDate("dataDevolucao").toLocalDate(),
                        rs.getFloat("valor"),
                        rs.getString("formaPagamento")
                );

                a1.setCliente(c1);
                a1.setVeiculo(v1);

                v1.setAluga(a1);
                alugueis.add(v1);
            }
        }
        return alugueis;
    }

    // Faz a devoulução do veículo
    public void updateReserva(Aluga a1) throws SQLException {

        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";
        String updateSQL = "UPDATE reserva SET quilometragemFinal = ?, danos = ? WHERE idVeiculo = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {

            int id = 0;

            try (PreparedStatement pstmt = connection.prepareStatement(getVeiculoSQL)) {
                pstmt.setString(1, a1.getVeiculo().getPlaca());

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) id = rs.getInt("id");
                }
            }

            try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
                pstmt.setFloat(1, a1.getQuilometragemFinal());
                pstmt.setString(2, a1.getDanos());
                pstmt.setInt(3, id);

                pstmt.executeUpdate();
            }
        }
    }

    public void deleteReserva(Aluga a1) throws SQLException {

        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";
        String deleteSQL = "DELETE FROM reserva WHERE idVeiculo = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {

            int id = 0;

            try (PreparedStatement pstmt = connection.prepareStatement(getVeiculoSQL)) {
                pstmt.setString(1, a1.getVeiculo().getPlaca());

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) id = rs.getInt("id");
                }
            }

            try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
        }
    }
}