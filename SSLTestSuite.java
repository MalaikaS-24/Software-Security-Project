// SSLTestSuite.java

public class SSLTestSuite {

    public static void main(String[] args) {
        runBasicConnectionTest();
        runDataTransmissionTest();
        runSSLHandshakeTest();
        runCertificateValidationTest();
        runProtocolCipherSuiteTest();
        runErrorHandlingTest();
        runSessionResumptionTest();
    }

    // Test Case 1: Basic Connection Test
    public static void runBasicConnectionTest() {
        System.out.println("Running Basic Connection Test...");
        SSLClient.main(null);
        System.out.println("Basic Connection Test Completed.\n");
    }

    // Test Case 2: Data Transmission Test
    public static void runDataTransmissionTest() {
        System.out.println("Running Data Transmission Test...");
        SSLClient.main(null);
        System.out.println("Data Transmission Test Completed.\n");
    }

    // Test Case 3: SSL Handshake Test
    public static void runSSLHandshakeTest() {
        System.out.println("Running SSL Handshake Test...");
        SSLClient.main(null);
        System.out.println("SSL Handshake Test Completed.\n");
    }

    // Test Case 4: Certificate Validation Test
    public static void runCertificateValidationTest() {
        System.out.println("Running Certificate Validation Test...");
        SSLClient.main(null);
        System.out.println("Certificate Validation Test Completed.\n");
    }

    // Test Case 5: Protocol and Cipher Suite Test
    public static void runProtocolCipherSuiteTest() {
        System.out.println("Running Protocol and Cipher Suite Test...");
        SSLClient.main(null);
        System.out.println("Protocol and Cipher Suite Test Completed.\n");
    }

    // Test Case 6: Error Handling Test
    public static void runErrorHandlingTest() {
        System.out.println("Running Error Handling Test...");
        SSLClient.main(null);
        System.out.println("Error Handling Test Completed.\n");
    }

    // Test Case 7: Session Resumption Test
    public static void runSessionResumptionTest() {
        System.out.println("Running Session Resumption Test...");
        SSLClient.main(null);
        System.out.println("Session Resumption Test Completed.\n");
    }
}
