import json
from faker import Faker

fake = Faker()

def generate_random_sample_records(schema, num_records):
    sample_records = []
    
    for _ in range(num_records):
        record = {}
        for column in schema:
            column_name = column["name"]
            if column["type"].lower() == "integer":
                record[column_name] = fake.random_int(min=1, max=1000)
            elif column["type"].lower() == "string":
                record[column_name] = fake.first_name()
            # Add more type handling as needed
            
        sample_records.append(record)
    
    return sample_records

# Your JSON schema
json_schema = [
    {"name": "oid", "type": "INTEGER"},
    {"name": "firstname", "type": "STRING"}
]

# Specify your desired table name
table_name = "your_table_name"

# Generate the MySQL CREATE TABLE statement
mysql_create_table_statement = generate_mysql_create_table(json_schema, table_name)

# Generate random sample records
num_sample_records = 5
sample_records = generate_random_sample_records(json_schema, num_sample_records)

# Generate the MySQL INSERT statements
mysql_insert_statements = generate_mysql_insert_statements(json_schema, table_name, sample_records)

# Print the results
print("MySQL CREATE TABLE statement:")
print(mysql_create_table_statement)

print("\nGenerated sample records:")
print(sample_records)

print("\nMySQL INSERT statements:")
for insert_statement in mysql_insert_statements:
    print(insert_statement)
