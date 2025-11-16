package DAO;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class ConnectionFactory {
    private static final Properties props = new Properties();

    static {
        // Este é o método CORRETO para ler do classpath (src/main/resources)
        try (InputStream input = ConnectionFactory.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                // Se cair aqui, é porque o arquivo não está em src/main/resources
                throw new RuntimeException("config.properties not found");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o arquivo config.properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password");
        return DriverManager.getConnection(url, user, pass);
    }
}