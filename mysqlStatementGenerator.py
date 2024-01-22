import json

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
        else:
            # Add more type mappings as needed
            mysql_type = column_type
        
        columns.append(f"{column_name} {mysql_type}")

    # Create the MySQL CREATE TABLE statement
    create_table_statement = f"CREATE TABLE {table_name} (\n\t{',\n\t'.join(columns)}\n);"
    
    return create_table_statement

# Your JSON schema
json_schema = [
    {"name": "oid", "type": "INTEGER"},
    {"name": "firstname", "type": "STRING"}
]

# Specify your desired table name
table_name = "your_table_name"

# Generate the MySQL CREATE TABLE statement
mysql_create_table_statement = generate_mysql_create_table(json_schema, table_name)

# Print the result
print(mysql_create_table_statement)
