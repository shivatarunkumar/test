{%- macro my_macro(table_name) -%}
    {%- set column_name = table_column_mapping[table_name] if table_name in table_column_mapping else None -%}

    {%- if not column_name -%}
        {{ log("No column mapping found for table: " ~ table_name) -}}
        {{ return(None) -}}
    {%- endif -%}

    {%- set query = -%}
        SELECT COUNT(1) AS count_result
        FROM (
            SELECT {{ column_name }}
            FROM {{ ref(table_name) }}
        ) AS subquery
    {%- endset -%}

    {%- set result = execute(query) -%}

    {%- if result.rows -%}
        {%- set count_value = result.rows[0].count_result -%}
        {{ return(count_value) }}
    {%- else -%}
        {{ return(0) }}
    {%- endif -%}
{%- endmacro %}

{# Define the table-column mapping outside of the macro #}
{%- set table_column_mapping = {
    'table1': 'column1',
    'table2': 'column2',
    'table3': 'column3',
    # Add mappings for other tables here...
} -%}
