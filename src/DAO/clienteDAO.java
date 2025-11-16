package DAO;
import model.Cliente;
import java.util.*;
import java.sql.*;

public class clienteDAO {
    private static final String c = "cliente";

    public void createCliente(Cliente c1) throws SQLException {
        String SQL = "INSERT into " +c+ "(cpf, nome, formas_pagamento, telefone) values (?, ?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQL)) {
                pstmt.setInt(1, c1.getCPF());
                pstmt.setString(2, c1.getNome());
                pstmt.setString(3, c1.getFormaPagamento());
                pstmt.setString(4, c1.getTelefone());
                pstmt.executeUpdate();
            }
    }

    public List<Cliente> getClientes() throws SQLException {
        String SQL = "SELECT * from " +c;
        List<Cliente> clientes = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cliente c1 = new Cliente(
                        rs.getInt("cpf"),
                        rs.getString("nome"),
                        rs.getString("formas_pagamento"),
                        rs.getString("telefone")
                );
                clientes.add(c1);
            }
            return clientes;
        }
    }

    public void updateCliente(Cliente c1) throws SQLException {
        String SQL = "UPDATE " +c+ " SET cpf = ?, nome = ?, formas_pagamento = ?, telefone = ? WHERE cpf = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1, c1.getCPF());
            pstmt.setString(2, c1.getNome());
            pstmt.setString(3, c1.getFormaPagamento());
            pstmt.setString(4, c1.getTelefone());
            pstmt.setInt(5, c1.getCPF());
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cliente com CPF " + c1.getCPF() + " atualizado com sucesso!");
            } else {
                System.out.println("Nenhum cliente encontrado com o CPF: " + c1.getCPF());
            }
        }
    }

    public void deleteCliente(Cliente c1) throws SQLException {
        String SQL = "DELETE from " +c+ " WHERE cpf = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1, c1.getCPF());
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cliente com CPF " + c1.getCPF() + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum cliente encontrado com o CPF: " + c1.getCPF());
            }
        }
    }
}

