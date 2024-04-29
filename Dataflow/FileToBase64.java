import java.io.*;
import java.util.Base64;

public class FileToBase64 {
    public static void main(String[] args) {
        String filePath = "path/to/your/file.txt"; // Replace with the path to your file
        
        try {
            // Read file content
            byte[] fileContent = readFileToBytes(filePath);
            
            // Convert bytes to Base64
            String base64Encoded = encodeToBase64(fileContent);
            
            // Print Base64 encoded string
            System.out.println(base64Encoded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] readFileToBytes(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }

    private static String encodeToBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
