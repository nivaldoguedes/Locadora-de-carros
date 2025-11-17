package DAO;

import model.Veiculo;
import java.sql.*;
import java.util.*;

public class veiculoDAO {
    private static final String TABLE = "veiculo";

    public void createVeiculo(Veiculo v1) throws SQLException {
        String SQL = "INSERT INTO " + TABLE + " (placa, marca, modelo, ano, quilometragemAtual, categoria, disponibilidade) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setString(1, v1.getPlaca());
            pstmt.setString(2, v1.getMarca());
            pstmt.setString(3, v1.getModelo());
            pstmt.setInt(4, v1.getAno());
            pstmt.setFloat(5, v1.getQuilometragem());
            pstmt.setString(6, v1.getCategoria());
            pstmt.setBoolean(7, true);
            pstmt.executeUpdate();
        }
    }

    public List<Veiculo> getVeiculos() throws SQLException {
        String SQL = "SELECT * FROM " + TABLE;
        List<Veiculo> veiculos = new ArrayList<>();

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
                veiculos.add(v1);
            }
        }
        return veiculos;
    }

    public void updateVeiculo(Veiculo v1) throws SQLException {
        String SQL = "UPDATE " + TABLE +
                " SET quilometragemAtual = ?, disponibilidade = ? WHERE placa = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setFloat(1, v1.getQuilometragem());
            pstmt.setBoolean(2, v1.isDisponibilidade());
            pstmt.setString(3, v1.getPlaca());
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Veiculo com placa " + v1.getPlaca() + " atualizado com sucesso!");
            } else {
                System.out.println("Nenhum cliente encontrado com o placa: " + v1.getPlaca());
            }
        }
    }

    public void deleteVeiculo(Veiculo v1) throws SQLException {
        String SQL = "DELETE FROM " + TABLE + " WHERE placa = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setString(1, v1.getPlaca());
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Veiculo com placa " + v1.getPlaca() + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum cliente encontrado com o placa: " + v1.getPlaca());
            }
        }
    }
}