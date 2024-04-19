import com.google.api.services.bigquery.model.TableRow;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.options.*;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.joda.time.Duration;

public class DatabaseToBigQuery {
    public interface DatabaseToBigQueryOptions extends PipelineOptions {
        @Description("JDBC connection string")
        @Validation.Required
        ValueProvider<String> getJdbcConnectionString();
        void setJdbcConnectionString(ValueProvider<String> value);

        @Description("Database username")
        @Validation.Required
        ValueProvider<String> getDbUsername();
        void setDbUsername(ValueProvider<String> value);

        @Description("Database password")
        @Validation.Required
        ValueProvider<String> getDbPassword();
        void setDbPassword(ValueProvider<String> value);

        @Description("BigQuery output table")
        @Validation.Required
        String getOutputTable();
        void setOutputTable(String value);
    }

    public static void main(String[] args) {
        DatabaseToBigQueryOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(DatabaseToBigQueryOptions.class);

        Pipeline pipeline = Pipeline.create(options);

        PCollection<TableRow> rows = pipeline
            .apply("Read from Database", JdbcIO.<TableRow>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                    options.getJdbcConnectionString())
                    .withUsername(options.getDbUsername())
                    .withPassword(options.getDbPassword()))
                .withQuery("SELECT * FROM your_table")
                .withRowMapper(new JdbcIO.RowMapper<TableRow>() {
                    @Override
                    public TableRow mapRow(ResultSet resultSet) throws Exception {
                        TableRow row = new TableRow();
                        // Map database columns to TableRow fields
                        row.set("column1", resultSet.getString("column1"));
                        row.set("column2", resultSet.getString("column2"));
                        // Add more columns as needed
                        return row;
                    }
                }));

        rows.apply("Write to BigQuery", BigQueryIO.writeTableRows()
            .to(options.getOutputTable())
            .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
            .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND));

        pipeline.run();
    }
}
/*
java -cp your-dataflow-job.jar your.package.DatabaseToBigQuery \
    --jdbcConnectionString="jdbc:mysql://your-database-host:port/database" \
    --dbUsername=your_username \
    --dbPassword=your_password \
    --outputTable=your_project_id:your_dataset_id.your_output_table

*/
