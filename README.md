# test

{
  "jobName": "your-job-name",
  "gcsLocation": "gs://path/to/your/job/JAR/file",
  "region": "your-region",
  "parameters": {
    "key1": "value1",
    "key2": "value2"
  }
}

SELECT 
  JSON_EXTRACT_SCALAR(json_column, '$.inputData.id') AS id,
  JSON_EXTRACT_SCALAR(data_element, '$.a') AS a,
  JSON_EXTRACT_SCALAR(data_element, '$.b') AS b
FROM 
  your_table,
  UNNEST(JSON_EXTRACT_ARRAY(json_column, '$.inputData.data')) AS data_element;
