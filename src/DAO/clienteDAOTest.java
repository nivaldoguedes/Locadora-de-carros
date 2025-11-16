package DAO;

import model.Cliente;
import java.sql.SQLException;
import java.util.List;

public class clienteDAOTest {

    public static void main(String[] args) {
        clienteDAO dao = new clienteDAO();

        try {
            // ==========================================================
            // 0. LIMPEZA (Para garantir que os testes comecem do zero)
            // ==========================================================
            // TRUNCATE reseta a tabela e a sequência do CPF (SERIAL)
            // Use isso apenas em ambiente de teste!


            // ==========================================================
            // 1. CREATE (Adicionar um novo Veterinário)
            // ==========================================================
            System.out.println("--- 1. TESTE DE INSERÇÃO ---");

            // Note: O construtor sem CPF é usado aqui, pois o banco gera o CPF.
            Cliente novoCliente = new Cliente(
                    123114789,
                    "João da Silva",
                    "cartão",
                    "(74)91234-5678"
            );
            dao.createCliente(novoCliente);

            // ==========================================================
            // 2. READ (Listar e confirmar a inserção)
            // ==========================================================
            System.out.println("\n--- 2. TESTE DE LEITURA (Lista Completa) ---");

            List<Cliente> lista = dao.getClientes();
            System.out.println("Clientes encontrados (" + lista.size() + "):");

            // O primeiro cliente na lista será o que acabamos de inserir (CPF 1)
            Cliente clienteParaAtualizar = lista.get(0);

            System.out.println("  CPF: " + clienteParaAtualizar.getCPF() + " | Nome: " + clienteParaAtualizar.getNome());

            // ==========================================================
            // 3. UPDATE (Atualizar o cliente recém-inserido)
            // ==========================================================
            System.out.println("\n--- 3. TESTE DE ATUALIZAÇÃO ---");

            // Alterar o telefone e a forma_pagamento do objeto
            clienteParaAtualizar.setTelefone("(74)98765-4321");
            clienteParaAtualizar.setFormaPagamento("pix");

            dao.updateCliente(clienteParaAtualizar);

            // Verificação Rápida: Listar novamente
            List<Cliente> listaAtualizada = dao.getClientes();
            System.out.println("Verificação do CPF " + clienteParaAtualizar.getCPF() + " após UPDATE:");
            System.out.println("Forma de Pagamento: " + listaAtualizada.get(0).getFormaPagamento());

            // ==========================================================
            // 4. DELETE (Deletar o cliente)
            // ==========================================================
            System.out.println("\n--- 4. TESTE DE DELEÇÃO ---");

            dao.deleteCliente(novoCliente);

            // Verificação final
            List<Cliente> listaFinal = dao.getClientes();
            System.out.println("\n--- FIM DOS TESTES ---");
            System.out.println("Registros restantes após DELETE: " + listaFinal.size());

            if (listaFinal.isEmpty()) {
                System.out.println("✅ CRUD COMPLETO E FUNCIONANDO COM SUCESSO!");
            }

        } catch (SQLException e) {
            System.err.println("!!! ERRO CRÍTICO DURANTE O TESTE DE CRUD !!!");
            System.err.println("Causa: " + e.getMessage());
            e.printStackTrace();
        }
    }
}