import com.google.api.services.dataflow.Dataflow;
import com.google.api.services.dataflow.model.Job;
import com.google.api.services.dataflow.model.JobMetrics;
import com.google.api.services.dataflow.DataflowScopes;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.auth.oauth2.AccessToken;

import java.io.FileInputStream;
import java.util.Collections;

public class DataflowJobStatus {
    public static void main(String[] args) throws Exception {
        String projectId = "your-project-id";
        String jobId = "bq-to-spanner";

        // Provide the path to your service account key JSON file
        String serviceAccountKeyPath = "path/to/service-account-key.json";

        // Authenticate using service account credentials
        GoogleCredentials credentials = ServiceAccountCredentials.fromStream(new FileInputStream(serviceAccountKeyPath))
                .createScoped(Collections.singletonList(DataflowScopes.CLOUD_PLATFORM));

        // Get an access token for the Dataflow service
        AccessToken accessToken = credentials.refreshAccessToken();
        credentials = credentials.createScoped(Collections.singletonList(DataflowScopes.CLOUD_PLATFORM));

        Dataflow dataflowService = new Dataflow.Builder(credentials.getTransport(), credentials.getJsonFactory())
                .setApplicationName("Your Dataflow Application")
                .build();

        // Get the job details
        Job job = dataflowService.projects().jobs().get(projectId, jobId).execute();

        // Check if the job is running
        String jobState = job.getCurrentState();
        if (jobState.equalsIgnoreCase("JOB_STATE_RUNNING")) {
            System.out.println("The Dataflow job is currently running.");
        } else {
            System.out.println("The Dataflow job is not running. Current state: " + jobState);
        }

        // You can also retrieve and print additional job information if needed
        JobMetrics jobMetrics = dataflowService.projects().jobs().getMetrics(projectId, jobId).execute();
        // Print job metrics or other relevant information
    }
}
