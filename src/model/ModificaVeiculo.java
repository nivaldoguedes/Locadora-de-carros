//package model;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//import java.util.*;
//
//public class ModificaVeiculo {
//    private List<Aluga> alugueis = new ArrayList<>();
//    private List<Reserva> reservas = new ArrayList<>();
//
//    private boolean datasConflitam(LocalDate inicio1, LocalDate fim1,
//                                   LocalDate inicio2, LocalDate fim2) {
//
//        return !inicio1.isAfter(fim2) && !inicio2.isAfter(fim1);
//    }
//
//    public Aluga locarVeiculo1(Aluga a1) {
//        Map<String, Float> categorias = new HashMap<>();
//        categorias.put("Hatch", 80.0F);
//        categorias.put("Sedan", 100.0F);
//        categorias.put("SUV", 180.0F);
//        Scanner sc = new Scanner(System.in);
//        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        // Entrada das datas
//        System.out.print("Digite a data do aluguel (dd/MM/yyyy): ");
//        LocalDate dataAluguel = LocalDate.parse(sc.nextLine(), formato);
//        System.out.print("Digite a data da devolução (dd/MM/yyyy): ");
//        LocalDate dataDevolucao = LocalDate.parse(sc.nextLine(), formato);
//
//        // 1 — Verificar conflitos com reservas
//        for (Reserva r : reservas) {
//            if (r.getVeiculo().equals(a1)) {
//                if (datasConflitam(dataAluguel, dataDevolucao,
//                        r.getDataReserva(), r.getDataDevolucao())) {
//                    throw new IllegalStateException("Este veículo está reservado nesse período.");
//                }
//            }
//        }
//
//        // 2 — Verificar se está disponível
//        if (!a1.getVeiculo().isDisponibilidade()) {
//            throw new IllegalStateException("Veículo indisponível");
//        }
//
//        // Calcular valor
//        float valorDiaria = categorias.get(a1.getVeiculo().getCategoria());
//        float valor = ChronoUnit.DAYS.between(dataAluguel, dataDevolucao) * valorDiaria;
//        System.out.print("Forma de pagamento: ");
//        String formaPagamento = sc.nextLine();
//
//        Aluga aluguel = new Aluga(dataAluguel, dataDevolucao, valor, formaPagamento);
//        alugueis.add(aluguel);
//        a1.getVeiculo().setDisponibilidade(false);
//
//        return aluguel;
//    }
//
//    public Reserva reservarVeiculo(Aluga a1) {
//        Map<String, Float> categorias = new HashMap<>();
//        categorias.put("Hatch", 80.0F);
//        categorias.put("Sedan", 100.0F);
//        categorias.put("SUV", 180.0F);
//
//        Scanner sc = new Scanner(System.in);
//        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        System.out.print("Digite a data para reserva do veículo (dd/MM/yyyy): ");
//        LocalDate dataReserva = LocalDate.parse(sc.nextLine(), formato);
//        System.out.print("Digite a data da devolução (dd/MM/yyyy): ");
//        LocalDate dataInicioAluguel = LocalDate.parse(sc.nextLine(), formato);
//
//        // 1 — Verificar conflitos com locações
//        for (Aluga a : alugueis) {
//            if (a.getVeiculo().equals(a1)) {
//                if (datasConflitam(dataReserva, dataInicioAluguel,
//                        a.getDataAluguel(), a.getDataDevolucao())) {
//                    throw new IllegalStateException("Este veículo está locado nesse período.");
//                }
//            }
//        }
//
//        // calcular valor
//        float valorDiaria = categorias.get(a1.getVeiculo().getCategoria());
//        float valor = ChronoUnit.DAYS.between(dataReserva, dataInicioAluguel) * valorDiaria;
//
//        Reserva r1 = new Reserva(dataReserva, dataInicioAluguel, valor);
//        reservas.add(r1);
//
//        return r1;
//    }
//
//    public void devolverVeiculo(Aluga a1) {
//        Scanner sc = new Scanner(System.in);
//
//        // Solicitar quilometragem final
//        System.out.print("Digite a quilometragem final do veículo: ");
//        float quilometragemFinal = Float.parseFloat(sc.nextLine());
//
//        // Atualizar a quilometragem atual do veículo
//        a1.getVeiculo().setQuilometragem(quilometragemFinal);
//
//        // Solicitar descrição de danos (opcional)
//        System.out.print("Digite danos ao veículo (opcional, pressione Enter se não houver): ");
//        String danos = sc.nextLine();
//
//        // Mensagem de registro
//        if (danos.isEmpty()) {
//            System.out.println("Veículo devolvido sem danos.");
//        } else {
//            System.out.println("Veículo devolvido com os seguintes danos: " + danos);
//        }
//
//        // Tornar o veículo disponível novamente
//        a1.getVeiculo().setDisponibilidade(true);
//
//        System.out.println("Devolução registrada com sucesso!");
//    }
//
//    public void manutencaoVeiculo(Aluga a1) {
//        Scanner sc = new Scanner(System.in);
//        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        System.out.print("Digite a data para manutenção do veículo (dd/MM/yyyy): ");
//        LocalDate data = LocalDate.parse(sc.nextLine(), formato);
//        System.out.print("Digite o custo da manutenção do veículo: ");
//        float custo = Float.parseFloat(sc.nextLine());
//        System.out.print("Digite os detalhes da manutenção do veículo: ");
//        String detalhesManutencao = sc.nextLine();
//
//        a1.getVeiculo().setDisponibilidade(false);
//    }
//}