# test

-Djavax.net.ssl.trustStore=/path/to/your/truststore -Djavax.net.ssl.trustStorePassword=yourTruststorePassword -Djavax.net.ssl.trustStoreType=yourTruststoreType -Dcom.sun.net.ssl.checkRevocation=false


keytool -import -trustcacerts -keystore /path/to/custom-truststore.jks -storepass yourTruststorePassword -noprompt -alias yourAlias -file /path/to/yourCertificate.crt

-Dhttps.cipherSuites=TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384
