package DAO;

import model.Veiculo;
import java.sql.SQLException;
import java.util.List;

public class veiculoDAOTest {

    public static void main(String[] args) {
        veiculoDAO dao = new veiculoDAO();

        try {
            System.out.println("--- 1. TESTE DE INSERÇÃO ---");

            Veiculo novoVeiculo = new Veiculo(
                    "ABC1234",
                    "Fiat",
                    "Argo",
                    2020,
                    16000.5f,
                    "Hatch",
                    true
            );
            dao.createVeiculo(novoVeiculo);

            System.out.println("\n--- 2. TESTE DE LEITURA (Lista Completa) ---");
            List<Veiculo> lista = dao.getVeiculos();

            System.out.println("Veículos encontrados (" + lista.size() + "):");
            for (Veiculo v : lista) {
                System.out.println(
                        "Placa: " + v.getPlaca() +
                                " | Marca: " + v.getMarca() +
                                " | Modelo: " + v.getModelo() +
                                " | Ano: " +v.getAno() +
                                " | Disponibilidade: " +v.isDisponibilidade()
                );
            }

            Veiculo veiculoParaAtualizar = lista.get(0);

            System.out.println("\n--- 3. TESTE DE ATUALIZAÇÃO ---");
            veiculoParaAtualizar.setQuilometragem(16000.8f);
            veiculoParaAtualizar.setDisponibilidade(false);

            dao.updateVeiculo(veiculoParaAtualizar);

            List<Veiculo> listaAtualizada = dao.getVeiculos();
            System.out.println("Quilometragem após UPDATE: " +
                    listaAtualizada.get(0).getQuilometragem());
            System.out.println("Disponibilidade após UPDATE: " +
                    listaAtualizada.get(0).isDisponibilidade());

            System.out.println("\n--- 4. TESTE DE DELEÇÃO ---");
            dao.deleteVeiculo(veiculoParaAtualizar);

            List<Veiculo> listaFinal = dao.getVeiculos();

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