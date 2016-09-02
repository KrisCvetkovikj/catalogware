package com.wp.finki.ukim.mk;

import com.wp.finki.ukim.mk.catalogware.model.BaseModel;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.propertyeditors.InputStreamEditor;
import org.springframework.context.ApplicationContext;

import javax.persistence.Table;
import java.io.IOException;
import java.io.InputStream;
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
     * @param model   model to be truncated
     * @param <T>     model type
     */
    public static <T extends BaseModel> void resetTableAutoincrement(ApplicationContext context, Class<T> model) {
        DataSource dataSource = context.getBean(DataSource.class);
        try (Connection dbConnection = dataSource.getConnection()) {
            try (Statement statement = dbConnection.createStatement()) {
                String tableName = ((Table) model.getAnnotation(Table.class)).name();
                String sql = String.format("ALTER TABLE %s ALTER COLUMN id RESTART WITH 1;", tableName);
                statement.execute(sql);

                /* Delete all data from the table */
//                String sql = "SET REFERENTIAL_INTEGRITY FALSE;";
//                String sql = "BEGIN TRANSACTION;";
//                sql += String.format("DELETE FROM %s;", tableName);
//                sql += "COMMIT;";
//                sql += "SET REFERENTIAL_INTEGRITY TRUE;";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the content of the file with the given path.
     *
     * @param loader class loader from wich the path will be searched
     * @param file   file path
     * @return file content
     */
    public static byte[] readFile(ClassLoader loader, String file) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        InputStream input = loader.getResourceAsStream(file);
        try {
            while ((length = input.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out.toByteArray();
    }
}
