package com.wp.finki.ukim.mk.catalogware;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.slf4j.LoggerFactory.*;

@SpringBootApplication
@EnableWebMvc
public class CatalogwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogwareApplication.class, args);
	}
}
