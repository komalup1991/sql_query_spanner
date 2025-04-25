package com.sql_query_spanner.analyzer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExplainAnalyzer {

    private final Connection conn;

    public ExplainAnalyzer(Connection conn) {
        this.conn = conn;
    }

    // Analyze the query and return recommendations as a string
    public String analyze(String query) throws SQLException {
        StringBuilder recommendations = new StringBuilder();
        String explainSql = "EXPLAIN " + query;

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(explainSql);
            while (rs.next()) {
                String plan = rs.getString(1);
                recommendations.append("[EXPLAIN] ").append(plan).append("\n");

                if (plan.contains("Seq Scan")) {
                    recommendations.append("⚠️ Detected Sequential Scan. Consider indexing columns in WHERE clauses.\n");
                }
            }
        }
        return recommendations.toString();
    }
}