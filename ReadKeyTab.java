import org.apache.kerby.kerberos.kerb.keytab.Keytab;
import org.apache.kerby.kerberos.kerb.keytab.KeytabEntry;

import java.io.File;
import java.io.IOException;

public class ReadKeytabFile {
    public static void main(String[] args) {
        String keytabFilePath = "path/to/your/keytab/file.keytab";
        try {
            // Read the keytab file
            Keytab keytab = Keytab.loadKeytab(new File(keytabFilePath));

            // Print each entry
            for (KeytabEntry entry : keytab.getKeytabEntries()) {
                System.out.println("Principal: " + entry.getPrincipalName());
                System.out.println("Key version: " + entry.getKeyVersion());
                System.out.println("Key type: " + entry.getKey().getKeyType().toString());
                System.out.println("Key bytes: " + entry.getKey().getKeyData().toString());
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*
<dependency>
    <groupId>org.apache.kerby</groupId>
    <artifactId>kerby-asn1</artifactId>
    <version>2.0.1</version>
</dependency>

*/
