package DAO;

import model.Funcionario;
import java.sql.SQLException;
import java.util.List;

public class FuncionarioDAOTest {

    public static void main(String[] args) {
        FuncionarioDAO dao = new FuncionarioDAO();

        try {
            System.out.println("--- 1. TESTE DE INSERÇÃO ---");

            Funcionario novo = new Funcionario(
                    "admin",
                    "admin",
                    "admin"
            );

            dao.createFuncionario(novo);

            System.out.println("\n--- 2. TESTE DE LEITURA (Lista Completa) ---");
            List<Funcionario> lista = dao.getFuncionarios();

            System.out.println("Funcionários encontrados (" + lista.size() + "):");

            Funcionario f = lista.get(0);
            System.out.println("CPF: " + f.getCPF() +
                    " | Nome: " + f.getNome());

//            System.out.println("\n--- 3. TESTE DE ATUALIZAÇÃO ---");
//            f.setNome("Maria de Souza");
//            f.setSenha("novaSenha456");
//
//            dao.updateFuncionario(f);
//
//            List<Funcionario> listaAtualizada = dao.getFuncionarios();
//            System.out.println("Nome após UPDATE: " +
//                    listaAtualizada.get(0).getNome());
//
//            System.out.println("\n--- 4. TESTE DE DELEÇÃO ---");
//            dao.deleteFuncionario(f);
//
//            List<Funcionario> listaFinal = dao.getFuncionarios();
//
//            System.out.println("\n--- FIM DOS TESTES ---");
//            System.out.println("Registros restantes: " + listaFinal.size());
//
//            if (listaFinal.isEmpty()) {
//                System.out.println("✅ CRUD COMPLETO E FUNCIONANDO!");
//            }

        } catch (SQLException e) {
            System.err.println("!!! ERRO CRÍTICO DURANTE O TESTE DE CRUD !!!");
            e.printStackTrace();
        }
    }
}