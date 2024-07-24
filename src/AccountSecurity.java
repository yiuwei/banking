// Import java functions
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Hash the passwords with SHA-256 and convert them to hex format
public class AccountSecurity {
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException exp) {
            throw new RuntimeException("Error not found", exp);
        }
    }
}
