import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyStore;


public class SSLClient {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        try {
            // Set truststore password for the client
            System.setProperty("javax.net.ssl.trustStorePassword", "password");

            // Create SSL context
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore trustStore = KeyStore.getInstance("JKS");
            trustStore.load(SSLClient.class.getResourceAsStream("/clienttruststore.jks"), "password".toCharArray());
            trustManagerFactory.init(trustStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            // Create SSL socket
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(host, port);

            // Perform SSL handshake
            sslSocket.startHandshake();

            // Receive data from server
            InputStream inputStream = sslSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            System.out.println("Received from server: " + new String(buffer, 0, bytesRead));

            // Send data to server
            OutputStream outputStream = sslSocket.getOutputStream();
            outputStream.write("Hello from client!".getBytes());

            // Close the connection
            sslSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
