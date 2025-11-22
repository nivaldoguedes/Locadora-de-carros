package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CategoriaDAO {
    public Map<String, Float> getCusto() throws SQLException {
        String SQL = "SELECT * FROM categoria";

        Map<String, Float> categorias = new HashMap<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                categorias.put(rs.getString("modelo"), rs.getFloat("custo"));
            }
        }
        return categorias;
    }
}