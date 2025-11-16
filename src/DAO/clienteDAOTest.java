package DAO;

import model.Cliente;
import java.sql.SQLException;
import java.util.List;

public class clienteDAOTest {

    public static void main(String[] args) {
        clienteDAO dao = new clienteDAO();

        try {
            System.out.println("--- 1. TESTE DE INSERÇÃO ---");

            Cliente novoCliente = new Cliente(
                    "123114789",
                    "João da Silva",
                    "cartão",
                    "(74)91234-5678"
            );

            dao.createCliente(novoCliente);

            System.out.println("\n--- 2. TESTE DE LEITURA (Lista Completa) ---");
            List<Cliente> lista = dao.getClientes();

            System.out.println("Clientes encontrados (" + lista.size() + "):");

            Cliente clienteParaAtualizar = lista.get(0);
            System.out.println("CPF: " + clienteParaAtualizar.getCPF() +
                    " | Nome: " + clienteParaAtualizar.getNome());

            System.out.println("\n--- 3. TESTE DE ATUALIZAÇÃO ---");
            clienteParaAtualizar.setTelefone("(74)98765-4321");
            clienteParaAtualizar.setFormaPagamento("pix");

            dao.updateCliente(clienteParaAtualizar);

            List<Cliente> listaAtualizada = dao.getClientes();
            System.out.println("Forma de Pagamento após UPDATE: " +
                    listaAtualizada.get(0).getFormaPagamento());

            System.out.println("\n--- 4. TESTE DE DELEÇÃO ---");
            dao.deleteCliente(clienteParaAtualizar);

            List<Cliente> listaFinal = dao.getClientes();

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
