import javax.net.ssl.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.security.KeyStore;

public class SSLServer {

    public static void main(String[] args) {
        int port = 12345;
        String keyStorePath = "/serverkeystore.jks";
        String keyStorePassword = "password";
        String trustStorePath = "/servertruststore.jks";
        String trustStorePassword = "password";
        String[] supportedProtocols = {"TLSv1.2"};
        String[] supportedCipherSuites = {"TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384"};

        try {
            // Create SSL context
            SSLContext sslContext = SSLContext.getInstance("TLS");

            // Load keystore
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(SSLServer.class.getResourceAsStream(keyStorePath), keyStorePassword.toCharArray());

            // Create key manager factory
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

            // Load truststore
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
                sslSocket.startHandshake();
                System.out.println("SSL handshake completed successfully.");

                // Handle client communication
                handleClient(sslSocket);

                // Close the SSL socket (not necessary if keeping the server running)
                sslSocket.close();
            }
        } catch (Exception e) {
            // Print stack trace in case of error
            e.printStackTrace();
        }
    }

    private static void handleClient(SSLSocket sslSocket) throws Exception {
        // Send data to client
        OutputStream outputStream = sslSocket.getOutputStream();
        outputStream.write("Hello from server!".getBytes());
        System.out.println("Data transmission completed successfully.");

        // Receive data from client
        InputStream inputStream = sslSocket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        System.out.println("Received from client: " + new String(buffer, 0, bytesRead));
    }
}
