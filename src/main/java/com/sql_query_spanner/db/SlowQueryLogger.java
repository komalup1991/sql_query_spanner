package com.sql_query_spanner.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SlowQueryLogger {
    private final Connection conn;

    public SlowQueryLogger(Connection conn) {
        this.conn = conn;
    }

    public List<String> getSlowQueries(double minExecutionTimeMs) throws SQLException {
        String sql = """
            SELECT query
            FROM pg_stat_statements
            WHERE total_exec_time > ?
            ORDER BY total_exec_time DESC
            LIMIT 10;
            """;
        List<String> slowQueries = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, minExecutionTimeMs);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                slowQueries.add(rs.getString("query"));
            }
        }
        return slowQueries;
    }
}