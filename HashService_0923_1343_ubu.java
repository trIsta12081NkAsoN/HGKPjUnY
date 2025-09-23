// 代码生成时间: 2025-09-23 13:43:33
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

@Service
public class HashService {

    private static final Logger logger = Logger.getLogger(HashService.class.getName());

    @PostConstruct
    public void init() {
        logger.info("HashService initialized");
    }

    /**
     * Calculate hash value for the given input string.
     *
     * @param input The string to hash.
     * @return The hash value as a hexadecimal string.
     * @throws NoSuchAlgorithmException If the specified algorithm does not exist.
     */
    public String calculateHash(String input) throws NoSuchAlgorithmException {
        if (!StringUtils.hasText(input)) {
            throw new IllegalArgumentException("Input string cannot be empty or null");
        }

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * A simple test method to demonstrate the hash calculation.
     *
     * @param args The command line arguments (not used in this method).
     */
    public static void main(String[] args) {
        try {
            HashService service = new HashService();
            String hashValue = service.calculateHash("Hello, World!");
            System.out.println("Hash value: " + hashValue);
        } catch (Exception e) {
            logger.severe("Error calculating hash: " + e.getMessage());
        }
    }
}
