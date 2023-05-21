import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataflowJobStatus {
    public static void main(String[] args) {
        // Replace with your project ID
        String projectId = "your-project-id";
        // Replace with your Dataflow job ID
        String jobId = "your-job-id";

        try {
            // Construct the gcloud command
            String[] command = {
                "gcloud",
                "dataflow",
                "jobs",
                "describe",
                jobId,
                "--project",
                projectId,
                "--format=value(status.state)"
            };

            // Execute the command
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // Read the command output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine();

            // Wait for the process to complete
            int exitCode = process.waitFor();

            // Check the command output and exit code
            if (exitCode == 0 && output != null) {
                System.out.println("Job status: " + output);
            } else {
                System.err.println("Failed to retrieve job status.");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error executing gcloud command: " + e.getMessage());
        }
    }
}
