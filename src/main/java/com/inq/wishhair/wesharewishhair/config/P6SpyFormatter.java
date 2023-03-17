package com.inq.wishhair.wesharewishhair.config;

import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import java.util.Locale;

public class P6SpyFormatter extends JdbcEventListener implements MessageFormattingStrategy {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        StringBuilder sb = new StringBuilder();

        sql = format(category, sql);
        sb.append(category)
                .append(" -> ")
                .append("[쿼리 수행시간 = ")
                .append(elapsed).append("ms")
                .append(" | DB 커넥션 정보(Connection ID) = ")
                .append(connectionId)
                .append("]")
                .append(sql);

        return sb.toString();
    }

    private String format(String category, String sql) {
        if(sql ==null || sql.trim().equals("")) return sql;

        // Only format Statement, distinguish DDL And DML
        if (Category.STATEMENT.getName().equals(category)) {
            String tmpsql = sql.trim().toLowerCase(Locale.ROOT);
            if(tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment")) {
                sql = FormatStyle.DDL.getFormatter().format(sql);
            }else {
                sql = FormatStyle.BASIC.getFormatter().format(sql);
            }
        }

        return sql;
    }
}