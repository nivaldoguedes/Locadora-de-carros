package DAO;

import model.Aluga;
import model.Reserva;
import model.Cliente;
import model.Veiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class modificaVeiculoDAO {

    private static final String TABLE_ALUGA = "aluga";
    private static final String TABLE_RESERVA = "reserva";

    // ==================== ALUGUEL ====================

    public void createAlugaModifica(Aluga a1) throws SQLException {
        String getClienteSQL = "SELECT id FROM cliente WHERE cpf = ?";
        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";
        String insertSQL = "INSERT INTO " + TABLE_ALUGA +
                " (idCliente, idVeiculo, dataAluguel, dataDevolucao, valor, formaPagamento) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection()) {

            int idCliente = 0;
            try (PreparedStatement pstmt = connection.prepareStatement(getClienteSQL)) {
                pstmt.setString(1, a1.getCliente().getCPF());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) idCliente = rs.getInt("id");
                    else throw new SQLException("Cliente não encontrado: " + a1.getCliente().getCPF());
                }
            }

            int idVeiculo = 0;
            try (PreparedStatement pstmt = connection.prepareStatement(getVeiculoSQL)) {
                pstmt.setString(1, a1.getVeiculo().getPlaca());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) idVeiculo = rs.getInt("id");
                    else throw new SQLException("Veículo não encontrado: " + a1.getVeiculo().getPlaca());
                }
            }

            try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setInt(1, idCliente);
                pstmt.setInt(2, idVeiculo);
                pstmt.setDate(3, Date.valueOf(a1.getDataAluguel()));
                pstmt.setDate(4, Date.valueOf(a1.getDataDevolucao()));
                pstmt.setFloat(5, a1.getValor());
                pstmt.setString(6, a1.getFormaPagamento());
                pstmt.executeUpdate();
            }
        }
    }

    public List<Aluga> getAlugueis() throws SQLException {
        String SQL = "SELECT a.*, v.*, c.* FROM aluga a " +
                "JOIN veiculo v ON a.idVeiculo = v.id " +
                "JOIN cliente c ON a.idCliente = c.id";

        List<Aluga> alugueis = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cliente c1 = new Cliente(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("formaPagamento"),
                        rs.getString("telefone")
                );

                Veiculo v1 = new Veiculo(
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getFloat("quilometragemAtual"),
                        rs.getString("categoria"),
                        rs.getBoolean("disponibilidade")
                );

                Aluga a1 = new Aluga(
                        rs.getDate("dataAluguel").toLocalDate(),
                        rs.getDate("dataDevolucao").toLocalDate(),
                        rs.getFloat("valor"),
                        rs.getString("formaPagamento")
                );

                a1.setCliente(c1);
                a1.setVeiculo(v1);
                alugueis.add(a1);
            }
        }
        return alugueis;
    }

    public void updateAluga(Aluga a1) throws SQLException {
        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";
        String updateSQL = "UPDATE aluga SET quilometragemFinal = ?, danos = ? WHERE idVeiculo = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            int idVeiculo = 0;
            try (PreparedStatement pstmt = connection.prepareStatement(getVeiculoSQL)) {
                pstmt.setString(1, a1.getVeiculo().getPlaca());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) idVeiculo = rs.getInt("id");
                }
            }

            try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
                pstmt.setFloat(1, a1.getQuilometragemFinal());
                pstmt.setString(2, a1.getDanos());
                pstmt.setInt(3, idVeiculo);
                pstmt.executeUpdate();
            }
        }
    }

    public void deleteAluga(Aluga a1) throws SQLException {
        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";
        String deleteSQL = "DELETE FROM aluga WHERE idVeiculo = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            int idVeiculo = 0;
            try (PreparedStatement pstmt = connection.prepareStatement(getVeiculoSQL)) {
                pstmt.setString(1, a1.getVeiculo().getPlaca());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) idVeiculo = rs.getInt("id");
                }
            }

            try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
                pstmt.setInt(1, idVeiculo);
                pstmt.executeUpdate();
            }
        }
    }

    // ==================== RESERVA ====================

    public void createReserva(Reserva r1, Veiculo v1) throws SQLException {
        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";
        String insertSQL = "INSERT INTO " + TABLE_RESERVA +
                " (idVeiculo, dataReserva, dataInicioAluguel, valor) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection()) {
            int idVeiculo = 0;
            try (PreparedStatement pstmt = connection.prepareStatement(getVeiculoSQL)) {
                pstmt.setString(1, v1.getPlaca());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) idVeiculo = rs.getInt("id");
                }
            }

            try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
                pstmt.setInt(1, idVeiculo);
                pstmt.setDate(2, Date.valueOf(r1.getDataReserva()));
                pstmt.setDate(3, Date.valueOf(r1.getDataInicioAluguel()));
                pstmt.setFloat(4, r1.getValor());
                pstmt.executeUpdate();
            }
        }
    }

    public List<Reserva> getReservas() throws SQLException {
        String SQL = "SELECT r.*, v.* FROM reserva r " +
                "JOIN veiculo v ON r.idVeiculo = v.id";

        List<Reserva> reservas = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Veiculo v1 = new Veiculo(
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("ano"),
                        rs.getFloat("quilometragemAtual"),
                        rs.getString("categoria"),
                        rs.getBoolean("disponibilidade")
                );

                Reserva r1 = new Reserva(
                        rs.getDate("dataReserva").toLocalDate(),
                        rs.getDate("dataInicioAluguel").toLocalDate(),
                        rs.getFloat("valor")
                );

                reservas.add(r1);
            }
        }
        return reservas;
    }

    public void deleteReserva(Reserva r1, Veiculo v1) throws SQLException {
        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";
        String deleteSQL = "DELETE FROM reserva WHERE idVeiculo = ? AND dataReserva = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            int idVeiculo = 0;
            try (PreparedStatement pstmt = connection.prepareStatement(getVeiculoSQL)) {
                pstmt.setString(1, v1.getPlaca());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) idVeiculo = rs.getInt("id");
                }
            }

            try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
                pstmt.setInt(1, idVeiculo);
                pstmt.setDate(2, Date.valueOf(r1.getDataReserva()));
                pstmt.executeUpdate();
            }
        }
    }
}