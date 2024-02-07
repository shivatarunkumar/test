import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadCSVFromGCS {

    public static void main(String[] args) {
        // Your GCP project ID
        String projectId = "your_project_id";
        // Your GCS bucket name
        String bucketName = "your_bucket_name";
        // Path to the CSV file in the bucket
        String blobName = "path/to/your/file.csv";

        // Instantiate a storage client
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

        // Get the CSV file from GCS
        Blob blob = storage.get(bucketName, blobName);

        // Read the CSV file
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(blob.getContent()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line of the CSV file
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }
}
