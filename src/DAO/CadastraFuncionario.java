package DAO;

import model.Funcionario;
import java.sql.SQLException;
import java.util.List;

public class CadastraFuncionario {

    public static void main(String[] args) {
        FuncionarioDAO dao = new FuncionarioDAO();

        try {
            System.out.println("--- 1. TESTE DE INSERÇÃO ---");

            Funcionario novo = new Funcionario(
                    "12345678903",
                    "Pedro de Souza",
                    "123456"
            );

            dao.createFuncionario(novo);

            System.out.println("\n--- 2. TESTE DE LEITURA (Lista Completa) ---");
            List<Funcionario> lista = dao.getFuncionarios();

            System.out.println("Funcionários encontrados (" + lista.size() + "):");

            Funcionario f = lista.get(0);
            System.out.println("CPF: " + f.getCPF() +
                    " | Nome: " + f.getNome());

            List<Funcionario> listaFinal = dao.getFuncionarios();

            System.out.println("\n--- FIM DOS TESTES ---");
            System.out.println("Registros restantes: " + listaFinal.size());

            if (listaFinal.isEmpty()) {
                System.out.println("✅ CRUD COMPLETO E FUNCIONANDO!");
            }

        } catch (SQLException e) {
            System.err.println("!!! ERRO CRÍTICO DURANTE O TESTE DE CRUD !!!");
            e.printStackTrace();
        }
    }
    }