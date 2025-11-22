package DAO;

import java.sql.SQLException;
import java.util.Map;

public class CategoriaDAOTest {
    public static void main(String[] args) {
        CategoriaDAO dao = new CategoriaDAO();

        try {
            Map<String, Float> categorias = dao.getCusto();

            System.out.println("==== Categorias encontradas ====");
            for (Map.Entry<String, Float> entry : categorias.entrySet()) {
                System.out.println("Modelo: " + entry.getKey() +
                        " | Custo: " + entry.getValue());
            }

            if (categorias.isEmpty()) {
                System.out.println("Nenhuma categoria encontrada.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar o banco:");
            e.printStackTrace();
        }
    }
}
