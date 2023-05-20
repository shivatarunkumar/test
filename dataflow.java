import com.google.api.services.dataflow.Dataflow;
import com.google.api.services.dataflow.model.Job;
import com.google.api.services.dataflow.model.JobMetrics;
import com.google.api.services.dataflow.model.ListJobsResponse;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dataflow.sdk.util.MonitoringUtil;

import java.io.IOException;
import java.util.List;

public class DataflowJobStatus {
    public static void main(String[] args) throws IOException {
        String projectId = "your-project-id";
        String jobName = "bq-to-spanner";

        // Authenticate using Application Default Credentials (ADC)
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();

        // Create a Dataflow client
        Dataflow dataflowService = new Dataflow.Builder(credentials.getTransport(), credentials.getJsonFactory(), credentials)
                .setApplicationName("Dataflow Job Status Checker")
                .build();

        // List jobs in the project
        ListJobsResponse response = dataflowService.projects().jobs().list(projectId).execute();
        List<Job> jobs = response.getJobs();

        // Find the job with the specified name
        Job targetJob = null;
        for (Job job : jobs) {
            if (job.getName().equals(jobName)) {
                targetJob = job;
                break;
            }
        }

        // Check if the job was found
        if (targetJob != null) {
            // Get the job details
            Job jobDetails = dataflowService.projects().jobs().get(projectId, targetJob.getId()).execute();

            // Check the current state of the job
            String jobState = jobDetails.getCurrentState();
            if (jobState.equalsIgnoreCase("JOB_STATE_RUNNING")) {
                System.out.println("The Dataflow job is currently running.");
            } else {
                System.out.println("The Dataflow job is not running. Current state: " + jobState);
            }

            // Retrieve and print additional job information if needed
            JobMetrics jobMetrics = dataflowService.projects().jobs().getMetrics(projectId, targetJob.getId()).execute();
            MonitoringUtil.printMetrics(jobMetrics);
        } else {
            System.out.println("The Dataflow job with the specified name was not found.");
        }
    }
}
