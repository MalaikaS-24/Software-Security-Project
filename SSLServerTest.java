import javax.net.ssl.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.security.KeyStore;

public class SSLServerTest {

    public static void main(String[] args) {
        int port = 12345;
        String keyStorePath = "/serverkeystore.jks"; // Path to server keystore
        String keyStorePassword = "password"; // Password for server keystore
        String trustStorePath = "/servertruststore.jks"; // Path to server truststore
        String trustStorePassword = "password"; // Password for server truststore
        String[] supportedProtocols = {"TLSv1.2"};
        String[] supportedCipherSuites = {"TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384"};

        try {
            // Create SSL context
            SSLContext sslContext = SSLContext.getInstance("TLS");

            // Load keystore (containing server's private key and certificate)
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(SSLServer.class.getResourceAsStream(keyStorePath), keyStorePassword.toCharArray());

            // Create key manager factory
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

            // Load truststore (containing client's trusted certificates)
            KeyStore trustStore = KeyStore.getInstance("JKS");
            trustStore.load(SSLServer.class.getResourceAsStream(trustStorePath), trustStorePassword.toCharArray());

            // Create trust manager factory
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            // Initialize SSL context with key managers and trust managers
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

            // Create SSLParameters and set the supported protocols and cipher suites
            SSLParameters sslParams = new SSLParameters();
            sslParams.setProtocols(supportedProtocols);
            sslParams.setCipherSuites(supportedCipherSuites);

            // Create SSL server socket with custom SSL parameters
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
            SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
            sslServerSocket.setSSLParameters(sslParams);

            // Keep accepting client connections
            while (true) {
                // Wait for client connection
                System.out.println("Waiting for client connection on port " + port);
                SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();

                // Perform SSL handshake
                try {
                    sslSocket.startHandshake();
                    System.out.println("SSL handshake completed successfully.");
                    
                    // Simulate certificate validation failure
                    throw new Exception("Certificate validation failed: Invalid certificate provided by the server.");
                } catch (Exception e) {
                    // Handle SSL handshake failure
                    System.err.println("SSL handshake failed: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    // Close the SSL socket
                    sslSocket.close();
                }
            }
        } catch (Exception e) {
            // Print stack trace in case of error
            e.printStackTrace();
        }
    }
}
