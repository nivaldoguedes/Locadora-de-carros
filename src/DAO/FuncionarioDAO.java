package DAO;

import model.Funcionario;
import util.PasswordUtils;

import java.util.*;
import java.sql.*;

public class FuncionarioDAO {

    private static final String TABLE = "funcionario";

    public void createFuncionario(Funcionario f1) throws SQLException {
        String SQL = "INSERT INTO " + TABLE + " (cpf, nome, senhaHash, salt) VALUES (?, ?, ?, ?)";

        String salt = PasswordUtils.generateSalt();
        String senhaHash = PasswordUtils.hashPassword(f1.getSenha(), salt);

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setString(1, f1.getCPF());
            pstmt.setString(2, f1.getNome());
            pstmt.setString(3, senhaHash);
            pstmt.setString(4, salt);
            pstmt.executeUpdate();
        }
    }

    public List<Funcionario> getFuncionarios() throws SQLException {
        String SQL = "SELECT cpf, nome FROM " + TABLE;
        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Funcionario f1 = new Funcionario(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        null
                );
                funcionarios.add(f1);
            }
        }
        return funcionarios;
    }

    public void updateFuncionario(Funcionario f1) throws SQLException {
        String SQL = "UPDATE " + TABLE +
                " SET nome = ?, senhaHash = ?, salt = ? WHERE cpf = ?";

        String newSalt = PasswordUtils.generateSalt();
        String newHash = PasswordUtils.hashPassword(f1.getSenha(), newSalt);

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setString(1, f1.getNome());
            pstmt.setString(2, newHash);
            pstmt.setString(3, newSalt);
            pstmt.setString(4, f1.getCPF());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Funcionario com CPF " + f1.getCPF() + " atualizado com sucesso!");
            } else {
                System.out.println("Nenhum funcionario encontrado com o CPF: " + f1.getCPF());
            }
        }
    }

    public void deleteFuncionario(Funcionario f1) throws SQLException {
        String SQL = "DELETE FROM " + TABLE + " WHERE cpf = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {

            pstmt.setString(1, f1.getCPF());
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Funcionario com CPF " + f1.getCPF() + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum funcionario encontrado com o CPF: " + f1.getCPF());
            }
        }
    }
}