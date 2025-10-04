// 代码生成时间: 2025-10-04 19:54:42
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.KeyManagerFactory;

@Component
public class CertificateManagementComponent {

    private SSLContext sslContext;

    @PostConstruct
    public void init() {
        try {
            // Load the keystore
            Resource keystoreResource = new ClassPathResource("/keystore.jks");
            KeyStore keystore = KeyStore.getInstance("JKS");
            try (InputStream inputStream = keystoreResource.getInputStream()) {
                keystore.load(inputStream, "keystore-password".toCharArray());
            }

            // Initialize KeyManagerFactory
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keystore, "key-password".toCharArray());

            // Load the truststore
            Resource truststoreResource = new ClassPathResource("/truststore.jks");
            KeyStore truststore = KeyStore.getInstance("JKS");
            truststore.load(truststoreResource.getInputStream(), "truststore-password".toCharArray());

            // Initialize TrustManagerFactory
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(truststore);

            // Initialize SSLContext
            this.sslContext = SSLContext.getInstance("TLS");
            this.sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException | KeyManagementException | UnrecoverableKeyException e) {
            throw new RuntimeException("Failed to initialize SSL Context", e);
        }
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return sslContext.getSocketFactory();
    }
}
