package com.wp.finki.ukim.mk;

import com.wp.finki.ukim.mk.catalogware.model.BaseModel;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.ApplicationContext;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Borce on 30.08.2016.
 */
public class TestUtils {

    /**
     * Truncate the models table to remove all table data and reset auto incrementing ids.
     *
     * @param context instance spring application context
     * @param model  model to be truncated
     * @param <T>     model type
     */
    public static <T extends BaseModel> void truncateModelTable(ApplicationContext context, Class<T> model) {
        DataSource dataSource = context.getBean(DataSource.class);
        try (Connection dbConnection = dataSource.getConnection()) {
            try (Statement statement = dbConnection.createStatement()) {
                String tableName = ((Table)model.getAnnotation(Table.class)).name();
                String sql = "SET REFERENTIAL_INTEGRITY FALSE;";
                sql += "BEGIN TRANSACTION;";
                sql += String.format("TRUNCATE TABLE %s;", tableName);
                sql += String.format("ALTER TABLE %s ALTER COLUMN id RESTART WITH 1;",
                        tableName);
                sql += "COMMIT;";
                sql += "SET REFERENTIAL_INTEGRITY TRUE;";
                statement.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
