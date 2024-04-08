import sun.security.krb5.EncryptionKey;
import sun.security.krb5.KerberosKey;
import sun.security.krb5.internal.ktab.KeyTab;
import sun.security.krb5.internal.ktab.KeyTabEntry;
import sun.security.krb5.internal.ktab.KeyTabInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadKeytabFile {
    public static void main(String[] args) {
        String keytabFilePath = "path/to/your/keytab/file.keytab";
        try {
            // Open the keytab file
            File keytabFile = new File(keytabFilePath);
            FileInputStream inputStream = new FileInputStream(keytabFile);
            KeyTabInputStream keyTabInputStream = new KeyTabInputStream(inputStream);

            // Read entries from the keytab file
            KeyTab keytab = KeyTab.getInstance(keytabFile);
            KeyTabEntry[] keytabEntries = keytab.getEntries();

            // Print each entry
            for (KeyTabEntry entry : keytabEntries) {
                System.out.println("Principal: " + entry.getService().getName());
                System.out.println("Key version number: " + entry.getKey().getKeyVersionNumber());
                EncryptionKey encryptionKey = entry.getKey();
                System.out.println("Encryption type: " + encryptionKey.getEType());
                System.out.println("Key bytes: " + encryptionKey.getBytes());
                System.out.println();
            }

            // Close the input streams
            keyTabInputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
