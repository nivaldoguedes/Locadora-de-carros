package DAO;

import model.Aluga;
import model.Cliente;
import model.Veiculo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AlugaDAOTest {

    public static void main(String[] args) {
        AlugaDAO dao = new AlugaDAO();

        try {
            System.out.println("--- 1. TESTE DE INSERÇÃO DE ALUGUEL ---");

            // === CLIENTE JÁ EXISTENTE NO BANCO ===
            Cliente c = new Cliente(
                    "00000000000",  // cpf
                    "Cliente Teste",
                    "Dinheiro",
                    "999999999"
            );

            // === VEÍCULO EXISTENTE NO BANCO ===
            Veiculo v = new Veiculo(
                    "ABC1234",
                    "Fiat",
                    "Argo",
                    2020,
                    16000.1f,
                    "Hatch",
                    true
            );

            // === NOVO ALUGUEL ===
            Aluga aluguel = new Aluga(
                    LocalDate.now(),
                    LocalDate.now().plusDays(5),
                    1200.0f,
                    "Cartão de Crédito"
            );

            // ASSOCIAÇÕES IMPORTANTES (ANTES DE ENVIAR AO DAO)
            aluguel.setCliente(c);
            aluguel.setVeiculo(v);

            // Envia para o DAO
            dao.createAluga(aluguel);
            System.out.println("Aluguel criado com sucesso!");

            // === LEITURA ===
            System.out.println("\n--- 2. TESTE DE LEITURA (getAlugueis) ---");
            List<Veiculo> listaVeiculos = dao.getAlugueis();
            System.out.println("Aluguéis encontrados: " + listaVeiculos.size());

            for (Veiculo veic : listaVeiculos) {
                Aluga a = veic.getAluga();  // pega o aluguel associado
                System.out.println(
                        "Cliente: " + a.getCliente().getNome() +
                                " | Veículo: " + veic.getPlaca() +
                                " | Valor: " + a.getValor() +
                                " | Data Aluguel: " + a.getDataAluguel() +
                                " | Data Devolução: " + a.getDataDevolucao()
                );
            }

            // === UPDATE ===
            System.out.println("\n--- 3. TESTE DE UPDATE ---");

            Veiculo veicUpdate = listaVeiculos.get(0);
            Aluga aUpdate = veicUpdate.getAluga();
            aUpdate.setQuilometragemFinal(16200.5f);
            aUpdate.setDanos("Arranhões na porta");

            dao.updateAluga(aUpdate);
            System.out.println("Update realizado com sucesso!");

            // === DELETE ===
            System.out.println("\n--- 4. TESTE DE DELETE ---");

            dao.deleteAluga(aUpdate);
            List<Veiculo> listaFinal = dao.getAlugueis();
            System.out.println("Registros restantes após DELETE: " + listaFinal.size());

            if (listaFinal.isEmpty()) {
                System.out.println("\n✅ CRUD DE ALUGUEL COMPLETO E FUNCIONAL!");
            }

        } catch (SQLException e) {
            System.err.println("!!! ERRO DURANTE O TESTE DE CRUD DE ALUGUEL !!!");
            e.printStackTrace();
        }
    }
}