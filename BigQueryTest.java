import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryResponse;

public class BigQueryTest {

    public static void main(String[] args) throws Exception {
        // Set up the BigQuery client
        BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

        // Define your SQL query
        String query = "SELECT * FROM `your_project_id.your_dataset_id.your_table_id` WHERE your_condition;";

        // Set up the query job configuration
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

        // Set up the job ID
        JobId jobId = JobId.of("your_project_id", "your_job_id");

        // Build and run the job
        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        // Wait for the job to complete
        queryJob = queryJob.waitFor();

        // Check for errors
        if (queryJob == null) {
            throw new RuntimeException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }

        // Get the query results
        QueryResponse response = bigquery.getQueryResults(jobId);

        // Process the results
        // You can iterate through the rows and columns to extract data

        System.out.println("Query results:");

        for (List<Object> row : response.getValues()) {
            for (Object val : row) {
                System.out.print(val + "\t");
            }
            System.out.println();
        }
    }
}
