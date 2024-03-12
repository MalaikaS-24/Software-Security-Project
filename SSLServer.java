import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.security.KeyStore;


public class SSLServer {

    public static void main(String[] args) {
        int port = 12345;

        try {
            // Set keystore password for the server
            System.setProperty("javax.net.ssl.keyStorePassword", "password");

            // Create SSL context
            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(SSLServer.class.getResourceAsStream("/serverkeystore.jks"), "password".toCharArray());
            keyManagerFactory.init(keyStore, "password".toCharArray());
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            // Create SSL server socket
            SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
            SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);

            // Wait for client connection
            System.out.println("Waiting for client connection on port " + port);
            SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();

            // Perform SSL handshake
            sslSocket.startHandshake();

            // Send data to client
            OutputStream outputStream = sslSocket.getOutputStream();
            outputStream.write("Hello from server!".getBytes());

            // Receive data from client
            InputStream inputStream = sslSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            System.out.println("Received from client: " + new String(buffer, 0, bytesRead));

            // Close the connection
            sslSocket.close();
            sslServerSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
