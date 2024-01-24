# test

-Djavax.net.ssl.trustStore=/path/to/your/truststore -Djavax.net.ssl.trustStorePassword=yourTruststorePassword -Djavax.net.ssl.trustStoreType=yourTruststoreType -Dcom.sun.net.ssl.checkRevocation=false


keytool -import -trustcacerts -keystore /path/to/custom-truststore.jks -storepass yourTruststorePassword -noprompt -alias yourAlias -file /path/to/yourCertificate.crt

-Dhttps.cipherSuites=TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384
mvn clean install -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true


import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.*;

public class SSLHandler {

    public static void main(String[] args) {
        // Replace 'your.jks' and 'your_password' with your actual keystore file and password
        String keystorePath = "your.jks";
        String keystorePassword = "your_password";

        try {
            // Load the keystore
            KeyStore keyStore = KeyStore.getInstance("JKS");
            InputStream keyStoreStream = new FileInputStream(keystorePath);
            keyStore.load(keyStoreStream, keystorePassword.toCharArray());

            // Create a KeyManagerFactory and initialize it with the keystore
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keystorePassword.toCharArray());

            // Create an SSLContext and initialize it with the KeyManagerFactory
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            // Set the default SSLSocketFactory for the HttpsURLConnection
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            // Use the HttpsURLConnection to make a secure connection
            // For example:
            // URL url = new URL("https://example.com");
            // HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            // // Continue with your connection...

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
