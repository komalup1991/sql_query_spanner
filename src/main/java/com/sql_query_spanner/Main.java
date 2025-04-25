package com.sql_query_spanner;

import com.sql_query_spanner.analyzer.ExplainAnalyzer;
import com.sql_query_spanner.db.DatabaseManager;
import com.sql_query_spanner.db.SlowQueryLogger;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
public class Main implements Runnable {
    @Option(names = {"-u", "--url"}, description = "Database connection URL", required = true)
    private String dbUrl;

    @Option(names = {"-p", "--password"}, description = "Database password")
    private String dbPassword;

    @Option(names = {"-t", "--threshold"}, description = "Threshold for slow queries in milliseconds", defaultValue = "500")
    private double threshold;

    @Option(names = {"-o", "--output"}, description = "Output file for recommendations", defaultValue = "recommendations.txt")
    private String outputFile;

    @Override
    public void run() {
        try {
            String dbUser = "postgres";

            Connection conn = DatabaseManager.getConnection(dbUrl, dbUser, dbPassword);
            System.out.println("✅ Connected to the database.");

            SlowQueryLogger slowQueryLogger = new SlowQueryLogger(conn);
            ExplainAnalyzer explainAnalyzer = new ExplainAnalyzer(conn);

            System.out.println("🔍 Fetching slow queries...");
            List<String> slowQueries = slowQueryLogger.getSlowQueries(threshold);

            if (slowQueries.isEmpty()) {
                System.out.println("🎉 No slow queries found above the threshold.");
            } else {
                try (FileWriter writer = new FileWriter(new File(outputFile))) {
                    for (String query : slowQueries) {
                        System.out.println("\n📄 Analyzing query:\n" + query);
                        writer.write("📄 Query:\n" + query + "\n");

                        String recommendations = explainAnalyzer.analyze(query);
                        writer.write("🔍 Recommendations:\n" + recommendations + "\n\n");
                    }
                    System.out.println("✅ Recommendations written to: " + outputFile);
                } catch (IOException e) {
                    System.err.println("❌ Failed to write to the output file: " + e.getMessage());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database connection error: " + e.getMessage(), e);
        }
    }
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}