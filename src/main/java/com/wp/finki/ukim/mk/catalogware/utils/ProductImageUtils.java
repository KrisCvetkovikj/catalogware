package com.wp.finki.ukim.mk.catalogware.utils;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Borce on 03.09.2016.
 */
@Component
public class ProductImageUtils {

    @Value("${app.product.test-image}")
    private String path;

    public byte[] getBytes() {
        ClassLoader loader = this.getClass().getClassLoader();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        InputStream input = loader.getResourceAsStream(path);
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
