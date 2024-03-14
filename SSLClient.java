import javax.net.ssl.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyStore;

public class SSLClient {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;
        String trustStorePath = "/clienttruststore.jks";
        String trustStorePassword = "password";
        String[] supportedProtocols = {"TLSv1.2"};
        String[] supportedCipherSuites = {"TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384"};

        try {
            // Create SSL context
            SSLContext sslContext = SSLContext.getInstance("TLS");

            // Load truststore
            java.security.KeyStore trustStore = java.security.KeyStore.getInstance("JKS");
            trustStore.load(SSLClient.class.getResourceAsStream(trustStorePath), trustStorePassword.toCharArray());

            // Create trust manager factory
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            // Initialize SSL context with trust managers and desired protocols/ciphers
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            // Create SSLParameters and set the supported protocols and cipher suites
            SSLParameters sslParams = new SSLParameters();
            sslParams.setProtocols(supportedProtocols);
            sslParams.setCipherSuites(supportedCipherSuites);

            // Create SSL socket with custom SSL parameters
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(host, port);
            sslSocket.setSSLParameters(sslParams);

            // Perform SSL handshake
            sslSocket.startHandshake();
            
            // Confirm SSL handshake completion
            System.out.println("SSL handshake completed successfully.");

            // Receive data from server
            InputStream inputStream = sslSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            System.out.println("Received from server: " + new String(buffer, 0, bytesRead));

            // Send data to server
            OutputStream outputStream = sslSocket.getOutputStream();
            outputStream.write("Hello from client!".getBytes());

            // Confirm data transmission completion
            System.out.println("Data transmission completed successfully.");

            // Close the connection
            sslSocket.close();
            
            // Confirm connection closure
            System.out.println("Connection closed.");
        } catch (Exception e) {
            // Print stack trace in case of error
            e.printStackTrace();
        }
    }
}