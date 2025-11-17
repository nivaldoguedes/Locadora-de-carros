package DAO;

import model.Aluga;
import model.Cliente;
import model.Veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class alugaDAO {
    private static final String TABLE = "aluga";

    public void createAluga(Aluga a1) throws SQLException {
        String getClienteSQL = "SELECT id FROM cliente WHERE cpf = ?";
        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";
        String SQL = "INSERT INTO " + TABLE +
                " (idCliente, idVeiculo, dataAluguel, dataDevolucao, valor, formaPagamento) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection()) {
            int idCliente = 0;
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
            int idVeiculo = 0;
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

            // Inserir aluguel
            try (PreparedStatement pstmt = connection.prepareStatement(SQL)) {
                pstmt.setInt(1, idCliente);
                pstmt.setInt(2, idVeiculo);
                pstmt.setDate(3, java.sql.Date.valueOf(a1.getDataAluguel()));
                pstmt.setDate(4, java.sql.Date.valueOf(a1.getDataDevolucao()));
                pstmt.setFloat(5, a1.getValor());
                pstmt.setString(6, a1.getFormaPagamento());

                pstmt.executeUpdate();
            }
        }
    }

    public List<Veiculo> getAlugueis() throws SQLException {
        String SQL = "SELECT a1.*, v1.*, c1.* FROM aluga a1 " +
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

                // Criar Aluga
                Aluga a1 = new Aluga(
                        rs.getDate("dataAluguel").toLocalDate(),
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
    public void updateAluga(Aluga a1) throws SQLException {

        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";
        String updateSQL = "UPDATE aluga SET quilometragemFinal = ?, danos = ? WHERE idVeiculo = ?";

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

    public void deleteAluga(Aluga a1) throws SQLException {

        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";
        String deleteSQL = "DELETE FROM aluga WHERE idVeiculo = ?";

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
