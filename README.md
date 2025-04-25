# SQL Query Spanner

`SQL Query Spanner` is a command-line tool that connects to a PostgreSQL database, logs slow queries, analyzes them using `EXPLAIN`, and recommends indexes to improve performance.

## 🚀 Features

- Connect to a PostgreSQL database via JDBC
- Identify slow queries using `pg_stat_statements`
- Analyze execution plans with `EXPLAIN`
- Detect sequential scans and suggest indexes
- Export query insights and recommendations to a file
- CLI options for threshold, database URL, password, and output file

## 🛠️ Getting Started

### Prerequisites

- Java 17+
- Maven
- PostgreSQL (with `pg_stat_statements` enabled)

### Build

```bash
mvn clean package
```

### Run

```bash
java -jar target/sql_query_spanner-1.0-SNAPSHOT.jar   --url=jdbc:postgresql://localhost:5432/postgres   --password=yourpassword   --threshold=500   --output=recommendations.txt
```

## 🔧 CLI Options

| Option            | Description                                   | Required | Default               |
|-------------------|-----------------------------------------------|----------|------------------------|
| `-u`, `--url`     | Database connection URL                       | ✅       | —                      |
| `-p`, `--password`| Database password                             |          | —                      |
| `-t`, `--threshold`| Threshold in milliseconds for slow queries   |          | 500                    |
| `-o`, `--output`  | Output file for recommendations               |          | `recommendations.txt`  |

## 📈 Sample Output

```
📄 Query:
SELECT * FROM users WHERE email = 'test@example.com';

[EXPLAIN] Seq Scan on users ...
⚠️  Detected Sequential Scan. Consider indexing columns in WHERE clauses.

🔍 Recommendations:
- Add an index on `users(email)`
```

## 📌 Future Improvements

- Include query execution time in output
- Analyze more `EXPLAIN` metrics like cost and rows
- Add flags for username and host separately
- Interactive CLI or web UI for better UX
- Multi-database support (MySQL, SQLite)

---

> Built with ☕ by Komal