package Controller;

import Model.Aluga;
import Model.Cliente;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class CadastroCliente {

    private static final String DB_URL = "jdbc:sqlite:banco.db";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // Cria as tabelas necessárias
    public static void connect() {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS clientes (CPF INTEGER PRIMARY KEY, nome TEXT)");

                stmt.execute("CREATE TABLE IF NOT EXISTS cliente_formas_pagamento (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, CPF INTEGER, formaPagamento TEXT, " +
                        "FOREIGN KEY(CPF) REFERENCES clientes(CPF) ON DELETE CASCADE)");

                stmt.execute("CREATE TABLE IF NOT EXISTS cliente_telefones (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, CPF INTEGER, telefone TEXT, " +
                        "FOREIGN KEY(CPF) REFERENCES clientes(CPF) ON DELETE CASCADE)");

                stmt.execute("CREATE TABLE IF NOT EXISTS cliente_historico_locacoes (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, CPF INTEGER, dataAluguel TEXT, dataDevolucao TEXT, valor REAL, formaPagamento TEXT, " +
                        "FOREIGN KEY(CPF) REFERENCES clientes(CPF) ON DELETE CASCADE)");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar/criar tabelas: " + e.getMessage());
        }
    }

    // Inserir cliente completo (clientes + multivalorados + histórico)
    public static void inserir(int CPF, String nome, ArrayList formaPagamento, ArrayList<Aluga> historicoLocacoes, ArrayList telefone) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            connection.setAutoCommit(false);
            try (PreparedStatement pCli = connection.prepareStatement("INSERT INTO clientes (CPF, nome) VALUES (?, ?)");) {
                pCli.setInt(1, CPF);
                pCli.setString(2, nome);
                pCli.executeUpdate();
            }

            // formas de pagamento
            if (formaPagamento != null) {
                try (PreparedStatement pFP = connection.prepareStatement("INSERT INTO cliente_formas_pagamento (CPF, formaPagamento) VALUES (?, ?)");) {
                    for (Object fpObj : formaPagamento) {
                        String fp = fpObj == null ? null : fpObj.toString();
                        pFP.setInt(1, CPF);
                        pFP.setString(2, fp);
                        pFP.executeUpdate();
                    }
                }
            }

            // telefones
            if (telefone != null) {
                try (PreparedStatement pTel = connection.prepareStatement("INSERT INTO cliente_telefones (CPF, telefone) VALUES (?, ?)");) {
                    for (Object telObj : telefone) {
                        String tel = telObj == null ? null : telObj.toString();
                        pTel.setInt(1, CPF);
                        pTel.setString(2, tel);
                        pTel.executeUpdate();
                    }
                }
            }

            // histórico de locações (cada Aluga -> linha própria)
            if (historicoLocacoes != null) {
                try (PreparedStatement pHist = connection.prepareStatement(
                        "INSERT INTO cliente_historico_locacoes (CPF, dataAluguel, dataDevolucao, valor, formaPagamento) VALUES (?, ?, ?, ?, ?)");) {
                    for (Aluga a : historicoLocacoes) {
                        pHist.setInt(1, CPF);
                        pHist.setString(2, a.getDataAluguel() == null ? null : a.getDataAluguel().format(DTF));
                        pHist.setString(3, a.getDataDevolucao() == null ? null : a.getDataDevolucao().format(DTF));
                        pHist.setFloat(4, a.getValor());
                        pHist.setString(5, a.getFormaPagamento());
                        pHist.executeUpdate();
                    }
                }
            }

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }
    }

    // Alterar cliente e multivalorados (substitui listas atuais)
    public static void alterar(int CPF, String nome, ArrayList formaPagamento, ArrayList<Aluga> historicoLocacoes, ArrayList telefone) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            connection.setAutoCommit(false);

            try (PreparedStatement pUpd = connection.prepareStatement("UPDATE clientes SET nome = ? WHERE CPF = ?");) {
                pUpd.setString(1, nome);
                pUpd.setInt(2, CPF);
                pUpd.executeUpdate();
            }

            // apagar multivalorados existentes
            try (PreparedStatement pDelFP = connection.prepareStatement("DELETE FROM cliente_formas_pagamento WHERE CPF = ?")) {
                pDelFP.setInt(1, CPF);
                pDelFP.executeUpdate();
            }
            try (PreparedStatement pDelTel = connection.prepareStatement("DELETE FROM cliente_telefones WHERE CPF = ?")) {
                pDelTel.setInt(1, CPF);
                pDelTel.executeUpdate();
            }
            try (PreparedStatement pDelHist = connection.prepareStatement("DELETE FROM cliente_historico_locacoes WHERE CPF = ?")) {
                pDelHist.setInt(1, CPF);
                pDelHist.executeUpdate();
            }

            // inserir novamente os multivalorados
            if (formaPagamento != null) {
                try (PreparedStatement pFP = connection.prepareStatement("INSERT INTO cliente_formas_pagamento (CPF, formaPagamento) VALUES (?, ?)");) {
                    for (Object fpObj : formaPagamento) {
                        String fp = fpObj == null ? null : fpObj.toString();
                        pFP.setInt(1, CPF);
                        pFP.setString(2, fp);
                        pFP.executeUpdate();
                    }
                }
            }

            if (telefone != null) {
                try (PreparedStatement pTel = connection.prepareStatement("INSERT INTO cliente_telefones (CPF, telefone) VALUES (?, ?)");) {
                    for (Object telObj : telefone) {
                        String tel = telObj == null ? null : telObj.toString();
                        pTel.setInt(1, CPF);
                        pTel.setString(2, tel);
                        pTel.executeUpdate();
                    }
                }
            }

            if (historicoLocacoes != null) {
                try (PreparedStatement pHist = connection.prepareStatement(
                        "INSERT INTO cliente_historico_locacoes (CPF, dataAluguel, dataDevolucao, valor, formaPagamento) VALUES (?, ?, ?, ?, ?)");) {
                    for (Aluga a : historicoLocacoes) {
                        pHist.setInt(1, CPF);
                        pHist.setString(2, a.getDataAluguel() == null ? null : a.getDataAluguel().format(DTF));
                        pHist.setString(3, a.getDataDevolucao() == null ? null : a.getDataDevolucao().format(DTF));
                        pHist.setFloat(4, a.getValor());
                        pHist.setString(5, a.getFormaPagamento());
                        pHist.executeUpdate();
                    }
                }
            }

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Erro ao alterar cliente: " + e.getMessage());
        }
    }

    // Listar todos os clientes com seus multivalorados e histórico (reconstrói objetos Cliente e Aluga)
    public static List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement p = connection.prepareStatement("SELECT CPF, nome FROM clientes")) {
                try (ResultSet rs = p.executeQuery()) {
                    while (rs.next()) {
                        int cpf = rs.getInt("CPF");
                        String nome = rs.getString("nome");

                        // buscar formas de pagamento
                        ArrayList formas = new ArrayList();
                        try (PreparedStatement pFP = connection.prepareStatement("SELECT formaPagamento FROM cliente_formas_pagamento WHERE CPF = ?")) {
                            pFP.setInt(1, cpf);
                            try (ResultSet rfp = pFP.executeQuery()) {
                                while (rfp.next()) formas.add(rfp.getString("formaPagamento"));
                            }
                        }

                        // buscar telefones
                        ArrayList telefones = new ArrayList();
                        try (PreparedStatement pTel = connection.prepareStatement("SELECT telefone FROM cliente_telefones WHERE CPF = ?")) {
                            pTel.setInt(1, cpf);
                            try (ResultSet rt = pTel.executeQuery()) {
                                while (rt.next()) telefones.add(rt.getString("telefone"));
                            }
                        }

                        // buscar histórico de locações
                        ArrayList<Aluga> historico = new ArrayList<>();
                        try (PreparedStatement pHist = connection.prepareStatement(
                                "SELECT dataAluguel, dataDevolucao, valor, formaPagamento FROM cliente_historico_locacoes WHERE CPF = ?")) {
                            pHist.setInt(1, cpf);
                            try (ResultSet rh = pHist.executeQuery()) {
                                while (rh.next()) {
                                    String dA = rh.getString("dataAluguel");
                                    String dD = rh.getString("dataDevolucao");
                                    float valor = rh.getFloat("valor");
                                    String fp = rh.getString("formaPagamento");

                                    LocalDateTime dataAluguel = dA == null ? null : LocalDateTime.parse(dA, DTF);
                                    LocalDateTime dataDevolucao = dD == null ? null : LocalDateTime.parse(dD, DTF);

                                    Aluga a = new Aluga(dataAluguel, dataDevolucao, valor, fp);
                                    historico.add(a);
                                }
                            }
                        }

                        // montar objeto Cliente conforme sua classe
                        Cliente c = new Cliente(cpf, nome, formas, historico, telefones);
                        lista.add(c);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
        return lista;
    }

    // Pegar cliente por CPF
    public static Cliente pegar(int CPF) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement p = connection.prepareStatement("SELECT CPF, nome FROM clientes WHERE CPF = ?")) {
                p.setInt(1, CPF);
                try (ResultSet rs = p.executeQuery()) {
                    if (!rs.next()) return null;

                    String nome = rs.getString("nome");

                    // formas de pagamento
                    ArrayList formas = new ArrayList();
                    try (PreparedStatement pFP = connection.prepareStatement("SELECT formaPagamento FROM cliente_formas_pagamento WHERE CPF = ?")) {
                        pFP.setInt(1, CPF);
                        try (ResultSet rfp = pFP.executeQuery()) {
                            while (rfp.next()) formas.add(rfp.getString("formaPagamento"));
                        }
                    }

                    // telefones
                    ArrayList telefones = new ArrayList();
                    try (PreparedStatement pTel = connection.prepareStatement("SELECT telefone FROM cliente_telefones WHERE CPF = ?")) {
                        pTel.setInt(1, CPF);
                        try (ResultSet rt = pTel.executeQuery()) {
                            while (rt.next()) telefones.add(rt.getString("telefone"));
                        }
                    }

                    // histórico
                    ArrayList<Aluga> historico = new ArrayList<>();
                    try (PreparedStatement pHist = connection.prepareStatement(
                            "SELECT dataAluguel, dataDevolucao, valor, formaPagamento FROM cliente_historico_locacoes WHERE CPF = ?")) {
                        pHist.setInt(1, CPF);
                        try (ResultSet rh = pHist.executeQuery()) {
                            while (rh.next()) {
                                String dA = rh.getString("dataAluguel");
                                String dD = rh.getString("dataDevolucao");
                                float valor = rh.getFloat("valor");
                                String fp = rh.getString("formaPagamento");

                                LocalDateTime dataAluguel = dA == null ? null : LocalDateTime.parse(dA, DTF);
                                LocalDateTime dataDevolucao = dD == null ? null : LocalDateTime.parse(dD, DTF);

                                Aluga a = new Aluga(dataAluguel, dataDevolucao, valor, fp);
                                historico.add(a);
                            }
                        }
                    }

                    return new Cliente(CPF, nome, formas, historico, telefones);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
            return null;
        }
    }

    // Apagar cliente e seus multivalorados
    public static void apagar(int CPF) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            try (PreparedStatement pDel = connection.prepareStatement("DELETE FROM clientes WHERE CPF = ?")) {
                pDel.setInt(1, CPF);
                pDel.executeUpdate();
            }
            // devido ao FOREIGN KEY ... ON DELETE CASCADE, as tabelas multivaloradas serão apagadas automaticamente
        } catch (SQLException e) {
            System.out.println("Erro ao apagar cliente: " + e.getMessage());
        }
    }
}