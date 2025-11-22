package DAO;

import model.Manutencao;
import model.Veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ManutencaoDAO {
    private static final String TABLE = "manutencao";

    public void createManutencao(Manutencao m1) throws SQLException {
        String SQL = "INSERT INTO " + TABLE + " (id, data, custo, detalhesManutencao) VALUES (?, ?, ?, ?)";
        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            int idVeiculo = 0;

            // Buscar ID do veículo
            try (PreparedStatement pstmt = connection.prepareStatement(getVeiculoSQL)) {
                pstmt.setString(1, m1.getVeiculo().getPlaca());
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        idVeiculo = rs.getInt("id");
                    } else {
                        throw new SQLException("Veículo não encontrado: " + m1.getVeiculo().getPlaca());
                    }
                }
            }

            // Inserir manutencao
            try (PreparedStatement pstmt = connection.prepareStatement(SQL)) {
                pstmt.setInt(1, idVeiculo);
                pstmt.setDate(2, java.sql.Date.valueOf(m1.getData()));
                pstmt.setFloat(3, m1.getCusto());
                pstmt.setString(4, m1.getDetalhesManutencao());
                pstmt.executeUpdate();
            }
        }
    }

    public List<Manutencao> getManutencoes() throws SQLException {
        String SQL = "SELECT m1.*, v1.* FROM manutencao m1 " +
                "JOIN veiculo v1 ON m1.idVeiculo = v1.id ";
        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";
        List<Manutencao> manutencoes = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
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

                Manutencao m1 = new Manutencao(
                        rs.getDate("data").toLocalDate(),
                        rs.getFloat("custo"),
                        rs.getString("detalhesManutencao")
                );
                m1.setVeiculo(v1);
                v1.setManutencao(m1);
                manutencoes.add(m1);
            }
        }
        return manutencoes;
    }

    public void updateManutencao(Manutencao m1) throws SQLException {
        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";
        String updateSQL = "UPDATE menutencao SET custo = ?, detalhesManutencao = ? WHERE idVeiculo = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {

            int id = 0;

            try (PreparedStatement pstmt = connection.prepareStatement(getVeiculoSQL)) {
                pstmt.setString(1, m1.getVeiculo().getPlaca());

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) id = rs.getInt("id");
                }
            }

            try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
                pstmt.setFloat(1, m1.getCusto());
                pstmt.setString(2, m1.getDetalhesManutencao());
                pstmt.setInt(3, id);

                pstmt.executeUpdate();
            }
        }
    }

    public void deleteManutencao(Manutencao m1) throws SQLException {
        String getVeiculoSQL = "SELECT id FROM veiculo WHERE placa = ?";
        String deleteSQL = "DELETE FROM " + TABLE + " WHERE cpf = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            int id = 0;

            try (PreparedStatement pstmt = connection.prepareStatement(getVeiculoSQL)) {
                pstmt.setString(1, m1.getVeiculo().getPlaca());

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