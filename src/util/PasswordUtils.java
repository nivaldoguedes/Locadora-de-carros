package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {

    // Gera um salt seguro (32 bytes)
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Faz hash com SHA-256 + salt
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(Base64.getDecoder().decode(salt)); // aplica o salt
            byte[] hashed = md.digest(password.getBytes());

            return Base64.getEncoder().encodeToString(hashed);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash SHA-256", e);
        }
    }

    // Verifica a senha
    public static boolean checkPassword(String enteredPassword, String storedHash, String storedSalt) {
        String hashOfEntered = hashPassword(enteredPassword, storedSalt);
        return hashOfEntered.equals(storedHash);
    }
}