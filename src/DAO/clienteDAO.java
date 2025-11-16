package DAO;

import model.Cliente;
import java.util.*;
import java.sql.*;

public class clienteDAO {

    private static final String TABLE = "cliente";

    public void createCliente(Cliente c1) throws SQLException {
        String SQL = "INSERT INTO " + TABLE + " (cpf, nome, formaPagamento, telefone) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setString(1, c1.getCPF());
            pstmt.setString(2, c1.getNome());
            pstmt.setString(3, c1.getFormaPagamento());
            pstmt.setString(4, c1.getTelefone());
            pstmt.executeUpdate();
        }
    }

    public List<Cliente> getClientes() throws SQLException {
        String SQL = "SELECT * FROM " + TABLE;
        List<Cliente> clientes = new ArrayList<>();

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
                clientes.add(c1);
            }
        }
        return clientes;
    }

    public void updateCliente(Cliente c1) throws SQLException {
        String SQL = "UPDATE " + TABLE +
                " SET nome = ?, formaPagamento = ?, telefone = ? WHERE cpf = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setString(1, c1.getNome());
            pstmt.setString(2, c1.getFormaPagamento());
            pstmt.setString(3, c1.getTelefone());
            pstmt.setString(4, c1.getCPF());
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cliente com CPF " + c1.getCPF() + " atualizado com sucesso!");
            } else {
                System.out.println("Nenhum cliente encontrado com o CPF: " + c1.getCPF());
            }
        }
    }

    public void deleteCliente(Cliente c1) throws SQLException {
        String SQL = "DELETE FROM " + TABLE + " WHERE cpf = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setString(1, c1.getCPF());
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cliente com CPF " + c1.getCPF() + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum cliente encontrado com o CPF: " + c1.getCPF());
            }
        }
    }
}