import json
from faker import Faker

fake = Faker()

def generate_mysql_create_table(json_schema, table_name):
    columns = []

    for column in json_schema:
        column_name = column["name"]
        column_type = column["type"].upper()

        # Map BigQuery types to MySQL types
        if column_type == "INTEGER":
            mysql_type = "INT"
        elif column_type == "STRING":
            mysql_type = "VARCHAR(255)"  # Adjust the length as needed
        elif column_type == "FLOAT":
            mysql_type = "FLOAT"
        elif column_type == "BOOLEAN":
            mysql_type = "BOOLEAN"
        elif column_type == "TIMESTAMP":
            mysql_type = "DATETIME"  # Adjust if necessary
        elif column_type == "DATE":
            mysql_type = "DATE"
        # Add more type mappings as needed
        else:
            # Default to VARCHAR for unsupported types
            mysql_type = "VARCHAR(255)"

        columns.append(f"{column_name} {mysql_type}")

    # Create the MySQL CREATE TABLE statement
    create_table_statement = f"CREATE TABLE {table_name} (\n\t{',\n\t'.join(columns)}\n);"

    return create_table_statement

def generate_mysql_insert_statements(json_schema, table_name, sample_records):
    insert_statements = []

    for record in sample_records:
        columns = []
        values = []

        for column in json_schema:
            column_name = column["name"]
            columns.append(column_name)

            value = record.get(column_name, "NULL")

            # Handle boolean values
            if column["type"].lower() == "boolean":
                if isinstance(value, bool):
                    value = str(value).upper()
                else:
                    value = "NULL"

            # Handle string and timestamp values
            elif column["type"].lower() in ["string", "timestamp", "date"]:
                if isinstance(value, str):
                    value = f"'{value}'"
                else:
                    value = "NULL"

            # Handle float values
            elif column["type"].lower() == "float":
                if isinstance(value, (int, float)):
                    value = str(value)
                else:
                    value = "NULL"

            # Handle integer values
            elif column["type"].lower() == "integer":
                if isinstance(value, int):
                    value = str(value)
                else:
                    value = "NULL"

            values.append(str(value))

        insert_statement = f"INSERT INTO {table_name} ({', '.join(columns)}) VALUES ({', '.join(values)});"
        insert_statements.append(insert_statement)

    return insert_statements

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
            elif column["type"].lower() == "boolean":
                record[column_name] = fake.boolean()
            elif column["type"].lower() == "float":
                record[column_name] = fake.random_float(min=1.0, max=1000.0)
            elif column["type"].lower() == "timestamp":
                record[column_name] = fake.date_time_this_decade()
            elif column["type"].lower() == "date":
                record[column_name] = fake.date_this_decade()
            # Add more type handling as needed

        sample_records.append(record)

    return sample_records

# Your JSON schema
json_schema = [
    {"name": "oid", "type": "INTEGER"},
    {"name": "firstname", "type": "STRING"},
    {"name": "is_active", "type": "BOOLEAN"},
    {"name": "price", "type": "FLOAT"},
    {"name": "registration_date", "type": "TIMESTAMP"},
    {"name": "birth_date", "type": "DATE"}
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
