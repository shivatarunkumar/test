import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

public class SqlServerDataflowJob {
    public static void main(String[] args) {
        PipelineOptions options = PipelineOptionsFactory.fromArgs(args).create();
        Pipeline pipeline = Pipeline.create(options);

        pipeline
            .apply(JdbcIO.<YourRowClass>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://your-sql-server-host:1433;databaseName=your-database"
                ).withUsername("your-username").withPassword("your-password"))
                .withQuery("SELECT * FROM your_table")
                .withCoder(YourRowClassCoder.of())
            )
            .apply(ParDo.of(new LogFn()));

        pipeline.run().waitUntilFinish();
    }

    static class LogFn extends DoFn<YourRowClass, Void> {
        @ProcessElement
        public void processElement(ProcessContext c) {
            YourRowClass record = c.element();
            System.out.println(record.toString());
        }
    }
}
