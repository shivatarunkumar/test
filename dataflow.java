import com.google.api.services.dataflow.Dataflow;
import com.google.api.services.dataflow.model.Job;
import com.google.api.services.dataflow.model.JobMetrics;

public class DataflowJobStatus {
    public static void main(String[] args) throws Exception {
        String projectId = "your-project-id";
        String jobId = "bq-to-spanner";

        Dataflow dataflowService = DataflowServiceOptions.newBuilder()
                .setProjectId(projectId)
                .build()
                .getService();

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
