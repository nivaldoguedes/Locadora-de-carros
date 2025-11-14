import Model.Aluga;
import Model.Cliente;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        try {
            Cliente c1 = new Cliente( 0,                       // CPF
                    "",                      // nome
                    new ArrayList(),         // formaPagamento
                    new ArrayList<Aluga>(),  // historicoLocacoes
                    new ArrayList()          // telefone
            );
            Aluga a1 = new Aluga( LocalDateTime.now(),
                    null,
                    0.0f,
                    "dinheiro");

            // ----------------------------- //
            // 1) TESTAR INSERT DE CLIENTE   //
            // ----------------------------- //

            ArrayList<String> formas = new ArrayList<>();
            formas.add("Débito");
            formas.add("Crédito");

            ArrayList<String> telefones = new ArrayList<>();
            telefones.add("81999990000");
            telefones.add("8133334444");

            Cliente c = new Cliente(
                    123456789,
                    "Maria Silva",
                    formas,
                    new ArrayList<>(),    // histórico começa vazio
                    telefones
            );

            c1.insert(c);  // INSERE NAS 3 TABELAS

            System.out.println("Cliente inserido com sucesso!");

            // ----------------------------- //
            // 2) TESTAR INSERT DE LOCAÇÃO   //
            // ----------------------------- //

            Aluga aluguel = new Aluga(
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(5),
                    120.50f,
                    "Crédito"
            );

            a1.insertAluguel(123456789, aluguel); // FK do cliente

            System.out.println("Aluguel cadastrado!");

            // ----------------------------- //
            // 3) TESTAR READ                //
            // ----------------------------- //
            Cliente buscado = c1.getByCPF(123456789);
            System.out.println("Cliente buscado:");
            System.out.println(buscado);

            // ----------------------------- //
            // 4) TESTAR UPDATE DE TELEFONES //
            // ----------------------------- //
            telefones.add("81911112222");
            c.setTelefone(telefones);

            c1.update(c);

            System.out.println("Cliente atualizado!");

            // ----------------------------- //
            // 5) TESTAR DELETE              //
            // ----------------------------- //

            // c1.delete(123456789);
            // System.out.println("Cliente deletado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}